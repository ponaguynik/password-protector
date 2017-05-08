package com.ponaguynik.passwordprotector.model;


public class User {

    private String username;
    private String keyword;

    public User(String username) {
        this.username = username;
    }

    public User(String username, String keyword) {
        this.username = username;
        this.keyword = keyword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
