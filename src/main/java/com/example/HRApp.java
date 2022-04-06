package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HRApp {
  
    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    boolean add(Employee employee) {
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

    boolean remove(int index) {
        try {
            this.employees.remove(index);
            return true;
        } catch (Error e) {
            System.out.print("remove Error: ");
            System.out.println(e);
            return false;
        }
    }

    boolean update(int index, Employee employee) {
        try {
            this.employees.set(index, employee);
            return true;
        } catch (Error e) {
            System.out.print("update Error: ");
            System.out.println(e);
            return false;
        }
    }

    Employee get(int index) {
        try {
            return this.employees.get(index);
        } catch (Error e) {
            System.out.print("get Error: ");
            System.out.println(e);
            throw e;
        }
    }

    ObservableList<Employee> getAll() {
        return this.employees;
    }

}
