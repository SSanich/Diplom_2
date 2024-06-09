package praktikum.user;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public User() {
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User random(){
        return new User(RandomStringUtils.randomAlphabetic(4,5)+"@yandex.ru","123456", "Lion");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}