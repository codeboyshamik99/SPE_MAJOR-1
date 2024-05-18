package net.spemajor.ems.service;

import net.spemajor.ems.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long employeeId);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long employeeID,EmployeeDto updatedEmployee);

    void deleteEmployee(Long employeeId);

}
