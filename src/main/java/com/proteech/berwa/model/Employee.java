package com.proteech.berwa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="Employees")
public class Employee {
@Id  
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "EMPLOYEE_ID")
private Long id;

@Column(name = "EMPLOYEE_NAME")
private String name;

@Column(name = "EMPLOYEE_SURNAME")
private String surname;


@Column(name = "EMPLOYEE_EMAIL")
private String email;

@Column(name = "EMPLOYEE_PHONE")
private String phone;

@Column(name = "EMPLOYEE_ADDRESS")
private String addres;

@Column(name = "EMPLOYEE_DEPARTMENT")
private String department;

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

public String getSurname() {
	return surname;
}

public void setSurname(String surname) {
	this.surname = surname;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getAddres() {
	return addres;
}

public void setAddres(String addres) {
	this.addres = addres;
}

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}




}
