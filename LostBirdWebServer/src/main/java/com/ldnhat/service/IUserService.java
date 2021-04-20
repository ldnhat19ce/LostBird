package com.ldnhat.service;

import com.ldnhat.model.UserModel;

public interface IUserService {

    UserModel findByUsernameAndEmail(String username, String email);
    UserModel save(UserModel userModel);
    UserModel findByEmailAndPassword(String email, String password);
    UserModel findOne(Long id);
}
