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

import com.api.boardcamp.dtos.customerDto;
import com.api.boardcamp.exceptions.ConflictException;
import com.api.boardcamp.exceptions.CustomerNotFoundException;
import com.api.boardcamp.models.customerModel;
import com.api.boardcamp.repositories.customerRepository;
import com.api.boardcamp.services.customerService;

@SpringBootTest
public class customersUnit {

    @InjectMocks
    private customerService customerService;

    @Mock
    private customerRepository customerRepository;

    @Test
    void givenUnregisteredCustomer_whenSearching_thenThrowsError() {
       
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, 
        () -> customerService.findById(1L));

        verify(customerRepository, times(1)).findById(any());
        assertNotNull(exception);
        assertEquals("This customer has not been found in the database", exception.getMessage());
    }

    @Test
    void givenRepeatedCustomer_whenCreatingCustomer_thenThrowsError() {

        customerDto customer = new customerDto("Name", "12345678921");
        doReturn(true).when(customerRepository).existsByCpf(any());

        ConflictException exception = assertThrows(ConflictException.class, 
        () -> customerService.save(customer));

        verify(customerRepository, times(1)).existsByCpf(any());
	    verify(customerRepository, times(0)).save(any());
	    assertNotNull(exception);
	    assertEquals("This customer already exists", exception.getMessage());
    }

    @Test
    void givenValidData_whenCreatingCustomer_thenCreatesCustomer() {

        customerDto customer = new customerDto("Name", "12345678921");
        doReturn(false).when(customerRepository).existsByCpf(any());
        customerModel newCustomer = new customerModel(customer);

        doReturn(newCustomer).when(customerRepository).save(any());

        customerModel result = customerService.save(customer);

        assertNotNull(result);
        verify(customerRepository, times(1)).existsByCpf(any());
        verify(customerRepository, times(1)).save(any());
        assertEquals(newCustomer, result);
    }
 }
    
