package com.api.boardcamp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.boardcamp.dtos.customerDto;
import com.api.boardcamp.models.customerModel;
import com.api.boardcamp.services.customerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/customers")
@RestController
public class customerController {

    final customerService customerService;

    customerController(customerService customerService) {
        this.customerService = customerService;
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById (@PathVariable("id") Long id) {
        customerModel customer = customerService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PostMapping()
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid customerDto dto) {
        customerModel customer = customerService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
       
}
