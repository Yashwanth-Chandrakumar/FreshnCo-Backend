package com.freshnco.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
	@Id
@GeneratedValue
private Long id;
private String fname;
private String lname;
private String email;
private String password;
@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Details details;

    // Constructors, getters, and setters

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
public User(){

}

public Long getId() {
	return id;
}public User(Long id, String fname, String lname, String email, String password) {
	this.id = id;
	this.fname = fname;
	this.lname = lname;
	this.email = email;
	this.password = password;
}

public void setId(Long id) {
	this.id = id;
}
public String getFname() {
	return fname;
}
public void setFname(String fname) {
	this.fname = fname;
}
public String getLname() {
	return lname;
}
public void setLname(String lname) {
	this.lname = lname;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

	
}