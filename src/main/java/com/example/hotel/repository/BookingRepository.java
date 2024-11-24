package com.example.hotel.repository;

import com.example.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUsername(String username);
    long countByRoomType(String roomType);  // Cập nhật với kiểu String

    List<Booking> findByCheckInDate(LocalDate date);
    //cập theo tháng
    @Query("SELECT b FROM Booking b WHERE MONTH(b.checkInDate) = :month")
    List<Booking> findBookingsByMonth(@Param("month") int month);
    // Tìm các đơn đặt phòng theo khoảng thời gian
    List<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);
}

