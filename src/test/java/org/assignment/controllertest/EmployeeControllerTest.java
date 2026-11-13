package org.assignment.controllertest;

import org.assignment.controller.EmployeeController;
import org.assignment.exception.EmployeeNotFoundException;
import org.assignment.exception.GlobalExceptionHandler;
import org.assignment.model.Employee;
import org.assignment.service.EmployeeService;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController controller;

    private Employee employee;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        employee = new Employee(
                1L,
                "Yamini",
                "yamini@example.com",
                "A1",
                Date.valueOf(LocalDate.of(2024, 1, 1)),
                'N'
        );
    }

    @Test
    public void testGetById_Found() throws Exception {
        when(service.getById(1L)).thenReturn(employee);

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yamini"));
    }

//    @Test
//    public void testGetById_NotFound() throws Exception {
//        when(service.getById(1L))
//                .thenThrow(new EmployeeNotFoundException("Not found"));
//
//        mockMvc.perform(get("/employee/1"))
//                .andExpect(status().isNotFound());
//    }

    @Test
    public void testGetById_NotFound() throws Exception {
        when(service.getById(1L))
                .thenThrow(new EmployeeNotFoundException("Not found"));

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(employee));

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("yamini@example.com"));
    }

    @Test
    public void testGetByDoj() throws Exception {
        when(service.getByDOJ(any(Date.class)))
                .thenReturn(Arrays.asList(employee));

        mockMvc.perform(get("/employee/doj/2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].grade").value("A1"));
    }

    @Test
    public void testCreate() throws Exception {
        when(service.create(any(Employee.class))).thenReturn(employee);

        String jsonBody =
                "{"
              + "\"name\":\"Yamini\","
              + "\"email\":\"yamini@example.com\","
              + "\"grade\":\"A1\","
              + "\"doj\":\"2024-01-01\","
              + "\"resigned\":\"N\""
              + "}";

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("yamini@example.com"));
    }

    @Test
    public void testRemoveResigned() throws Exception {
        when(service.removeResigned()).thenReturn(5);

        mockMvc.perform(delete("/employee/remove-resigned"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted records: 5"));
    }

    @Test
    public void testUpdate() throws Exception {
        when(service.update(eq(1L), any(Employee.class))).thenReturn(employee);

        String jsonBody =
                "{"
              + "\"name\":\"Yamini\","
              + "\"email\":\"yamini@example.com\","
              + "\"grade\":\"A1\","
              + "\"doj\":\"2024-01-01\","
              + "\"resigned\":\"N\""
              + "}";

        mockMvc.perform(put("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yamini"));
    }

    @Test
    public void testUpdateGrade() throws Exception {
        when(service.updateGrade(1L, "A2")).thenReturn(employee);

        mockMvc.perform(patch("/employee/1/grade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"grade\":\"A2\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateGrade_Missing() throws Exception {
        mockMvc.perform(patch("/employee/1/grade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}