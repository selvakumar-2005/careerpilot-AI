package com.careerpilot.security;

import com.careerpilot.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String fullName;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(User user) {
        this.id          = user.getId();
        this.fullName    = user.getFullName();
        this.email       = user.getEmail();
        this.password    = user.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    public static UserPrincipal from(User user) {
        return new UserPrincipal(user);
    }

    public Long getId()         { return id; }
    public String getFullName() { return fullName; }
    public String getEmail()    { return email; }

    @Override public String getUsername()                         { return email; }
    @Override public String getPassword()                         { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public boolean isAccountNonExpired()                { return true; }
    @Override public boolean isAccountNonLocked()                 { return true; }
    @Override public boolean isCredentialsNonExpired()            { return true; }
    @Override public boolean isEnabled()                          { return true; }
}
