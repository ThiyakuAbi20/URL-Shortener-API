package com.abiramy.urlshortener.dto.request;

public class UpdateShortUrlRequest {

    private String originalUrl;

    public UpdateShortUrlRequest() {
    }

    public UpdateShortUrlRequest(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}