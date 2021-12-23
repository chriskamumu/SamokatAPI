package ru.yandex.samokat.model;


import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentials {

    private final String login;
    private final String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "CourierCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
