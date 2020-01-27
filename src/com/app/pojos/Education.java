package com.app.pojos;
import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="education")
public class Education 
{
   private Integer eId;
   private String exam;
   private double percentage;
   private int passYear;
   //from candidate(resume) for one to many bidirectional relation with candidate and education 
   //i.e one candidate can have many educations
   
   @JsonBackReference
   private Candidate candidate;
   
   public Education() 
   {
	System.out.println("in education ctor");
   }
    public Education(String exam, double percentage, int passYear) {
	super();
	this.exam = exam;
	this.percentage = percentage;
	this.passYear = passYear;	
   }    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer geteId() {
		return eId;
	}
	public void seteId(Integer eId) {
		this.eId = eId;
	}
	public String getExam() {
		return exam;
	}
	public void setExam(String exam) {
		this.exam = exam;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public int getPassYear() {
		return passYear;
	}
	public void setPassYear(int passYear) {
		this.passYear = passYear;
	}
	@ManyToOne
	@JoinColumn(name="resume_id") //foreign key from candidate
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
		
	}
	@Override
	public String toString() {
		return "Education [eId=" + eId + ", exam=" + exam + ", percentage=" + percentage + ", passYear=" + passYear
				+ "]";
	}   
}
