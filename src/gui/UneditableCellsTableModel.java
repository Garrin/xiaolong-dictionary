package gui;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class UneditableCellsTableModel extends DefaultTableModel {

	public UneditableCellsTableModel(String[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	/*
	@Override
	public void setDataVector(Object[][] rowData, Object[] columnNames) {
		//this.columnNames = (String[]) columnNames;
		setColumnIdentifiers(columnNames);
		for(int i = 0; i < rowData.length; i++) {
			setValueAt(i, i, 0);
			setValueAt(rowData[i][1], i, 1);
			setValueAt(rowData[i][2], i, 2);
			setValueAt(rowData[i][3], i, 3);
		}
	}*/
}
