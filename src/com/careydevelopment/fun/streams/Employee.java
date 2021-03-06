package com.careydevelopment.fun.streams;

public class Employee {

	private Long id;
	private String firstName;
	private String lastName;
	private String department;
	
	public Employee() {
		
	}
	
	public Employee(Long id, String firstName, String lastName, String department) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", department="
				+ department + "]";
	}
	
	
}
