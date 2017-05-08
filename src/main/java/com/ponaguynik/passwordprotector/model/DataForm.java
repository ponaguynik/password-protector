package com.ponaguynik.passwordprotector.model;


public class DataForm {

    private final int id;
    private String title;
    private String login;
    private String password;

    public DataForm(int id) {
        this.id = id;
    }

    public DataForm(int id, String title, String login, String password) {
        this.id = id;
        this.title = title;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
