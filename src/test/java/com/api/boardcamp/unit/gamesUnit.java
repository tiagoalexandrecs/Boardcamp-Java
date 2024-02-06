package com.api.boardcamp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.boardcamp.dtos.gameDto;
import com.api.boardcamp.exceptions.ConflictException;
import com.api.boardcamp.models.gameModel;
import com.api.boardcamp.repositories.gamesRepository;
import com.api.boardcamp.services.gameServices;

@SpringBootTest
public class gamesUnit {

    @InjectMocks
    private gameServices gameServices;

    @Mock
    private gamesRepository gamesRepository;

    @Test
    void givenRepeatedGame_whenCreatingGame_thenThrowsError() {

        gameDto game = new gameDto("Game","link da imagem",2, 4500);
        doReturn(true).when(gamesRepository).existsByName(any());

        ConflictException exception = assertThrows(ConflictException.class, 
        () -> gameServices.save(game));

        verify(gamesRepository, times(1)).existsByName(any());
	    verify(gamesRepository, times(0)).save(any());
	    assertNotNull(exception);
	    assertEquals("Such a game already exists in our stock", exception.getMessage());
    }

    @Test
    void givenValidData_whenCreatingGame_thenCreatesGame() {

        
        gameDto game = new gameDto("Game","link da imagem",2, 4500);
        doReturn(false).when(gamesRepository).existsByName(any());
        gameModel newGame = new gameModel(game);
        
        doReturn(newGame).when(gamesRepository).save(any());
        gameModel result = gameServices.save(game);
        
        assertNotNull(result);
        verify(gamesRepository, times(1)).existsByName(any());
        verify(gamesRepository, times(1)).save(any());
        assertEquals(newGame, result);
    }
    
}
