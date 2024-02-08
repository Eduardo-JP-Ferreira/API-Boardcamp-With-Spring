package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.services.RentalService;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/rentals")
public class RentalController {

  final RentalService rentalService;

  RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @GetMapping
  public ResponseEntity<Object> getRentals() {
    return ResponseEntity.status(HttpStatus.OK).body(rentalService.findAll());
  }

  @PostMapping
  public ResponseEntity<Object> createRental(@RequestBody @Valid RentalDTO body) {
    Optional<RentalModel> rental = rentalService.save(body);

    if (!rental.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("CustomerId or GameId invalid");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(rental);
  }
}
