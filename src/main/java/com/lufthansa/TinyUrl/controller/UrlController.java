package com.lufthansa.TinyUrl.controller;


import com.lufthansa.TinyUrl.dto.UrlResponse;
import com.lufthansa.TinyUrl.entity.UrlEntity;
import com.lufthansa.TinyUrl.service.UrlService;
import com.lufthansa.TinyUrl.utils.UrlNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getLongUrl(@PathVariable String shortUrl) {
        try {
            String longUrl = urlService.getLongUrl(shortUrl);
            return ResponseEntity.ok(longUrl);
        } catch (UrlNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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