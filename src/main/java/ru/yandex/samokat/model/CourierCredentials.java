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

    public static CourierCredentials getCourierCredentials(Courier courier){
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public static CourierCredentials getCourierCredentialsWithRandomPassword(String login){
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(login, password);
    }

    public static CourierCredentials getCourierCredentialsWithRandomLogin(String password){
        final String login = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(login, password);
    }

}
