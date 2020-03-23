package org.theeric.auth.dto;

public class AuthToken {

    private final String access_token;

    private final String token_type = "Bearer";

    public AuthToken(String token) {
        this.access_token = token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

}
