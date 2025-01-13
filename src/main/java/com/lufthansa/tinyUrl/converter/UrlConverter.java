package com.lufthansa.tinyUrl.converter;

import com.lufthansa.tinyUrl.dto.UrlResponse;
import com.lufthansa.tinyUrl.entity.UrlEntity;
import org.springframework.stereotype.Component;

@Component
public class UrlConverter {

    public  UrlResponse convertToUrlResponse(UrlEntity urlEntity) {
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setLongUrl(urlEntity.getLongUrl());
        urlResponse.setShortUrl(urlEntity.getShortUrl());
        return urlResponse;
    }

//    public UrlEntity convertToUrlEntity(UrlResponse urlResponse) {
//        UrlEntity urlEntity = new UrlEntity();
//        urlEntity.setLongUrl(urlResponse.getLongUrl());
//        urlEntity.setShortUrl(urlResponse.getShortUrl());
//        return urlEntity;
//    }
}
