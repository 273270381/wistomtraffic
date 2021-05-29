package com.analysis.wisdomtraffic.been;

/**
 * @Author hejunfeng
 * @Date 14:25 2021/5/22 0022
 * @Description com.analysis.wisdomtraffic.been
 **/
public class Auth2User {
    private String token;
    private String refreshToken;
    private String tokenHead;
    private String expiresIn;

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
