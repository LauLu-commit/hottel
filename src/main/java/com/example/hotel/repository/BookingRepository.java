package com.example.hotel.repository;

import com.example.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUsername(String username);
    long countByRoomType(String roomType);  // Cập nhật với kiểu String

    List<Booking> findByCheckInDate(LocalDate date);
}

