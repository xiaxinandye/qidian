package com.yunche.novels.vo;

/**
 * @author yunche
 * @date 2019/04/04
 */
public class AuthTokenVO {

    private String access_token;

    private String token_type;

    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public AuthTokenVO() {
    }

    public AuthTokenVO(String access_token, String token_type, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.scope = scope;
    }
}
