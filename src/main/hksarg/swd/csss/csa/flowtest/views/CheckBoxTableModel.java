package hksarg.swd.csss.csa.flowtest.views;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CheckBoxTableModel extends AbstractTableModel {

	private Object rowData[][];
	private String columnNames[];
	private List<String> columnNameList;
	
	public CheckBoxTableModel(){
		columnNameList = new ArrayList<String>();
	}
	
	@Override
	public int getRowCount() {
		 return rowData.length;
	}

	@Override
	public int getColumnCount() {
		 return rowData.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	    return rowData[rowIndex][columnIndex];
	}

	public void addColumn(String columnName){
		columnNameList.add(columnName);
		columnNames = columnNameList.toArray(new String[columnNameList.size()]);
	}
	
}
