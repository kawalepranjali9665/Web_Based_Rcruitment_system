
package com.app.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.IUserDao;
import com.app.pojos.User;

@Service

@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private IUserDao dao;

	@Override
	public int generateOtp() {
		Random random = new Random();
		int num = random.nextInt(99999) + 99999;
		if (num < 100000 || num > 999999) {
			num = random.nextInt(99999) + 99999;
			if (num < 100000 || num > 999999) {
				System.out.println("Unable to generate PIN at this time..");
			}
		}
		return num;
	}

	@Override
	public User findByEmail(String email) {
		return dao.findByEmail(email);
	}

	@Override
	public void updatePassword(User user) {
		dao.updatePassword(user);

	}

}
