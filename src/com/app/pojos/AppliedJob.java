package com.app.pojos;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "appliedjob")
public class AppliedJob {
	
	private Integer appliedId;
	
	@JsonBackReference
	private User user;
	
	private JobList joblist;
	
	public AppliedJob() {
		System.out.println("applied job ctor..");
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getAppliedId() {
		return appliedId;
	}
	public void setAppliedId(Integer appliedId) {
		this.appliedId = appliedId;
	}

	@ManyToOne
	@JoinColumn(name="user_id") 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="job_id") 
	public JobList getJoblist() {
		return joblist;
	}

	public void setJoblist(JobList joblist) {
		this.joblist = joblist;
	}
	
	
}
