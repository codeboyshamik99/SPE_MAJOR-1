package net.spemajor.ems.service;

import net.spemajor.ems.dto.DepartmentDto;
import net.spemajor.ems.dto.EmployeeDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartment(Long departmentId);
    List<DepartmentDto> getAllDepartments();
    DepartmentDto updateDepartment(Long departmentID, DepartmentDto updatedDepartment);
    void deleteDepartment(Long departmentId);
}
