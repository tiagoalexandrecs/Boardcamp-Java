package com.api.boardcamp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.boardcamp.dtos.rentalDto;
import com.api.boardcamp.models.rentalModel;
import com.api.boardcamp.services.rentalService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/rentals")
public class rentalController {

    final rentalService rentalService;

    rentalController (rentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<Object> getRentals () {
        List<rentalModel> rents = rentalService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(rents);
    }

    @PostMapping("")
    public ResponseEntity<Object> createRent(@RequestBody @Valid rentalDto dto) {
        Optional<rentalModel> rent = rentalService.save(dto);
        if (rent.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(rent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }

    }

    @PutMapping("{id}/return")
    public ResponseEntity<Object> updateRent (@PathVariable Long id) {
        Optional<rentalModel> update = rentalService.update(id);
        if(update.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(update);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
    }
    
}
