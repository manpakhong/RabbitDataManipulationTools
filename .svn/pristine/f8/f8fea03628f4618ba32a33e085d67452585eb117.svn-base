package hksarg.swd.csss.csa.flowtest.ui;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import hksarg.swd.csss.csa.flowtest.views.MainScreenView;
import hksarg.swd.csss.csa.flowtest.views.RestoreMainView;

public class CaptureVoJTable extends JTable {
	private static final long serialVersionUID = 8974545327267268810L;
	private Map<Integer, Boolean> editableMap = new HashMap<Integer, Boolean>();
	private MainScreenView mainScreenView;
	
	public final static int FIELD_INDEX_TABLE_NAME = 0;
	public final static int FIELD_INDEX_XML_FILE_NAME = 1;
	public final static int FIELD_INDEX_CAPTURE_SQL = 2;
	public final static int FIELD_INDEX_ACTUAL_TABLE_ASSERTION_SQL = 3;
	public final static int FIELD_INDEX_ACTUAL_TABLE_ASS_IGNORE_COLS = 4;
	
	public CaptureVoJTable(MainScreenView mainScreenView){
		this.mainScreenView = mainScreenView;
	}
	public void setEditableMap(Map<Integer, Boolean> editableMap) {
		this.editableMap = editableMap;
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		Component c = super.prepareRenderer(renderer, row, col);
		if (mainScreenView instanceof RestoreMainView){
			if (col == 0) {
				Boolean value = (Boolean) this.dataModel.getValueAt(row, col);
				if (value == null || value == false) {
	//				c.setEnabled(false);
	//				c.setVisible(false);
				}
			}
		}
		return c;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		Component c = this.getComponentAt(row, column);
		if (mainScreenView instanceof RestoreMainView){
			if (column == 0) {
				Object o = getValueAt(row, column);
				if (o == null) {
					return false;
				} else {
					Boolean isDeletedValide = (Boolean) o;
					if (isDeletedValide == false) {
						Boolean isEditable = editableMap.get(row);
	
						if (!isEditable){
	//						c.setEnabled(false);
	//						c.setBackground(Color.RED);
							return false;
						}
	
					} else {
	
					}
				}
			}
		}
//		c.setEnabled(true);
		return true;
	}
	
	
}
