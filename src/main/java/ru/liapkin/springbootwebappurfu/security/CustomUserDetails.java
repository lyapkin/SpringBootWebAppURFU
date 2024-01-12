package ru.liapkin.springbootwebappurfu.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.liapkin.springbootwebappurfu.entity.User;

import java.util.Collection;
import java.util.stream.Collectors;


@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map((role -> new SimpleGrantedAuthority(role.getName())))
                .collect(Collectors.toList());
    }

    public long getId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
