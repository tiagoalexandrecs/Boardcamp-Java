package com.api.boardcamp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.boardcamp.dtos.gameDto;
import com.api.boardcamp.dtos.rentalDto;
import com.api.boardcamp.exceptions.CustomerNotFoundException;
import com.api.boardcamp.exceptions.GameNotFoundException;
import com.api.boardcamp.models.gameModel;
import com.api.boardcamp.repositories.customerRepository;
import com.api.boardcamp.repositories.gamesRepository;
import com.api.boardcamp.repositories.rentalRepository;
import com.api.boardcamp.services.rentalService;

@SpringBootTest
public class rentalsUnit {

    @InjectMocks
    private rentalService rentalService;

    @Mock
    private gamesRepository gamesRepository;

    @Mock
    private customerRepository customerRepository;

    @Mock
    private rentalRepository rentalRepository;

    @Test
    void givenUnregisteredGame_whenCreatingRent_thenThrowsErrors() {

        rentalDto rent = new rentalDto(1L, 2L, 3);
        doReturn(Optional.empty()).when(gamesRepository).findById(any());

        GameNotFoundException exception = assertThrows(GameNotFoundException.class, 
        () -> rentalService.save(rent));

        verify(gamesRepository, times(1)).findById(any());
        verify(gamesRepository,times(0)).save(any());
        assertNotNull(exception);
        assertEquals("This game has not been found in the stock!", exception.getMessage());
    }

    @Test
    void givenUnregisteredCustomer_whenCreatingRent_thenThrowsErrors() {

        rentalDto rent = new rentalDto(1L, 2L, 3);
        gameDto game = new gameDto("Game","link da imagem",2, 4500);
        gameModel newGame = new gameModel(game);
        
        doReturn(Optional.of(newGame)).when(gamesRepository).findById(any());
        doReturn(Optional.empty()).when(customerRepository).findById(any());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, 
        () -> rentalService.save(rent));

        verify(gamesRepository, times(1)).findById(any());
        verify(customerRepository, times(1)).findById(any());
        verify(rentalRepository,times(0)).save(any());
        assertNotNull(exception);
        assertEquals("This customer is not registered in the database!", exception.getMessage());
    }
    
}
