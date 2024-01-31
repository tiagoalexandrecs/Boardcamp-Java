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

    
    @GetMapping("{id}")
    public ResponseEntity<Object> getCustomerById (@PathVariable("id") Long id) {
        Optional <customerModel> customer = customerService.findById(id);

        if(customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This customer does not exist");
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid customerDto dto) {
        Optional <customerModel> customer = customerService.save(dto);
        if (customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A customer with this Cpf is already registered");
        }
    }
       
}
