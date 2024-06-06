package com.mateocuevas.ecommerceapi.service;

import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRespository userRespository;
    @Autowired
    public UserServiceImpl(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public void save(User user) {
        userRespository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRespository.findById(id);
    }
}
