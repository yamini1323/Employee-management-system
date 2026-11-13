package org.assignment.repository;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.assignment.model.Employee;

public interface EmployeeRepository {
	
	Optional<Employee> findById(Long id);
    List<Employee> findByDOJ(Date doj);
    List<Employee> findAll();
    int insert(Employee e);
    int update(Employee e);
    int updateGrade(Long id, String grade);
    int deleteWhereResignedIsY();
    boolean existsById(Long id);

}
