package com.lufthansa.tinyUrl.dto;

public class UrlDto {

    private String shortUrl;
    private String longUrl;
    private String numberOfClicks;

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

    public String getNumberOfClicks() {
        return numberOfClicks;
    }

    public void setNumberOfClicks(String numberOfClicks) {
        this.numberOfClicks = numberOfClicks;
    }
}
