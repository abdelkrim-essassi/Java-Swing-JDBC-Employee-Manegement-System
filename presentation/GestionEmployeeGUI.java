package presentation;

import java.awt.*;
import javax.swing.*;
import DAO.GestionEmployeeJDBC;
import DAO.IGestionEmployee;
import entities.Employee;

public class GestionEmployeeGUI extends JFrame {

    JLabel lFullName = new JLabel("Full Name:");
    JLabel lGender = new JLabel("Gender:");
    JLabel lDepartment = new JLabel("Department:");
    JLabel lPosition = new JLabel("Position:");
    JLabel lSalary = new JLabel("Salary:");
    JLabel lSearch = new JLabel("Search:");

    JTextField tFullName = new JTextField(20);
    JComboBox<String> genderCombo = new JComboBox<>(new String[] {"Male", "Female"});
    JTextField tDepartment = new JTextField(10);
    JTextField tPosition = new JTextField(20);
    JTextField tSalary = new JTextField(10);
    JTextField tSearch = new JTextField(20);

    JButton addNew = new JButton("Add New");
    JButton update = new JButton("Update");
    JButton delete = new JButton("Delete");
    JButton close = new JButton("Close");
    JButton searchBtn = new JButton("Search");
    JButton clearSearch = new JButton("Clear");
    JButton messageButton = new JButton("Message");  

    TableModele tableModele = new TableModele();
    JTable tableau = new JTable(tableModele);
    JScrollPane jsp = new JScrollPane(tableau);

    IGestionEmployee gestion = new GestionEmployeeJDBC();

    public GestionEmployeeGUI() {
        super("Employee Records Editor");
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(lSearch);
        topPanel.add(tSearch);
        topPanel.add(searchBtn);
        topPanel.add(clearSearch);
        add(topPanel, BorderLayout.NORTH);

       
        add(jsp, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Records Editor"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lFullName, gbc);
        gbc.gridx = 1;
        formPanel.add(tFullName, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        formPanel.add(lGender, gbc);
        gbc.gridx = 3;
        formPanel.add(genderCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lDepartment, gbc);
        gbc.gridx = 1;
        formPanel.add(tDepartment, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        formPanel.add(lPosition, gbc);
        gbc.gridx = 3;
        formPanel.add(tPosition, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lSalary, gbc);
        gbc.gridx = 1;
        formPanel.add(tSalary, gbc);

        bottomPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addNew);
        buttonPanel.add(update);
        buttonPanel.add(delete);
        buttonPanel.add(messageButton);  
        buttonPanel.add(close);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        tableModele.charger(gestion.getAllEmployees());

        tableau.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableau.getSelectedRow();
                if (selectedRow >= 0) {
                    Employee selectedEmployee = tableModele.getEmployee(selectedRow);
                    tFullName.setText(selectedEmployee.getNomComplet());
                    genderCombo.setSelectedItem(selectedEmployee.getGender());
                    tDepartment.setText(selectedEmployee.getDepartment());
                    tPosition.setText(selectedEmployee.getPosition());
                    tSalary.setText(String.valueOf(selectedEmployee.getSalary()));
                }
            }
        });

        
        addNew.addActionListener(e -> {
            try {
                String nomComplet = tFullName.getText();
                String gender = (String) genderCombo.getSelectedItem();
                String department = tDepartment.getText();
                String position = tPosition.getText();
                double salary = Double.parseDouble(tSalary.getText());

                Employee emp = new Employee(nomComplet, gender, department, position, salary);
                gestion.addEmployee(emp);

                tableModele.charger(gestion.getAllEmployees());
                clearForm();
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid salary format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

       
        update.addActionListener(e -> {
            int selectedRow = tableau.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    Employee selectedEmployee = tableModele.getEmployee(selectedRow);
                    
                    String nomComplet = tFullName.getText();
                    String gender = (String) genderCombo.getSelectedItem();
                    String department = tDepartment.getText();
                    String position = tPosition.getText();
                    double salary = Double.parseDouble(tSalary.getText());

                    Employee updatedEmployee = new Employee(
                        selectedEmployee.getId(), 
                        nomComplet, 
                        gender, 
                        department, 
                        position, 
                        salary
                    );

                    gestion.UpdateEmployee(updatedEmployee);
                    tableModele.charger(gestion.getAllEmployees());
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Employee updated successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid salary format!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee to update", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

     
        delete.addActionListener(e -> {
            int selectedRow = tableau.getSelectedRow();
            if (selectedRow >= 0) {
                int response = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this employee?", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    try {
                        Employee selectedEmployee = tableModele.getEmployee(selectedRow);
                        gestion.deleteEmployee(selectedEmployee.getId());
                        tableModele.charger(gestion.getAllEmployees());
                        clearForm();
                        JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

   
        searchBtn.addActionListener(e -> {
            String searchTerm = tSearch.getText().trim();
            if (!searchTerm.isEmpty()) {
                tableModele.charger(gestion.searchEmployeesByName(searchTerm));
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search term", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

    
        clearSearch.addActionListener(e -> {
            tSearch.setText("");
            tableModele.charger(gestion.getAllEmployees());
        });

        close.addActionListener(e -> dispose());
     
        messageButton.addActionListener(e -> {
            int selectedRow = tableau.getSelectedRow();
            if (selectedRow >= 0) {
                Employee selectedEmployee = tableModele.getEmployee(selectedRow);
                new MessageWindow(selectedEmployee.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee to message", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        pack();
    }

    private void clearForm() {
        tFullName.setText("");
        genderCombo.setSelectedIndex(0);
        tDepartment.setText("");
        tPosition.setText("");
        tSalary.setText("");
    }
}