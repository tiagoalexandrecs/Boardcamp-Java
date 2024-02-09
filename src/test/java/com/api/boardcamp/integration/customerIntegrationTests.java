package com.api.boardcamp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.api.boardcamp.models.customerModel;
import com.api.boardcamp.repositories.customerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class customerIntegrationTests {

    @Autowired
	private TestRestTemplate restTemplate;

    @Autowired
    private customerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void cleanUpDatabase() {
       customerRepository.deleteAll();
    }  

    @Test
    void givenUnregisteredCustomer_whenSearching_thenThrowsError() {
       
        ResponseEntity<String> response = restTemplate.getForEntity("/customers/1", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void givenValidData_whenCreatingCustomer_thenCreates() {
    // given
    customerDto customer = new customerDto("Name", "12345678921");

    HttpEntity<customerDto> body = new HttpEntity<>(customer);

    // when
    ResponseEntity<customerModel> response = restTemplate.exchange(
        "/customers", 
        HttpMethod.POST,
        body,
        customerModel.class);
        

    // then
    assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
	assertEquals(1, customerRepository.count()); 
   }

   @Test
   void givenRepeatedCustomer_whenCreatingCustomer_thenThrowsError() {
    // given
    customerDto customer = new customerDto("Name", "12345678921");
    customerModel newCustomer = new customerModel(customer);
  
    customerRepository.save(newCustomer);
    
    HttpEntity<customerDto> body = new HttpEntity<>(customer);
    
    // when
    ResponseEntity<String> response = restTemplate.exchange(
        "/customers",      // rota
        HttpMethod.POST, // método
        body,            // body da requisição
        String.class);   // tipo esperado da resposta
    
    // then
      // verifica status code
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode()); 
      // não criou nada novo no banco
    assertEquals(1, customerRepository.count()); 
}

@Test
void givenRegisteredCustomer_whenSearching_thenReturns() {

    customerDto customer = new customerDto("Name", "12345678921");
    customerModel newCustomer = new customerModel(customer);
  
    customerRepository.save(newCustomer);
   
    ResponseEntity<customerModel> response = restTemplate.getForEntity("/customers/"+newCustomer.getId(), customerModel.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(newCustomer,response.getBody());
}

    



    
}
