package com.api.boardcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.boardcamp.models.gameModel;


@Repository
public interface gamesRepository extends JpaRepository < gameModel, Long > {
    boolean existsByName(String name);
}
