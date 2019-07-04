package com.yunche.novels.service.impl;

import com.yunche.novels.mapper.AuthForQQMapper;
import com.yunche.novels.service.AuthService;
import com.yunche.novels.util.AuthHelper;
import com.yunche.novels.vo.AuthTokenVO;
import com.yunche.novels.vo.AuthUserVO;
import com.yunche.novels.vo.QQUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class QQAuthServiceImpl implements AuthService {
    @Autowired
    private AuthForQQMapper qqMapper;

    private static final String GET_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    private static final String GET_OPEN_ID = "https://graph.qq.com/oauth2.0/me";

    private static final String GET_USER_URL = "https://graph.qq.com/user/get_user_info";

    private static final String CLIENT_ID = "101570628";

    private static final String CLIENT_SECRET = "dfd675ba76487505aede850d7e8a395e";

//    private static final String REDIRECT_URI = "http://localhost:8080/oauth/qq/callback";
    private static final String REDIRECT_URI = "https://www.xiaxinandye.cn/oauth/qq/callback";

    private static final String GRANT_TYPE = "authorization_code";


    public String getToken(Map<String, String> params) {
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("grant_type", GRANT_TYPE);
        String token = AuthHelper.sendGetToGetToken(GET_TOKEN_URL, params);
        return token;
    }

    public String getQQOpenId(String token) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", token);
        String openId = AuthHelper.sendGetToGetOpenId(GET_OPEN_ID, params);
        return openId;
    }

    @Override
    public String getToken(MultiValueMap<String, String> params) {
        return null;
    }

    @Override
    public AuthUserVO getUserInfo(String token) {
        return null;
    }


    public QQUserVO getUserInfo(String token, String openId) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", token);
        map.put("oauth_consumer_key", CLIENT_ID);
        map.put("openid", openId);
        return AuthHelper.sendGetToQQUser(GET_USER_URL, map);
    }

    @Override
    public boolean checkIsExistsOpenId(String openId) {
        return qqMapper.checkIsExists(openId) > 0;
    }

    @Override
    public boolean storeOpenIdByUser(String openId, Integer userId) {
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        return qqMapper.storeOpenIdByUser(openId, userId, timeStamp) > 0;
    }

    @Override
    public String getUserNameByOpenId(String openId) {
        return qqMapper.getUserNameByOpenId(openId);
    }
}
