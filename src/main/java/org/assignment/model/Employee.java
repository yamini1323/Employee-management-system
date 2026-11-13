package org.assignment.model;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Employee {

	private Long id;

	@NotBlank(message = "Name cannot be blank")
	private String name;

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Grade cannot be blank")
	private String grade;

	@NotNull(message = "Date of joining cannot be null")
	private Date doj;

	private char resigned;

	public Employee() {
	}

	public Employee(Long id, String name, String email, String grade, Date doj, char resigned) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.grade = grade;
		this.doj = doj;
		this.resigned = resigned;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public char getResigned() {
		return resigned;
	}

	public void setResigned(char resigned) {
		this.resigned = resigned;
	}

	@Override
	public String toString() {
		return "Employee{id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", grade='" + grade + '\''
				+ ", doj=" + doj + ", resigned=" + resigned + '}';
	}
}