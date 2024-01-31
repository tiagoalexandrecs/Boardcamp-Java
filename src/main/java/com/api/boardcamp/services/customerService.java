package com.api.boardcamp.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.api.boardcamp.dtos.customerDto;
import com.api.boardcamp.models.customerModel;
import com.api.boardcamp.repositories.customerRepository;

@Service
public class customerService {
    final customerRepository customerRepository;

    customerService(customerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional <customerModel> findById(Long id) {
        boolean exists = customerRepository.existsById(id);
        if (!exists) {
            return Optional.empty();
        }
        return customerRepository.findById(id);
    }

    public Optional<customerModel> save (customerDto dto) {
        boolean exists = customerRepository.existsByCpf(dto.getCpf());
        if (exists) {
            return Optional.empty();
        } else {
            customerModel customer = new customerModel(dto);
            return Optional.of(customerRepository.save(customer));
        }
    }






}
