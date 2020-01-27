
package com.app.dao;

import java.util.List;

import com.app.pojos.User;

public interface IUserDao {
	public Integer registerUser(User user);

	public User login(User user);

	public User findByEmail(String email);

	public void setPass(String password, String email);

	void updatePassword(User user);

	public List<User> getAllUsers();
}
