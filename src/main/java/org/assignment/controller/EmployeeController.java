package org.assignment.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.assignment.exception.EmployeeNotFoundException;
import org.assignment.model.Employee;
import org.assignment.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/employee/doj/{date}")
    public ResponseEntity<List<Employee>> getByDoj(@PathVariable String date) {
        LocalDate parsed = LocalDate.parse(date);
        return ResponseEntity.ok(employeeService.getByDOJ(Date.valueOf(parsed)));
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @DeleteMapping("/employee/remove-resigned")
    public ResponseEntity<String> removeResigned() {
        return ResponseEntity.ok("Deleted records: " + employeeService.removeResigned());
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @PatchMapping(value = "/employee/{id}/grade", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateGrade(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String grade = body.get("grade");

        if (grade == null || grade.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(employeeService.updateGrade(id, grade));
    }
}