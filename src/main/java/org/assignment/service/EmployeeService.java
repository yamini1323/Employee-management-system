package org.assignment.service;

import java.sql.Date;
import java.util.List;
import org.assignment.model.Employee;

public interface EmployeeService {

    Employee getById(Long id);   // ✅ FIXED: Return Employee, not Optional

    List<Employee> getByDOJ(Date doj);

    List<Employee> getAll();

    Employee create(Employee e);

    Employee update(Long id, Employee e);

    Employee updateGrade(Long id, String grade);

    int removeResigned();
}