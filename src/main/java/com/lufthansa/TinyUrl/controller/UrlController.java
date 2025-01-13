package com.lufthansa.TinyUrl.controller;


import com.lufthansa.TinyUrl.entity.UrlEntity;
import com.lufthansa.TinyUrl.service.UrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    @Value("${expiration-time:300}")
    private long expirationTime;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlEntity> saveNewURL(@RequestParam(name = "url") String url){
        return ResponseEntity.ok(urlService.save(url));
    }



    /**
     * Endpoint to retrieve the long URL based on the short URL.
     *
     * @param shortUrl The short URL to look up.
     * @return The long URL, or a 404 if not found.
     */
    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getLongUrl(@PathVariable String shortUrl,
                                             @RequestParam Long userId) {
        // Retrieve the long URL from the service
        String longUrl = urlService.getLongUrl(shortUrl);
        if (longUrl != null) {
            // Increment the click count for this user and URL
            Optional<UrlEntity> urlEntityOptional = urlService.findUrlByShortUrl(shortUrl);
            if (urlEntityOptional.isPresent()) {
                Long urlId = urlEntityOptional.get().getId();
                urlService.incrementClickCount(userId, urlId); // Call incrementClickCount
                return ResponseEntity.ok(longUrl);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }
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
