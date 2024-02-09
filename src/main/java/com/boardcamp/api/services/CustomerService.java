package com.boardcamp.api.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.CustomerConflictException;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.models.CustomerModel;

@Service
public class CustomerService {

  final CustomerRepository customerRepository;

  CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<CustomerModel> findAll() {
    return customerRepository.findAll();
  }

  public CustomerModel findById(Long id) {
    return customerRepository.findById((id)).orElseThrow(
        () -> new CustomerNotFoundException("Customer not found by this id!"));
  }

  public CustomerModel save(CustomerDTO dto) {
    if (customerRepository.existsByCpf(dto.getCpf())) {
      throw new CustomerConflictException("This CPF Already Exist!");
    }
    CustomerModel user = new CustomerModel(dto);
    return customerRepository.save(user);
  }
}
