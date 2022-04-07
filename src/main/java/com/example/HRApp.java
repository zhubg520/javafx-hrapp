package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for human resouce system
 * 
 * @author Wenjing Ma
 * @version 1.0
 */

public class HRApp {

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    /**
     * Add a subclass object of abstract class Employee to ObservableList employees
     * 
     * @param employee Employee to add, A、an object of Employee subclass Doctor,
     *                 Nurse or Staff
     * @return Return true on success, false on failure
     */

    public boolean add(Employee employee) {
        try {
            employee.setId(this.employees.size());
            this.employees.add(employee);
            return true;
        } catch (Error e) {
            System.out.print("add Error: ");
            System.out.println(e);
            return false;
        }
    }

    /**
     * Remove a employee from ObservableList employees
     * 
     * @param index The index of an element of the ObservableList employees
     * @return Return true on success, false on failure
     */

    public boolean remove(int index) {
        try {
            this.employees.remove(index);
            return true;
        } catch (Error e) {
            System.out.print("remove Error: ");
            System.out.println(e);
            return false;
        }
    }

    /**
     * Update a employee from ObservableList employees
     * 
     * @param index The index of an element of the ObservableList employees
     * @return Return true on success, false on failure
     */

    public boolean update(int index, Employee employee) {
        try {
            this.employees.set(index, employee);
            return true;
        } catch (Error e) {
            System.out.print("update Error: ");
            System.out.println(e);
            return false;
        }
    }

    /**
     * Get a employee from ObservableList employees
     * 
     * @param index The index of an element of the ObservableList employees
     * @return An object of Employee subclass Doctor, Nurse or Staff
     */

    public Employee get(int index) {
        try {
            return this.employees.get(index);
        } catch (Error e) {
            System.out.print("get Error: ");
            System.out.println(e);
            throw e;
        }
    }

    /**
     * Get the ObservableList employees, all employees
     * 
     * @return The ObservableList employees
     */

    public ObservableList<Employee> getAll() {
        return this.employees;
    }

}
