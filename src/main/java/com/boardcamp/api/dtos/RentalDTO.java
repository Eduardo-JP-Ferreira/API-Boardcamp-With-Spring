package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RentalDTO {

  @NotNull(message = "Fild customerId is mandatory")
  private Long customerId;

  @NotNull(message = "Fild gameId is mandatory")
  private Long gameId;

  @NotNull(message = "Fild daysRented is mandatory")
  @Positive
  private Integer daysRented;
}
