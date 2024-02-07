package com.api.boardcamp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.api.boardcamp.dtos.gameDto;
import com.api.boardcamp.models.gameModel;
import com.api.boardcamp.repositories.gamesRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class gamesIntegrationTests {

     @Autowired
	private TestRestTemplate restTemplate;

    @Autowired
    private gamesRepository gamesRepository;

    @BeforeEach
    @AfterEach
    public void cleanUpDatabase() {
       gamesRepository.deleteAll();
    }  

    @Test
    void givenValidData_whenCreatingGame_thenCreatesGame() {
    // given
    gameDto game = new gameDto("Game","link da imagem",2, 4500);
    HttpEntity<gameDto> body = new HttpEntity<>(game);

    // when
    ResponseEntity<gameModel> response = restTemplate.exchange(
        "/games", 
        HttpMethod.POST,
        body,
        gameModel.class);
        

    // then
    assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
	  assertEquals(1, gamesRepository.count()); 
   }

   @Test
   void givenRepeatedGame_whenCreatingGame_thenThrowsError() {
    // given
    gameDto game = new gameDto("Game","link da imagem",2, 4500);
    gameModel newGame = new gameModel(game);
    gamesRepository.save(newGame);
    
    HttpEntity<gameDto> body = new HttpEntity<>(game);
    
    // when
    ResponseEntity<String> response = restTemplate.exchange(
        "/games",      // rota
        HttpMethod.POST, // método
        body,            // body da requisição
        String.class);   // tipo esperado da resposta
    
    // then
      // verifica status code
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode()); 
      // não criou nada novo no banco
    assertEquals(1, gamesRepository.count()); 
}

   @Test
   void givenGames_whenGetting_returnsList () {
    
    gameDto game = new gameDto("Game","link da imagem",2, 4500);
    gameModel newGame = new gameModel(game);
    gamesRepository.save(newGame);
    gameDto game2 = new gameDto("Game2","link da imagem 2",2, 4500);
    gameModel newGame2 = new gameModel(game2);
    gamesRepository.save(newGame2);

    ResponseEntity<gameModel[]> response = restTemplate.getForEntity("/games", gameModel[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, gamesRepository.count());
   }
    
}
