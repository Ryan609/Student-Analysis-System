package com.spa.serviceImpl;

import com.spa.dao.userDao;
import com.spa.model.User;
import com.spa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private userDao userDao;
	
	@Override
	public User getUser(String username, String password) {
		return userDao.getUser(username, password);
	}

}
