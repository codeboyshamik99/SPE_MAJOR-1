package net.spemajor.ems.service.impl;

import lombok.AllArgsConstructor;
import net.spemajor.ems.dto.DepartmentDto;
import net.spemajor.ems.entity.Department;
import net.spemajor.ems.entity.Employee;
import net.spemajor.ems.exception.ResourceNotFoundException;
import net.spemajor.ems.mapper.DepartmentMapper;
import net.spemajor.ems.mapper.EmployeeMapper;
import net.spemajor.ems.repository.DepartmentRepository;
import net.spemajor.ems.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartment(Long departmentId) {
        System.out.println("Fetching department with ID: " + departmentId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    System.out.println("Employee not found with ID: " + departmentId);
                    return new ResourceNotFoundException("Department does not exist with given ID: " + departmentId);
                });
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments= departmentRepository.findAll();
        return departments.stream().map((department)-> DepartmentMapper.mapToDepartmentDto(department))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentID, DepartmentDto updatedDepartment) {
        Department department = departmentRepository.findById(departmentID).orElseThrow(
                ()->new ResourceNotFoundException("Department does not exist with given ID: "+departmentID)
        );

        department.setDepartmentName(updatedDepartment.getDepartmentName());
        department.setDepartmentDescription(updatedDepartment.getDepartmentDescription());
        Department updatedDepartmentObj=departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(updatedDepartmentObj);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist with given ID: " + departmentId));

        departmentRepository.deleteById(departmentId);
    }


}
