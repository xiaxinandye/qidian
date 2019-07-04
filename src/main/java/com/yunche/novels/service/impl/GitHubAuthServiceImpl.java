package com.yunche.novels.service.impl;

import com.yunche.novels.mapper.AuthForGitHubMapper;
import com.yunche.novels.service.AuthService;
import com.yunche.novels.util.AuthHelper;
import com.yunche.novels.vo.AuthTokenVO;
import com.yunche.novels.vo.AuthUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yunche
 * @date 2019/04/04
 */
@Service
public class GitHubAuthServiceImpl implements AuthService {
    @Autowired
    private AuthForGitHubMapper gitHubMapper;

    private static final String GET_TOKEN_URL = "https://github.com/login/oauth/access_token";

    private static final String GET_USER_URL = "https://api.github.com/user";

    private static final String CLIENT_ID = "50d7f61132da7f8574a1";

    private static final String CLIENT_SECRET = "6779d154cfc44115e1f3607c0000085c5c1cf178";

//    private static final String REDIRECT_URI = "http://localhost:8080/oauth/github/callback";
private static final String REDIRECT_URI = "https://www.xiaxinandye.cn/oauth/github/callback";

    @Override
    public String getToken(MultiValueMap<String, String> params) {
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("redirect_uri", REDIRECT_URI);
        AuthTokenVO authTokenVO = AuthHelper.sendPostGetToken(GET_TOKEN_URL, params);
        String token = authTokenVO.getAccess_token();
        return token;
    }

    @Override
    public AuthUserVO getUserInfo(String token) {
       Map<String, String> map = new HashMap<>();
        map.put("access_token", token);
        return AuthHelper.sendGetToUser(GET_USER_URL, map);
    }

    @Override
    public boolean checkIsExistsOpenId(String openId) {
        return gitHubMapper.checkIsExists(openId) > 0;
    }

    @Override
    public boolean storeOpenIdByUser(String openId, Integer userId) {
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        return gitHubMapper.storeOpenIdByUser(openId, userId, timeStamp) > 0;
    }

    @Override
    public String getUserNameByOpenId(String openId) {
        return gitHubMapper.getUserNameByOpenId(openId);
    }
}
