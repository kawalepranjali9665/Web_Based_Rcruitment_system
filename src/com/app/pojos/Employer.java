package com.app.pojos;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "employer")
public class Employer {
	private Integer companyId;
	private String companyName, address;
	//one to one bidirectional bet. user and employer
	
	@JsonBackReference
	private User user;
	//one to many bet. joblist and employer
	
	private List<JobList> joblist = new ArrayList<>();

	public Employer() {
		System.out.println("in employer ctor");
	}

	public Employer(String companyName, String address) {
		super();
		this.companyName = companyName;
		this.address = address;
	}
	
	
    
	public Employer(String companyName, String address, List<JobList> joblist) {
		super();
		this.companyName = companyName;
		this.address = address;
		this.joblist = joblist;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCompanyId() {
		return companyId;
	}
    
   	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
    
   	@Column(length = 100,unique = true)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    
	@OneToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
	@OneToMany(mappedBy = "employer",cascade = CascadeType.ALL,orphanRemoval = true)
	public List<JobList> getJoblist() {
		return joblist;
	}

	public void setJoblist(List<JobList> joblist) {
		this.joblist = joblist;
	}
	
	public void addJobs(JobList j)
	{
	   joblist.add(j);
	   j.setEmployer(this);
	   System.out.println("from pojo emp id"+this.companyId);
	}
    
	public void removeJobs(JobList j)
	{
		joblist.remove(j);
		j.setEmployer(null);		
	}
	@Override
	public String toString() {
		return "Employer [companyId=" + companyId + ", companyName=" + companyName + ", address=" + address + "]";
	}
}
