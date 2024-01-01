package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.RessourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRespository;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")


public class EmployeeController {
	
	@Autowired
	private EmployeeRespository employeeRespository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRespository.findAll();
	}
	
	//create employee rest API
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return employeeRespository.save(employee);
	}
	
	//get one employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long id)
	{
		Employee employee = employeeRespository.findById(id).
		orElseThrow(()-> new RessourceNotFoundException("Employee not found with id: "+id));
		
		return ResponseEntity.ok(employee);
	}
	
	//Update one employee 
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long id,@RequestBody Employee employee){
		Employee SelectedEmployee = employeeRespository.findById(id).
				orElseThrow(()-> new RessourceNotFoundException("Employee not found with id: "+id));
		SelectedEmployee.setFirstname(employee.getFirstname());
		SelectedEmployee.setLastname(employee.getLastname());
		SelectedEmployee.setEmailId(employee.getEmailId());
		Employee UpdatedEmployee = employeeRespository.save(SelectedEmployee);
		return ResponseEntity.ok(UpdatedEmployee);
	}
	
	//delete one employee by Id
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable(value = "id") Long id)
	{
		 Employee employee=employeeRespository.findById(id).
		orElseThrow(()-> new RessourceNotFoundException("Employee not found with id: "+id));
		 employeeRespository.delete(employee);
		 
		 Map<String, Boolean> response = new HashMap<>();
		 
		 response.put("deleted", Boolean.TRUE);
		 
		 return ResponseEntity.ok(response);
	}
}
