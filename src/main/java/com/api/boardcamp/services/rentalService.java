package com.api.boardcamp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.boardcamp.dtos.rentalDto;
import com.api.boardcamp.models.customerModel;
import com.api.boardcamp.models.gameModel;
import com.api.boardcamp.models.rentalModel;
import com.api.boardcamp.repositories.customerRepository;
import com.api.boardcamp.repositories.gamesRepository;
import com.api.boardcamp.repositories.rentalRepository;

@Service
public class rentalService {
    
    final rentalRepository rentalRepository;
    final gamesRepository gamesRepository;
    final customerRepository customerRepository;

    rentalService(rentalRepository rentalRepository, gamesRepository gamesRepository,
     customerRepository customerRepository) {
        this.rentalRepository = rentalRepository;
        this.gamesRepository = gamesRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<rentalModel> save(rentalDto dto) {
        boolean exists = gamesRepository.existsById(dto.getGameId());
        boolean exists2 = customerRepository.existsById(dto.getCustomerId());

        if (!exists || !exists2) {
            return Optional.empty();
        } else {
            Optional<gameModel> game = gamesRepository.findById(dto.getGameId());
            Optional <customerModel> customer = customerRepository.findById(dto.getCustomerId());
            Integer price = game.get().getPricePerDay() * dto.getDaysRented();
            rentalModel rent = new rentalModel(dto, game.get(), customer.get(), price );
            return Optional.of(rentalRepository.save(rent));
        }
    }


}
