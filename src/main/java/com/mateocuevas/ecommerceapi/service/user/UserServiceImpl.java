package com.mateocuevas.ecommerceapi.service.user;

import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.respository.UserRespository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRespository userRespository;

    public Optional<User> getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //Find user authenticated in  the moment
        String username = authentication.getName();
        return findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRespository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRespository.findById(id);
    }

    @Override
    public Optional<User> findByRole(UserRole role) {return userRespository.findByRole(role);
    }

    @Override
    public Optional<User> findByUsername(String username) {return userRespository.findByUsername(username);
    }
}
