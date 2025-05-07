package DAO;

  

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

  

import entities.Employee;

  

public class GestionEmployeeJDBC implements IGestionEmployee {

  

private final Connection cx;

  

public GestionEmployeeJDBC() {

this.cx = SingletonConnection.getInstnce();

}

@Override

public boolean authenticateAdmin(String username, String password) {

String adminUsername = "rh@company.tn";

String adminPassword = "rh123";

return adminUsername.equals(username) && adminPassword.equals(password);

}

  

@Override

public void addEmployee(Employee e) {

String sql = "INSERT INTO Employees(nom_complet, gender, department, position, salary) VALUES (?, ?, ?, ?, ?)";

try (PreparedStatement ps = cx.prepareStatement(sql)) {

ps.setString(1, e.getNomComplet());

ps.setString(2, e.getGender());

ps.setString(3, e.getDepartment());

ps.setString(4, e.getPosition());

ps.setDouble(5, e.getSalary());

ps.executeUpdate();

} catch (SQLException ex) {

ex.printStackTrace();

throw new RuntimeException("Failed to add employee", ex);

}

}

  

@Override

public void deleteEmployee(int id) {

String sql = "DELETE FROM Employees WHERE id = ?";

try (PreparedStatement ps = cx.prepareStatement(sql)) {

ps.setInt(1, id);

ps.executeUpdate();

} catch (SQLException ex) {

ex.printStackTrace();

throw new RuntimeException("Failed to delete employee with id: " + id, ex);

}

}

  

@Override

public List<Employee> getAllEmployees() {

List<Employee> employees = new ArrayList<>();

String sql = "SELECT * FROM Employees";

  

try (PreparedStatement ps = cx.prepareStatement(sql);

ResultSet rs = ps.executeQuery()) {

while (rs.next()) {

Employee emp = new Employee(

rs.getInt("id"),

rs.getString("nom_complet"),

rs.getString("gender"),

rs.getString("department"),

rs.getString("position"),

rs.getDouble("salary")

);

employees.add(emp);

}

} catch (SQLException ex) {

ex.printStackTrace();

throw new RuntimeException("Failed to retrieve employees", ex);

}

return employees;

}

  

@Override

public List<Employee> getEmployeeByMC(int id) {

List<Employee> employees = new ArrayList<>();

String sql = "SELECT * FROM Employees WHERE id = ?";

  

try (PreparedStatement ps = cx.prepareStatement(sql)) {

ps.setInt(1, id);

try (ResultSet rs = ps.executeQuery()) {

while (rs.next()) {

Employee emp = new Employee(

rs.getInt("id"),

rs.getString("nom_complet"),

rs.getString("gender"),

rs.getString("department"),

rs.getString("position"),

rs.getDouble("salary")

);

employees.add(emp);

}

}

} catch (SQLException ex) {

ex.printStackTrace();

throw new RuntimeException("Failed to find employee with id: " + id, ex);

}

return employees;

}

  

@Override

public void UpdateEmployee(Employee e) {

String sql = "UPDATE Employees SET nom_complet = ?, gender = ?, department = ?, position = ?, salary = ? WHERE id = ?";

try (PreparedStatement ps = cx.prepareStatement(sql)) {

ps.setString(1, e.getNomComplet());

ps.setString(2, e.getGender());

ps.setString(3, e.getDepartment());

ps.setString(4, e.getPosition());

ps.setDouble(5, e.getSalary());

ps.setInt(6, e.getId());

ps.executeUpdate();

} catch (SQLException ex) {

ex.printStackTrace();

throw new RuntimeException("Failed to update employee with id: " + e.getId(), ex);

}

}

  

@Override

public List<Employee> searchEmployeesByName(String keyword) {

List<Employee> employees = new ArrayList<>();

String sql = "SELECT * FROM Employees WHERE nom_complet LIKE ?";

  

try (PreparedStatement ps = cx.prepareStatement(sql)) {

ps.setString(1, "%" + keyword + "%");

try (ResultSet rs = ps.executeQuery()) {

while (rs.next()) {

Employee emp = new Employee(

rs.getInt("id"),

rs.getString("nom_complet"),

rs.getString("gender"),

rs.getString("department"),

rs.getString("position"),

rs.getDouble("salary")

);

employees.add(emp);

}

}

} catch (SQLException ex) {

ex.printStackTrace();

throw new RuntimeException("Failed to search employees by name: " + keyword, ex);

}

return employees;

}


}