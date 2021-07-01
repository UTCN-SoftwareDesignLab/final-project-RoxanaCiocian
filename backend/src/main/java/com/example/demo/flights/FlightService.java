package com.example.demo.flights;

import com.example.demo.flights.dto.FlightDTO;
import com.example.demo.flights.mapper.FlightMapper;
import com.example.demo.flights.model.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found: " + id));
    }

    public Flight findByDestination(String destination){
        return flightRepository.findByDestination(destination)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found at destination: " + destination));
    }



    public List<FlightDTO> allFlights(){
        return flightRepository.findAll()
                .stream().map(flightMapper::toDto)
                .collect(Collectors.toList());
    }

    public FlightDTO createFlight(FlightDTO flightDTO){
        return flightMapper.toDto(flightRepository.save(flightMapper.fromDto(flightDTO)));
    }

    public FlightDTO updateFlight(Long id, FlightDTO flightDTO){
        Flight flight = findById(id);

        flight.setDestination(flightDTO.getDestination());
        flight.setDateTime(flightDTO.getDateTime());
        flight.setAvailable_seats(flightDTO.getAvailable_seats());

        return flightMapper.toDto(flightRepository.save(flight));
    }

    public List<FlightDTO> noAvailableSeats(){
        return flightRepository.findAllByAvailable_seats()
                .stream().map(flightMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteAll(){
        flightRepository.deleteAll();
    }

    public void deleteFlight(Long id){
        flightRepository.deleteById(id);
    }
}

