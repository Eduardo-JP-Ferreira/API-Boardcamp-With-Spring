package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IntegrationTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private GameRepository gameRepository;

  @AfterEach
  public void cleanUpDatabase() {
    customerRepository.deleteAll();
    gameRepository.deleteAll();
  }

  @Test
  void givenRepeteadCPF_whenCreatingNewCustomer_thenThrowsError() {
    CustomerDTO dto = new CustomerDTO("joao", "12345678910");

    CustomerModel customer = new CustomerModel(dto);
    customerRepository.save(customer);

    HttpEntity<CustomerDTO> body = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
        "/customers",
        HttpMethod.POST,
        body,
        String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("This CPF Already Exist!", response.getBody());
  }

  @Test
  void givenValidDTOwhenCreatingNewCustomer() {
    CustomerDTO dto = new CustomerDTO("joao", "12345678910");

    HttpEntity<CustomerDTO> body = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
        "/customers",
        HttpMethod.POST,
        body,
        String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void givenRepeteadName_whenCreatingNewGame_thenThrowsError() {
    GameDTO dto = new GameDTO("Jogo",
        "http://www.imagem.com.br/banco_imobiliario.jpg", 1, (long) 2000);

    GameModel game = new GameModel(dto);
    gameRepository.save(game);

    HttpEntity<GameDTO> body = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
        "/games",
        HttpMethod.POST,
        body,
        String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("This name Already Exist!", response.getBody());
  }

  @Test
  void givenValidDTOwhenCreatingNewGame() {
    GameDTO dto = new GameDTO("Jogo",
        "http://www.imagem.com.br/banco_imobiliario.jpg", 1, (long) 2000);

    HttpEntity<GameDTO> body = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
        "/games",
        HttpMethod.POST,
        body,
        String.class);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }
}
