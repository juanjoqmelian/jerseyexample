package com.drago.jerseyexample.payload;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 15/10/13
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class UserPayload {

    private String firstName;

    private String lastName;

    private String phonePrefix;

    private String phone;

    public static final class Builder{

        private final String firstName;

        private final String lastName;

        private String phonePrefix;

        private String phone;

        public Builder(final String firstName, final String lastName){
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

        public UserPayload build(){
            return new UserPayload(this);
        }

    }

    private UserPayload(Builder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
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
}
