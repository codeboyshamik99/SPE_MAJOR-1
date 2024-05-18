package net.spemajor.ems.service.impl;

import lombok.AllArgsConstructor;
import net.spemajor.ems.dto.EmployeeDto;
import net.spemajor.ems.entity.Department;
import net.spemajor.ems.entity.Employee;
import net.spemajor.ems.exception.ResourceNotFoundException;
import net.spemajor.ems.mapper.EmployeeMapper;
import net.spemajor.ems.repository.DepartmentRepository;
import net.spemajor.ems.repository.EmployeeRepository;
import net.spemajor.ems.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(()->new ResourceNotFoundException("The department with this department ID does not exist"));
        employee.setDepartment(department);
        Employee savedEmployee =employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        System.out.println("Fetching employee with ID: " + employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> {
                    System.out.println("Employee not found with ID: " + employeeId);
                    return new ResourceNotFoundException("Employee does not exist with given ID: " + employeeId);
                });
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee)->EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoundException("Employee does not exist with given ID: "+employeeId)
        );

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        // Assuming departmentId is a field in the EmployeeDto class
        Long departmentId = updatedEmployee.getDepartmentId();

        System.out.println("dept id: "+departmentId);

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()->new ResourceNotFoundException("The department with this department ID does not exist"));
        employee.setDepartment(department);

        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }


    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with given ID: " + employeeId));

        employeeRepository.deleteById(employeeId);
    }

}
