package com.hanksha.ces.data.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

public class User {

	private int memberId;

	@NotNull
	@Size(min = 8, max = 45)
	private String username;

	@NotNull
	@Size(min = 8, max = 45)
	private String password;

	@NotNull
	private Date dateCreated;

	@NotNull
	@Size(min = 1, max = 45)
	private String role;

	public User(int memberId, String username, String password, Date dateCreated) {
		this.memberId = memberId;
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
