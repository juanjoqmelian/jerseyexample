package com.drago.jerseyexample.service;

import com.drago.jerseyexample.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 18/10/13
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    public Long create(User user);

    User get(Long id);

    List<User> getAll();

    User update(User user);

    void delete(Long id);
}
