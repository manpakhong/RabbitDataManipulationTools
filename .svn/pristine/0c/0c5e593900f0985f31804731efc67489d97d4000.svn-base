package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.helpers.CaptureVoHelper;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureScopeVo;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureVo;
import hksarg.swd.csss.csa.flowtest.ui.CaptureVoJTable;
import hksarg.swd.csss.csa.flowtest.views.MainScreenView;

public class MoveSelectedRecordUpButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainView;
	private CaptureVoHelper captureVoHelper;

	public MoveSelectedRecordUpButtonActionHandler(MainScreenView mainView) {
		this.mainView = mainView;
		captureVoHelper = new CaptureVoHelper();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			CaptureVoJTable mainTable = this.mainView.getMainTable();
			int selectedRow = mainTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "Select rows before moving up record!");
				return;
			}
			CaptureVo selectedCaptureVo = getSelectedCaptureVo(selectedRow);
			CaptureScopeVo captureScopeVo = this.mainView.getCaptureScopeVo();
			List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
			List<CaptureVo> captureVoSortedList = new ArrayList<CaptureVo>();
			Integer positionA = null;
			Integer positionB = null;
			if (selectedCaptureVo != null) {
				for (int i = 0; i < captureVoList.size(); i++) {
					CaptureVo captureVo = captureVoList.get(i);
					if (selectedCaptureVo.getTableName() != null && selectedCaptureVo.getOutputXmlFileName() != null) {
						if (captureVo.getTableName() != null && captureVo.getOutputXmlFileName() != null) {
							if (selectedCaptureVo.getTableName().equals(captureVo.getTableName()) && selectedCaptureVo
									.getOutputXmlFileName().equals(captureVo.getOutputXmlFileName())) {
								if (i != 0) {
									positionA = i;
									positionB = positionA - 1;
									break;
								}
							}
						}
					}
				}
			}
			if (positionA != null && positionB != null) {
				for (int i = 0; i < captureVoList.size(); i++) {
					CaptureVo captureVo = captureVoList.get(i);
					if (i == positionA) {
						captureVoSortedList.add(captureVoList.get(positionB));
					} else if (i == positionB) {
						captureVoSortedList.add(captureVoList.get(positionA));
					} else {
						captureVoSortedList.add(captureVo);
					}
				}
			}
			if (captureVoList.size() == captureVoSortedList.size()) {
				captureVoList = captureVoSortedList;
				captureScopeVo.setCaptureVoList(captureVoList);
				mainView.bindCaptureVo2TableModel();
				
				mainTable.setRowSelectionInterval(selectedRow -1, selectedRow -1);
			}

		} catch (Exception ex) {
			logger.error(className + ".actionPerformed() - ", ex);
			JOptionPane.showMessageDialog(mainView.getMainFrame(), "Exception found, please check colsole message!");
		}
	}

	private CaptureVo getSelectedCaptureVo(int selectedRow) throws Exception {
		CaptureVo captureVo = null;
		try {
			CaptureVoJTable mainTable = this.mainView.getMainTable();
			TableModel tableModel = mainTable.getModel();
			captureVo = new CaptureVo();
			for (int i = 0; i < tableModel.getColumnCount(); i++) {
				Object value = tableModel.getValueAt(selectedRow, i);
				String valueStr = null;
				switch (i) {
				case CaptureVoJTable.FIELD_INDEX_TABLE_NAME:
					valueStr = (String) value;
					captureVo.setTableName(valueStr);
					break;
				case CaptureVoJTable.FIELD_INDEX_XML_FILE_NAME:
					valueStr = (String) value;
					captureVo.setOutputXmlFileName(valueStr);
					break;
				case CaptureVoJTable.FIELD_INDEX_CAPTURE_SQL:
					valueStr = (String) value;
					captureVo.setCaptureSql(valueStr);
					break;
				case CaptureVoJTable.FIELD_INDEX_ACTUAL_TABLE_ASSERTION_SQL:
					valueStr = (String) value;
					captureVo.setActualAssertionTargetSql(valueStr);
					break;
				case CaptureVoJTable.FIELD_INDEX_ACTUAL_TABLE_ASS_IGNORE_COLS:
					valueStr = (String) value;
					captureVo.setActualAssertionTargetIgnoreColumnsList(valueStr);
					break;
				}

			}
		} catch (Exception e) {
			logger.error(className + ".getSelectedCaptureVo() - ", e);
			throw e;
		}
		return captureVo;
	}
}
