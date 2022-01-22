package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.employee.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
	
	@Query("select e from EmployeeEntity  e where e.empMob= :empMob")
	public EmployeeEntity getEmpByMob(@Param("empMob") String empMob);
	
	
	@Query("select e from EmployeeEntity  e where e.empId= :empId")
	public EmployeeEntity getEmpById(@Param("empId") String empId);
	
	

}
