package com.example.demo.booking;

import com.example.demo.booking.dto.BookingDTO;
import com.example.demo.booking.model.Booking;
import com.example.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query( value = "select * from Booking booking where booking.user_id = ?1", nativeQuery = true )
    List <Booking> findByCustomerId(Long id);

    @Query( value = "select * from Booking booking where booking.destination = ?1", nativeQuery = true )
    List <Booking> findByDestination(String destination);
}
