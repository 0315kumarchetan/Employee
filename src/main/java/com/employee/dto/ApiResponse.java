package com.employee.dto;

import java.util.Arrays;

public class ApiResponse {
	
	
	String status;
	Object message;
	String[] error;

	public ApiResponse(String status, Object message, String[] error) {
		super();
		this.status = status;
		this.message = message;
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String[] getError() {
		return error;
	}

	public void setError(String[] error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ApiResponse [status=" + status + ", message=" + message + ", error=" + Arrays.toString(error) + "]";
	}

	

}
