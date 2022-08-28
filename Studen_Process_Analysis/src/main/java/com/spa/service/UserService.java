package com.spa.service;

import com.spa.model.User;

public interface UserService {

    User getUser(String username, String password);
}
