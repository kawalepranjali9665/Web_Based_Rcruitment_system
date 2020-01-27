
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.IEmployerDao;
import com.app.pojos.Candidate;
import com.app.pojos.Employer;
import com.app.pojos.JobList;
import com.app.pojos.User;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/employer")
public class EmployerController {

	@Autowired
	private IEmployerDao dao;

	@GetMapping("/listofallemp")
	public List<User> getCompanyDetails(Employer e) {
		List<User> u = dao.getCompanyDetails(e);

		List<User> ulist = new ArrayList<>();

		for (User user : u) {
			
			List<JobList> jlist = new ArrayList<>();

			User newUser = new User(user.getUserId(), user.getEmail(), user.getMobile());

			Employer newEmp = new Employer(user.getEmployer().getCompanyName(), user.getEmployer().getAddress(),user.getEmployer().getJoblist());

			for (JobList j : newEmp.getJoblist()) {
				JobList newJob = new JobList(j.getJobId(), j.getJobProfile(), j.getJobDesc(), j.getInterviewDate(),
						j.getInterviewTime(), j.getNoOfVacancies(), j.getSalary(), j.getLocation());

				jlist.add(newJob);
				newEmp.setJoblist(jlist);
			}
			
			newUser.setEmployer(newEmp);
			ulist.add(newUser);
		}

		System.out.println("in company mathod in controller");
		return ulist;
	}

	@GetMapping("/companydtls")
	public List<User> getCompanyDetailsOnId(@RequestParam Integer eid, Employer e) {
		return dao.getCompanyDetailsOnId(eid, e);
	}

	@GetMapping("/addcompany")
	public String addEmp(@RequestBody Employer emps) {
		System.out.println("in show employee add form");
		return "/employer/addcompany";
	}

	@PostMapping("/addcompany")
	public ResponseEntity<Integer> processCompanyDetailsForm(@RequestBody Employer emps) {
		User u = emps.getUser();
		emps.setUser(u);
		System.out.println(u);
		return new ResponseEntity<Integer>(dao.addEmployer(emps), HttpStatus.OK);
	}

	@GetMapping("/addjobs")
	public String addjobs(@RequestBody JobList jobs) {
		System.out.println("in add joblist form");
		return "/employer/addjobs";
	}

	@PostMapping("/addjobs")
	public ResponseEntity<Integer> processJobDetailsForm(@RequestBody JobList jobs) {
		Employer e = jobs.getEmployer();
		System.out.println(e);
		e.addJobs(jobs);
		return new ResponseEntity<Integer>(dao.addJobsDetails(jobs), HttpStatus.OK);
	}
	
}
