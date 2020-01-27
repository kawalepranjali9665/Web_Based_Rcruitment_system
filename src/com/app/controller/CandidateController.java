
package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.ICandidateDao;
import com.app.pojos.AppliedJob;
import com.app.pojos.Candidate;
import com.app.pojos.Education;
import com.app.pojos.Employer;
import com.app.pojos.JobList;
import com.app.pojos.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/candidate")
public class CandidateController {

	@Autowired
	private ICandidateDao dao;

	public CandidateController() {
		System.out.println("in candidate controller");
	}


	// for adding candidate personal details seperately in table/pojo candidate
	@GetMapping("/addcand")
	public String add(Candidate c) {
		System.out.println("in show register form");
		return "/candidate/addcand";
	}

	@PostMapping("/addcand") 
	public ResponseEntity<Integer>
	  processCandidateForm(@RequestBody Candidate cnd) 
	{ 
	  System.out.println(cnd);
	  User u = cnd.getUser(); 
	  cnd.setUser(u); 
	  System.out.println(u);
	  System.out.println("this is fk " + cnd); 
	  return new ResponseEntity<Integer>(dao.addRCandidate(cnd), HttpStatus.OK);
	  
	  }



	// for adding candidate education details seperately in table/pojo education
	@GetMapping("/addedu")
	public String addEducation(Education edu) {
		System.out.println("in show register form");
		return "/candidate/addedu";
	}

	@PostMapping("/addedu")
	public ResponseEntity<Integer> processEducationForm(@RequestBody Education educ) {

		Candidate c = educ.getCandidate();

		System.out.println(educ.getCandidate().getResumeId());
		c.addEducationDetails(educ);

		System.out.println(c);
		System.out.println("candidate data : " + c);

		return new ResponseEntity<Integer>(dao.addR_C_Education(educ), HttpStatus.OK);

	}

	@GetMapping("/resume")
	public User getUserDetails(@RequestParam int user_id) {
		System.out.println("in get cand dtls " + user_id);
		User u = dao.getEntireResumeDetail(user_id);
		
		
		User newUser = new User(u.getName(), u.getEmail(), u.getPassword(), u.getMobile(), u.getType());
		List<Education> elist = new ArrayList<>();
		Candidate newCand = new Candidate(u.getCandidate().getGender(), u.getCandidate().getAge(), u.getCandidate().getObjective(), u.getCandidate().getAddress(), u.getCandidate().getpTitle(), u.getCandidate().getpDesc(),u.getCandidate().getEducation());
		 for(Education e : newCand.getEducation())
		 {
			 
			 Education newEdu = new Education(e.getExam(), e.getPercentage(), e.getPassYear());
			 elist.add(newEdu);
			 
		 }
		 newCand.setEducation(elist);
		 newUser.setCandidate(newCand);
		
		System.out.println(newUser);

//		if (u == null)
//			return new ResponseEntity<String>("Candidate not found...", HttpStatus.NOT_FOUND);
//		return new ResponseEntity<User>(u, HttpStatus.OK);
		return newUser;
	}
	
	@PostMapping("/apply")
	public Integer ApplyCompany(@RequestBody AppliedJob app)
	{
		return dao.ApplyCompany(app);
	}
	
	
	@GetMapping("/search")
	public List<User> getCompanyDetails(@RequestParam String location,@RequestParam String jobProfile) {
		List<User> u = dao.getListOfCompany(location, jobProfile);

		List<User> ulist = new ArrayList<>();

		for (User user : u) {
			
			List<JobList> jlist = new ArrayList<>();

			User newUser = new User(user.getUserId(), user.getEmail(), user.getMobile());
			System.out.println(newUser);
			
			Employer newEmp = new Employer(user.getEmployer().getCompanyName(), user.getEmployer().getAddress(),user.getEmployer().getJoblist());

			for (JobList j : newEmp.getJoblist()) {
				JobList newJob = new JobList(j.getJobId(), j.getJobProfile(), j.getJobDesc(), j.getInterviewDate(),
						j.getInterviewTime(), j.getNoOfVacancies(), j.getSalary(), j.getLocation());

				jlist.add(newJob);
				newEmp.setJoblist(jlist);
				newUser.setEmployer(newEmp);
			}
			
			ulist.add(newUser);
		}
		return ulist;
	}
	
	@GetMapping("/getcand/{user_id}")
	public Candidate getCandidate(@PathVariable Integer user_id)
	{
		System.out.println(user_id);
		Candidate c = dao.getCandidateDetails(user_id);
		Candidate newCand = new Candidate(c.getResumeId() ,c.getGender(), c.getAge(), c.getObjective(), c.getAddress(), c.getpTitle(), c.getpDesc());
		return newCand;
	}
	
	@PutMapping("/{user_id}")
	public Candidate UpdateCandidate1(@PathVariable Integer user_id, @RequestBody Candidate candidate)
	{
		System.out.println(user_id);
		Candidate c = dao.candidateUpdate(user_id, candidate);
		Candidate newCand = new Candidate(c.getGender(), c.getAge(), c.getObjective(), c.getAddress(), c.getpTitle(), c.getpDesc());
		return newCand;
	}
	
}