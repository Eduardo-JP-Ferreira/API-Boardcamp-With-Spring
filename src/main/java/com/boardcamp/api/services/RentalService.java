package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
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

  public Optional<RentalModel> save(RentalDTO dto) {

    Optional<CustomerModel> customer = customerRepository.findById(dto.getCustomerId());
    Optional<GameModel> game = gameRepository.findById(dto.getGameId());
    if (!customer.isPresent() || !game.isPresent()) {
      return Optional.empty();
    }

    GameModel gameModel = game.get();
    CustomerModel customerModel = customer.get();

    List<RentalModel> gameRentals = rentalRepository.findByGameIdAndReturnDateIsNull(dto.getGameId());

    if (gameRentals.size() == gameModel.getStockTotal()) {
      return Optional.empty();
    }

    RentalModel rental = new RentalModel(dto, customerModel, gameModel);
    rental.setOriginalPrice(gameModel.getPricePerDay() * dto.getDaysRented());
    rental.setDelayFee(Long.valueOf(0));
    rental.setRentDate(LocalDate.now());

    return Optional.of(rentalRepository.save(rental));
  }

  public Optional<RentalModel> update(Long id) {

    Optional<RentalModel> rental = rentalRepository.findById(id);

    if (!rental.isPresent()) {
      return Optional.empty();
    }

    RentalModel rentalModel = rental.get();

    if (rentalModel.getReturnDate() != null) {
      return Optional.empty();
    }

    rentalModel.setReturnDate(LocalDate.now());

    long daysDifference = ChronoUnit.DAYS.between(rentalModel.getRentDate(), LocalDate.now());
    if (daysDifference > rentalModel.getDaysRented()) {
      rentalModel.setDelayFee((daysDifference - rentalModel.getDaysRented()) * rentalModel.getGame().getPricePerDay());
    }

    return Optional.of(rentalRepository.save(rentalModel));
  }
}
