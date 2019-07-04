package com.yunche.novels.service;

import com.yunche.novels.vo.AuthUserVO;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @author yunche
 * @date 2019/04/04
 */
public interface AuthService {

    String getToken(MultiValueMap<String, String> params);

    AuthUserVO getUserInfo(String token);

    boolean checkIsExistsOpenId(String openId);

    boolean storeOpenIdByUser(String openId, Integer userId);

    String getUserNameByOpenId(String openId);
}
