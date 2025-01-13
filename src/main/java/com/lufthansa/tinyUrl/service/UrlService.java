package com.lufthansa.tinyUrl.service;

import com.lufthansa.tinyUrl.converter.UrlConverter;
import com.lufthansa.tinyUrl.dto.CreateShortUrlRequest;
import com.lufthansa.tinyUrl.dto.UrlDto;
import com.lufthansa.tinyUrl.entity.ClickActivity;
import com.lufthansa.tinyUrl.entity.ClickActivityId;
import com.lufthansa.tinyUrl.entity.UrlEntity;
import com.lufthansa.tinyUrl.entity.UserEntity;
import com.lufthansa.tinyUrl.repository.ClickActivityRepository;
import com.lufthansa.tinyUrl.repository.UrlRepository;
import com.lufthansa.tinyUrl.utils.UrlNotFoundException;
import com.lufthansa.tinyUrl.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final ClickActivityRepository clickActivityRepository;
    private final UrlConverter urlConverter;
    private final Utils utils;

    @Value("${expiration-time}")
    private Long expirationTime;


    public UrlService(UrlRepository urlRepository,
                      ClickActivityRepository clickActivityRepository,
                      UrlConverter urlConverter,
                      Utils utils) {
        this.urlRepository = urlRepository;
        this.clickActivityRepository = clickActivityRepository;
        this.urlConverter = urlConverter;
        this.utils = utils;
    }

    public UrlDto shortenUrl(CreateShortUrlRequest request) {
        UrlEntity urlEntity;
        Optional<UrlEntity> optionalUrl = urlRepository.findByLongUrl(request.getLongUrl());
        if (optionalUrl.isPresent()) {
            urlEntity = optionalUrl.get();
            urlEntity.setExpirationTime(LocalDateTime.now().plusSeconds(expirationTime));
            urlRepository.save(urlEntity);
            return urlConverter.toDto(urlEntity);
        } else {
            urlEntity = urlConverter.toEntity(request);
            UrlEntity saved = urlRepository.save(urlEntity);
            return urlConverter.toDto(saved);
        }
    }

    public UrlDto retrieveShortenUrl(String shortUrl) {
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl).orElseThrow(
                () -> new UrlNotFoundException("Url not found " + shortUrl));

        UserEntity loggedInUser = utils.getLoggedInUser();
        incrementClickCount(loggedInUser.getId(), urlEntity.getId());
        return urlConverter.toDto(urlEntity);
    }


    public void incrementClickCount(Long userId, Long urlId) {
        Optional<ClickActivity> clickActivityOptional = clickActivityRepository.findById(new ClickActivityId(userId, urlId));

        if (clickActivityOptional.isPresent()) {
            ClickActivity clickActivity = clickActivityOptional.get();
            clickActivity.setClickCount(clickActivity.getClickCount() + 1);
            clickActivityRepository.save(clickActivity);
        } else {
            ClickActivity newClickActivity = new ClickActivity();
            ClickActivityId clickActivityId = new ClickActivityId(userId, urlId);
            newClickActivity.setId(clickActivityId);
            newClickActivity.setClickCount(1);

            Optional<UrlEntity> urlEntityOptional = urlRepository.findById(urlId);
            urlEntityOptional.ifPresent(newClickActivity::setUrl);

            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            newClickActivity.setUser(userEntity);

            clickActivityRepository.save(newClickActivity);
        }
    }

    @Scheduled(fixedRateString = "${task.schedule.rate}")
    public void deleteExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        List<UrlEntity> expiredUrls = urlRepository.getExpiredUrls(now);
        expiredUrls.forEach(urlEntity -> urlEntity.setStatus("DELETED"));
        urlRepository.saveAll(expiredUrls);

    }

    public List<UrlEntity> getAllUrls(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UrlEntity> urlPage = urlRepository.findAll(pageable);
        return urlPage.getContent();
    }

    public UrlDto renewUrlExpiration(String shortUrl) {
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Url not found " + shortUrl));
        urlEntity.setExpirationTime(LocalDateTime.now().plusSeconds(expirationTime));
        urlRepository.save(urlEntity);
        return urlConverter.toDto(urlEntity);
    }
}
