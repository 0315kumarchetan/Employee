package com.employee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Employee")
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	

	private String empId;
	

	private String empName;

	private String empMob;
	
	private String empDept;
	
	private String empStatus;





	public EmployeeEntity(int id, String empId, String empName, String empMob, String empDept, String empStatus) {
		super();
		this.id = id;
		this.empId = empId;
		this.empName = empName;
		this.empMob = empMob;
		this.empDept = empDept;
		this.empStatus = empStatus;
	}

	public EmployeeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpMob() {
		return empMob;
	}

	public void setEmpMob(String empMob) {
		this.empMob = empMob;
	}

	public String getEmpDept() {
		return empDept;
	}

	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	@Override
	public String toString() {
		return "EmployeeEntity [id=" + id + ", empId=" + empId + ", empName=" + empName + ", empMob=" + empMob
				+ ", empDept=" + empDept + ", empStatus=" + empStatus + "]";
	}
	
	
	

}
