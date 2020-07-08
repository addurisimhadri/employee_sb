package com.sim.cms.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.sim.cms.entities.Employee;

public interface EmployeeService {
	
	Iterable<Employee> getEmployees(Pageable pageable);
	Employee save(Employee employee);
	void delete(int id);
	Optional<Employee> getEmployeeById(int id);

}
