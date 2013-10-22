package com.drago.jerseyexample.assembler;

import com.drago.jerseyexample.model.User;
import com.drago.jerseyexample.payload.UserPayload;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 19/10/13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class UserAssembler {

    public static User toEntity(UserPayload userPayload){
        return new User.Builder(null, userPayload.getFirstName(), userPayload.getLastName())
                                .withPhone(userPayload.getPhone())
                                .withPhonePrefix(userPayload.getPhonePrefix()).build();
    }

}
