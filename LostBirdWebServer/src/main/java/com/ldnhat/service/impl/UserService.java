package com.ldnhat.service.impl;

import com.ldnhat.DAO.IUserDAO;
import com.ldnhat.DAO.impl.UserDAO;
import com.ldnhat.model.Role;
import com.ldnhat.model.UserModel;
import com.ldnhat.service.IUserService;

import java.util.List;

public class UserService implements IUserService {

    private IUserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public List<UserModel> findAll(){
        return userDAO.findAll();
    }

    @Override
    public UserModel findByUsernameAndEmail(String username, String email){
        return userDAO.findByUsernameAndEmail(username, email);
    }

    @Override
    public UserModel save(UserModel userModel){
        Long userId = userDAO.save(userModel);
        return userDAO.findOne(userId);
    }
    @Override
    public UserModel findByEmailAndPassword(String email, String password){
        return userDAO.findByEmailAndPassword(email, password);
    }

    @Override
    public UserModel findOne(Long id){
        return userDAO.findOne(id);
    }
}
