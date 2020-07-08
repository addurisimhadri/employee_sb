package com.sim.cms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.cms.entities.Employee;
import com.sim.cms.exception.EmployeeIdNotFoundException;
import com.sim.cms.service.EmployeeService;
import com.sim.cms.vo.ApiResponse;
import com.sim.cms.vo.EmployeeDTO;

import lombok.extern.java.Log;


@CrossOrigin(origins = "*", allowedHeaders = "*",allowCredentials = "false")
@RestController
@RequestMapping(value = "/api")
@Log
public class EmployeeController {	
	private static final String ACTION = "Employee id is ##ID## Not exist.";
	private static final String ACTION_1 = "##ID##";
	@Autowired
	EmployeeService employeeService;
	@GetMapping(value="/employee/getAll")	
	public Iterable<Employee> getEmployee(Pageable pageable){		
		return employeeService.getEmployees(pageable);
	}
	@PostMapping(path = { "/employee/add" })
	public ApiResponse<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) {
		Employee employee = convertObjToXXX(employeeDTO, new TypeReference<Employee>(){});
		employeeService.save(employee);
		return new ApiResponse<>(HttpStatus.CREATED.value(), "Employee Deatails are SuccessFully Added", employeeDTO);
	}
	@PutMapping(path = { "/employee/update/{id}" })
	public ApiResponse<EmployeeDTO> update(@RequestBody EmployeeDTO employeeDTO,@PathVariable("id") int id ) throws EmployeeIdNotFoundException {
		Employee employee = convertObjToXXX(employeeDTO, new TypeReference<Employee>(){});
		log.info(employee+"");		
		Optional<Employee> employeeOp=employeeService.getEmployeeById(id);
		if(!employeeOp.isPresent()) 
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, id+""));	
		employeeService.save(employee);
		return new ApiResponse<>(HttpStatus.OK.value(), "Employee Deatails are SuccessFully Updated", employeeDTO);
	}
	@GetMapping(path = { "/employee/get/{id}" })
	public ApiResponse<EmployeeDTO> getEmployee(@PathVariable("id") int id ) throws EmployeeIdNotFoundException {		
		Optional<Employee> employee=employeeService.getEmployeeById(id);
		if(!employee.isPresent()) 
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, id+""));	
		
		return new ApiResponse<>(HttpStatus.OK.value(), "Employee Deatails are Getting SuccessFully", employee.get());	
	}
	@DeleteMapping(path = { "/employee/delete/{id}" })
	public ApiResponse<Void> delete(@PathVariable("id") int id) throws EmployeeIdNotFoundException {		
		Optional<Employee> employee=employeeService.getEmployeeById(id);
		if(!employee.isPresent()) 
			throw new EmployeeIdNotFoundException(ACTION.replace(ACTION_1, id+""));	
		employeeService.delete(id);
		return new ApiResponse<>(HttpStatus.OK.value(),"Employee deleted successfully.",null);
	}
	
	public  Employee convertObjToXXX(Object o, TypeReference<Employee> ref) {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.convertValue(o, ref);
	}
}
