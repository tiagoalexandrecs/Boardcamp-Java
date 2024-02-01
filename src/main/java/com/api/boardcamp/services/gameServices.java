package com.api.boardcamp.services;

import org.springframework.stereotype.Service;

import com.api.boardcamp.dtos.gameDto;
import com.api.boardcamp.exceptions.ConflictException;
import com.api.boardcamp.models.gameModel;
import com.api.boardcamp.repositories.gamesRepository;
import java.util.List;
import java.util.Optional;

@Service
public class gameServices {

    final gamesRepository gamesRepository;

    gameServices(gamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public List<gameModel> findAll() {
        return gamesRepository.findAll();
    }

    public gameModel save (gameDto dto) {
        boolean exists = gamesRepository.existsByName(dto.getName());
        if (exists) {
            throw new ConflictException("Such a game already exists in our stock");
        } else {
            gameModel game = new gameModel(dto);
            return gamesRepository.save(game);
        }
    }
    
}
