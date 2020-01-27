
package com.app.dao;

import java.util.List;

import com.app.pojos.User;

public interface IAdminDao {
	List<User> getAllUsers();

	Integer removeUser(int user_id);

}
