package com.api.boardcamp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.boardcamp.models.customerModel;
import com.api.boardcamp.models.rentalModel;

@Repository
public interface rentalRepository  extends JpaRepository<rentalModel, Long>{
    boolean existsById( Long id);
    @Query(value = "SELECT * FROM rentals WHERE gameId= :gameId AND returnDate = null", nativeQuery = true)
    public List<rentalModel> findByGameId (@Param("gameId") Long id);
}
