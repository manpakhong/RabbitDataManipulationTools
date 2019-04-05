package com.rabbitforever.datamanipulation.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.services.SnapshotMgr;
import com.rabbitforever.datamanipulation.views.EditView;

public class CheckDependentTablesActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private EditView editView;

	public CheckDependentTablesActionHandler(EditView editView) {
		this.editView = editView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			String tableName = editView.getTableNameTextField().getText();
			SnapshotMgr snapshotMgr = new SnapshotMgr();
			List<String> dependentTableNameList = snapshotMgr.getAllDependentTableNameList(tableName);
			if (dependentTableNameList == null || dependentTableNameList.size() == 0){
				JOptionPane.showMessageDialog(editView.getEditFrame(), "No dependent table found.");
			} else {
				StringBuilder sbTableNames = new StringBuilder();
				int count = 0;
				for (String dependentTableName: dependentTableNameList){
					if (count > 0){
						sbTableNames.append(", ");
					}
					sbTableNames.append("\"" + dependentTableName + "\"");
					count++;
				}
				JOptionPane.showMessageDialog(editView.getEditFrame(), "Tables with dependency:\n" + sbTableNames.toString() + ".");
			}
		} catch (Exception ex){
			logger.error(className + ".actionPerformed()", ex);
			JOptionPane.showMessageDialog(editView.getEditFrame(), "Exception caught! check console message for reference! \n(*NoSuchTableException - usually the problem of table name case sensitive!)");
		}
	}
}
