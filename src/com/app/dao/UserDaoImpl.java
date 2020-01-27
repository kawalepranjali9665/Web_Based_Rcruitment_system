
package com.app.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.User;

@Service

@Transactional
public class UserDaoImpl implements IUserDao {

	@Autowired
	SessionFactory sf;

	@Override
	public Integer registerUser(User user) {

		return (Integer) sf.getCurrentSession().save(user);
	}

	@Override
	public User login(User user) {
		String jpql = "select u from User u where u.email = :em and u.password = :pass";
		return sf.getCurrentSession().createQuery(jpql, User.class).setParameter("em", user.getEmail())
				.setParameter("pass", user.getPassword()).getSingleResult();
	}

	@Override
	public User findByEmail(String email) {
		String str = "select u from User u where u.email=:em";
		return sf.getCurrentSession().createQuery(str, User.class).setParameter("em", email).getSingleResult();
	}

	@Override
	public void setPass(String password, String email) {
		String str = "select u from User u where u.email=:em";
		User u = sf.getCurrentSession().createQuery(str, User.class).setParameter("em", email).getSingleResult();
		u.setPassword(password);

	}

	@Override
	public void updatePassword(User userPojo) {
		sf.getCurrentSession().update(userPojo);

	}

	@Override
	public List<User> getAllUsers() {
		String jpql = "select u from User u ";
		System.out.println();
		List<User> user = sf.getCurrentSession().createQuery(jpql, User.class).getResultList();
		for (User userList : user) {
			System.out.println(userList);
		}
		return user;
	}

}
