package com.lufthansa.TinyUrl.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "click_activity")
public class ClickActivity {
    @EmbeddedId
    private ClickActivityId id;

    @ManyToOne
    @MapsId("userId")
    @JsonBackReference
    private UserEntity user;

    @ManyToOne
    @MapsId("urlId")
    @JoinColumn(name = "url_id", insertable = false, updatable = false)
    @JsonBackReference
    private UrlEntity url;

    private Integer clickCount;

    private LocalDateTime clickTimestamp;

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public ClickActivityId getId() {
        return id;
    }

    public void setId(ClickActivityId id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UrlEntity getUrl() {
        return url;
    }

    public void setUrl(UrlEntity url) {
        this.url = url;
    }

    public LocalDateTime getClickTimestamp() {
        return clickTimestamp;
    }

    public void setClickTimestamp(LocalDateTime clickTimestamp) {
        this.clickTimestamp = clickTimestamp;
    }
}