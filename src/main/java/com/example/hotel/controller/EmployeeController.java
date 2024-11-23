package com.example.hotel.controller;

import com.example.hotel.model.Employee;
import com.example.hotel.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Đường dẫn cho admin services
    @GetMapping("/admin/employees")
    public String viewAllServiceRequests(Model model) {
        List<Employee> employees = employeeService.findAllEmployees();
        model.addAttribute("employees", employees); // Đảm bảo rằng danh sách dịch vụ được đưa vào model
        return "admin-employee"; // Tên view sẽ là admin-services.html
    }
}
