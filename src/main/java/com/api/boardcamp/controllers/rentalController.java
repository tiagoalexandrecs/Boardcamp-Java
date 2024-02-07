package com.api.boardcamp.controllers;

import java.util.List;

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
            rentalModel rent = rentalService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(rent);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Object> updateRent (@PathVariable Long id) {
        rentalModel update = rentalService.update(id);
        return ResponseEntity.status(HttpStatus.OK).body(update);
        
    }
    
}
