package com.drago.jerseyexample.jpa.repository;

import com.drago.jerseyexample.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 14/10/13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
