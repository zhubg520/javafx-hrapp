package com.example;

/**
 * Class for hospital staff
 * 
 * @author Wenjing Ma
 * @version 1.0
 */

public class Staff extends Employee {

    String role = "";
    String type;
    static final String[] TYPES = {
            "clinical assistants",
            "patient services assistants",
            "porters",
            "volunteers",
            "ward clerks",
    };

    public Staff(int id, String name, String sex, int age, String phone, double salary, String address, String type) {
        super(id, name, sex, age, phone, salary, address);
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
