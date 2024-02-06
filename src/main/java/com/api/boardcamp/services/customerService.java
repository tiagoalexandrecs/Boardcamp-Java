package com.api.boardcamp.services;

import org.springframework.stereotype.Service;

import com.api.boardcamp.dtos.customerDto;
import com.api.boardcamp.exceptions.ConflictException;
import com.api.boardcamp.exceptions.CustomerNotFoundException;
import com.api.boardcamp.models.customerModel;
import com.api.boardcamp.repositories.customerRepository;

@Service
public class customerService {
    final customerRepository customerRepository;

    customerService(customerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public customerModel findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("This customer has not been found in the database"));
    }

    public customerModel save (customerDto dto) {
        boolean exists = customerRepository.existsByCpf(dto.getCpf());
        if (exists) {
            throw new ConflictException("This customer already exists");
        } else {
            customerModel customer = new customerModel(dto);
            return customerRepository.save(customer);
    }

    }
}
