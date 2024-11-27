package com.example.hotel.controller;

import com.example.hotel.model.Booking;
import com.example.hotel.model.Dates;
import com.example.hotel.model.Employee;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.DatesRepository;
import com.example.hotel.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

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
        // Lấy ngày bắt đầu và ngày kết thúc của tháng hiện tại
        LocalDate startOfMonth = getStartOfCurrentMonth();
        LocalDate endOfMonth = getEndOfCurrentMonth();
        // Lấy ngày bắt đầu và ngày kết thúc của năm hiện tại
        LocalDate startOfYear = getStartOfCurrentYear();
        LocalDate endOfYear = getEndOfCurrentYear();

        // Tính tổng doanh thu tuần
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startOfYear, endOfYear);
        // Tính tổng doanh thu tháng
        double totalRevenuemonth = bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth);
        //Tổng doanh thu năm
        double totalRevenueyear = bookingRepository.calculateTotalRevenue(startOfYear, endOfYear);

        // Đưa thông tin tổng doanh thu và khoảng thời gian vào model để hiển thị trên view
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
    @GetMapping("/admin/finaces/year")
    public String findYearlyRevenue(@RequestParam("year") int year, HttpSession session, Model model) {
        // Lưu year vào session
        session.setAttribute("selectedYear", year);
        LocalDate startOfMonth = LocalDate.of(year, 1, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate startOfYear = LocalDate.of(year, 1, 1); // Ngày 1 của năm đã chọn
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        // Tính tổng doanh thu tuần
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startOfYear, endOfYear);
        // Tính tổng doanh thu tháng
        double totalRevenuemonth1 = bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth)!= null
                ? bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth)
                : 0.0;
        //Tổng doanh thu năm
        double totalRevenueyear1 = bookingRepository.calculateTotalRevenue(startOfYear, endOfYear)!= null
                ? bookingRepository.calculateTotalRevenue(startOfYear, endOfYear)
                : 0.0;

        // Đưa thông tin tổng doanh thu và khoảng thời gian vào model để hiển thị trên view
        //hiện tháng
        model.addAttribute("totalRevenuemonth", totalRevenuemonth1);
        model.addAttribute("startOfMonth", startOfMonth);
        model.addAttribute("endOfMonth", endOfMonth);
        //hiện năm
        model.addAttribute("totalRevenueyear", totalRevenueyear1);
        model.addAttribute("startOfYear", startOfYear);
        model.addAttribute("endOfYear", endOfYear);
        // Lịch sử mặc định
        model.addAttribute("bookings", bookings);

        // Xử lý doanh thu năm theo năm được chọn
        model.addAttribute("selectedYear", year); // Truyền năm đã chọn vào model
        return "admin-finaces"; // Trả về view
    }
    @GetMapping("/admin/finaces/month")
    public String findMonthlyRevenue(@RequestParam("month") int month, HttpSession session, Model model) {
        // Lấy year từ session
        Integer year = (Integer) session.getAttribute("selectedYear");

        // Lấy ngày đầu tuần và cuối tuần đầu tiên của tháng

        // Xác định ngày bắt đầu và kết thúc của tháng
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        // Xác định ngày bắt đầu và kết thúc của năm
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        // Tính tổng doanh thu tuần
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startOfMonth, endOfMonth );
        // Lấy danh sách các đơn đặt phòng trong khoảng thời gian này
        // Tính tổng doanh thu tháng
        double totalRevenuemonth = bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth)!= null
                ? bookingRepository.calculateTotalRevenue(startOfMonth, endOfMonth)
                : 0.0;
        //Tổng doanh thu năm
        double totalRevenueyear = bookingRepository.calculateTotalRevenue(startOfYear, endOfYear)!= null
                ? bookingRepository.calculateTotalRevenue(startOfYear, endOfYear)
                : 0.0;

        // Đưa thông tin tổng doanh thu và khoảng thời gian vào model để hiển thị trên view
        //hiện tháng
        model.addAttribute("totalRevenuemonth", totalRevenuemonth);
        model.addAttribute("startOfMonth", startOfMonth);
        model.addAttribute("endOfMonth", endOfMonth);
        //hiện năm
        model.addAttribute("totalRevenueyear", totalRevenueyear);
        model.addAttribute("startOfYear", startOfYear);
        model.addAttribute("endOfYear", endOfYear);
        // Lịch sử mặc thang
        model.addAttribute("bookings", bookings);

        // Xử lý doanh thu năm theo năm được chọn
        model.addAttribute("selectedmonth", month);
        model.addAttribute("selectedYear", year);// Truyền năm đã chọn vào model
        return "admin-finaces"; // Trả về view
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
