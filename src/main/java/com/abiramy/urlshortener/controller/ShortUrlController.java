package com.abiramy.urlshortener.controller;

import com.abiramy.urlshortener.dto.request.CreateShortUrlRequest;
import com.abiramy.urlshortener.dto.request.UpdateShortUrlRequest;
import com.abiramy.urlshortener.dto.response.CreateShortUrlResponse;
import com.abiramy.urlshortener.dto.response.ShortUrlResponse;
import com.abiramy.urlshortener.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {

        this.shortUrlService = shortUrlService;
    }

    @PostMapping
    public ResponseEntity<CreateShortUrlResponse> createShortUrl(
            @RequestBody CreateShortUrlRequest request){

        CreateShortUrlResponse response =
                shortUrlService.createShortUrl(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping
    public ResponseEntity<List<ShortUrlResponse>> getAllUrls() {

        return ResponseEntity.ok(
                shortUrlService.getAllUrls()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Long id){

        shortUrlService.deleteUrl(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateShortUrlResponse> updateUrl(
            @PathVariable Long id,
            @RequestBody UpdateShortUrlRequest request) {

        CreateShortUrlResponse response =
                shortUrlService.updateUrl(id, request);

        return ResponseEntity.ok(response);
    }





}
