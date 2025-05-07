package DAO;

import java.util.List;

import entities.Employee;

public interface IGestionEmployee {
	
	boolean authenticateAdmin(String username, String password);
	void addEmployee(Employee e);
	void deleteEmployee(int id);
	void UpdateEmployee(Employee e);
	List<Employee> getAllEmployees();
	List<Employee> getEmployeeByMC(int i);
	List<Employee> searchEmployeesByName(String keyword);

	
}

