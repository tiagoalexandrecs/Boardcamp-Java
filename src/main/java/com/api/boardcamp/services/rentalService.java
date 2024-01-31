package com.api.boardcamp.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
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
            LocalDate date = LocalDate.now();
            Optional<gameModel> game = gamesRepository.findById(dto.getGameId());
            Optional <customerModel> customer = customerRepository.findById(dto.getCustomerId());
            Integer price = game.get().getPricePerDay() * dto.getDaysRented();
            rentalModel rent = new rentalModel(dto, game.get(), customer.get(), price , date);
            return Optional.of(rentalRepository.save(rent));
        }
    }

    public List<rentalModel> findAll() {
        List<rentalModel> rents = rentalRepository.findAll();
        return rents;
    }

    public Optional<rentalModel> update(Long id) {
        boolean exists = rentalRepository.existsById(id);
        if (exists) {
            Optional<rentalModel> rent = rentalRepository.findById(id);

            LocalDate now = LocalDate.now();
            Timestamp timestamp = Timestamp.valueOf(now.atStartOfDay());
            Long nowMili = timestamp.getTime();

            LocalDate rentDate = rent.get().getRentDate();
            Timestamp timestampRent = Timestamp.valueOf(rentDate.atStartOfDay());
            Long rentMili = timestampRent.getTime();

            Integer interval = rent.get().getDaysRented() * 86400000;

            Long difference = nowMili - rentMili;

            if (difference <= interval) {
                rentalModel updated = new rentalModel(rent.get(), now);
                updated.setId(id);
                return Optional.of(rentalRepository.save(updated));
            } else if ( difference > interval) {
                Long delay = difference - interval;
                Long days = delay / 86400000;
                Long debt = rent.get().getGame().getPricePerDay() * days;

                rentalModel updated = new rentalModel(rent.get(), now, debt);
                updated.setId(id);
                rentalModel done = rentalRepository.save(updated);
                return Optional.of(done);
            }

        } else {
            return Optional.empty();
        }
    }


}
