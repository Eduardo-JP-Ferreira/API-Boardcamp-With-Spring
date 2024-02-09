package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

  @NotBlank(message = "Fild name is mandatory")
  private String name;

  @Size(min = 11, max = 11, message = "Fild cpf need to have 11 caracteres")
  @NotBlank(message = "Fild cpf is mandatory")
  private String cpf;
}
