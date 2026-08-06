package com.abiramy.urlshortener.repository;

import com.abiramy.urlshortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {


}
