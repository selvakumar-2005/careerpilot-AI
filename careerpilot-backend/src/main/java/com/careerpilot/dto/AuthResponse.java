package com.careerpilot.dto;

public class AuthResponse {

    private String token;
    private String tokenType;
    private UserDto user;

    public AuthResponse() {}

    private AuthResponse(Builder b) {
        this.token     = b.token;
        this.tokenType = b.tokenType;
        this.user      = b.user;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token;
        private String tokenType;
        private UserDto user;

        public Builder token(String v)     { this.token = v; return this; }
        public Builder tokenType(String v) { this.tokenType = v; return this; }
        public Builder user(UserDto v)     { this.user = v; return this; }
        public AuthResponse build()        { return new AuthResponse(this); }
    }

    public String getToken()           { return token; }
    public void setToken(String v)     { this.token = v; }
    public String getTokenType()       { return tokenType; }
    public void setTokenType(String v) { this.tokenType = v; }
    public UserDto getUser()           { return user; }
    public void setUser(UserDto v)     { this.user = v; }
}
