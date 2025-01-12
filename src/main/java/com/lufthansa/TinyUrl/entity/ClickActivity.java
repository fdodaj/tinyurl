package com.lufthansa.TinyUrl.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "click_activity")
public class ClickActivity extends AuditEntity<String> {

    @EmbeddedId
    private ClickActivityId id;

    @ManyToOne
    @JoinColumn(name = "url_id", insertable = false, updatable = false)
    private UrlEntity url;

    @Column(nullable = false)
    private Integer clickCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;


}
