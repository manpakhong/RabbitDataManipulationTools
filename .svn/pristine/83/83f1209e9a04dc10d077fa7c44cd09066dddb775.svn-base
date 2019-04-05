package hksarg.swd.csss.csa.flowtest.views.handlers;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureDto;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureVo;
import hksarg.swd.csss.csa.flowtest.views.CaptureMainView;
import hksarg.swd.csss.csa.flowtest.views.EditView;
import hksarg.swd.csss.csa.flowtest.views.MainScreenView;
import hksarg.swd.csss.csa.flowtest.views.validators.EditViewValidator;

public class OkButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private EditView editView;

	public OkButtonActionHandler(EditView editView) {
		this.editView = editView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainScreenView mainView = editView.getMainView();
		try {
			CaptureVo captureVo = editView.getCaptureVo();
			String tableName = editView.getTableNameTextField().getText();
			String outputXmlFileName = editView.getOutputXmlFileNameTextField().getText();
			String captureSql = editView.getCaptureSqlTextArea().getText();
			String actualAssertionTargetSql = editView.getActualAssertionTargetSqlTextArea().getText();
			String actualAssertionTargetIgnoreColumnsList = editView.getActualAssertionTargetIgnoreColumnsListTextArea().getText();
			
			
			captureVo.setTableName(tableName);
			captureVo.setOutputXmlFileName(outputXmlFileName);
			captureVo.setCaptureSql(captureSql);
			
			if (actualAssertionTargetSql != null && !actualAssertionTargetSql.isEmpty()){
				captureVo.setActualAssertionTargetSql(actualAssertionTargetSql);
			} else {
				captureVo.setActualAssertionTargetSql(CaptureDto.ACTUAL_ASSERTION_TARGET_SQL_BLANK);
			}
			
			if (actualAssertionTargetIgnoreColumnsList != null && !actualAssertionTargetIgnoreColumnsList.isEmpty()){
				captureVo.setActualAssertionTargetIgnoreColumnsList(actualAssertionTargetIgnoreColumnsList);
			} else {
				captureVo.setActualAssertionTargetIgnoreColumnsList(CaptureDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_BLANK);
			}
			
			

			
			String currentMode = mainView.getCurrentMode();
			List<CaptureVo> captureVoList = mainView.getCaptureScopeVo().getCaptureVoList();
			
			EditViewValidator validator = new EditViewValidator();
			
			if (!validator.isValid(editView)){
				JOptionPane.showMessageDialog(editView.getEditFrame(), "All fields must have input!");
				return;
			}
			
			
			if (currentMode.equals(CaptureMainView.MODE_ADD)){
				if (!validator.isDuplicatedRecord(editView)){
					captureVoList.add(captureVo);				
				} else {
					JOptionPane.showMessageDialog(editView.getEditFrame(), "Duplicated record is found!");
				}
			}
			
			if (currentMode.equals(CaptureMainView.MODE_EDIT)){
				for (CaptureDto captureVoLoop : captureVoList) {
					if (captureVoLoop.getOutputXmlFileName().equals(outputXmlFileName)) {
						captureVoLoop.setCaptureSql(captureSql);
						captureVoLoop.setTableName(tableName);
					}
				}
			}
			
			if (currentMode.equals(CaptureMainView.MODE_DELETE)){
				for (int i =0; i < captureVoList.size(); i++) {
					if (captureVoList.get(i).getOutputXmlFileName().equals(outputXmlFileName)) {
						captureVoList.remove(i);
					}
				}
			}
			mainView.removeAllRowsFromTableModel();
			mainView.bindCaptureVo2TableModel();
			mainView.getTableModel().fireTableDataChanged();
			editView.getEditFrame().dispose();
			editView = null;
		} catch (Exception ex) {
        	logger.error(className + ".actionPerformed() - ",e);
        	JOptionPane.showMessageDialog(mainView.getMainFrame(), "Exception found, please check colsole message!");
		}
	}

	
	
}
