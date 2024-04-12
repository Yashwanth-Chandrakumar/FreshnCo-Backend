package com.freshnco.backend.repository;

import com.freshnco.backend.model.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepo extends JpaRepository<Details, Long> {
}
