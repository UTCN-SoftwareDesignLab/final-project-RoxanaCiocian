package com.example.demo.booking.mapper;

import com.example.demo.booking.dto.BookingDTO;
import com.example.demo.booking.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mappings({
            @Mapping(target="customerId", expression = "java(booking.getCustomerId().getId())"),
            @Mapping(target="destination", expression = "java(booking.getDestination().getDestination())"),
           // @Mapping(target="date", expression = "java(booking.getDate().getDateTime())"),
    })
    BookingDTO toDto(Booking booking);

    @Mappings({
            @Mapping(target = "customerId.id", source = "customerId"),
            @Mapping(target = "destination.destination", source = "destination"),
            //@Mapping(target = "date.dateTime", source = "date"),
    })
    Booking fromDto(BookingDTO bookingDTO);
}
