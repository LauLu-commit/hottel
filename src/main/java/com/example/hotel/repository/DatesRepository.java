package com.example.hotel.repository;

import com.example.hotel.model.Dates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DatesRepository extends JpaRepository<Dates, Long> {
    // Phương thức truy vấn tất cả các bản ghi Dates theo employee_id
    List<Dates> findByEmployeeId(Long employeeId);

    // Phương thức truy vấn tất cả các bản ghi Dates theo ngày (date)
    List<Dates> findByDate(LocalDate date);
}
