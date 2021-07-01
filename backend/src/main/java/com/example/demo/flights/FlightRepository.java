package com.example.demo.flights;

import com.example.demo.flights.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query(value = "select * from flight where flight.available_seats = 0", nativeQuery = true)
    List<Flight> findAllByAvailable_seats();

    Optional<Flight> findByDestination(String destination);

    Optional<Flight> findByDateTime(LocalDateTime date);
}

