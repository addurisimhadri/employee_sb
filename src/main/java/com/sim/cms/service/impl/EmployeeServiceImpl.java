package com.sim.cms.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sim.cms.entities.Employee;
import com.sim.cms.repo.EmployeeRepository;
import com.sim.cms.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	@Override
	public Iterable<Employee> getEmployees(Pageable pageable) {
		Iterable<Employee> employees=employeeRepository.findAll(pageable);
		return employees;
	}
	@Override
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}
	@Override
	public void delete(int id) {
		employeeRepository.deleteById(id);		
	}
	@Override
	public Optional<Employee> getEmployeeById(int id) {		
		return employeeRepository.findById(id);
	}

}
