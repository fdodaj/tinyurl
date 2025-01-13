package com.lufthansa.tinyUrl.dto;

import lombok.Builder;

@Builder
public class UrlResponse {

    String shortUrl;
    String longUrl;


    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public UrlResponse(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public UrlResponse() {
    }
}
