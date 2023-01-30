package com.kosavpa.first.boot.example.dao.service.Interface;


import com.kosavpa.first.boot.example.dao.entity.users.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    UserEntity findUserById(Long userId);
    List<UserEntity> findAll();
    boolean saveUser(UserEntity user);
    boolean deleteUser(Long userId);
    boolean isExist(String username);
}
