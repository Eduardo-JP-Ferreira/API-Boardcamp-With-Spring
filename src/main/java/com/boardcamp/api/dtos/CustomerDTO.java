package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

  public CustomerDTO(String string, String string2) {
  }

  @NotBlank(message = "Fild name is mandatory")
  private String name;

  @Size(min = 11, max = 11, message = "Fild cpf need to have 11 caracteres")
  @NotBlank(message = "Fild cpf is mandatory")
  private String cpf;
}
