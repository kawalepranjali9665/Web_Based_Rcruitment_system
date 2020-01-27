
package com.app.controller;

import javax.persistence.NoResultException;


import static org.springframework.http.HttpStatus.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.IUserDao;
import com.app.pojos.User;
import com.app.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(allowedHeaders = "*")

@RestController

@RequestMapping("/user")
public class UserController {
	User user;
	int otp;

	@Autowired
	IUserDao iuserDao;

	@Autowired
	private UserService service;

	@Autowired
	private JavaMailSender mailSender;

	@PostMapping("/register")
	public Integer register(@RequestBody User user) {
		System.out.println(user);
		iuserDao.registerUser(user);

		String msg = "Thank you for Registration.";
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("One Time Password");
		mailMessage.setText(msg);
		try {
			mailSender.send(mailMessage);
		} catch (MailException e) {
			System.out.println("inside mail exception");
			e.printStackTrace();
		}
		return user.getUserId();
	}

	@PostMapping("/login")
	public User login(@RequestBody User user) {
		System.out.println(user);
		User u = iuserDao.login(user);
		User newdata = new User(u.getUserId(),u.getName(),u.getEmail(),u.getPassword(),u.getMobile(),u.getType());
		return newdata;
	}

	@GetMapping("/forgot")
	public ResponseEntity<?> processForgotPassword(@RequestParam String em) {

		System.out.println("email  " + em);
		user = service.findByEmail(em);
		;
		try {

			System.out.println(user);
			if (user != null) {

				otp = service.generateOtp();
				System.out.println(otp);
				String msg = "Your one time password(OTP) is = " + otp;
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(user.getEmail());
				mailMessage.setSubject("One Time Password");
				mailMessage.setText(msg);
				try {
					mailSender.send(mailMessage);
					return new ResponseEntity<String>("success", OK);
				} catch (MailException e) {
					System.out.println("inside mail exception");
					e.printStackTrace();
				}
			}
		} catch (NoResultException e) {
			System.out.println("in other exception");
			e.printStackTrace();
		}
		return new ResponseEntity<String>("error", INTERNAL_SERVER_ERROR);

	}

	@PostMapping("/setpass")
	public ResponseEntity<?> setpassword(@RequestBody User u) {

		System.out.println(u);
		System.out.println("mail " + user.getEmail() + " otp " + otp);
		if (otp == u.getOtp()) {
			iuserDao.setPass(u.getPassword(), user.getEmail());
			return new ResponseEntity<Integer>(1, OK);
		} else {
			return new ResponseEntity<Integer>(0, OK);
		}
	}
	
}
