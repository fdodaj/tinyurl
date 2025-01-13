package com.lufthansa.TinyUrl.service;

import com.lufthansa.TinyUrl.converter.UrlConverter;
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

    @Value("${expiration-time:300}")
    private Long expirationTime;

    public UrlService(UrlRepository urlRepository, ClickActivityRepository clickActivityRepository) {
        this.urlRepository = urlRepository;
        this.clickActivityRepository = clickActivityRepository;
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
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByShortUrl(shortUrl);
        return urlEntityOptional.map(UrlEntity::getLongUrl).orElse(null);
    }

    public Optional<UrlEntity> findUrlByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    public void incrementClickCount(Long userId, Long urlId) {
        // Find if a ClickActivity already exists for this user and URL
        Optional<ClickActivity> clickActivityOptional = clickActivityRepository.findById(new ClickActivityId(userId, urlId));

        if (clickActivityOptional.isPresent()) {
            // If a record exists, increment the click count
            ClickActivity clickActivity = clickActivityOptional.get();
            clickActivity.setClickCount(clickActivity.getClickCount() + 1);
            clickActivityRepository.save(clickActivity);
        } else {
            // If no record exists, create a new ClickActivity record with a click count of 1
            ClickActivity newClickActivity = new ClickActivity();
            ClickActivityId clickActivityId = new ClickActivityId(userId, urlId);
            newClickActivity.setId(clickActivityId);
            newClickActivity.setClickCount(1L);

            // Set the related URL entity (optional, if needed for more associations)
            Optional<UrlEntity> urlEntityOptional = urlRepository.findById(urlId);
            urlEntityOptional.ifPresent(newClickActivity::setUrl);

            // Set the related user entity (optional, if needed for more associations)
            UserEntity userEntity = new UserEntity();  // You will need to fetch this from a UserRepository or service
            userEntity.setId(userId);
            newClickActivity.setUser(userEntity);

            // Save the new ClickActivity record
            clickActivityRepository.save(newClickActivity);
        }
    }



    public List<UrlEntity> getAllUrls(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // Create a Pageable object using the page and size parameters
        Page<UrlEntity> urlPage = urlRepository.findAll(pageable);  // Fetch the page of UrlEntities
        return urlPage.getContent();  // Return the content of the page as a list
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
