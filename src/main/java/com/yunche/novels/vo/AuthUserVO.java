package com.yunche.novels.vo;

/**
 * @author yunche
 * @date 2019/04/04
 */
public class AuthUserVO {

    /**
     * 用户第三方应用名
     */
    private String login;

    /**
     * 用户第三方唯一标识
     */
    private String id;

    /**
     * 用户第三方头像
     */
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

}
