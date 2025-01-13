package com.lufthansa.tinyUrl.controller;


import com.lufthansa.tinyUrl.dto.CreateShortUrlRequest;
import com.lufthansa.tinyUrl.dto.UrlDto;
import com.lufthansa.tinyUrl.entity.UrlEntity;
import com.lufthansa.tinyUrl.service.UrlService;
import com.lufthansa.tinyUrl.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;
    private final UserService userService;

    @Value("${expiration-time:300}")
    private long expirationTime;

    public UrlController(UrlService urlService, UserService userService) {
        this.urlService = urlService;
        this.userService = userService;
    }

    /**
     * Shorten a long url
     *
     * @param request CreateShortUrlRequest
     * @return UrlDto
     */
    @PostMapping("/shorten")
    public ResponseEntity<UrlDto> shortenUrl(@RequestBody CreateShortUrlRequest request) {
        UrlDto shortenedUrl = urlService.shortenUrl(request);
        return ResponseEntity.ok(shortenedUrl);
    }


    /**
     * Retrieve a long url from a short input
     * @param shortUrl shortUrl
     * @return
     */
    @GetMapping("/{shortUrl}")
    public ResponseEntity<UrlDto> retrieveLongUrl(@PathVariable String shortUrl) {
        return ResponseEntity.ok(urlService.retrieveShortenUrl(shortUrl));
    }


    @GetMapping
    public ResponseEntity<List<UrlEntity>> getAllUrls(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        List<UrlEntity> UrlEntities = urlService.getAllUrls(page, size);
        return ResponseEntity.ok(UrlEntities);
    }

    @PutMapping("/urls/renew")
    public ResponseEntity<UrlDto> renewUrlExpiration(
            @RequestParam String shortUrl) {
        UrlDto urlDto = urlService.renewUrlExpiration(shortUrl);
        return ResponseEntity.ok(urlDto);
    }
}
