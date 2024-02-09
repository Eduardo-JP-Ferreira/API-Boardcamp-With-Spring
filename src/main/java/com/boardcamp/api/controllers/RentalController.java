package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.services.RentalService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    RentalModel rental = rentalService.save(body);
    return ResponseEntity.status(HttpStatus.CREATED).body(rental);
  }

  @PutMapping("/{id}/return")
  public ResponseEntity<Object> updateRental(@PathVariable Long id) {
    RentalModel rental = rentalService.update(id);
    return ResponseEntity.status(HttpStatus.OK).body(rental);
  }
}
