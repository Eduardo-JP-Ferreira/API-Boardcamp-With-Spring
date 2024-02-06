package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GameDTO {

  @NotBlank(message = "Fild name is mandatory")
  private String name;

  @NotBlank(message = "Fild image is mandatory")
  private String image;

  @NotNull(message = "Fild stockTotal is mandatory")
  @Positive
  private Integer stockTotal;

  @NotNull(message = "Fild pricePerDay is mandatory")
  @Positive
  private Long pricePerDay;
}
