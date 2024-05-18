package net.spemajor.ems.controller;

import lombok.AllArgsConstructor;
import net.spemajor.ems.dto.DepartmentDto;
import net.spemajor.ems.dto.EmployeeDto;
import net.spemajor.ems.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    //Build add department REST api
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDto createdepartment = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(createdepartment,HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long departmentId){
        DepartmentDto departmentDto = departmentService.getDepartment(departmentId);
        return ResponseEntity.ok(departmentDto);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @PutMapping("{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("id") Long departmentID,@RequestBody DepartmentDto updatedDepartment){
        DepartmentDto departmentDto=departmentService.updateDepartment(departmentID,updatedDepartment);
        return ResponseEntity.ok(departmentDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentId, @PathVariable String id)
    {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department deleted successfully!!!");
    }
}
