
package com.app.dao;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.AppliedJob;
import com.app.pojos.Education;
import com.app.pojos.User;

@Repository

@Transactional
public class AdminDaoImpl implements IAdminDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public List<User> getAllUsers() {
		String jpql = "select u from User u where u.type != 'ADMIN'";
		System.out.println();
		List<User> user = sf.getCurrentSession().createQuery(jpql, User.class).getResultList();
		for (User userList : user) {
			System.out.println(userList);
		}
		return user;
	}

	@Override
	public Integer removeUser(int user_id) {
		User u = sf.getCurrentSession().get(User.class, user_id);
			
		sf.getCurrentSession().delete(u);
		return u.getUserId();
	}

}
