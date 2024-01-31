package com.api.boardcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.boardcamp.models.rentalModel;

@Repository
public interface rentalRepository  extends JpaRepository<rentalModel, Long>{
    boolean existsById( Long id);
}
