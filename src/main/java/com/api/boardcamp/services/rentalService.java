package com.api.boardcamp.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.boardcamp.dtos.rentalDto;
import com.api.boardcamp.exceptions.CustomerNotFoundException;
import com.api.boardcamp.exceptions.GameNotFoundException;
import com.api.boardcamp.exceptions.RentNotFoundException;
import com.api.boardcamp.exceptions.UnprocessableEntityException;
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

    public rentalModel save(rentalDto dto) {
        
            LocalDate date = LocalDate.now();
            gameModel game = gamesRepository.findById(dto.getGameId()).orElseThrow(() -> new GameNotFoundException("This game has not been found in the stock!"));
            customerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("This customer is not registered in the database!"));
            Integer price = game.getPricePerDay() * dto.getDaysRented();

            List<rentalModel> amount = rentalRepository.findByGameId(game.getId());
            if ( amount.size() > game.getStockTotal()) {
                throw new UnprocessableEntityException("Our stock has ran out of this game!Please, await until they're available again");
            } else {
                rentalModel rent = new rentalModel(dto, game, customer, price , date);
                return rentalRepository.save(rent);
            }
        }

    public List<rentalModel> findAll() {
        List<rentalModel> rents = rentalRepository.findAll();
        return rents;
    }

    public rentalModel update(Long id) {
            
            rentalModel rent = rentalRepository.findById(id).orElseThrow(() -> new RentNotFoundException("The rent register has not been found"));

            if (rent.getReturnDate() == null) {
                throw new UnprocessableEntityException("This rent has already been finalized!");
            } else {
                LocalDate now = LocalDate.now();
                Timestamp timestamp = Timestamp.valueOf(now.atStartOfDay());
                Long nowMili = timestamp.getTime();

                LocalDate rentDate = rent.getRentDate();
                Timestamp timestampRent = Timestamp.valueOf(rentDate.atStartOfDay());
                Long rentMili = timestampRent.getTime();

                Integer interval = rent.getDaysRented() * 86400000;

                Long difference = nowMili - rentMili;

                if (difference <= interval) {
                 rentalModel updated = new rentalModel(rent, now);
                updated.setId(id);
                return rentalRepository.save(updated);
               } else {
                  Long delay = difference - interval;
                  Long days = delay / 86400000;
                  Long debt = rent.getGame().getPricePerDay() * days;

                  rentalModel updated = new rentalModel(rent, now, debt);
                  updated.setId(id);
                  rentalModel done = rentalRepository.save(updated);
                  return (done);
               }

            }
            
        } 
    }

