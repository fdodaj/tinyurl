package com.lufthansa.TinyUrl.repository;

import com.lufthansa.TinyUrl.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);


}
