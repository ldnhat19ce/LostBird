package com.ldnhat.mapper.impl;

import com.ldnhat.mapper.RowMapper;
import com.ldnhat.model.Role;
import com.ldnhat.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserModel> {
    @Override
    public UserModel mapRow(ResultSet rs) throws SQLException {
        UserModel userModel = new UserModel();
        userModel.setId(rs.getInt("u.user_id"));
        userModel.setEmail(rs.getString("u.email"));
        userModel.setUsername(rs.getString("u.username"));
        userModel.setScreenName(rs.getString("u.screen_name"));
        userModel.setProfileImage(rs.getString("u.profile_image"));
        userModel.setProfileCover(rs.getString("u.profile_cover"));
        userModel.setFollowing(rs.getInt("u.following"));
        userModel.setFollower(rs.getInt("u.followers"));
        userModel.setBio(rs.getString("u.bio"));
        userModel.setPassword(rs.getString("u.password"));

        Role role = new Role();
        role.setId(rs.getInt("u.role_id"));
        userModel.setRole(role);
        return userModel;
    }
}
