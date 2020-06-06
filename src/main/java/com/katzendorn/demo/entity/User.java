package com.katzendorn.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, message = "более 2 знаков")
    private String username;

    @Size(min=2, message = "более 3 знаков")
    private String password;

    @Transient
    private String passwordConfirm; //для дублирующего ввода пароля.

    private String email;

    private int maxweight;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    //constructors
    public User() {}

    //this constructor required from rest controller, from create user without password
    public User(Long id, String username, String email, Set<Role> roles, int maxweight) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.maxweight = maxweight;
    }


    //getters and setters
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getMaxweight() {
        return maxweight;
    }

    public void setMaxweight(int maxweight) {
        this.maxweight = maxweight;
    }

    //create user without password
    public static User UserRest(User user) {
        return new User(user.getId(), user.getUsername(), user.getEmail(), user.getRoles(), user.getMaxweight());
    }

    //override methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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
