package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.services.CustomerService;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  final CustomerService customerService;

  CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public ResponseEntity<Object> getCustomers() {
    return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getTweetById(@PathVariable("id") Long id) {
    Optional<Optional<CustomerModel>> customer = customerService.findById(id);

    if (!customer.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    return ResponseEntity.status(HttpStatus.OK).body(customer.get());
  }

  @PostMapping
  public ResponseEntity<Object> createTweet(@RequestBody @Valid CustomerDTO body) {
    Optional<CustomerModel> customer = customerService.save(body);

    if (!customer.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("This CPF Already Exist");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(customer);
  }
}
