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
    public String findWeeklyRevenue(Model model) {
        // Lấy ngày bắt đầu (Thứ Hai) và ngày kết thúc (Chủ Nhật) của tuần hiện tại
        LocalDate startOfWeek = getStartOfCurrentWeek();
        LocalDate endOfWeek = getEndOfCurrentWeek();
        // Lấy ngày bắt đầu và ngày kết thúc của tháng hiện tại
        LocalDate startOfMonth = getStartOfCurrentMonth();
        LocalDate endOfMonth = getEndOfCurrentMonth();
        // Lấy ngày bắt đầu và ngày kết thúc của năm hiện tại
        LocalDate startOfYear = getStartOfCurrentYear();
        LocalDate endOfYear = getEndOfCurrentYear();

        // Tính tổng doanh thu tuần
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startOfWeek, endOfWeek);
        // Lấy danh sách các đơn đặt phòng trong khoảng thời gian này
        double totalRevenue = bookings.stream()
                .mapToDouble(Booking::getPrice)
                .sum();
        // Tính tổng doanh thu tháng
        double totalRevenuemonth = bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth);
        //Tổng doanh thu năm
        double totalRevenueyear = bookingRepository.calculateTotalRevenue(startOfYear, endOfYear);

        // Đưa thông tin tổng doanh thu và khoảng thời gian vào model để hiển thị trên view
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("startDate", startOfWeek);
        model.addAttribute("endDate", endOfWeek);
        //hiện tháng
        model.addAttribute("totalRevenuemonth", totalRevenuemonth);
        model.addAttribute("startOfMonth", startOfMonth);
        model.addAttribute("endOfMonth", endOfMonth);
        //hiện năm
        model.addAttribute("totalRevenueyear", totalRevenueyear);
        model.addAttribute("startOfYear", startOfYear);
        model.addAttribute("endOfYear", endOfYear);
        // Lịch sử mặc định
        model.addAttribute("bookings", bookings);

        // Trả về tên view
        return "admin-finaces"; // Tương ứng với file admin-finances.html
    }
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
}
