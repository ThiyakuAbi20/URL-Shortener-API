package com.abiramy.urlshortener.service;

import com.abiramy.urlshortener.dto.request.CreateShortUrlRequest;
import com.abiramy.urlshortener.dto.request.UpdateShortUrlRequest;
import com.abiramy.urlshortener.dto.response.CreateShortUrlResponse;
import com.abiramy.urlshortener.dto.response.ShortUrlResponse;
import com.abiramy.urlshortener.entity.ShortUrl;
import com.abiramy.urlshortener.exception.UrlNotFoundException;
import com.abiramy.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    public String redirectToOriginalUrl(String shortCode) {

        ShortUrl shortUrl = shortUrlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new UrlNotFoundException("Short URL not found."));

        shortUrl.setClickCount(
                shortUrl.getClickCount() + 1
        );

        shortUrlRepository.save(shortUrl);

        return shortUrl.getOriginalUrl();
    }


    public ShortUrlService (ShortUrlRepository shortUrlRepository){
        this.shortUrlRepository = shortUrlRepository;
    }

    private String generateShortCode() {

        String shortCode;

        do {

            Random random = new Random();

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < SHORT_CODE_LENGTH; i++) {

                int randomIndex = random.nextInt(CHARACTERS.length());

                builder.append(
                        CHARACTERS.charAt(randomIndex)
                );
            }

            shortCode =  builder.toString();
        } while (shortUrlRepository.findByShortCode(shortCode).isPresent());

        return shortCode;
    }

    public CreateShortUrlResponse createShortUrl(
            CreateShortUrlRequest request
    ) {

        if(request.getOriginalUrl() == null ||
        request.getOriginalUrl().isBlank()){

            throw new IllegalArgumentException(
                    "Original URL is required"
            );
        }

        String shortCode = generateShortCode();

        ShortUrl shortUrl = new ShortUrl(
                request.getOriginalUrl(),
                shortCode,
                request.getExpiresAt()
        );

        ShortUrl savedShortUrl = shortUrlRepository.save(shortUrl);

        return new CreateShortUrlResponse(
                savedShortUrl.getId(),
                savedShortUrl.getOriginalUrl(),
                savedShortUrl.getShortCode(),
                "http://localhost:8080/r/" + savedShortUrl.getShortCode(),
                "Short URL created successfully"
        );
    }

    public List<ShortUrlResponse> getAllUrls(){

                return shortUrlRepository.findAll()
                        .stream()
                        .map(shortUrl -> new ShortUrlResponse(
                                shortUrl.getId(),
                                shortUrl.getOriginalUrl(),
                                shortUrl.getShortCode(),
                                "http://localhost:8080/r/" + shortUrl.getShortCode(),
                                shortUrl.getClickCount(),
                                shortUrl.getCreatedAt()
                        ))
                        .toList();
    }

    public void deleteUrl(Long id) {

        ShortUrl shortUrl = shortUrlRepository
                .findById(id)
                .orElseThrow(()->
                        new UrlNotFoundException("Short URL not found."));

        shortUrlRepository.delete(shortUrl);
    }

    public CreateShortUrlResponse updateUrl(
            Long id,
            UpdateShortUrlRequest request) {

        ShortUrl shortUrl = shortUrlRepository
                .findById(id)
                .orElseThrow(() ->
                        new UrlNotFoundException("Short URL not found."));

        if (request.getOriginalUrl() == null ||
                request.getOriginalUrl().isBlank()) {

            throw new IllegalArgumentException(
                    "Original URL is required");
        }

        shortUrl.setOriginalUrl(request.getOriginalUrl());
        shortUrl.setExpiresAt(request.getExpiresAt());

        ShortUrl updatedShortUrl = shortUrlRepository.save(shortUrl);

        return new CreateShortUrlResponse(
                updatedShortUrl.getId(),
                updatedShortUrl.getOriginalUrl(),
                updatedShortUrl.getShortCode(),
                "http://localhost:8080/r/" + updatedShortUrl.getShortCode(),
                "Short URL updated successfully"
        );
    }

}
