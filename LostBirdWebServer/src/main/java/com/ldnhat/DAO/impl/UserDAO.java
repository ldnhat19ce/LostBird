package com.ldnhat.DAO.impl;

import com.ldnhat.DAO.IUserDAO;
import com.ldnhat.mapper.impl.UserMapper;
import com.ldnhat.model.UserModel;

import java.util.List;

public class UserDAO extends AbstractDAO<UserModel> implements IUserDAO {
    @Override
    public UserModel findByUsernameAndEmail(String username, String email) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users u ");
        sql.append("WHERE u.username = ? AND u.email = ?");

        Object[] params = {username, email};
        List<UserModel> userModels = query(sql.toString(), new UserMapper(), params);
        return userModels.isEmpty() ? null : userModels.get(0);
    }

    @Override
    public List<UserModel> findAll() {
        StringBuilder sql = new StringBuilder("SELECT * FROM users u");

        return query(sql.toString(), new UserMapper());
    }

    @Override
    public Long save(UserModel userModel) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append("users(role_id, username, email, password, screen_name, profile_image, profile_cover, ");
        sql.append("following, followers, bio) ");
        sql.append("VALUES(?,?,?,?,?,?,?,?,?,?)");

        Object[] params = {userModel.getRole().getId(), userModel.getUsername(),
                    userModel.getEmail(), userModel.getPassword(), userModel.getScreenName(),
                    userModel.getProfileImage(), userModel.getProfileCover(), 
                    userModel.getFollowing(), userModel.getFollower(), userModel.getBio()};
        return insert(sql.toString(), params);
    }

    @Override
    public UserModel findOne(Long id) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users u WHERE u.user_id = ?");
        List<UserModel> userModels = query(sql.toString(), new UserMapper(), id);
        return userModels.isEmpty() ? null : userModels.get(0);
    }
}
