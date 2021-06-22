package org.launchcode.codingevents.models;

import com.sun.istack.NotNull;

import javax.persistence.Entity;

import java.util.Objects;

@Entity
public class User extends AbstractEntity{

    @NotNull
    //@NotBlank(message = "Username is required.")
    private String userName;

    @NotNull
    //@NotBlank(message = "Password is required.")
    private String password;

    private UserCategory userCategory;

    public User(String userName, String password, UserCategory userCategory) {
        this.userName = userName;
        this.password = password;
        this.userCategory = userCategory;
    }

    public User(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password);
    }
}
