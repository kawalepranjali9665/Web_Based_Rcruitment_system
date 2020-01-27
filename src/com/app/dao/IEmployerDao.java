
package com.app.dao;

import java.util.List;

import com.app.pojos.Employer;
import com.app.pojos.JobList;
import com.app.pojos.User;

public interface IEmployerDao {
	Integer addEmployer(Employer emps);
	
	Integer addJobsDetails(JobList jobs);

	List<User> getCompanyDetailsOnId(Integer eid, Employer e);

	List<User> getCompanyDetails(Employer e);

}
