package net.spemajor.ems.controller;

import lombok.AllArgsConstructor;
import net.spemajor.ems.dto.EmployeeDto;
import net.spemajor.ems.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/Employees")
public class EmployeeController {

    private EmployeeService employeeService;
    @PostMapping
    //Build add employee API
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //Build get employee API
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId){
       EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
       return ResponseEntity.ok(employeeDto);
    }
    //Build get all employee API
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    //Build update employee API
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeId,@RequestBody EmployeeDto updatedEmployee){
        System.out.println("employeeid: "+employeeId);
        System.out.println(updatedEmployee.getFirstName());
        System.out.println(updatedEmployee.getLastName());
        System.out.println(updatedEmployee.getEmail());
        System.out.println(updatedEmployee.getDepartmentId());
        EmployeeDto employeeDto=employeeService.updateEmployee(employeeId,updatedEmployee);
        return ResponseEntity.ok(employeeDto);
    }

    //Build delete REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId, @PathVariable String id)
    {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully!!!");
    }
}
