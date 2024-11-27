package com.example.hotel.controller;

import com.example.hotel.model.Booking;
import com.example.hotel.repository.BookingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/finaces")
public class FinancesRequestController {
    private final BookingRepository bookingRepository;

    public FinancesRequestController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Lấy ngày bắt đầu (Thứ Hai) và ngày kết thúc (Chủ Nhật) của tuần hiện tại
    LocalDate startOfWeek = getStartOfCurrentWeek();
    LocalDate endOfWeek = getEndOfCurrentWeek();
    // Lấy ngày bắt đầu và ngày kết thúc của tháng hiện tại
    LocalDate startOfMonth = getStartOfCurrentMonth();
    LocalDate endOfMonth = getEndOfCurrentMonth();
    // Lấy ngày bắt đầu và ngày kết thúc của năm hiện tại
    LocalDate startOfYear = getStartOfCurrentYear();
    LocalDate endOfYear = getEndOfCurrentYear();
    // Hàm tính ngày bắt đầu của tuần hiện tại (Thứ Hai)
    private LocalDate getStartOfCurrentWeek() {
        LocalDate today = LocalDate.now();
        return today.with(java.time.DayOfWeek.MONDAY); // Đặt về Thứ Hai
    }

    // Hàm tính ngày kết thúc của tuần hiện tại (Chủ Nhật)
    private LocalDate getEndOfCurrentWeek() {
        LocalDate today = LocalDate.now();
        return today.with(java.time.DayOfWeek.SUNDAY); // Đặt về Chủ Nhật
    }
    // Hàm tính ngày bắt đầu của tháng hiện tại (Ngày 1 của tháng)
    private LocalDate getStartOfCurrentMonth() {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(1); // Ngày đầu tháng
    }
    // Hàm tính ngày kết thúc của tháng hiện tại (Ngày cuối tháng)
    private LocalDate getEndOfCurrentMonth() {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(today.lengthOfMonth()); // Ngày cuối tháng
    }
    // Hàm tính ngày bắt đầu của năm hiện tại (Ngày 1 tháng 1 của năm)
    private LocalDate getStartOfCurrentYear() {
        LocalDate today = LocalDate.now();
        return today.withDayOfYear(1); // Ngày 1 của năm
    }

    // Hàm tính ngày kết thúc của năm hiện tại (Ngày 31 tháng 12 của năm)
    private LocalDate getEndOfCurrentYear() {
        LocalDate today = LocalDate.now();
        return today.withDayOfYear(today.lengthOfYear()); // Ngày cuối của năm
    }
    @GetMapping("/week")
    public String findWeeklyRevenue1(Model model) {
        // Lấy danh sách các đơn đặt phòng trong tuần này
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startOfWeek, endOfWeek);
        double totalRevenue = bookingRepository.calculateTotalRevenue(startOfWeek, endOfWeek);
        // Tính tổng doanh thu tháng
        double totalRevenuemonth = bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth);
        //Tổng doanh thu năm
        double totalRevenueyear = bookingRepository.calculateTotalRevenue(startOfYear, endOfYear);
        // Thêm dữ liệu vào model
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("startDate", startOfWeek);
        model.addAttribute("endDate", endOfWeek);
        model.addAttribute("bookings", bookings);
        //hiện tháng
        model.addAttribute("totalRevenuemonth", totalRevenuemonth);
        model.addAttribute("startOfMonth", startOfMonth);
        model.addAttribute("endOfMonth", endOfMonth);
        //hiện năm
        model.addAttribute("totalRevenueyear", totalRevenueyear);
        model.addAttribute("startOfYear", startOfYear);
        model.addAttribute("endOfYear", endOfYear);

        return "admin-finaces"; // Trả về view
    }
    @GetMapping("/month")
    public String findMonthlyRevenue(Model model) {
        // Lấy danh sách các đơn trong tháng này
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startOfMonth, endOfMonth);
        double totalRevenue = bookingRepository.calculateTotalRevenue(startOfWeek, endOfWeek);
        // Tính tổng doanh thu tháng
        double totalRevenuemonth = bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth);
        //Tổng doanh thu năm
        double totalRevenueyear = bookingRepository.calculateTotalRevenue(startOfYear, endOfYear);
        // Thêm dữ liệu vào model
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("startDate", startOfWeek);
        model.addAttribute("endDate", endOfWeek);
        model.addAttribute("bookings", bookings);
        //hiện tháng
        model.addAttribute("totalRevenuemonth", totalRevenuemonth);
        model.addAttribute("startOfMonth", startOfMonth);
        model.addAttribute("endOfMonth", endOfMonth);
        //hiện năm
        model.addAttribute("totalRevenueyear", totalRevenueyear);
        model.addAttribute("startOfYear", startOfYear);
        model.addAttribute("endOfYear", endOfYear);

        return "admin-finaces"; // Trả về view
    }
    @GetMapping("/year")
    public String findYearlyRevenue(Model model) {
        /// Lấy danh sách các đơn đặt trong năm hiện tại
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startOfYear, endOfYear);
        double totalRevenue = bookingRepository.calculateTotalRevenue(startOfWeek, endOfWeek);
        // Tính tổng doanh thu tháng
        double totalRevenuemonth = bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth);
        //Tổng doanh thu năm
        double totalRevenueyear = bookingRepository.calculateTotalRevenue(startOfYear, endOfYear);
        // Thêm dữ liệu vào model
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("startDate", startOfWeek);
        model.addAttribute("endDate", endOfWeek);
        model.addAttribute("bookings", bookings);
        //hiện tháng
        model.addAttribute("totalRevenuemonth", totalRevenuemonth);
        model.addAttribute("startOfMonth", startOfMonth);
        model.addAttribute("endOfMonth", endOfMonth);
        //hiện năm
        model.addAttribute("totalRevenueyear", totalRevenueyear);
        model.addAttribute("startOfYear", startOfYear);
        model.addAttribute("endOfYear", endOfYear);

        return "admin-finaces"; // Trả về vie
    }
}
