
package com.app.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.Employer;
import com.app.pojos.JobList;
import com.app.pojos.User;

@Repository

@Transactional
public class EmployerDaoImpl implements IEmployerDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public Integer addEmployer(Employer emps) {
		System.out.println("in add method of user");
		sf.getCurrentSession().save(emps);
		return emps.getCompanyId();
	}
	@Override
	public Integer addJobsDetails(JobList jobs) 
	{
		sf.getCurrentSession().save(jobs);
		return jobs.getJobId();
	}
	
	
	
	//only on id
	@Override
	public List<User> getCompanyDetailsOnId(Integer eid, Employer e) {
		String jpql = "select u from User u join fetch u.employer e join fetch e.joblist where u.userId=:eid";
		return sf.getCurrentSession().createQuery(jpql, User.class).setParameter("eid", eid).getResultList();
	}
	
	//all 
	@Override
	public List<User> getCompanyDetails(Employer e) {
		String jpql = "select u from User u join fetch u.employer e left join fetch e.joblist";
		
		List<User> l = sf.getCurrentSession().createQuery(jpql, User.class).getResultList();
		return l;
	}

}
