package com.lufthansa.TinyUrl.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ClickActivityId implements Serializable {
    private Long userId;
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

    public ClickActivityId(Long userId, Long urlId) {
        this.userId = userId;
        this.urlId = urlId;
    }

    public ClickActivityId() {

    }
}

