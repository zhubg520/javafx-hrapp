package com.example;

/**
 * Class for nurse
 * 
 * @author Wenjing Ma
 * @version 1.0
 */

public class Nurse extends Employee {
    String role;
    static final String[] ROLES = {
            "Registered Nurses",
            "Radiology Nurses",
            "Public Health Nurses",
            "Oncology Nurses",
            "enrolled nurses",
            "NICU Nurses",
            "Plastic Surgery Nurses",
            "Corrections Facility Nurses",
            "Rehabilitation Nurses",
    };

    String type;
    static final String[] TYPES = {
            "nurse unit manager",
            "associate nurse unit manager",
            "nurse practitioners",
            "registered nurses",
            "enrolled nurses",
    };

    public Nurse(int id, String name, String sex, int age, String phone, double salary, String address, String role, String type) {
        super(id, name, sex, age, phone, salary, address);
        this.role = role;
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
