package com.lufthansa.tinyUrl.converter;

import com.lufthansa.tinyUrl.dto.CreateShortUrlRequest;
import com.lufthansa.tinyUrl.dto.UrlDto;
import com.lufthansa.tinyUrl.entity.UrlEntity;
import com.lufthansa.tinyUrl.utils.UrlShortenerUtil;
import com.lufthansa.tinyUrl.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UrlConverter {

    @Value("${expiration-time}")
    private Long expirationTime;

    @Autowired
    private Utils utils;

    public UrlDto toDto(UrlEntity urlEntity) {
        UrlDto urlDto = new UrlDto();
        urlDto.setLongUrl(urlEntity.getLongUrl());
        urlDto.setShortUrl(urlEntity.getShortUrl());
        // @TODO et number of clicks
        return urlDto;
    }

    public UrlEntity toEntity(CreateShortUrlRequest input) {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setLongUrl(input.getLongUrl());
        urlEntity.setExpirationTime(LocalDateTime.now().plusSeconds(expirationTime));
        urlEntity.setStatus("ACTIVE");
        urlEntity.setUser(utils.getLoggedInUser());
        urlEntity.setShortUrl(UrlShortenerUtil.generateShortURL());
        return urlEntity;
    }


}
