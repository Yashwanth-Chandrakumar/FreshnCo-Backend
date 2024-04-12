package com.freshnco.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freshnco.backend.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Userrepo extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.details d WHERE d.state = ?1")
    List<User> findByState(String state);

    Page<User> findAll(Pageable pageable);
}