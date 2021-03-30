package com.ldnhat.service;

import com.ldnhat.DAO.impl.UserDAO;
import com.ldnhat.model.Role;
import com.ldnhat.model.UserModel;

import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public List<UserModel> findAll(){
        return userDAO.findAll();
    }

    public UserModel findByUsernameAndEmail(String username, String email){
        return userDAO.findByUsernameAndEmail(username, email);
    }

    public UserModel save(UserModel userModel){
        Long userId = userDAO.save(userModel);
        return userDAO.findOne(userId);
    }
}
