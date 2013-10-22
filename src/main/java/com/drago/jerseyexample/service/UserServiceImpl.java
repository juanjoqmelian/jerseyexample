package com.drago.jerseyexample.service;

import com.drago.jerseyexample.jpa.repository.UserRepository;
import com.drago.jerseyexample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 19/10/13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public Long create(User user){

        return userRepository.save(user).getId();

    }

    @Override
    public User get(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
