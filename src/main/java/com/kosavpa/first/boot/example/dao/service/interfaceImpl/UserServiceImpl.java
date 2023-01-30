package com.kosavpa.first.boot.example.dao.service.interfaceImpl;


import com.kosavpa.first.boot.example.dao.entity.users.Role;
import com.kosavpa.first.boot.example.dao.entity.users.UserEntity;
import com.kosavpa.first.boot.example.dao.repository.UserRepository;
import com.kosavpa.first.boot.example.dao.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    UserRepository repository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(repository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("User not found!!!"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findUserById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new NoSuchElementException(String.format("User with ID = %s, not found!!!")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return (List<UserEntity>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(String username){
        return repository.existsByUsername(username);
    }

    @Override
    public boolean saveUser(UserEntity user) {
        if (isExist(user.getUsername()))
            return false;

        user.setRole(Role.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);

        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (repository.existsById(userId)) {
            repository.deleteById(userId);

            return true;
        }

        return false;
    }
}