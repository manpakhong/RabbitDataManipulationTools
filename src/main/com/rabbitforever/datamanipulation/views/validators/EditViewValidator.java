package com.rabbitforever.datamanipulation.views.validators;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.models.vos.CaptureVo;
import com.rabbitforever.datamanipulation.views.EditView;
import com.rabbitforever.datamanipulation.views.MainScreenView;

public class EditViewValidator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public boolean isValid(EditView editView) {
		boolean isValid = true;
		try {
			String tableName = editView.getTableNameTextField().getText();
			String outputXmlFileName = editView.getOutputXmlFileNameTextField().getText();
			String sql = editView.getCaptureSqlTextArea().getText();
			String actualAssertionTargetSql = editView.getActualAssertionTargetSqlTextArea().getText();
			String actualAssertionTargetIgnoreColumnsList = editView.getActualAssertionTargetIgnoreColumnsListTextArea().getText();
			
			if (tableName == null || tableName.isEmpty()) {
				isValid = false;
			}

			if (outputXmlFileName == null || outputXmlFileName.isEmpty()) {
				isValid = false;
			}

			if (sql == null || sql.isEmpty()) {
				isValid = false;
			}
		} catch (Exception e) {
        	logger.error(className + ".isValid() - editView=" + editView ,e);
		}
		return isValid;
	}

	public boolean isDuplicatedRecord(EditView editView) {
		boolean isDuplicated = false;
		try{
			String tableName = editView.getTableNameTextField().getText();
			String outputXmlFileName = editView.getOutputXmlFileNameTextField().getText();
			String sql = editView.getCaptureSqlTextArea().getText();
			MainScreenView mainView = editView.getMainView();
			List<CaptureVo> captureVoList = mainView.getCaptureScopeVo().getCaptureVoList();
	
			for (CaptureVo captureVoLoop : captureVoList) {
				if (captureVoLoop.getOutputXmlFileName().equals(outputXmlFileName)) {
					isDuplicated = true;
				}
			}
		} catch (Exception e){
        	logger.error(className + ".isDuplicatedRecord() - editView=" + editView ,e);
		}
		return isDuplicated;
	}

}
