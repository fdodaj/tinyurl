package com.lufthansa.TinyUrl.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClickActivity> clickActivities = new ArrayList<>();

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<ClickActivity> getClickActivities() {
        return clickActivities;
    }

    public void setClickActivities(List<ClickActivity> clickActivities) {
        this.clickActivities = clickActivities;
    }

    public UserEntity() {

    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // You can add other user properties like email, etc.
}
