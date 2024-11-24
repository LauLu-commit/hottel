package com.example.hotel.controller;

import com.example.hotel.model.Booking;
import com.example.hotel.model.Dates;
import com.example.hotel.model.Employee;
import com.example.hotel.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeRequestController {
    private final EmployeeService employeeService;

    public EmployeeRequestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // Xem tất cả yêu cầu dịch vụ (dành cho admin)
    @GetMapping("/all")
    public String viewAllEmpolyeeRequests(Model model) {
        List<Employee> employees = employeeService.findAllEmployees();
        model.addAttribute("employees", employees); // Đảm bảo rằng danh sách dịch vụ được đưa vào model
        return "admin-employee"; // Tên view sẽ là admin-services.html
    }
    // Hiển thị form sửa nhân viên
    @GetMapping("/update/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findEmployeeById(id); // Lấy thông tin nhân viên
        model.addAttribute("employee", employee); // Đưa thông tin nhân viên vào model
        return "edit-employee"; // Chuyển tới view edit-employee.html
    }
    // Cập nhật thông tin nhân viên
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, Employee employee) {
        employee.setId(id); // Đảm bảo ID được truyền vào và không thay đổi
        employeeService.saveEmployee(employee); // Lưu thông tin nhân viên đã cập nhật
        return "redirect:/employee/all"; // Quay lại danh sách nhân viên sau khi cập nhật
    }
    // Thêm mới nhân viên (Hiển thị form)
    @GetMapping("/create")
    public String createEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "create-emloyee"; // Tên view cho form thêm mới
    }

    // Xử lý form thêm mới nhân viên
    @PostMapping("/save")
    public String saveEmployee(Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employee/all"; // Quay lại trang danh sách nhân viên
    }
    // Xóa yêu cầu dịch vụ
    @PostMapping("/delete/{id}")
    public String deletEmployee(@PathVariable Long id) {
        employeeService.deleteEmloyee(id);
        return "redirect:/employee/all"; // Quay lại trang danh sách yêu cầu dịch vụ
    }
    // Ghi nhận chấm công
    @PostMapping("/attendance/{id}")
    public String trackAttendance(@PathVariable Long id, @RequestParam LocalDate date, @RequestParam int workingHours, RedirectAttributes redirectAttributes) {
        employeeService.trackAttendance(id, date, workingHours);
        // Tính lương nhân viên
        double salary = employeeService.calculateSalary(id);
        // Thêm thông báo vào FlashAttributes
        redirectAttributes.addFlashAttribute("message", "Chấm công thành công!");
        return "redirect:/employee/all";
    }

//    // Tính lương nhân viên
//    @PostMapping("/salary/{id}")
//    public String calculateSalary(@PathVariable Long id) {
//        double salary = employeeService.calculateSalary(id);
//        return "redirect:/employee/all";
//    }
}
