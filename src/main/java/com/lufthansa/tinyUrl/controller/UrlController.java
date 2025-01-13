package com.lufthansa.tinyUrl.controller;


import com.lufthansa.tinyUrl.entity.UrlEntity;
import com.lufthansa.tinyUrl.service.UrlService;
import com.lufthansa.tinyUrl.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/shorten")
    public ResponseEntity<UrlEntity> saveNewURL(@RequestParam(name = "url") String url) {
        UrlEntity savedUrlEntity = urlService.save(url);
        return ResponseEntity.ok(savedUrlEntity);
    }


    /**
     * Endpoint to retrieve the long URL based on the short URL.
     *
     * @param shortUrl The short URL to look up.
     * @return The long URL, or a 404 if not found.
     */
    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> click(@PathVariable String shortUrl) {
        String longUrl = urlService.getLongUrlAndIncrementClicks(shortUrl);
        if (longUrl.equals("User not found")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        } else if (longUrl.equals("Short URL not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }
        return ResponseEntity.ok(longUrl);
    }


    @GetMapping
    public ResponseEntity<List<UrlEntity>> getAllUrls(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        List<UrlEntity> UrlEntities = urlService.getAllUrls(page, size);
        return ResponseEntity.ok(UrlEntities);
    }

//    @PutMapping("/urls/renew")
//    public ResponseEntity<String> renewUrlExpiration(
//            @RequestParam String shortUrl,
//            @RequestParam int expirationTime) {
//        String renewedUrl = urlService.renewUrlExpiration(shortUrl, expirationTime);
//        return ResponseEntity.ok("Expiration renewed for URL: " + renewedUrl);
//    }
}
