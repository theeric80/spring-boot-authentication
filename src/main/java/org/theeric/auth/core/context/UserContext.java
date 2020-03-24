package org.theeric.auth.core.context;

public class UserContext {

    private Long userId;

    private String token;

    public UserContext() {
        this(null, "");
    }

    public UserContext(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
