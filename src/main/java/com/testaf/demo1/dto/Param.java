package com.testaf.demo1.dto;

import com.testaf.demo1.model.User;

public class Param {


    private User user;
    private String countryCode;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}

