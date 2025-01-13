package com.lufthansa.tinyUrl.repository;

import com.lufthansa.tinyUrl.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByLongUrl(String longUrl);

    Optional<UrlEntity> findByShortUrl(String shortUrl);

    @Query("SELECT u FROM UrlEntity u where u.expirationTime <= :expirationTime  ")
    List<UrlEntity> getExpiredUrls(@Param("expirationTime") LocalDateTime expirationTime);
}
