package net.spemajor.ems;
import net.spemajor.ems.controller.DepartmentController;
import net.spemajor.ems.dto.DepartmentDto;
import net.spemajor.ems.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)

public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateDepartment() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("IT");
        departmentDto.setDepartmentDescription("Information Technology");

        DepartmentDto createdDepartment = new DepartmentDto();
        createdDepartment.setId(1L);
        createdDepartment.setDepartmentName("IT");
        createdDepartment.setDepartmentDescription("Information Technology");

        Mockito.when(departmentService.createDepartment(any(DepartmentDto.class))).thenReturn(createdDepartment);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.departmentName").value("IT"))
                .andExpect(jsonPath("$.departmentDescription").value("Information Technology"));
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1L);
        departmentDto.setDepartmentName("IT");
        departmentDto.setDepartmentDescription("Information Technology");

        Mockito.when(departmentService.getDepartment(1L)).thenReturn(departmentDto);

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.departmentName").value("IT"))
                .andExpect(jsonPath("$.departmentDescription").value("Information Technology"));
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        DepartmentDto department1 = new DepartmentDto();
        department1.setId(1L);
        department1.setDepartmentName("IT");
        department1.setDepartmentDescription("Information Technology");

        DepartmentDto department2 = new DepartmentDto();
        department2.setId(2L);
        department2.setDepartmentName("HR");
        department2.setDepartmentDescription("Human Resources");

        List<DepartmentDto> departments = Arrays.asList(department1, department2);

        Mockito.when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].departmentName").value("IT"))
                .andExpect(jsonPath("$[0].departmentDescription").value("Information Technology"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].departmentName").value("HR"))
                .andExpect(jsonPath("$[1].departmentDescription").value("Human Resources"));
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        DepartmentDto updatedDepartment = new DepartmentDto();
        updatedDepartment.setId(1L);
        updatedDepartment.setDepartmentName("IT Updated");
        updatedDepartment.setDepartmentDescription("Information Technology Updated");

        Mockito.when(departmentService.updateDepartment(eq(1L), any(DepartmentDto.class))).thenReturn(updatedDepartment);

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDepartment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.departmentName").value("IT Updated"))
                .andExpect(jsonPath("$.departmentDescription").value("Information Technology Updated"));
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        Mockito.doNothing().when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Department deleted successfully!!!"));

        Mockito.verify(departmentService, Mockito.times(1)).deleteDepartment(1L);
    }
}
