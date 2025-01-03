package com.example.hotel.service;

import com.example.hotel.model.Booking;
import com.example.hotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {

        this.bookingRepository = bookingRepository;
    }

    public Booking saveBooking(Booking booking) {
        // Kiểm tra và lưu thông tin booking vào cơ sở dữ liệu
        return bookingRepository.save(booking);
    }


    public List<Booking> findByUsername(String username) {

        return bookingRepository.findByUsername(username);
    }

    public void deleteBooking(Long id) {

        bookingRepository.deleteById(id);
    }

    // Thêm phương thức này
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll(); // Giả định rằng bookingRepository đã có phương thức này
    }

    public long countRoomsByType(String roomType) {
        return bookingRepository.countByRoomType(roomType); // Cập nhật với kiểu String
    }
    //tính tổng boking theo khoang thoi gian
    public double calculateTotalRevenue(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startDate, endDate);

        // Tính tổng giá từ các booking
        return bookings.stream()
                .mapToDouble(Booking::getPrice) // Lấy giá từ từng Booking
                .sum();
    }
}
