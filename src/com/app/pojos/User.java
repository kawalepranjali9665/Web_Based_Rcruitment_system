package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "user")
public class User {
	private Integer userId;
	private String name, email, password, mobile;
	private UserRole type;
	private int otp;
	// from candidate for one to one relation with user and candidate
   
	private Candidate candidate; // one to one bet. employer and user
    
	private Employer employer;
	
	private List<AppliedJob> appliedjob = new ArrayList<>();

	public User() {
		System.out.println("in user ctor");
	}

	public User(String password, int otp) {
		super();
		this.password = password;
		this.otp = otp;
	}
	
	
	public User(String name, String email, String password, String mobile, UserRole type) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.type = type;
	}
	

	public User(Integer userId, String email, String mobile) {
		super();
		this.userId = userId;
		this.email = email;
		this.mobile = mobile;
	}

	public User(Integer userId, String name, String email, String password, String mobile, UserRole type) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 30, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	public UserRole getType() {
		return type;
	}

	public void setType(UserRole type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL) //
	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;

	}
	//employer
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL) //
	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	//applied job
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	public List<AppliedJob> getAppliedjob() {
		return appliedjob;
	}

	public void setAppliedjob(List<AppliedJob> appliedjob) {
		this.appliedjob = appliedjob;
	}

	@Transient
	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	
	public void addAppliedJobDetails(AppliedJob a)
	{
		appliedjob.add(a);
		a.setUser(this);
	}
	
	public void removeAppliedJobDetails(AppliedJob a)
	{
		appliedjob.remove(a);
		a.setUser(null);
	}
	
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", mobile=" + mobile + ", type="
				+ type + "]";
	}
}
