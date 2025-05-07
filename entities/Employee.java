package entities;

public class Employee {
	private int id;
	private String nomComplet;
	private String gender;
	private String department;
	private String position;
	private double salary;
	private static int cpt;

	public Employee(String nomComplet, String gender, String department, String position, double salary) {
		this.nomComplet = nomComplet;
		this.gender = gender;
		this.department = department;
		this.position = position;
		this.salary = salary;
	}

	public Employee(int id, String nomComplet, String gender, String department, String position, double salary) {
		this.id = id;
		this.nomComplet = nomComplet;
		this.gender = gender;
		this.department = department;
		this.position = position;
		this.salary = salary;
	}

	public Employee() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
}
