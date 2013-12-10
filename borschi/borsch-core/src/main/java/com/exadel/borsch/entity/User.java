package com.exadel.borsch.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class User implements Serializable {


    private Integer id;

    @NotNull
    @NotEmpty
    private String login;
    @NotNull
    @Size(min = 6, max = 250)
    private String password;
    @Pattern(regexp = "(1|2)")//
    private String role;
    @Size(min = 0, max = 500)
    private String info;
    @Email
    private String email;

    public User(){};

    public User(Integer id, String login, String password, String role, String info, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.info = info;
        this.email = email;
    }
    public User(String login, String password, String info, String email) {

        this.login = login;
        this.password = password;
        this.info = info;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
