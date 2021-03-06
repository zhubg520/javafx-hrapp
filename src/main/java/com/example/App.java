package com.example;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * Interface for common event handler
 * 
 * @author Wenjing Ma
 * @version 1.0
 */

interface CommonEventHandler {
    void eventProcess(ActionEvent e);
}

/**
 * Class for javafx auto increase indexes TableCell in TableView
 * 
 * @author Wenjing Ma
 * @version 1.0
 */

class IDCell<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {

        TableCell cell = new TableCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);
                if (!empty) {
                    int rowIndex = this.getIndex() + 1;
                    this.setText(String.valueOf(rowIndex));
                }
            }

        };
        return cell;
    }
}

/**
 * Class for javafx GUI Application
 * 
 * @author Wenjing Ma
 * @version 1.0
 */

public class App extends Application {
    private HRApp hrApp = new HRApp();

    /**
     * Variable to record the index of the selected row
     * 
     */
    private int tableSelectIndex = -1;

    public void start(Stage stage) {

        /**
         * Regular expression for detecting numbers
         * 
         */
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };

        /**
         * Convert String to double
         * 
         */
        StringConverter<Double> doubleConverter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0;
                } else {
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        /**
         * Convert String to Integer
         * 
         */
        StringConverter<Integer> intConverter = new StringConverter<Integer>() {

            @Override
            public Integer fromString(String s) {
                if (s.isEmpty() || "-".equals(s)) {
                    return 0;
                } else {
                    return Integer.valueOf(s);
                }
            }

            @Override
            public String toString(Integer d) {
                return d.toString();
            }
        };

        /**
         * Remove a employee from hrApp
         * 
         */
        Button romoveButton = new Button("Romove Employee");
        romoveButton.setFont(Font.font("Arial", 15));
        romoveButton.setDisable(true);
        romoveButton.setOnAction(e -> {
            System.out.println("tableSelectIndex: " + tableSelectIndex);
            if (tableSelectIndex != -1) {
                hrApp.remove(tableSelectIndex);
            }
            romoveButton.setDisable(true);
        });

        /**
         * Table used to display all employees
         * 
         */
        TableView<Employee> table = new TableView<>();
        table.setEditable(true);

        TableColumn<Employee, String> idCol = new TableColumn<>("ID");
        idCol.setCellFactory(new IDCell<>());

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(evt -> {
            Employee temp = evt.getRowValue();
            temp.setName(evt.getNewValue());
            hrApp.update(evt.getRowValue().getId(), temp);
            table.refresh();
        });

        TableColumn<Employee, String> sexCol = new TableColumn<>("Sex");
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        // sexCol.setEditable(true);
        sexCol.setCellFactory(ComboBoxTableCell.forTableColumn(Employee.SEX));
        sexCol.setOnEditCommit(evt -> {
            System.out.println("evt.getNewValue(): " + evt.getRowValue().toString());
            Employee temp = evt.getRowValue();
            temp.setSex(evt.getNewValue());
            hrApp.update(evt.getRowValue().getId(), temp);
            table.refresh();
        });

        TableColumn<Employee, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        typeCol.setOnEditCommit(evt -> {
            System.out.println("typeCol.getRowValue().getId():");
            System.out.println(evt.getRowValue().getId());

            if (evt.getRowValue() instanceof Doctor) {
                Doctor temp = (Doctor) evt.getRowValue();
                temp.setType(evt.getNewValue());
                hrApp.update(evt.getRowValue().getId(), temp);
            } else if (evt.getRowValue() instanceof Nurse) {
                Nurse temp = (Nurse) evt.getRowValue();
                temp.setType(evt.getNewValue());
                hrApp.update(evt.getRowValue().getId(), temp);
            } else if (evt.getRowValue() instanceof Staff) {
                Staff temp = (Staff) evt.getRowValue();
                temp.setType(evt.getNewValue());
                hrApp.update(evt.getRowValue().getId(), temp);
            }
            table.refresh();
        });

        TableColumn<Employee, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        roleCol.setOnEditCommit(evt -> {
            System.out.println("roleCol.getRowValue().getId():");

            System.out.println(evt.getRowValue().getId());

            if (evt.getRowValue() instanceof Doctor) {
                Doctor temp = (Doctor) evt.getRowValue();
                temp.setRole(evt.getNewValue());
                hrApp.update(evt.getRowValue().getId(), temp);
            } else if (evt.getRowValue() instanceof Nurse) {
                Nurse temp = (Nurse) evt.getRowValue();
                temp.setRole(evt.getNewValue());
                hrApp.update(evt.getRowValue().getId(), temp);
            } else if (evt.getRowValue() instanceof Staff) {

            }
            table.refresh();
        });

        TableColumn<Employee, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setOnEditCommit(evt -> {
            Employee temp = evt.getRowValue();
            temp.setPhone(evt.getNewValue());
            hrApp.update(evt.getRowValue().getId(), temp);
            table.refresh();
        });

        TableColumn<Employee, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        ageCol.setCellFactory(TextFieldTableCell.<Employee, Integer>forTableColumn(new IntegerStringConverter()));
        ageCol.setOnEditCommit(evt -> {
            Employee temp = evt.getRowValue();
            temp.setAge(evt.getNewValue());
            hrApp.update(evt.getRowValue().getId(), temp);
            table.refresh();
        });

        TableColumn<Employee, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        salaryCol.setCellFactory(TextFieldTableCell.<Employee, Double>forTableColumn(new DoubleStringConverter()));

        salaryCol.setOnEditCommit(evt -> {
            Employee temp = evt.getRowValue();
            temp.setSalary(evt.getNewValue());
            hrApp.update(evt.getRowValue().getId(), temp);
            table.refresh();
        });

        TableColumn<Employee, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setOnEditCommit(evt -> {
            Employee temp = evt.getRowValue();
            temp.setAddress(evt.getNewValue());
            hrApp.update(evt.getRowValue().getId(), temp);
            table.refresh();
        });

        table.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            /**
             * Handle the row click event of the table
             * 
             */
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    System.out.println(row.getIndex());
                    Employee rowData = (Employee) row.getItem();
                    tableSelectIndex = row.getIndex();
                    romoveButton.setDisable(false);
                    /**
                     * If the clicked row of the table is a Doctor object, change the role and type
                     * ComboBox to Doctor.ROLES and Doctor.TYPES
                     * 
                     */
                    if (rowData instanceof Doctor) {
                        roleCol.setEditable(true);
                        roleCol.setCellFactory(ComboBoxTableCell.forTableColumn(Doctor.ROLES));
                        typeCol.setCellFactory(ComboBoxTableCell.forTableColumn(Doctor.TYPES));
                    }
                    /**
                     * If the clicked row of the table is a Nurse object, change the role and type
                     * ComboBox to Nurse.ROLES and Nurse.TYPES
                     * 
                     */
                    else if (rowData instanceof Nurse) {
                        roleCol.setEditable(true);
                        roleCol.setCellFactory(ComboBoxTableCell.forTableColumn(Nurse.ROLES));
                        typeCol.setCellFactory(ComboBoxTableCell.forTableColumn(Nurse.TYPES));
                    }
                    /**
                     * If the clicked row of the table is a Staff object, change type
                     * ComboBox to Staff.TYPES and change role ComboBox editable state to false
                     * 
                     */
                    else if (rowData instanceof Staff) {
                        roleCol.setEditable(false);
                        typeCol.setCellFactory(ComboBoxTableCell.forTableColumn(Staff.TYPES));
                    }
                }
            });
            return row;
        });

        table.getColumns().addAll(idCol, nameCol, sexCol, typeCol, roleCol, phoneCol, ageCol, salaryCol, addressCol);

        /**
         * Add demo data to hrApp
         * 
         */
        Doctor doctor = new Doctor(1, "John", "Male", 31, "1011", 20000.00, "Sheffield",
                "Family physicians", "Registrars");
        hrApp.add(doctor);

        Nurse nurse = new Nurse(2, "Julia", "Female", 31, "1012",
                20000.00, "London", "Rehabilitation Nurses", "nurse unit manager");
        hrApp.add(nurse);

        Staff staff = new Staff(3, "jennie", "Female", 32, "333000333",
                20000.00, "Sheffield", "porters");
        hrApp.add(staff);

        table.setItems(hrApp.getAll());

        /**
         * Show tips information
         * 
         */
        Label tipLabel = new Label("Tips:");
        tipLabel.setTextFill(Color.BLACK);
        tipLabel.setFont(Font.font("Arial", 15));
        TextField tipTextField = new TextField();
        tipTextField.setEditable(false);
        tipTextField.setMaxWidth(500);
        tipTextField.setPrefWidth(500);
        tipTextField.setPrefHeight(40);

        Label cbLabel = new Label("Employee Type:");
        cbLabel.setTextFill(Color.BLACK);
        cbLabel.setFont(Font.font("Arial", 15));
        ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList("Doctor", "Nurse", "Staff"));

        /**
         * Functional Interfaces to open a new window for add a employee to hrApp
         * 
         */
        CommonEventHandler addButtonEventHandler = e -> {
            System.out.println(cb.getValue());
            if (cb.getValue() == null) {
                tipTextField.setStyle("-fx-text-fill: red;");
                tipTextField.setText("Error! Please select the employee type.");
            } else {
                Stage secondStage = new Stage();
                secondStage.setTitle("Add Employee");

                Label tipLabel1 = new Label("Tips:");
                tipLabel1.setTextFill(Color.BLACK);
                tipLabel1.setFont(Font.font("Arial", 15));
                TextField tipTextField1 = new TextField();
                tipTextField1.setEditable(false);
                tipTextField1.setMaxWidth(300);
                tipTextField1.setPrefWidth(300);
                tipTextField1.setPrefHeight(40);

                HBox tipBox1 = new HBox(10);
                tipBox1.setAlignment(Pos.CENTER);
                tipBox1.getChildren().addAll(tipLabel1, tipTextField1);

                Label nameLabel = new Label("Name:");
                nameLabel.setTextFill(Color.BLACK);
                nameLabel.setFont(Font.font("Arial", 15));
                TextField nameTextField = new TextField();
                nameTextField.setMaxWidth(250);
                HBox nameBox = new HBox(10);
                nameBox.getChildren().addAll(nameLabel, nameTextField);
                nameBox.setAlignment(Pos.CENTER);

                Label sexLabel = new Label("Sex:");
                sexLabel.setTextFill(Color.BLACK);
                sexLabel.setFont(Font.font("Arial", 15));
                ChoiceBox<String> sexCb = new ChoiceBox<String>();
                HBox sexBox = new HBox(10);
                sexBox.getChildren().addAll(sexLabel, sexCb);
                sexBox.setAlignment(Pos.CENTER);
                sexCb.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

                Label phoneLabel = new Label("Phone:");
                phoneLabel.setTextFill(Color.BLACK);
                phoneLabel.setFont(Font.font("Arial", 15));
                TextField phoneTextField = new TextField();
                phoneTextField.setMaxWidth(250);
                HBox phoneBox = new HBox(10);
                phoneBox.getChildren().addAll(phoneLabel, phoneTextField);
                phoneBox.setAlignment(Pos.CENTER);

                Label ageLabel = new Label("Age:");
                ageLabel.setTextFill(Color.BLACK);
                ageLabel.setFont(Font.font("Arial", 15));
                TextFormatter<Integer> intFormatter = new TextFormatter<>(intConverter, 0, filter);
                TextField ageField = new TextField();
                ageField.setTextFormatter(intFormatter);
                HBox ageBox = new HBox(10);
                ageBox.getChildren().addAll(ageLabel, ageField);
                ageBox.setAlignment(Pos.CENTER);

                Label addressLabel = new Label("Address:");
                addressLabel.setTextFill(Color.BLACK);
                addressLabel.setFont(Font.font("Arial", 15));
                TextField addressTextField = new TextField();
                addressTextField.setMaxWidth(250);
                HBox addressBox = new HBox(10);
                addressBox.getChildren().addAll(addressLabel, addressTextField);
                addressBox.setAlignment(Pos.CENTER);

                Label salaryLabel = new Label("Salary:");
                salaryLabel.setTextFill(Color.BLACK);
                salaryLabel.setFont(Font.font("Arial", 15));
                TextFormatter<Double> doubleFormatter = new TextFormatter<>(doubleConverter, 0.0, filter);
                TextField salaryField = new TextField();
                salaryField.setTextFormatter(doubleFormatter);
                HBox salaryBox = new HBox(10);
                salaryBox.getChildren().addAll(salaryLabel, salaryField);
                salaryBox.setAlignment(Pos.CENTER);

                Label typeLabel = new Label("Type:");
                typeLabel.setTextFill(Color.BLACK);
                typeLabel.setFont(Font.font("Arial", 15));
                ChoiceBox<String> typeCb = new ChoiceBox<String>();
                HBox typeBox = new HBox(10);
                typeBox.getChildren().addAll(typeLabel, typeCb);
                typeBox.setAlignment(Pos.CENTER);

                Label roleLabel = new Label("Role:");
                roleLabel.setTextFill(Color.BLACK);
                roleLabel.setFont(Font.font("Arial", 15));
                ChoiceBox<String> roleCb = new ChoiceBox<String>();
                HBox roleBox = new HBox(10);
                roleBox.getChildren().addAll(roleLabel, roleCb);
                roleBox.setAlignment(Pos.CENTER);

                Label label = new Label();
                label.setTextFill(Color.BLACK);
                label.setFont(Font.font("Arial", 15));

                Button registerButton = new Button("Add");
                registerButton.setFont(Font.font("Arial", 15));

                VBox vbox = new VBox(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.getChildren().addAll(tipBox1, label, nameBox, sexBox, phoneBox, ageBox, addressBox, salaryBox);

                /**
                 * Functional Interfaces to add a subclass Doctor,
                 * Nurse or Staff object of abstract class Employee to hrApp
                 * 
                 */
                CommonEventHandler registerButtonActionProcess = ev -> {
                    System.out.println("Name: " + nameTextField.getText());
                    System.out.println("Sex: " + sexCb.getValue());
                    System.out.println("Phone: " + phoneTextField.getText());
                    System.out.println("Age: " + Integer.parseInt(ageField.getText()));
                    System.out.println("Address: " + addressTextField.getText());
                    System.out.println("Salary: " + Double.parseDouble(salaryField.getText()));
                    System.out.println("Role: " + roleCb.getValue());
                    System.out.println("Type: " + typeCb.getValue());
                    if (nameTextField.getText().trim().isEmpty()) {
                        tipTextField1.setStyle("-fx-text-fill: red;");
                        tipTextField1.setText("Error, name can not be null.");
                    } else if (sexCb.getValue() == null) {
                        tipTextField1.setStyle("-fx-text-fill: red;");
                        tipTextField1.setText("Error, sex can not be null.");
                    } else if (phoneTextField.getText().trim().isEmpty()) {
                        tipTextField1.setStyle("-fx-text-fill: red;");
                        tipTextField1.setText("Error, phone can not be null.");
                    } else if (addressTextField.getText().trim().isEmpty()) {
                        tipTextField1.setStyle("-fx-text-fill: red;");
                        tipTextField1.setText("Error, address can not be null.");
                    } else if (roleCb.getValue() == null && cb.getValue() != "Staff") {
                        tipTextField1.setStyle("-fx-text-fill: red;");
                        tipTextField1.setText("Error, role can not be null.");
                    } else if (typeCb.getValue() == null) {
                        tipTextField1.setStyle("-fx-text-fill: red;");
                        tipTextField1.setText("Error, type can not be null.");
                    } else {
                        if (cb.getValue() == "Doctor") {
                            hrApp.add(new Doctor(0, nameTextField.getText(), sexCb.getValue(),
                                    Integer.parseInt(ageField.getText()), phoneTextField.getText(),
                                    Double.parseDouble(salaryField.getText()), addressTextField.getText(),
                                    roleCb.getValue(), typeCb.getValue()));
                        } else if (cb.getValue() == "Nurse") {
                            hrApp.add(new Nurse(0, nameTextField.getText(), sexCb.getValue(),
                                    Integer.parseInt(ageField.getText()), phoneTextField.getText(),
                                    Double.parseDouble(salaryField.getText()), addressTextField.getText(),
                                    roleCb.getValue(), typeCb.getValue()));
                        } else if (cb.getValue() == "Staff") {
                            hrApp.add(new Staff(0, nameTextField.getText(), sexCb.getValue(),
                                    Integer.parseInt(ageField.getText()), phoneTextField.getText(),
                                    Double.parseDouble(salaryField.getText()), addressTextField.getText(),
                                    typeCb.getValue()));
                        }

                        secondStage.close();
                    }

                };

                if (cb.getValue() == "Doctor") {
                    label.setText("Doctor");
                    typeCb.setItems(FXCollections.observableArrayList(Doctor.TYPES));
                    roleCb.setItems(FXCollections.observableArrayList(Doctor.ROLES));
                    vbox.getChildren().addAll(typeBox, roleBox);
                } else if (cb.getValue() == "Nurse") {
                    label.setText("Nurse");
                    typeCb.setItems(FXCollections.observableArrayList(Nurse.TYPES));
                    roleCb.setItems(FXCollections.observableArrayList(Nurse.ROLES));
                    vbox.getChildren().addAll(typeBox, roleBox);
                } else if (cb.getValue() == "Staff") {
                    label.setText("Staff");
                    typeCb.setItems(FXCollections.observableArrayList(Staff.TYPES));
                    vbox.getChildren().addAll(typeBox);
                }

                registerButton.setOnAction(evt -> registerButtonActionProcess.eventProcess(evt));

                vbox.getChildren().add(registerButton);

                Scene secondScene = new Scene(vbox, 400, 600);
                secondScene.getRoot().setStyle("-fx-font-family: 'serif'");
                secondStage.setScene(secondScene);
                secondStage.show();
            }
        };

        Button addButton = new Button("Add Employee");
        addButton.setFont(Font.font("Arial", 15));
        addButton.setOnAction(evt -> addButtonEventHandler.eventProcess(evt));

        /**
         * Functional Interfaces to open a new window for search the employees in hrApp
         * 
         */
        CommonEventHandler searchEventHandler = e -> {
            Stage searchStage = new Stage();
            searchStage.setTitle("Search Employee");

            Label tipLabel2 = new Label("Tips:");
            tipLabel2.setTextFill(Color.BLACK);
            tipLabel2.setFont(Font.font("Arial", 15));
            TextField tipTextField2 = new TextField();
            tipTextField2.setEditable(false);
            tipTextField2.setMaxWidth(300);
            tipTextField2.setPrefWidth(300);
            tipTextField2.setPrefHeight(40);

            HBox tipBox2 = new HBox(10);
            tipBox2.setAlignment(Pos.CENTER);
            tipBox2.getChildren().addAll(tipLabel2, tipTextField2);

            Label nameLabel = new Label("Name:");
            nameLabel.setTextFill(Color.BLACK);
            nameLabel.setFont(Font.font("Arial", 15));
            TextField nameTextField = new TextField();
            nameTextField.setMaxWidth(250);

            Label sexLabel = new Label("Sex:");
            sexLabel.setTextFill(Color.BLACK);
            sexLabel.setFont(Font.font("Arial", 15));
            ChoiceBox<String> sexCb = new ChoiceBox<String>();
            sexCb.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

            Label phoneLabel = new Label("Phone:");
            phoneLabel.setTextFill(Color.BLACK);
            phoneLabel.setFont(Font.font("Arial", 15));
            TextField phoneTextField = new TextField();
            phoneTextField.setMaxWidth(250);

            Label ageLabel = new Label("Age:");
            ageLabel.setTextFill(Color.BLACK);
            ageLabel.setFont(Font.font("Arial", 15));
            TextFormatter<Integer> intFormatter = new TextFormatter<>(intConverter, 0, filter);
            TextField ageField = new TextField();
            ageField.setTextFormatter(intFormatter);

            Label addressLabel = new Label("Address:");
            addressLabel.setTextFill(Color.BLACK);
            addressLabel.setFont(Font.font("Arial", 15));
            TextField addressTextField = new TextField();
            addressTextField.setMaxWidth(250);

            Label salaryLabel = new Label("Salary:");
            salaryLabel.setTextFill(Color.BLACK);
            salaryLabel.setFont(Font.font("Arial", 15));
            TextFormatter<Double> doubleFormatter = new TextFormatter<>(doubleConverter, 0.0, filter);
            TextField salaryField = new TextField();
            salaryField.setTextFormatter(doubleFormatter);

            Label typeLabel = new Label("Type:");
            typeLabel.setTextFill(Color.BLACK);
            typeLabel.setFont(Font.font("Arial", 15));
            ChoiceBox<String> typeCb = new ChoiceBox<String>();
            HBox typeBox = new HBox(10);
            typeBox.getChildren().addAll(typeLabel, typeCb);
            typeBox.setAlignment(Pos.CENTER);

            Label roleLabel = new Label("Role:");
            roleLabel.setTextFill(Color.BLACK);
            roleLabel.setFont(Font.font("Arial", 15));
            ChoiceBox<String> roleCb = new ChoiceBox<String>();
            HBox roleBox = new HBox(10);
            roleBox.getChildren().addAll(roleLabel, roleCb);
            roleBox.setAlignment(Pos.CENTER);

            typeCb.setDisable(true);
            roleCb.setDisable(true);

            TableView<Employee> table1 = new TableView<>();
            table1.setEditable(false);
            ObservableList<Employee> employeesTemp = FXCollections.observableArrayList(hrApp.getAll());
            table1.setItems(employeesTemp);

            Button searchButton1 = new Button("Search");
            searchButton1.setFont(Font.font("Arial", 15));
            searchButton1.setOnAction(ev -> {
                System.out.println("Name: " + nameTextField.getText());
                System.out.println("Sex: " + sexCb.getValue());
                System.out.println("Phone: " + phoneTextField.getText());
                System.out.println("Age: " + Integer.parseInt(ageField.getText()));
                System.out.println("Address: " + addressTextField.getText());
                System.out.println("Salary: " + Double.parseDouble(salaryField.getText()));
                System.out.println("Role: " + roleCb.getValue());
                System.out.println("Type: " + typeCb.getValue());
                employeesTemp.clear();
                System.out.println(employeesTemp.size());

                /**
                 * If all search options are empty, show all employees
                 * 
                 */
                if (nameTextField.getText().trim().isEmpty() &&
                        sexCb.getValue() == null &&
                        phoneTextField.getText().trim().isEmpty() &&
                        addressTextField.getText().trim().isEmpty() &&
                        Integer.parseInt(ageField.getText()) == 0 &&
                        Double.parseDouble(salaryField.getText()) == 0 &&
                        roleCb.getValue() == null &&
                        typeCb.getValue() == null) {
                    employeesTemp.addAll(hrApp.getAll());
                } else {
                    for (int i = 0; i < hrApp.getAll().size(); i++) {

                        /**
                         * Set the initial search condition match detection value to false
                         * 
                         */
                        Boolean nameMatch = false;
                        Boolean phoneMatch = false;
                        Boolean ageMatch = false;
                        Boolean addressMatch = false;
                        Boolean salaryMatch = false;
                        Boolean roleMatch = false;
                        Boolean typeMatch = false;
                        Boolean sexMatch = false;

                        /**
                         * All employees whose name field contains the search value will be searched
                         * 
                         */
                        if (!nameTextField.getText().trim().isEmpty()) {
                            if (hrApp.get(i).getName().contains(nameTextField.getText().trim())) {
                                nameMatch = true;
                            }
                        } else {
                            nameMatch = true;
                        }

                        /**
                         * All employees whose phone field contains the search value will be searched
                         * 
                         */
                        if (!phoneTextField.getText().trim().isEmpty()) {
                            if (hrApp.get(i).getPhone().contains(phoneTextField.getText().trim())) {
                                phoneMatch = true;
                            }
                        } else {
                            phoneMatch = true;
                        }

                        /**
                         * All employees whose age field equal the search value will be searched
                         * 
                         */
                        if (Integer.parseInt(ageField.getText()) != 0) {
                            if (hrApp.get(i).getAge() == Integer.parseInt(ageField.getText())) {
                                ageMatch = true;
                            }
                        } else {
                            ageMatch = true;
                        }

                        /**
                         * All employees whose salary field equal the search value will be searched
                         * 
                         */
                        if (Double.parseDouble(salaryField.getText()) != 0) {
                            if (hrApp.get(i).getSalary() == Double.parseDouble(salaryField.getText())) {
                                salaryMatch = true;
                            }
                        } else {
                            salaryMatch = true;
                        }

                        /**
                         * All employees whose address field contains the search value will be searched
                         * 
                         */
                        if (!addressTextField.getText().trim().isEmpty()) {
                            if (hrApp.get(i).getAddress().contains(addressTextField.getText().trim())) {
                                addressMatch = true;
                            }
                        } else {
                            addressMatch = true;
                        }

                        /**
                         * All employees whose sex field equal the search value will be searched
                         * 
                         */
                        if (sexCb.getValue() != null) {
                            Employee temp = hrApp.get(i);

                            if (temp.getSex() == sexCb.getValue()) {
                                sexMatch = true;
                            }
                        } else {
                            sexMatch = true;
                        }

                        /**
                         * All employees whose role field equal the search value will be searched
                         * 
                         */
                        if (roleCb.getValue() != null) {
                            if (hrApp.get(i) instanceof Doctor) {
                                Doctor temp = (Doctor) hrApp.get(i);
                                if (temp.getRole() == roleCb.getValue()) {
                                    roleMatch = true;
                                }
                            } else if (hrApp.get(i) instanceof Nurse) {
                                Nurse temp = (Nurse) hrApp.get(i);
                                if (temp.getRole() == roleCb.getValue()) {
                                    roleMatch = true;
                                }
                            } else if (hrApp.get(i) instanceof Staff) {
                                roleMatch = false;
                            }
                        } else {
                            roleMatch = true;
                        }

                        /**
                         * All employees whose type field equal the search value will be searched
                         * 
                         */
                        if (typeCb.getValue() != null) {
                            if (hrApp.get(i) instanceof Doctor) {
                                Doctor temp = (Doctor) hrApp.get(i);
                                if (temp.getType() == typeCb.getValue()) {
                                    typeMatch = true;
                                }
                            } else if (hrApp.get(i) instanceof Nurse) {
                                Nurse temp = (Nurse) hrApp.get(i);
                                if (temp.getType() == typeCb.getValue()) {
                                    typeMatch = true;
                                }
                            } else if (hrApp.get(i) instanceof Staff) {
                                Staff temp = (Staff) hrApp.get(i);
                                if (temp.getType() == typeCb.getValue()) {
                                    typeMatch = true;
                                }
                            }
                        } else {
                            typeMatch = true;
                        }

                        /**
                         * If there are multiple search criteria, all must be true to be searched
                         * 
                         */
                        if (nameMatch && sexMatch && phoneMatch && ageMatch && addressMatch && salaryMatch && roleMatch
                                && typeMatch) {
                            System.out.println(hrApp.get(i).toString());
                            employeesTemp.add(hrApp.get(i));
                        }
                    }
                }

            });

            Label searchCbLabel = new Label("Employee Type:");
            searchCbLabel.setTextFill(Color.BLACK);
            searchCbLabel.setFont(Font.font("Arial", 15));
            ChoiceBox<String> searchCb = new ChoiceBox<String>(
                    FXCollections.observableArrayList("Doctor", "Nurse", "Staff"));
            searchCb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> selected, String oldV, String newV) {
                    System.out.println(selected);
                    System.out.println(oldV);
                    System.out.println(newV);
                    if (newV == "Doctor") {
                        typeCb.setItems(FXCollections.observableArrayList(Doctor.TYPES));
                        roleCb.setItems(FXCollections.observableArrayList(Doctor.ROLES));
                        typeCb.setDisable(false);
                        roleCb.setDisable(false);
                    } else if (newV == "Nurse") {
                        typeCb.setItems(FXCollections.observableArrayList(Nurse.TYPES));
                        roleCb.setItems(FXCollections.observableArrayList(Nurse.ROLES));
                        typeCb.setDisable(false);
                        roleCb.setDisable(false);
                    } else if (newV == "Staff") {
                        typeCb.setItems(FXCollections.observableArrayList(Staff.TYPES));
                        roleCb.setValue(null);
                        typeCb.setDisable(false);
                        roleCb.setDisable(true);
                    }
                }
            });

            Button clearButton = new Button("Clear");
            clearButton.setFont(Font.font("Arial", 15));

            /**
             * Clear the value of all search criteria
             * 
             */
            clearButton.setOnAction(ev -> {
                nameTextField.setText("");
                sexCb.setValue(null);
                phoneTextField.setText("");
                addressTextField.setText("");
                ageField.setText("0");
                salaryField.setText("0");
                typeCb.setValue(null);
                typeCb.setItems(FXCollections.observableArrayList());
                roleCb.setValue(null);
                roleCb.setItems(FXCollections.observableArrayList());
                searchCb.setValue(null);
                typeCb.setDisable(true);
                roleCb.setDisable(true);
                employeesTemp.clear();
                employeesTemp.addAll(hrApp.getAll());
            });

            TableColumn<Employee, String> idCol1 = new TableColumn<>("Id");
            idCol1.setCellFactory(new IDCell<>());

            TableColumn<Employee, String> nameCol1 = new TableColumn<>("Name");
            nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Employee, String> sexCol1 = new TableColumn<>("Sex");
            sexCol1.setCellValueFactory(new PropertyValueFactory<>("sex"));

            TableColumn<Employee, String> typeCol1 = new TableColumn<>("Type");
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));

            TableColumn<Employee, String> roleCol1 = new TableColumn<>("Role");
            roleCol1.setCellValueFactory(new PropertyValueFactory<>("role"));

            TableColumn<Employee, String> phoneCol1 = new TableColumn<>("Phone");
            phoneCol1.setCellValueFactory(new PropertyValueFactory<>("phone"));

            TableColumn<Employee, Integer> ageCol1 = new TableColumn<>("Age");
            ageCol1.setCellValueFactory(new PropertyValueFactory<>("age"));

            TableColumn<Employee, Double> salaryCol1 = new TableColumn<>("Salary");
            salaryCol1.setCellValueFactory(new PropertyValueFactory<>("salary"));

            TableColumn<Employee, String> addressCol1 = new TableColumn<>("Address");
            addressCol1.setCellValueFactory(new PropertyValueFactory<>("address"));

            table1.getColumns().addAll(idCol1, nameCol1, sexCol1, typeCol1, roleCol1, phoneCol1, ageCol1, salaryCol1,
                    addressCol1);

            VBox searchVbox = new VBox(10);
            HBox searchHbox = new HBox(10);
            searchHbox.setAlignment(Pos.CENTER);
            searchHbox.getChildren().addAll(nameLabel, nameTextField, sexLabel, sexCb, phoneLabel, phoneTextField);
            HBox searchHbox1 = new HBox(10);
            searchHbox1.setAlignment(Pos.CENTER);
            searchHbox1.getChildren().addAll(addressLabel, addressTextField, salaryLabel, salaryField, ageLabel,
                    ageField);
            HBox searchhbox2 = new HBox(10);
            searchhbox2.setAlignment(Pos.CENTER);
            searchhbox2.getChildren().addAll(searchCbLabel, searchCb, roleBox, typeBox);
            HBox searchhbox3 = new HBox(10);
            searchhbox3.setAlignment(Pos.CENTER);
            searchhbox3.getChildren().addAll(clearButton, searchButton1);

            searchVbox.setAlignment(Pos.CENTER);
            searchVbox.getChildren().addAll(tipBox2, searchHbox, searchHbox1, searchhbox2, searchhbox3, table1);

            Scene searchScene = new Scene(searchVbox, 800, 600);
            searchScene.getRoot().setStyle("-fx-font-family: 'serif'");
            searchStage.setScene(searchScene);
            searchStage.show();
        };

        Button searchButton = new Button("Search Employee");
        searchButton.setFont(Font.font("Arial", 15));
        searchButton.setOnAction(evt -> searchEventHandler.eventProcess(evt));

        HBox tipBox = new HBox(10);
        HBox hBox = new HBox(10);
        VBox root = new VBox(10);

        tipBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);

        tipBox.getChildren().addAll(tipLabel, tipTextField);
        hBox.getChildren().addAll(cb, addButton);

        /**
         * add Main menu
         * 
         */
        Menu mainMenu = new Menu("Main");

        MenuBar menuBar = new MenuBar();
        MenuItem menuItem1 = new MenuItem("Search");
        MenuItem menuItem2 = new MenuItem("Exit");

        /**
         * Open a new window for search the employees in hrApp
         * 
         */
        menuItem1.setOnAction(evt -> searchEventHandler.eventProcess(evt));

        /**
         * exit the application, close all windows
         * 
         */
        menuItem2.setOnAction(evt -> Platform.exit());
        mainMenu.getItems().add(menuItem1);
        mainMenu.getItems().add(menuItem2);

        menuBar.getMenus().add(mainMenu);

        root.getChildren().addAll(menuBar, tipBox, table, hBox, romoveButton, searchButton);

        Scene scene = new Scene(root, 800, 600, Color.YELLOW);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage.setTitle("Hospital Staff Management System");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}