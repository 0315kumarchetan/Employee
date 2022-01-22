package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.dto.ApiResponse;
import com.employee.dto.Data;
import com.employee.entity.EmployeeEntity;
import com.employee.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/test")
	public String test() {
		return "test";
	}

	public boolean isUnique(String mob) {
		List<EmployeeEntity> empList = employeeService.getEmpList();
		for (EmployeeEntity emp : empList) {
			if (emp.getEmpMob().equals(mob)) {
				return false;
			}
		}
		return true;
	}

	public boolean isGoodPtrn(String mob) {
		for (int i = 0; i < mob.length(); i++) {
			if (mob.charAt(i) - '0' <= 9 && mob.charAt(i) - '0' >= 0) {
				System.out.println(mob.charAt(i) - '0');
			} else {
				return false;
			}
		}
		return true;
	}

	/*
	 * ############## ::: CREATE EMPLOYEE ::: ##############
	 * 
	 */

	@PostMapping("/createEmp")
	public ResponseEntity<ApiResponse> saveEmp(@RequestBody EmployeeEntity emp) {
		ApiResponse api = null;
		try {
			if (emp.getEmpMob()!=null &&isUnique(emp.getEmpMob())) {
				if (emp.getEmpMob().length() == 10) {
					if (isGoodPtrn(emp.getEmpMob())) {
						EmployeeEntity empObj = employeeService.saveEmpSer(emp);
						String empid = "EMP" + empObj.getId();
						empObj.setEmpId(empid);
						EmployeeEntity empObj2 = employeeService.saveEmpSer(empObj);
						api = new ApiResponse(Data.SUCCESS, empObj2, null);
					} else {
						String[] err = { " Digit must be 0-9" };
						api = new ApiResponse(Data.FAILED, null, err);
					}
				} else {
					String[] err = { " Must be 10 digits" };
					api = new ApiResponse(Data.FAILED, null, err);
				}
			}

			else {
				if(emp.getEmpMob()==null) {
					api = new ApiResponse(Data.FAILED, "Mobile number is : "+Data.NA, null);
				}else {
				EmployeeEntity empOb = employeeService.findByMob(emp.getEmpMob());
				api = new ApiResponse(Data.EXISTS, "Name of employee : " + empOb.getEmpName(), null);
				}
			}
		} catch (Exception e) {
			String[] err = { e.toString() };
			api = new ApiResponse(Data.FAILED, null, err);
		}

		return new ResponseEntity<ApiResponse>(api, HttpStatus.ACCEPTED);
	}

	/*
	 * ############ :::: GET ALL EMPLOYEES LIST ::: ########
	 *
	 */
	@GetMapping("/getAllEmp")
	public ResponseEntity<ApiResponse> getAllEmp() {
		ApiResponse api = null;
		try {
			List<EmployeeEntity> list = employeeService.getEmpList();
			api = new ApiResponse(Data.SUCCESS, list, null);
		} catch (Exception e) {
			String[] err = { "Something gone wrong" + " , " + e.toString() };
			api = new ApiResponse(Data.FAILED, null, err);
		}
		return new ResponseEntity<ApiResponse>(api, HttpStatus.ACCEPTED);

	}

	/*
	 * ############## :::: GET EMPLOYEE BY EMPLOYEE_ID ::: #############
	 * 
	 */
	@GetMapping("/getEmpById/{empId}")
	public ResponseEntity<ApiResponse> getByEmpId(@PathVariable("empId") String empId) {
		ApiResponse api = null;
		try {
			if (empId.length() <= 10 && empId.length() >= 4) { // Validate employeeId
				EmployeeEntity findByEmpId = employeeService.findByEmpId(empId);
				if (findByEmpId == null) {
					api = new ApiResponse(Data.NOTFOUND, " Name of employee : " + Data.NA, null);
				} else {
					api = new ApiResponse(Data.SUCCESS, findByEmpId, null);
				}
			} else {
				String[] err = { "Invalid Employee Id" };
				api = new ApiResponse(Data.FAILED, null, err);
			}
		} catch (Exception e) {
			String[] err = { e.toString() };
			api = new ApiResponse(Data.FAILED, null, err);

		}
		return new ResponseEntity<ApiResponse>(api, HttpStatus.ACCEPTED);

	}

	/*
	 * ####### :::: GET EMPLOYEE BY EMPLOYEE_MOBILE_NUMBER ::: #########
	 * 
	 */
	@GetMapping("/getEmpByMobNum/{empMob}")
	public ResponseEntity<ApiResponse> getByEmpMob(@PathVariable("empMob") String empMob) {
		ApiResponse api = null;
		try {
			if (empMob.length() == 10) { // Validation of Mobile Number line 133-148
				if (isGoodPtrn(empMob)) {
					EmployeeEntity empObj = employeeService.findByMob(empMob);
					if (empObj == null) {
						api = new ApiResponse(Data.NOTFOUND, " Name of employee : " + Data.NA, null);
					} else {
						api = new ApiResponse(Data.SUCCESS, empObj, null);
					}
				} else {
					String[] err = { " Digit must be 0-9" };
					api = new ApiResponse(Data.FAILED, null, err);
				}
			} else {
				String[] err = { " Must be 10 digits" };
				api = new ApiResponse(Data.FAILED, null, err);
			}
		} catch (Exception e) {
			String[] err = { e.toString() };
			api = new ApiResponse(Data.FAILED, null, err);
		}
		return new ResponseEntity<ApiResponse>(api, HttpStatus.ACCEPTED);

	}

	/*
	 * ##### :::: UPDATE EMPLOYEE BY EMPLOYEE_ID ::: #######
	 * 
	 */
	@PutMapping("/update/{empId}")
	public ResponseEntity<ApiResponse> updateByEmpId(@PathVariable("empId") String empId,
			@RequestBody EmployeeEntity empObj) {
		ApiResponse api = null;
		try {
			if (empId.length() <= 10 && empId.length() >= 4) {
				EmployeeEntity findByEmpId = employeeService.findByEmpId(empId);
				if (findByEmpId == null) {
					api = new ApiResponse(Data.NOTFOUND, findByEmpId, null);
				} else {
					if (empObj != null) {
						if (empObj.getEmpName() != null) {
							findByEmpId.setEmpName(empObj.getEmpName());
						}
						if (empObj.getEmpDept() != null) {
							findByEmpId.setEmpDept(empObj.getEmpDept());
						}
						if (empObj.getEmpStatus() != null) {
							findByEmpId.setEmpStatus(empObj.getEmpStatus());
						}
						if (empObj.getEmpMob() != null) {
							if (isUnique(empObj.getEmpMob())) {
								if (empObj.getEmpMob().length() == 10) {
									if (isGoodPtrn(empObj.getEmpMob())) {
										findByEmpId.setEmpMob(empObj.getEmpMob());
									} else {
										String[] err = { " Digit must be 0-9" };
										api = new ApiResponse(Data.FAILED, null, err);
									}
								} else {
									String[] err = { " Must be 10 digits" };
									api = new ApiResponse(Data.FAILED, null, err);
								}
							} else {
								EmployeeEntity empOb = employeeService.findByMob(empObj.getEmpMob());
								api = new ApiResponse(Data.EXISTS, "Name of employee : " + empOb.getEmpName(), null);
							}

						}

						if (api != null) {
							return new ResponseEntity<ApiResponse>(api, HttpStatus.ACCEPTED);

						} else {
							EmployeeEntity saved = employeeService.saveEmpSer(findByEmpId);
							api = new ApiResponse(Data.SUCCESS, saved, null);
							return new ResponseEntity<ApiResponse>(api, HttpStatus.ACCEPTED);
						}
					} else {
						api = new ApiResponse(Data.SUCCESS, Data.NA, null);
						return new ResponseEntity<ApiResponse>(api, HttpStatus.ACCEPTED);
					}

				}
			} else {
				String[] err = { "Invalid Employee Id" };
				api = new ApiResponse(Data.FAILED, null, err);
			}
		} catch (Exception e) {
			String[] err = { empId.toString() };
			api = new ApiResponse(Data.FAILED, null, err);

		}
		return new ResponseEntity<ApiResponse>(api, HttpStatus.ACCEPTED);
	}

}
