package com.yashwanth.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yashwanth.backend.model.User;

import java.util.Optional;

public interface Userrepo extends JpaRepository<User,Long> {
//    Optional<User>findByUsername(String username);
    User findByEmail(String email);
}
