package com.abiramy.urlshortener.service;

import com.abiramy.urlshortener.dto.request.CreateShortUrlRequest;
import com.abiramy.urlshortener.dto.response.CreateShortUrlResponse;
import com.abiramy.urlshortener.entity.ShortUrl;
import com.abiramy.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 6;


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

}
