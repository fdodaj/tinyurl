package com.lufthansa.TinyUrl.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "click_activity")
public class ClickActivity  {

    @EmbeddedId
    private ClickActivityId id;

    @ManyToOne
    @JoinColumn(name = "url_id", nullable = false, insertable = false, updatable = false)
    private UrlEntity url;


    @Column(nullable = false)
    private Integer clickCount = 0;

    @ManyToOne
    @JoinColumn(name = "url_id", nullable = false, insertable = false, updatable = false)
    private UserEntity user;

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

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
