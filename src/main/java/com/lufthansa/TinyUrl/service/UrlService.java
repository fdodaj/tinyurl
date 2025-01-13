package com.lufthansa.TinyUrl.service;

import com.lufthansa.TinyUrl.converter.UrlConverter;
import com.lufthansa.TinyUrl.dto.RegisterRequest;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final ClickActivityRepository clickActivityRepository;
    private final UserService userService;

    @Value("${expiration-time:300}")
    private Long expirationTime;

    public UrlService(UrlRepository urlRepository, ClickActivityRepository clickActivityRepository, UserService userService) {
        this.urlRepository = urlRepository;
        this.clickActivityRepository = clickActivityRepository;
        this.userService = userService;
    }

    // Save a URL and associate it with the authenticated user
    public UrlEntity save(String longUrl) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty()) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUsername(username);
            registerRequest.setPassword("default_password");  // Set a default password or handle it appropriately
            userService.registerUser(registerRequest);

            userOptional = userService.findByUsername(username);
        }

        UserEntity user = userOptional.get();

        Optional<UrlEntity> optionalUrl = urlRepository.findByLongUrl(longUrl);
        if (optionalUrl.isPresent()) {
            UrlEntity existingUrl = optionalUrl.get();
            existingUrl.setExpirationTime(LocalDateTime.now().plusMinutes(10)); // Reset expiration time
            existingUrl.setUser(user); // Associate the user with the existing URL
            return urlRepository.save(existingUrl);
        }

        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setLongUrl(longUrl);
        urlEntity.setShortUrl(UrlShortenerUtil.generateShortURL());  // Generate the short URL
        urlEntity.setExpirationTime(LocalDateTime.now().plusMinutes(10));  // Set expiration time to 10 minutes
        urlEntity.setUser(user);  // Associate the user with the new URL

        return urlRepository.save(urlEntity);
    }

    // Method to get the long URL and increment the click count for the authenticated user
    public String getLongUrlAndIncrementClicks(String shortUrl) {
        // Get the authenticated username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Retrieve user by username
        Optional<UserEntity> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "User not found"; // If user doesn't exist, return error message
        }

        Long userId = userOptional.get().getId(); // Get user ID

        // Retrieve the long URL and increment click count
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByShortUrl(shortUrl);
        if (urlEntityOptional.isPresent()) {
            UrlEntity urlEntity = urlEntityOptional.get();
            String longUrl = urlEntity.getLongUrl();

            // Increment the click count
            incrementClickCount(userId, urlEntity.getId());

            return longUrl; // Return the long URL if found
        } else {
            return "Short URL not found"; // Return error message if URL not found
        }
    }

    // Method to find a URL by short URL
    public Optional<UrlEntity> findUrlByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    // Method to increment the click count for a specific URL and user
    public void incrementClickCount(Long userId, Long urlId) {
        Optional<ClickActivity> clickActivityOptional = clickActivityRepository.findById(new ClickActivityId(userId, urlId));

        if (clickActivityOptional.isPresent()) {
            ClickActivity clickActivity = clickActivityOptional.get();
            clickActivity.setClickCount(clickActivity.getClickCount() + 1);
            clickActivityRepository.save(clickActivity);
        } else {
            // If no record exists, create a new ClickActivity record with a click count of 1
            ClickActivity newClickActivity = new ClickActivity();
            ClickActivityId clickActivityId = new ClickActivityId(userId, urlId);
            newClickActivity.setId(clickActivityId);
            newClickActivity.setClickCount(1);

            Optional<UrlEntity> urlEntityOptional = urlRepository.findById(urlId);
            urlEntityOptional.ifPresent(newClickActivity::setUrl);

            UserEntity userEntity = new UserEntity();  // Fetch this from the UserRepository or service
            userEntity.setId(userId);
            newClickActivity.setUser(userEntity);

            clickActivityRepository.save(newClickActivity);
        }
    }

    // Method to get all URLs with pagination
    public List<UrlEntity> getAllUrls(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UrlEntity> urlPage = urlRepository.findAll(pageable);
        return urlPage.getContent();
    }
}
