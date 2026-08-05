package com.abiramy.urlshortener.repository;

import com.abiramy.urlshortener.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
