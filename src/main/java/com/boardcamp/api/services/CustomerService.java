package com.boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.dtos.CustomerDTO;
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

  public Optional<Optional<CustomerModel>> findById(Long id) {
    Optional<CustomerModel> customer = customerRepository.findById((id));
    if (!customer.isPresent()) {
      return Optional.empty();
    }
    return Optional.of(customerRepository.findById(id));
  }

  public Optional<CustomerModel> save(CustomerDTO dto) {
    if (customerRepository.existsByCpf(dto.getCpf())) {
      return Optional.empty();
    }
    CustomerModel user = new CustomerModel(dto);
    return Optional.of(customerRepository.save(user));
  }
}
