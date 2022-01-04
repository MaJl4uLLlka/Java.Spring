package com.example.mainspingproject.service;

import com.example.mainspingproject.entity.User;
import com.example.mainspingproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByEmail(String email) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        return user;
    }

    public void addNewUser(User user) throws LoginException {
        if(!userRepository.findByEmail(user.getEmail()).isEmpty()){
            throw new LoginException("User with this login now exists");
        }

        userRepository.save(user);
    }
}
