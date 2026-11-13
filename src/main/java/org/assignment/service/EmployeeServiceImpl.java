package org.assignment.service;

import java.sql.Date;
import java.util.List;

import org.assignment.exception.EmployeeNotFoundException;
import org.assignment.model.Employee;
import org.assignment.repository.EmployeeRepository;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;

    public void setRepository(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee getById(Long id) {       // ✅ FIXED
        return repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with ID: " + id));
    }

    @Override
    public List<Employee> getByDOJ(Date doj) {
        return repository.findByDOJ(doj);
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee create(Employee e) {
        repository.insert(e);
        return e;
    }

    @Override
    public Employee update(Long id, Employee e) {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }

        e.setId(id);
        repository.update(e);

        return repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found after update with ID: " + id));
    }

    @Override
    public Employee updateGrade(Long id, String grade) {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }

        repository.updateGrade(id, grade);

        return repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found after grade update with ID: " + id));
    }

    @Override
    public int removeResigned() {
        return repository.deleteWhereResignedIsY();
    }
}