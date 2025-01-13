package com.lufthansa.TinyUrl.repository;

import com.lufthansa.TinyUrl.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByLongUrl(String longUrl);

    Optional<UrlEntity> findByShortUrl(String shortUrl);


}
