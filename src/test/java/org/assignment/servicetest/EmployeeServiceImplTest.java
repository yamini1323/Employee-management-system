package org.assignment.servicetest;

import org.assignment.exception.EmployeeNotFoundException;
//import org.assignment.exception.EmployeeNotFoundException;
import org.assignment.model.Employee;
import org.assignment.repository.EmployeeRepository;
import org.assignment.service.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepository repository;

	@InjectMocks
	private EmployeeServiceImpl service;

	private Employee employee;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);

		employee = new Employee(1L, "Yamini", "yamini@example.com", "A1", Date.valueOf(LocalDate.of(2024, 1, 1)), 'N');
	}

	@Test
	public void testGetById_Found() {
		when(repository.findById(1L)).thenReturn(Optional.of(employee));

		Employee result = service.getById(1L);

		assertEquals("Yamini", result.getName());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testGetById_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		service.getById(1L);
	}

	@Test
	public void testGetAll() {
		when(repository.findAll()).thenReturn(Arrays.asList(employee));

		List<Employee> list = service.getAll();

		assertEquals(1, list.size());
	}

	@Test
	public void testGetByDOJ() {
		Date date = Date.valueOf("2024-01-01");

		when(repository.findByDOJ(date)).thenReturn(Arrays.asList(employee));

		List<Employee> list = service.getByDOJ(date);

		assertEquals(1, list.size());
	}

	@Test
	public void testCreate() {
		when(repository.insert(any(Employee.class))).thenReturn(1);

		Employee created = service.create(employee);

		assertEquals("Yamini", created.getName());
	}

	@Test
	public void testUpdate_WhenExists() {
		when(repository.existsById(1L)).thenReturn(true);
		when(repository.update(any(Employee.class))).thenReturn(1);
		when(repository.findById(1L)).thenReturn(Optional.of(employee));

		Employee updated = service.update(1L, employee);

		assertEquals("Yamini", updated.getName());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testUpdate_WhenNotExists() {
		when(repository.existsById(1L)).thenReturn(false);

		service.update(1L, employee);
	}

	@Test
	public void testUpdateGrade_WhenExists() {
		when(repository.existsById(1L)).thenReturn(true);
		when(repository.updateGrade(1L, "A2")).thenReturn(1);
		when(repository.findById(1L)).thenReturn(Optional.of(employee));

		Employee updated = service.updateGrade(1L, "A2");

		assertNotNull(updated);
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testUpdateGrade_WhenNotExists() {
		when(repository.existsById(1L)).thenReturn(false);

		service.updateGrade(1L, "A2");
	}

	@Test
	public void testRemoveResigned() {
		when(repository.deleteWhereResignedIsY()).thenReturn(5);

		int result = service.removeResigned();

		assertEquals(5, result);
	}
}