package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.exceptions.GameUnprocessableEntityException;
import com.boardcamp.api.exceptions.RentalNotFoundException;
import com.boardcamp.api.exceptions.RentalUnprocessableEntityException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@Service
public class RentalService {

  final RentalRepository rentalRepository;
  final CustomerRepository customerRepository;
  final GameRepository gameRepository;

  RentalService(RentalRepository rentalRepository, CustomerRepository customerRepository,
      GameRepository gameRepository) {
    this.rentalRepository = rentalRepository;
    this.customerRepository = customerRepository;
    this.gameRepository = gameRepository;
  }

  public List<RentalModel> findAll() {
    return rentalRepository.findAll();
  }

  public RentalModel save(RentalDTO dto) {

    CustomerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(
        () -> new CustomerNotFoundException("Customer not found by this id!"));

    GameModel game = gameRepository.findById(dto.getGameId()).orElseThrow(
        () -> new GameNotFoundException("Game not found by this id!"));

    List<RentalModel> gameRentals = rentalRepository.findByGameIdAndReturnDateIsNull(dto.getGameId());

    if (gameRentals.size() == game.getStockTotal()) {
      throw new GameUnprocessableEntityException("There is no games avaiable on the stock!");
    }

    RentalModel rental = new RentalModel(dto, customer, game);
    rental.setOriginalPrice(game.getPricePerDay() * dto.getDaysRented());
    rental.setDelayFee(Long.valueOf(0));
    rental.setRentDate(LocalDate.now());

    return rentalRepository.save(rental);
  }

  public RentalModel update(Long id) {

    RentalModel rental = rentalRepository.findById(id).orElseThrow(
        () -> new RentalNotFoundException("Rental not found by this id!"));

    if (rental.getReturnDate() != null) {
      throw new RentalUnprocessableEntityException("This rental is already finished");
    }

    rental.setReturnDate(LocalDate.now());

    long daysDifference = ChronoUnit.DAYS.between(rental.getRentDate(), LocalDate.now());
    if (daysDifference > rental.getDaysRented()) {
      rental.setDelayFee((daysDifference - rental.getDaysRented()) * rental.getGame().getPricePerDay());
    }

    return rentalRepository.save(rental);
  }
}
