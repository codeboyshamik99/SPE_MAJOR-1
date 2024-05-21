package net.spemajor.ems;

import net.spemajor.ems.controller.DepartmentController;
import net.spemajor.ems.dto.DepartmentDto;
import net.spemajor.ems.service.DepartmentService;
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

public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateDepartment() {
        DepartmentDto departmentDto = new DepartmentDto(1L, "IT", "Information Technology");
        when(departmentService.createDepartment(any(DepartmentDto.class))).thenReturn(departmentDto);

        ResponseEntity<DepartmentDto> response = departmentController.createDepartment(departmentDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(departmentDto, response.getBody());
        verify(departmentService, times(1)).createDepartment(any(DepartmentDto.class));
    }

    @Test
    public void testGetDepartmentById() {
        Long departmentId = 1L;
        DepartmentDto departmentDto = new DepartmentDto(departmentId, "IT", "Information Technology");
        when(departmentService.getDepartment(departmentId)).thenReturn(departmentDto);

        ResponseEntity<DepartmentDto> response = departmentController.getDepartmentById(departmentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentDto, response.getBody());
        verify(departmentService, times(1)).getDepartment(departmentId);
    }

    @Test
    public void testGetAllDepartments() {
        List<DepartmentDto> departments = Arrays.asList(
                new DepartmentDto(1L, "IT", "Information Technology"),
                new DepartmentDto(2L, "HR", "Human Resources")
        );
        when(departmentService.getAllDepartments()).thenReturn(departments);

        ResponseEntity<List<DepartmentDto>> response = departmentController.getAllDepartments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departments, response.getBody());
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    public void testUpdateDepartment() {
        Long departmentId = 1L;
        DepartmentDto updatedDepartmentDto = new DepartmentDto(departmentId, "IT Updated", "Information Technology Updated");
        when(departmentService.updateDepartment(eq(departmentId), any(DepartmentDto.class))).thenReturn(updatedDepartmentDto);

        ResponseEntity<DepartmentDto> response = departmentController.updateDepartment(departmentId, updatedDepartmentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDepartmentDto, response.getBody());
        verify(departmentService, times(1)).updateDepartment(eq(departmentId), any(DepartmentDto.class));
    }

    @Test
    public void testDeleteDepartment() {
        Long departmentId = 1L;

        ResponseEntity<String> response = departmentController.deleteDepartment(departmentId, "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department deleted successfully!!!", response.getBody());
        verify(departmentService, times(1)).deleteDepartment(departmentId);
    }


}
