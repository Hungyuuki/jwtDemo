package com.pmhung2.jwtdemo.Service.user;


import com.pmhung2.jwtdemo.Repository.IUserRepository;
import com.pmhung2.jwtdemo.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<Users> findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public Boolean existByUsername(String username) {
        return userRepository.existsByUserName(username);
    }
}
