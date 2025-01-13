package com.lufthansa.TinyUrl.service;

import com.lufthansa.TinyUrl.converter.UrlConverter;
import com.lufthansa.TinyUrl.dto.UrlResponse;
import com.lufthansa.TinyUrl.entity.ClickActivity;
import com.lufthansa.TinyUrl.entity.ClickActivityId;
import com.lufthansa.TinyUrl.entity.UrlEntity;

import com.lufthansa.TinyUrl.entity.UserEntity;
import com.lufthansa.TinyUrl.repository.ClickActivityRepository;
import com.lufthansa.TinyUrl.repository.UrlRepository;
import com.lufthansa.TinyUrl.utils.UrlNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final ClickActivityRepository clickActivityRepository;
    private final UrlConverter urlConverter;

    @Value("${expiration-time:300}")
    private Long expirationTime;

    public UrlService(UrlRepository urlRepository, ClickActivityRepository clickActivityRepository, UrlConverter urlConverter) {
        this.urlRepository = urlRepository;
        this.clickActivityRepository = clickActivityRepository;
        this.urlConverter = urlConverter;
    }

    public UrlEntity save(String longUrl) {
        Optional<UrlEntity> optionalUrl = urlRepository.findByLongUrl(longUrl);
        if (optionalUrl.isPresent()) {
            UrlEntity existingUrl = optionalUrl.get();
            existingUrl.setExpirationTime(LocalDateTime.now().plusMinutes(10));
            return urlRepository.save(existingUrl);
        }

        UrlEntity url = new UrlEntity();
        url.setLongUrl(longUrl);
        url.setShortUrl(UrlShortenerUtil.generateShortURL());
        url.setExpirationTime(LocalDateTime.now().plusMinutes(10)); // Set expiration time to 10 minutes
        return urlRepository.save(url);
    }

    public String getLongUrl(String shortUrl) {
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found: " + shortUrl));

        if (urlEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new UrlNotFoundException("Short URL has expired: " + shortUrl);
        }

        return urlEntity.getLongUrl();
    }
    public List<UrlEntity> getAllUrls(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // Create a Pageable object using the page and size parameters
        Page<UrlEntity> urlPage = urlRepository.findAll(pageable);  // Fetch the page of UrlEntities
        return urlPage.getContent();  // Return the content of the page as a list
    }

    private void incrementClickCount(UrlEntity UrlEntity, Long userId) {
        ClickActivityId clickActivityId = new ClickActivityId();
        clickActivityId.setUserId(userId);
        clickActivityId.setUrlId(UrlEntity.getId());

        ClickActivity clickActivity = clickActivityRepository.findById(clickActivityId).orElseThrow();
        clickActivity.setClickCount(clickActivity.getClickCount() + 1);
        clickActivityRepository.save(clickActivity);
    }

//    public String renewUrlExpiration(String shortUrl, int expirationTime) {
//        UrlEntity optionalUrl = urlRepository.findByShortUrl(shortUrl);
//
//        if (optionalUrl != null) {
//            LocalDateTime newExpirationDate = LocalDateTime.now().plusSeconds(expirationTime);
//            optionalUrl.setExpirationTime(newExpirationDate);
//            urlRepository.save(optionalUrl);
//            return shortUrl;
//        } else {
//            throw new UrlNotFoundException("Short URL not found");
//        }
//    }

}
