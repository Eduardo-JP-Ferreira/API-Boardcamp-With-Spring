package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RentalDTO {

  @NotNull(message = "Fild customerId is mandatory")
  private Long customerId;

  @NotNull(message = "Fild gameId is mandatory")
  private Long gameId;

  @NotNull(message = "Fild daysRented is mandatory")
  private Integer daysRented;
}
