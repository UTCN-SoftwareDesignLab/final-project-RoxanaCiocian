package com.example.demo.flights.mapper;

import com.example.demo.flights.dto.FlightDTO;
import com.example.demo.flights.model.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightDTO toDto(Flight flight);
    Flight fromDto(FlightDTO flightDTO);
}
