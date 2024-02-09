package com.api.boardcamp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

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

import com.api.boardcamp.dtos.customerDto;
import com.api.boardcamp.dtos.gameDto;
import com.api.boardcamp.dtos.rentalDto;
import com.api.boardcamp.models.customerModel;
import com.api.boardcamp.models.gameModel;
import com.api.boardcamp.models.rentalModel;
import com.api.boardcamp.repositories.customerRepository;
import com.api.boardcamp.repositories.gamesRepository;
import com.api.boardcamp.repositories.rentalRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class rentalsIntegrationTest {
    
    @Autowired
	private TestRestTemplate restTemplate;

    @Autowired
    private gamesRepository gamesRepository;

    @Autowired
    private customerRepository customerRepository;

    @Autowired
    private rentalRepository rentalRepository;

    @BeforeEach
    @AfterEach
    public void cleanUpDatabase() {
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
        gamesRepository.deleteAll();
    }  

    @Test
    void givenUnregisteredGame_whenCreatingRent_thenThrowsErrors() {

        customerDto customer = new customerDto("Name", "12345678921");
        customerModel newCustomer = new customerModel(customer);
        customerRepository.save(newCustomer);
        rentalDto rent = new rentalDto(newCustomer.getId(), 1L, 3);

        HttpEntity<rentalDto> body = new HttpEntity<>(rent);

        // when
        ResponseEntity<String> response = restTemplate.exchange(
        "/rentals",      // rota
        HttpMethod.POST, // método
        body,            // body da requisição
        String.class);   // tipo esperado da resposta

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void givenUnregisteredCustomer_whenCreatingRent_thenThrowsErrors() {

        
        gameDto game = new gameDto("Game","link da imagem",2, 4500);
        gameModel newGame = new gameModel(game);
        gamesRepository.save(newGame);
        rentalDto rent = new rentalDto(1L, newGame.getId(), 3);

        HttpEntity<rentalDto> body = new HttpEntity<>(rent);

        // when
        ResponseEntity<String> response = restTemplate.exchange(
        "/rentals",      // rota
        HttpMethod.POST, // método
        body,            // body da requisição
        String.class);   // tipo esperado da resposta

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void givenValidData_whenCreatingRent_thenCreates() {
        
        gameDto game = new gameDto("Game","link da imagem",2, 4500);
        gameModel newGame = new gameModel(game);
        gamesRepository.save(newGame);
        customerDto customer = new customerDto("Name", "12345678921");
        customerModel newCustomer = new customerModel(customer);
        customerRepository.save(newCustomer);
        rentalDto rent = new rentalDto(newCustomer.getId(), newGame.getId(), 3);

        HttpEntity<rentalDto> body = new HttpEntity<>(rent);

        // when
        ResponseEntity<rentalModel> response = restTemplate.exchange(
        "/rentals",      // rota
        HttpMethod.POST, // método
        body,            // body da requisição
        rentalModel.class);   // tipo esperado da resposta

        assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
	    assertEquals(1, rentalRepository.count()); 
    }

    @Test
    void givenUnregisteredRent_whenFinishingIt_thenThrowsError () {


        ResponseEntity<String> response = restTemplate.exchange(
        "/rentals/1/return",      // rota
        HttpMethod.PUT, // método
        null,            // body da requisição
        String.class);   // tipo esperado da resposta

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 

    }

    @Test
    void givenValidData_whenRentingOverbookedGame_thenThrowsError() {

        customerDto customer = new customerDto("Name", "12345678921");
        customerModel newCustomer = new customerModel(customer);
        customerRepository.save(newCustomer);
        gameDto game = new gameDto("Game","link da imagem",1, 4500);
        gameModel newGame = new gameModel(game);
        gamesRepository.save(newGame);
        rentalDto rent = new rentalDto(newCustomer.getId(), newGame.getId(), 3);
        rentalModel newRent = new rentalModel(rent, newGame, newCustomer, 13500, LocalDate.now());
        rentalRepository.save(newRent);

        HttpEntity<rentalDto> body = new HttpEntity<>(rent);

        ResponseEntity<String> response = restTemplate.exchange(
        "/rentals",      // rota
        HttpMethod.POST, // método
        body,            // body da requisição
        String.class);   // tipo esperado da resposta

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    void givenOpenRental_whenReturning_thenReturnUpdatedRent () {

        customerDto customer = new customerDto("Name", "12345678921");
        customerModel newCustomer = new customerModel(customer);
        customerRepository.save(newCustomer);
        gameDto game = new gameDto("Game","link da imagem",1, 4500);
        gameModel newGame = new gameModel(game);
        gamesRepository.save(newGame);
        rentalDto rent = new rentalDto(newCustomer.getId(), newGame.getId(), 3);
        rentalModel newRent = new rentalModel(rent, newGame, newCustomer, 13500, LocalDate.now());
        rentalRepository.save(newRent);

        ResponseEntity<rentalModel> response = restTemplate.exchange(
            "/rentals/{id}/return",
            HttpMethod.PUT,
            null,
            rentalModel.class,
            newRent.getId());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getDelayFee());
        assertEquals(LocalDate.now(), response.getBody().getRentDate());
        assertEquals(LocalDate.now(), response.getBody().getReturnDate());
        assertEquals(1, rentalRepository.count());

    }

    @Test
    void givenValidOpenDelayedRental_whenReturningRental_thenReturnsUpdatedRentalWithFee() {
        

        customerDto customer = new customerDto("Name", "12345678921");
        customerModel newCustomer = new customerModel(customer);
        customerRepository.save(newCustomer);
        gameDto game = new gameDto("Game","link da imagem",1, 4500);
        gameModel newGame = new gameModel(game);
        gamesRepository.save(newGame);
        rentalDto rent = new rentalDto(newCustomer.getId(), newGame.getId(), 3);
       
        LocalDate today = LocalDate.now();
        int daysRented = 3;
        int daysLate = 2;
        int pricePerDay = 4500;
        LocalDate rentDate = today.minusDays(daysRented + daysLate);

        rentalModel newRent = new rentalModel(rent, newGame, newCustomer, 13500, rentDate);
        rentalRepository.save(newRent);


        

        ResponseEntity<rentalModel> response = restTemplate.exchange(
                "/rentals/{id}/return",
                HttpMethod.PUT,
                null,
                rentalModel.class,
                newRent.getId());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(daysLate * pricePerDay, response.getBody().getDelayFee());
        assertEquals(rentDate, response.getBody().getRentDate());
        assertEquals(today, response.getBody().getReturnDate());
        assertEquals(1, rentalRepository.count());
    }

    @Test
    void givenReturnedRent_whenReturningAgain_thenThrowsError() {

        customerDto customer = new customerDto("Name", "12345678921");
        customerModel newCustomer = new customerModel(customer);
        customerRepository.save(newCustomer);
        gameDto game = new gameDto("Game","link da imagem",1, 4500);
        gameModel newGame = new gameModel(game);
        gamesRepository.save(newGame);
        rentalDto rent = new rentalDto(newCustomer.getId(), newGame.getId(), 3);
       
        LocalDate today = LocalDate.now();
        int daysRented = 3;
        int daysLate = 2;
        LocalDate rentDate = today.minusDays(daysRented + daysLate);

        rentalModel newRent = new rentalModel(rent, newGame, newCustomer, 13500, rentDate);
        newRent.setReturnDate(today);
        rentalRepository.save(newRent);

        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals/{id}/return",
            HttpMethod.PUT,
            null,
            String.class,
            newRent.getId());

            assertNotNull(response.getBody());
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
            assertEquals("This rent has already been finalized!", response.getBody());
            assertEquals(1, rentalRepository.count());

    }



}
