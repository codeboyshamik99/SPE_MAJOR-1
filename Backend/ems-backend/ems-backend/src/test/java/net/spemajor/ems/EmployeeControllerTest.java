package net.spemajor.ems;

import net.spemajor.ems.controller.EmployeeController;
import net.spemajor.ems.dto.EmployeeDto;
import net.spemajor.ems.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEmployee() {
        EmployeeDto employeeDto = new EmployeeDto(1L, "John", "Doe", "john.doe@example.com", 1L);
        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.createEmployee(employeeDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).createEmployee(any(EmployeeDto.class));
    }

    @Test
    public void testGetEmployeeById() {
        Long employeeId = 1L;
        EmployeeDto employeeDto = new EmployeeDto(employeeId, "John", "Doe", "john.doe@example.com", 1L);
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    public void testGetAllEmployees() {
        List<EmployeeDto> employees = Arrays.asList(
                new EmployeeDto(1L, "John", "Doe", "john.doe@example.com", 1L),
                new EmployeeDto(2L, "Jane", "Smith", "jane.smith@example.com", 2L)
        );
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<EmployeeDto>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void testUpdateEmployee() {
        Long employeeId = 1L;
        EmployeeDto updatedEmployeeDto = new EmployeeDto(employeeId, "Updated", "Employee", "updated.employee@example.com", 2L);
        when(employeeService.updateEmployee(eq(employeeId), any(EmployeeDto.class))).thenReturn(updatedEmployeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.updateEmployee(employeeId, updatedEmployeeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEmployeeDto, response.getBody());
        verify(employeeService, times(1)).updateEmployee(eq(employeeId), any(EmployeeDto.class));
    }

    @Test
    public void testDeleteEmployee() {
        Long employeeId = 1L;

        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId, "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully!!!", response.getBody());
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}
