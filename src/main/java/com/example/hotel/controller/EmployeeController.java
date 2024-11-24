package com.example.hotel.controller;

import com.example.hotel.model.Booking;
import com.example.hotel.model.Dates;
import com.example.hotel.model.Employee;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.DatesRepository;
import com.example.hotel.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final BookingRepository bookingRepository;

    public EmployeeController(EmployeeService employeeService, BookingRepository bookingRepository) {
        this.employeeService = employeeService;
        this.bookingRepository = bookingRepository;
    }

    // Đường dẫn cho admin employees
    @GetMapping("/admin/employees")
    public String viewAllServiceRequests(Model model) {
        List<Employee> employees = employeeService.findAllEmployees();
        model.addAttribute("employees", employees); // Đảm bảo rằng danh sách dịch vụ được đưa vào model
        return "admin-employee"; // Tên view sẽ là admin-services.html
    }
    // Đường dẫn cho admin timsheets
    @GetMapping("/admin/timsheets")
    public String viewAllTimsheetsRequests(Model model) {
        List<Dates> dates = employeeService.findAllDataes();
        model.addAttribute("dates", dates); // Đảm bảo rằng danh sách dịch vụ được đưa vào model
        return "pages-timsheets"; // Tên view sẽ là admin-services.html
    }
    // Đường dẫn cho admin timsheets
    @GetMapping("/admin/finaces")
    public String findBookingsByWeek(Model model) {
        // Tính toán khoảng thời gian tuần hiện tại
        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(7);

        // Lấy danh sách đơn đặt phòng theo khoảng thời gian
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(oneWeekAgo, today);

        // Đưa danh sách đơn đặt phòng vào model để hiển thị trên view
        model.addAttribute("bookings", bookings);

        // Trả về tên view
        return "admin-finaces"; // View tương ứng là admin-finances.html
    }
}
