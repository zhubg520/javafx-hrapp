package com.example;

public class Doctor extends Employee {

    String role;
    static final String[] ROLES = {
            "Family physicians",
            "Internists",
            "Emergency physicians",
            "Psychiatrists",
            "Obstetricians and gynecologists",
            "Neurologists",
            "Radiologists",
            "Anesthesiologists",
    };


    String type;
    static final String[] TYPES = {
            "senior consultants",
            "registrars",
            "residents",
            "interns",
            "student doctors",
    };

    public Doctor(int id, String name, String sex, int age, String phone, double salary, String address, String role, String type) {
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

/**
 * sql
 * crate table
 * 
 * CREATE TABLE doctor(
 * id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 * name TEXT NOT NULL,
 * phone TEXT NOT NULL,
 * age INT NOT NULL,
 * address CHAR(50),
 * salary REAL,
 * role TEXT NOT NULL,
 * type TEXT NOT NULL
 * );
 * 
 * insert into table
 * 
 * INSERT INTO doctor
 * VALUES (null, 'Damon', '18602031111', 32, 'California', 20000.00, 'senior',
 * 'Family physicians');
 * 
 * select all
 * 
 * SELECT * FROM doctor;
 */
