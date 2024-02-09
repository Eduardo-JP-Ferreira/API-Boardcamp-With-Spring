package com.boardcamp.api;

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

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.exceptions.CustomerConflictException;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameConflictException;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.services.CustomerService;
import com.boardcamp.api.services.GameService;

@SpringBootTest
class UnitTests {

  @InjectMocks
  private CustomerService customerService;

  @InjectMocks
  private GameService gameService;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private GameRepository gameRepository;

  @Test
  void givenRepeteadCPF_whenCreatingNewCustomer_thenThrowsError() {
    CustomerDTO dto = new CustomerDTO("Joao", "12345678910");

    doReturn(true).when(customerRepository).existsByCpf(any());

    CustomerConflictException exception = assertThrows(CustomerConflictException.class,
        () -> customerService.save(dto));

    assertNotNull(exception);
    assertEquals("This CPF Already Exist!", exception.getMessage());
    verify(customerRepository, times(1)).existsByCpf(any());
    verify(customerRepository, times(0)).save(any());
  }

  @Test
  void givenWrongId_whenFindingNewCustomerById_thenThrowsError() {

    doReturn(Optional.empty()).when(customerRepository).findById(any());

    CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
        () -> customerService.findById(1L));

    assertNotNull(exception);
    assertEquals("Customer not found by this id!", exception.getMessage());
    verify(customerRepository, times(1)).findById(any());
  }

  @Test
  void givenRepeteadName_whenCreatingNewGame_thenThrowsError() {
    GameDTO dto = new GameDTO("Jogo", "http://www.imagem.com.br/banco_imobiliario.jpg", 1, 2000);

    doReturn(true).when(gameRepository).existsByName(any());

    GameConflictException exception = assertThrows(GameConflictException.class,
        () -> gameService.save(dto));

    assertNotNull(exception);
    assertEquals("This name Already Exist!", exception.getMessage());
    verify(gameRepository, times(1)).existsByName(any());
    verify(gameRepository, times(0)).save(any());
  }
}
