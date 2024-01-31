package com.api.boardcamp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.boardcamp.dtos.gameDto;
import com.api.boardcamp.models.gameModel;
import com.api.boardcamp.services.gameServices;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/games")
public class gameController {
    
     final gameServices gameServices;

     gameController(gameServices gameServices) {
        this.gameServices = gameServices;
    }

    @PostMapping
    public ResponseEntity<Object> createGame(@RequestBody @Valid gameDto dto) {
        Optional<gameModel> game = gameServices.save(dto);
        if(game.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(game);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A game with this name already exists");
        }
    }

    @GetMapping
    public ResponseEntity<Object> getGames() {
        List <gameModel> games = gameServices.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(games);
    }
    
}
