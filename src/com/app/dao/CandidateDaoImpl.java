
package com.app.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.AppliedJob;
import com.app.pojos.Candidate;
import com.app.pojos.Education;
import com.app.pojos.Employer;
import com.app.pojos.User;

@Repository

@Transactional
public class CandidateDaoImpl implements ICandidateDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public List<Candidate> getDetails() {
		System.out.println();
		String jpql = "select c from Candidate c";
		return sf.getCurrentSession().createQuery(jpql, Candidate.class).getResultList();
	}

	@Override
	public Integer addRCandidate(Candidate cand) {
		System.out.println("in add method of user");
		sf.getCurrentSession().save(cand);
		return cand.getResumeId();
	}

	@Override
	public Integer addR_C_Education(Education Edu) {
		System.out.println("in add method of user");

		sf.getCurrentSession().save(Edu);
		return Edu.geteId();
	}


	@Override
	public Candidate getSpecificCandidate(int cid) {
		String jpql = "select c from Candidate c where c.resumeId=:cid";
		return sf.getCurrentSession().createQuery(jpql, Candidate.class).setParameter("cid", cid).getSingleResult();
	}
	@Override
	public User getEntireResumeDetail(int uid) {
		String jpql = "select u from User u join fetch u.candidate c left join fetch c.education where u.userId = :uid";
		System.out.println("in dao :"+uid);
		System.out.println(jpql);
		return sf.getCurrentSession().createQuery(jpql, User.class).setParameter("uid", uid).getSingleResult();	
	}

	@Override
	public Integer ApplyCompany(AppliedJob app) {
		return (Integer)sf.getCurrentSession().save(app);
	}

	@Override
	public List<User> getListOfCompany(String location,String jobProfile) {
		String jpql = "select u from User u join fetch u.employer e left join fetch e.joblist j where j.location = :location or j.jobProfile = :jobProfile";     
		List<User> list =  sf.getCurrentSession().createQuery(jpql, User.class).setParameter("location", location).setParameter("jobProfile", jobProfile).getResultList();
		 return list;
	}

	@Override
	public Candidate candidateUpdate(Integer user_id, Candidate candidate) {
		
		User user = sf.getCurrentSession().get(User.class, user_id);
		Integer resumeId = user.getCandidate().getResumeId();
		Candidate cnd = sf.getCurrentSession().get(Candidate.class, resumeId);
		
		cnd.setAge(candidate.getAge());
		cnd.setAddress(candidate.getAddress());
		cnd.setObjective(candidate.getObjective());
		cnd.setpTitle(candidate.getpTitle());
		cnd.setpDesc(candidate.getpDesc());
		return cnd;
	}

	@Override
	public Candidate getCandidateDetails(Integer user_id) {
		
		User user = sf.getCurrentSession().get(User.class, user_id);
		Integer resumeId = user.getCandidate().getResumeId();	
		Candidate cnd = sf.getCurrentSession().get(Candidate.class, resumeId);
		
		String jpql = "select c from Candidate c where c.resumeId = :resumeId";
		return sf.getCurrentSession().createQuery(jpql, Candidate.class).setParameter("resumeId", resumeId).getSingleResult();
	}
	
}
