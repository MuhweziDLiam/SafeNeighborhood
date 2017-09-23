package com.example.muhwezidenisliam.safeneighborhoodalpha.group;

/**
 * Created by basasa on 9/13/2017.
 */

public class Group_Model {

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;
    public Group_Model(String name, String id) {
        this.name = name;
        this.id = id;

    }

    public Group_Model() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;
    private String id;

}
