package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTests {

  @Test
  void myFirstTest() {
    assertEquals(1, 1); // verifica se 1 é igual a 1
  }

}
