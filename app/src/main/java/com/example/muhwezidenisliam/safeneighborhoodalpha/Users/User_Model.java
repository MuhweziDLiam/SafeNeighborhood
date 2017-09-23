package com.example.muhwezidenisliam.safeneighborhoodalpha.Users;

/**
 * Created by basasa on 9/21/2017.
 */

public class User_Model {
    private String UserPhone;

    public User_Model() {
    }

    public User_Model(String userPhone, String username) {
        UserPhone = userPhone;
        Username = username;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    private String Username;

}
