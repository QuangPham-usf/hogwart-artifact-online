package edu.usf.cs.hogwart_artifact_online.User;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;
/*
Compare 2 encrypted password, user type and it encode by bscrypt then just compared to existed one
 */
public class MyUserPrincipal implements UserDetails {
    private Users users;

    public MyUserPrincipal(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Checked in the last step of authentication process, after password is correct, to check if the user has the required role to access the resource
        // Convert space-separated roles to GrantedAuthority list
        return Arrays.stream(users.getRoles().split(" "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return users.getEnabled();
    }

    public Users getUsers() {
        return users;
    }
}
