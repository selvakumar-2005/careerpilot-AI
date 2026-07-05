package com.careerpilot.dto;

import com.careerpilot.entity.Role;
import java.time.LocalDateTime;

public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private LocalDateTime createdAt;

    public UserDto() {}

    private UserDto(Builder b) {
        this.id        = b.id;
        this.fullName  = b.fullName;
        this.email     = b.email;
        this.role      = b.role;
        this.createdAt = b.createdAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String fullName;
        private String email;
        private Role role;
        private LocalDateTime createdAt;

        public Builder id(Long v)               { this.id = v; return this; }
        public Builder fullName(String v)       { this.fullName = v; return this; }
        public Builder email(String v)          { this.email = v; return this; }
        public Builder role(Role v)             { this.role = v; return this; }
        public Builder createdAt(LocalDateTime v){ this.createdAt = v; return this; }
        public UserDto build()                  { return new UserDto(this); }
    }

    public Long getId()                  { return id; }
    public void setId(Long v)            { this.id = v; }
    public String getFullName()          { return fullName; }
    public void setFullName(String v)    { this.fullName = v; }
    public String getEmail()             { return email; }
    public void setEmail(String v)       { this.email = v; }
    public Role getRole()                { return role; }
    public void setRole(Role v)          { this.role = v; }
    public LocalDateTime getCreatedAt()  { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
}
