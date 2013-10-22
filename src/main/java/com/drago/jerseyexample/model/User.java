package com.drago.jerseyexample.model;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 14/10/13
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */

import com.drago.jerseyexample.payload.UserPayload;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String phonePrefix;

    private String phone;

    public static class Builder{

        private final Long id;

        private final String firstName;

        private final String lastName;

        private String phonePrefix;

        private String phone;

        public Builder(final Long id, final String firstName, final String lastName){
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder withPhonePrefix(String phonePrefix){
            this.phonePrefix = phonePrefix;
            return this;
        }

        public Builder withPhone(String phone){
            this.phone = phone;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }

    private User(Builder builder){
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserPayload toPayload(){
       return new UserPayload.Builder(this.firstName, this.lastName)
                                                            .withPhonePrefix(this.phonePrefix)
                                                            .withPhone(this.phone)
                                                            .build();
    }
}
