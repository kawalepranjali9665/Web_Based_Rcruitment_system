
package com.app.dao;

import java.util.List;

import com.app.pojos.AppliedJob;
import com.app.pojos.Candidate;
import com.app.pojos.Education;
import com.app.pojos.Employer;
import com.app.pojos.User;

public interface ICandidateDao {
	List<Candidate> getDetails();

	Integer addRCandidate(Candidate cand);

	Integer addR_C_Education(Education Edu);

	Candidate getSpecificCandidate(int cid);

	User getEntireResumeDetail(int uid);

	Integer ApplyCompany(AppliedJob app);

	List<User> getListOfCompany(String location, String jobProfile);
	
	Candidate candidateUpdate(Integer user_id, Candidate candidate);

	Candidate getCandidateDetails(Integer user_id);

}
