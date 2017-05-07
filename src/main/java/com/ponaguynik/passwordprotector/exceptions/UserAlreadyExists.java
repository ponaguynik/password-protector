package com.ponaguynik.passwordprotector.exceptions;


public class UserAlreadyExists extends Exception {

    public UserAlreadyExists() {
        super();
    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}
