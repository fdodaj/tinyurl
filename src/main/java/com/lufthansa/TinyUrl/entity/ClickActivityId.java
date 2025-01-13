package com.lufthansa.TinyUrl.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class ClickActivityId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "url_id")
    private Long urlId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }
}

