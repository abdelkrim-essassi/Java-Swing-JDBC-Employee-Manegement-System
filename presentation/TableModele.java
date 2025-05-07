package presentation;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entities.Employee;

public class TableModele extends AbstractTableModel {
	
	private List<Employee> liste = new ArrayList<>();
	private String titres[] = {"Id", "Nom Complet", "Gender", "Department", "Position", "Salary"};

	@Override
	public int getRowCount() {
		return liste.size();
	}

	@Override
	public int getColumnCount() {
		return titres.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Employee emp = liste.get(rowIndex);
		switch(columnIndex) {
			case 0: return emp.getId();
			case 1: return emp.getNomComplet();
			case 2: return emp.getGender();
			case 3: return emp.getDepartment();
			case 4: return emp.getPosition();
			case 5: return emp.getSalary();
			default: return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return titres[column];
	}

	public void charger(List<Employee> l) {
		this.liste = l;
		fireTableDataChanged();
	}

	// ✅ This method is needed for deletion
	public Employee getEmployee(int rowIndex) {
		return liste.get(rowIndex);
	}
}
