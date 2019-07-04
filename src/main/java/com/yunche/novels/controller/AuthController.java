package com.yunche.novels.controller;


import com.yunche.novels.bean.User;
import com.yunche.novels.service.UserService;
import com.yunche.novels.service.impl.GitHubAuthServiceImpl;
import com.yunche.novels.service.impl.QQAuthServiceImpl;
import com.yunche.novels.util.MD5Utils;
import com.yunche.novels.util.StringHelper;
import com.yunche.novels.vo.AuthUserVO;

import com.yunche.novels.vo.QQUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yunche
 * @date 2019/04/04
 */
@Controller
public class AuthController {

    @Autowired
    private GitHubAuthServiceImpl authService;

    @Autowired
    private QQAuthServiceImpl qqAuthService;

    @Autowired
    private UserService userService;

    @GetMapping("/oauth/github/callback")
    public String authorizeForGitHub(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("state", state);
        String token = authService.getToken(map);
        //获取用户在第三方的信息
        AuthUserVO userVO = authService.getUserInfo(token);
        String openId = userVO.getId();
        //注册该openId
        if(!authService.checkIsExistsOpenId(openId)) {
            User u = new User();
            String userName = userVO.getLogin();
            //确保用户的用户名唯一
            while (userService.IsExistsName(userName)) {
                userName += StringHelper.getRandomString(3);
            }
            u.setUserName(userName);
            //生成一个随机的一定长度的字符串并使用MD5加密，由于第三方的密码不可用，故随机。
            u.setPassword(MD5Utils.getMD5(StringHelper.getRandomString(16)));

            //注册用户
            if(userService.insertUser(u)) {
                //将本地用户与OpenId相关联
                if(authService.storeOpenIdByUser(openId, u.getId())) {
                    //存储用户session
                    session.setAttribute("user", u.getUserName());
                }
            }
        }
        else {
            session.setAttribute("user", authService.getUserNameByOpenId(openId));
        }
        // 重定向到之前需要授权的页面
        return "redirect:" + state;
    }

    @GetMapping("/oauth/qq/callback")
    public String authorizeForQQ(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("state", state);
        String token = qqAuthService.getToken(map);
        //获取用户在第三方的openID
        String openId = qqAuthService.getQQOpenId(token);

        //注册该openId
        if(!qqAuthService.checkIsExistsOpenId(openId)) {
            QQUserVO qqUserVO = qqAuthService.getUserInfo(token, openId);
            String userName = qqUserVO.getNickname();
            User u = new User();
            //确保用户的用户名唯一
            while (userService.IsExistsName(userName)) {
                userName += StringHelper.getRandomString(3);
            }
            u.setUserName(userName);
            //生成一个随机的一定长度的字符串并使用MD5加密，由于第三方的密码不可用，故随机。
            u.setPassword(MD5Utils.getMD5(StringHelper.getRandomString(16)));

            //注册用户
            if(userService.insertUser(u)) {
                //将本地用户与OpenId相关联
                if(qqAuthService.storeOpenIdByUser(openId, u.getId())) {
                    //存储用户session
                    session.setAttribute("user", u.getUserName());
                }
            }
        }
        else {
            session.setAttribute("user", qqAuthService.getUserNameByOpenId(openId));
        }
        // 重定向到之前需要授权的页面
        return "redirect:" + state;
    }
}
