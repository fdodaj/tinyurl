package com.lufthansa.TinyUrl.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "click_activity")
public class ClickActivity  {

    @EmbeddedId
    private ClickActivityId id;

    @ManyToOne
    @MapsId("userId")
    private UserEntity user;

    @ManyToOne
    @MapsId("urlId")
    private UrlEntity url;

    @Column(name = "click_count")
    private Long clickCount = 0L;

    public ClickActivityId getId() {
        return id;
    }

    public void setId(ClickActivityId id) {
        this.id = id;
    }

    public UrlEntity getUrl() {
        return url;
    }

    public void setUrl(UrlEntity url) {
        this.url = url;
    }


    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
