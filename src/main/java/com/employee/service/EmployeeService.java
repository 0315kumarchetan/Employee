package com.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.entity.EmployeeEntity;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public EmployeeEntity saveEmpSer(EmployeeEntity emp) {
		EmployeeEntity empObj = employeeRepository.save(emp);
		return empObj;
	}

	public List<EmployeeEntity> getEmpList() {
		List<EmployeeEntity> li = employeeRepository.findAll();
		return li;
	}

	public EmployeeEntity findByMob(String mob) {
		return employeeRepository.getEmpByMob(mob);
	}

	public EmployeeEntity findByEmpId(String empId) {
		return employeeRepository.getEmpById(empId);

	}

}
