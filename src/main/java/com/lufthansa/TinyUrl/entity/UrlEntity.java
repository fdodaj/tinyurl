package com.lufthansa.TinyUrl.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "url", schema = "public")
public class UrlEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 50)
    @Column(unique = true, nullable = false, name = "short_url")
    private String shortUrl;

    @NotBlank
    @Size(max = 255)
    @Column(name = "long_url", nullable = false)
    private String longUrl;

    @NotNull
    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ClickActivity> clickActivities = new ArrayList<>();

    public UrlEntity() {
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }



    public List<ClickActivity> getClickActivities() {
        return clickActivities;
    }

    public void setClickActivities(List<ClickActivity> clickActivities) {
        this.clickActivities = clickActivities;
    }
}