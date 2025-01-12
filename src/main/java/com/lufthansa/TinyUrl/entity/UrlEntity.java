package com.lufthansa.TinyUrl.entity;

import com.lufthansa.TinyUrl.dto.UrlStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "url", schema = "public")
public class UrlEntity extends AuditEntity<String> {

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
    private List<ClickActivity> clickActivities;


}