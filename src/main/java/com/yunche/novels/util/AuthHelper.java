package com.yunche.novels.util;

import com.alibaba.fastjson.JSON;
import com.yunche.novels.vo.AuthQQOpenIdVO;
import com.yunche.novels.vo.AuthTokenVO;
import com.yunche.novels.vo.AuthUserVO;
import com.yunche.novels.vo.QQUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Auth授权工具类
 *
 * @author yunche
 * @date 2019/04/04
 */
public class AuthHelper {

    /**
     * 获取Token
     *
     * @param url    目的url
     * @param params post参数
     * @return
     */
    public static AuthTokenVO sendPostGetToken(String url, MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;

        //以表单的方式提交,接受json数据
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行http请求
        ResponseEntity<AuthTokenVO> response = client.exchange(url, method, requestEntity, AuthTokenVO.class);
        return response.getBody();
    }

    public static String sendGetToGetToken(String url, Map<String, String> params) {
        RestTemplate client = new RestTemplate();
        url += "?";
        for (String k : params.keySet()) {
            url += k + "=" + params.get(k) + "&";
        }
        url = url.substring(0, url.length() - 1);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        //需要对uri进行编码，否则会出错得到401错误
        URI uri = builder.build().encode().toUri();
        String result = client.getForObject(uri, String.class);
        //获取其中的token
        String token = result.split("&")[0].split("=")[1];
        return token;
    }

    public static String sendGetToGetOpenId(String url, Map<String, String> params) {
        RestTemplate client = new RestTemplate();
//        client.setInterceptors(Collections.singletonList(new AcceptJsonInterceptor()));
        url += "?";
        for (String k : params.keySet()) {
            url += k + "=" + params.get(k) + "&";
        }
        url = url.substring(0, url.length() - 1);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        //需要对uri进行编码，否则会出错得到401错误
        URI uri = builder.build().encode().toUri();

        String object = client.getForObject(url, String.class);
        String openId = object.split("\"openid\":")[1].split("\"")[1];
        return openId;
    }

    /**
     * 获取第三方用户信息
     *
     * @param url    第三方提供的用户api
     * @param params 请求参数如token
     * @return
     */
    public static AuthUserVO sendGetToUser(String url, Map<String, String> params) {
        RestTemplate client = new RestTemplate();
        url += "?";
        for (String k : params.keySet()) {
            url += k + "=" + params.get(k) + "&";
        }
        url = url.substring(0, url.length() - 1);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        //需要对uri进行编码，否则会出错得到401错误
        URI uri = builder.build().encode().toUri();
        AuthUserVO authUserVO = client.getForObject(uri, AuthUserVO.class);
        return authUserVO;
    }

    /**
     * 获取第三方QQ用户信息
     * @param url
     * @param params
     * @return
     */
    public static QQUserVO sendGetToQQUser(String url, Map<String,String> params) {
        RestTemplate client = new RestTemplate();
        client.setInterceptors(Collections.singletonList(new AcceptJsonInterceptor()));
        url += "?";
        for (String k : params.keySet()) {
            url += k + "=" + params.get(k) + "&";
        }
        url = url.substring(0, url.length() - 1);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        //需要对uri进行编码，否则会出错得到401错误
        URI uri = builder.build().encode().toUri();
        String result = client.getForObject(uri, String.class);
        QQUserVO authUserVO = JSON.parseObject(result, QQUserVO.class);
        return authUserVO;
    }
}
