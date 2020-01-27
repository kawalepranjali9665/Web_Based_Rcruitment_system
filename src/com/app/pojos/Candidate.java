package com.app.pojos;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "candidate")
public class Candidate {
	private Integer resumeId;
	private String gender;
	private int age;
	private String objective, address, pTitle, pDesc;
	
	// from user for one to one relation with user and candidate	
	@JsonBackReference
	private User user;
	
	
	private List<Education> education = new ArrayList<>();
	// many to may bet. skill and candidate

	public Candidate() {
		System.out.println("in candidate ctor");
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getResumeId() {
		return resumeId;
	}

	
//image removed from ctor 
	public Candidate(String gender, int age, String objective, String address, String pTitle, String pDesc) {
		super();
		this.gender = gender;
		this.age = age;
		this.objective = objective;
		this.address = address;
		this.pTitle = pTitle;
		this.pDesc = pDesc;
	}
	
	
     
	public Candidate(String gender, int age, String objective, String address, String pTitle, String pDesc,
		List<Education> education) {
	super();
	this.gender = gender;
	this.age = age;
	this.objective = objective;
	this.address = address;
	this.pTitle = pTitle;
	this.pDesc = pDesc;
	this.education = education;
}
	
	public Candidate(Integer resumeId, String gender, int age, String objective, String address, String pTitle,
			String pDesc) {
		super();
		this.resumeId = resumeId;
		this.gender = gender;
		this.age = age;
		this.objective = objective;
		this.address = address;
		this.pTitle = pTitle;
		this.pDesc = pDesc;
	}

	public void setResumeId(Integer resumeId) {
		this.resumeId = resumeId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column(length = 2000)
	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(length = 700)
	public String getpTitle() {
		return pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}

	@Column(length = 2500)
	public String getpDesc() {
		return pDesc;
	}

	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}
	
	@OneToOne
	@JoinColumn(name = "user_id") // foreign key from user
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}
    
	public void addEducationDetails(Education e)
	{
		education.add(e);
		e.setCandidate(this);
	}
	
	public void removeEducationDetails(Education e)
	{
		education.add(e);
		e.setCandidate(null);
	}

	@Override
	public String toString() {
		return "Candidate [resumeId=" + resumeId + ", gender=" + gender + ", age=" + age + ", objective=" + objective
				+ ", address=" + address + ", pTitle=" + pTitle + ", pDesc=" + pDesc + "]";
	}
	
}
