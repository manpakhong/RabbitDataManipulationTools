package com.rabbitforever.datamanipulation.views;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.models.vos.CaptureScopeVo;
import com.rabbitforever.datamanipulation.models.vos.CaptureVo;
import com.rabbitforever.datamanipulation.services.SnapshotMgr;
import com.rabbitforever.datamanipulation.ui.CaptureVoJTable;

public abstract class MainScreenView {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	protected JFrame mainFrame = null;
	protected JPanel mainPanel = null;
	protected JButton addButton = null;
	protected JButton editButton = null;
	protected JButton deleteButton = null;
	protected JButton viewButton = null;
	protected JButton saveButton = null;
	protected JButton executeButton = null;
	protected JButton restoreButton = null;
	protected JButton verAndGenDelStatementsButton = null;
	protected JButton transformUnicodeButton = null;
	protected JButton closeButton = null;
	protected JButton browseFileListButton = null;
	protected JButton doScopeAssertionButton = null;
	protected JButton doTablesAssertionButton = null;
	protected JButton moveSelectedRecordUpButton = null;
	protected JButton moveSelectedRecordDownButton = null;
	protected CaptureVoJTable mainTable = null;
	protected String selectedKey = null;
	protected JLabel scopeNameLabel = null;
	protected JTextField scopeNameTextField = null;
	protected JLabel scopeFolderLabel = null;
	protected JTextField scopeFolderTextField = null;
	protected JLabel progressStatusLabel = null;
	protected DefaultTableModel tableModel;
	protected String currentMode;	
	protected EditView editView;
	
	protected SnapshotMgr snapshotMgr;
	
	protected CaptureScopeVo captureScopeVo;
	protected CaptureVo selectedCaptureVo;
	
	protected FileSelectionView fileSelectionView;

	protected SysProperties sysProperties;
	
	public final static int LABEL_WIDTH = 100;
	public final static int LABEL_HEIGHT = 20;
	public final static int TEXT_FIELD_WIDTH = 200;
	public final static int TEXT_FIELD_HEIGHT = 20;
	
	public static final String MODE_ADD = "ADD";
	public static final String MODE_EDIT = "EDIT";
	public static final String MODE_DELETE = "DELETE";
	public static final String MODE_VIEW = "VIEW";
	
	public static final int MAIN_FRAME_WIDTH = 1200;
	public static final int MAIN_FRAME_HEIGHT = 500;
	
	public static final String MAIN_SCREEN_TYPE_CAPTURE = "CAPTURE";
	public static final String MAIN_SCREEN_TYPE_RESTORE = "RESTORE";
	
	public static final int MAINTABLE_WIDTH = 1000;
	public static final int MAINTABLE_HEIGHT = 250;

	public static final String PROGRESS_STATUS_STAND_BY = "STANDBY";
	public static final String PROGRESS_STATUS_PROCESSING = "PROCESSING";
	
	private final static int CHECK_BOX_COLUMN_INDEX=0;
	
	public MainScreenView(){
		try{
			initParams();
			init();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void initParams() throws Exception{
		snapshotMgr = new SnapshotMgr();
		sysProperties = PropertiesFactory.getInstanceOfSysProperties();
	}
	
	protected abstract void init();
	protected abstract void addEventHandlers();
	
	
	public void updateCaptureDtoVo(){
		CaptureVoJTable captureVoJTable = this.getMainTable();
		List<CaptureVo> captureVoList = this.captureScopeVo.getCaptureVoList();
		for (int i=0; i < captureVoList.size(); i++){
			CaptureVo captureVo = captureVoList.get(i);
			Boolean isSelected = (Boolean) captureVoJTable.getValueAt(i, CHECK_BOX_COLUMN_INDEX);
			captureVo.setIsSelectedToExecute(isSelected);
		}
	}
	

//	public void updateCaptureDtoVo(int row, Boolean isSelected){
//		if (this.captureScopeVo != null){
//			List<CaptureVo> captureVoList = this.captureScopeVo.getCaptureVoList();
//			for (int i=0;i < captureVoList.size(); i++){
//				CaptureVo captureVo = captureVoList.get(i);
//				if (row == i){
//					captureVo.setIsSelectedToExecute(isSelected);
//				}
//			}
//		}
//		
//	}


	public JLabel getProgressStatusLabel() {
		return progressStatusLabel;
	}

	public void setProgressStatusLabel(JLabel progressStatusLabel) {
		this.progressStatusLabel = progressStatusLabel;
	}



	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JButton getDoScopeAssertionButton() {
		return doScopeAssertionButton;
	}

	public void setDoScopeAssertionButton(JButton doScopeAssertionButton) {
		this.doScopeAssertionButton = doScopeAssertionButton;
	}

	public JButton getDoTablesAssertionButton() {
		return doTablesAssertionButton;
	}

	public void setDoTablesAssertionButton(JButton doTablesAssertionButton) {
		this.doTablesAssertionButton = doTablesAssertionButton;
	}

	public JButton getTransformUnicodeButton() {
		return transformUnicodeButton;
	}

	public void setTransformUnicodeButton(JButton transformUnicodeButton) {
		this.transformUnicodeButton = transformUnicodeButton;
	}

	public JButton getRestoreButton() {
		return restoreButton;
	}

	public void setRestoreButton(JButton restoreButton) {
		this.restoreButton = restoreButton;
	}

	public JButton getExecuteButton() {
		return executeButton;
	}

	public void setExecuteButton(JButton executeButton) {
		this.executeButton = executeButton;
	}

	public JButton getViewButton() {
		return viewButton;
	}

	public void setViewButton(JButton viewButton) {
		this.viewButton = viewButton;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JButton getEditButton() {
		return editButton;
	}

	public void setEditButton(JButton editButton) {
		this.editButton = editButton;
	}

	public JLabel getScopeNameLabel() {
		return scopeNameLabel;
	}

	public void setScopeNameLabel(JLabel scopeNameLabel) {
		this.scopeNameLabel = scopeNameLabel;
	}

	public JTextField getScopeNameTextField() {
		return scopeNameTextField;
	}

	public void setScopeNameTextField(JTextField scopeNameTextField) {
		this.scopeNameTextField = scopeNameTextField;
	}

	public JLabel getScopeFolderLabel() {
		return scopeFolderLabel;
	}

	public void setScopeFolderLabel(JLabel scopeFolderLabel) {
		this.scopeFolderLabel = scopeFolderLabel;
	}

	public JTextField getScopeFolderTextField() {
		return scopeFolderTextField;
	}

	public CaptureVo getSelectedCaptureVo() {
		return selectedCaptureVo;
	}

	public void setSelectedCaptureVo(CaptureVo selectedCaptureVo) {
		this.selectedCaptureVo = selectedCaptureVo;
	}

	public void removeAllRowsFromTableModel(){
		if (tableModel.getRowCount() > 0) {
		    for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
		    	tableModel.removeRow(i);
		    }
		}
	}
	
	public void bindCaptureVo2TableModel(){
		removeAllRowsFromTableModel();
		if (this instanceof CaptureMainView){
			bindGeneralCaptureVo2TableModel();
		}
		if (this instanceof RestoreMainView){
			bindRestoreCaptureVo2TableModel();
		}
		if (this instanceof AssertionMainView){
			bindGeneralCaptureVo2TableModel();
		}

	}

	private void bindGeneralCaptureVo2TableModel(){
		List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
		for (CaptureVo captureVo: captureVoList){
			String [] row = new String[5];
			row[0] = captureVo.getTableName();
			row[1] = captureVo.getOutputXmlFileName();
			row[2] = captureVo.getCaptureSql();
			row[3] = captureVo.getActualAssertionTargetSql();
			row[4] = captureVo.getActualAssertionTargetIgnoreColumnsList();
			tableModel.addRow(row);
		}		
	}
	
	private void bindRestoreCaptureVo2TableModel(){
	
		List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
		for (int i = 0; i < captureVoList.size(); i++){
			CaptureVo captureVo = captureVoList.get(i);
			Object [] row = new Object[6];
			row[0] = captureVo.getIsSelectedToExecute();
			row[1] = captureVo.getTableName();
			row[2] = captureVo.getOutputXmlFileName();
			row[3] = captureVo.getCaptureSql();
			row[4] = captureVo.getActualAssertionTargetSql();
			row[5] = captureVo.getActualAssertionTargetIgnoreColumnsList();
			
			tableModel.addRow(row);
		}	

		
	}
	
	
	public void bindCaptureScopeDto2Controls(){
		if (captureScopeVo != null){
			this.getScopeNameTextField().setText(captureScopeVo.getScopeFileName());
			this.getScopeFolderTextField().setText(captureScopeVo.getScopeFolderName());
		}
	}
	
	
	public void setScopeFolderTextField(JTextField scopeFolderTextField) {
		this.scopeFolderTextField = scopeFolderTextField;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}



	public CaptureScopeVo getCaptureScopeVo() {
		return captureScopeVo;
	}

	public void setCaptureScopeVo(CaptureScopeVo captureScopeVo) {
		this.captureScopeVo = captureScopeVo;
	}

	public void setEditView(EditView editView) {
		this.editView = editView;
	}
	
	public void render(){
		mainFrame.setVisible(true);			
	}
	
	public JFrame getFrame() {
		return mainFrame;
	}

	public void setFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JPanel getPanel() {
		return mainPanel;
	}

	public void setPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JButton getAddButton() {
		return addButton;
	}

	public void setAddButton(JButton addButton) {
		this.addButton = addButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
	}

	public CaptureVoJTable getMainTable() {
		return mainTable;
	}

	public void setMainTable(CaptureVoJTable mainTable) {
		this.mainTable = mainTable;
	}

	public String getSelectedKey() {
		return selectedKey;
	}

	public void setSelectedKey(String selectedKey) {
		this.selectedKey = selectedKey;
	}
	
	protected void matchCurrentTableSelected2SelectedCaptureVo(){
		if (selectedKey != null && !selectedKey.isEmpty()){
			List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
			for (CaptureVo captureVo: captureVoList){
				if (captureVo.getOutputXmlFileName().equals(selectedKey)){
					selectedCaptureVo = captureVo;
					break;
				}
			}
		} 
	}
	
	public void collectCaptureScopeVoData(){
		String scopeName = getScopeNameTextField().getText();
		String scopeFolderName = getScopeFolderTextField().getText();
		if (scopeName != null && !scopeName.isEmpty()){
			captureScopeVo.setScopeFileName(scopeName);
		}
		if (scopeFolderName != null && !scopeFolderName.isEmpty()){
			captureScopeVo.setScopeFolderName(scopeFolderName);
		}
	}
	
	public JButton getBrowseFileListButton() {
		return browseFileListButton;
	}

	public void setBrowseFileListButton(JButton browseFileListButton) {
		this.browseFileListButton = browseFileListButton;
	}

	public String getCurrentMode() {
		return currentMode;
	}



	public JButton getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(JButton closeButton) {
		this.closeButton = closeButton;
	}

	public void setCurrentMode(String currentMode) {
		this.currentMode = currentMode;
	}
	public EditView getEditView(String mode) {
		
		if (mode.equals(CaptureMainView.MODE_EDIT) ){
			matchCurrentTableSelected2SelectedCaptureVo();
			currentMode = MODE_EDIT;
		}
		
		if (mode.equals(CaptureMainView.MODE_ADD)){
			selectedCaptureVo = null;
			currentMode = MODE_ADD;
		}

		if (mode.equals(CaptureMainView.MODE_DELETE)){
			matchCurrentTableSelected2SelectedCaptureVo();
			currentMode = MODE_DELETE;
		}
		
		if (currentMode.equals(MODE_EDIT) || currentMode.equals(MODE_DELETE)){
			if (selectedCaptureVo == null){
				JOptionPane.showMessageDialog(this.getMainFrame(), "You need to select a record in Edit/Delete mode!");
				return null;
			}
		} 
		editView = new EditView(this);
		return editView;
	}
	
	public void updateSelectedOpenFile(String selectedFileName){
		try {
			CaptureScopeDto captureScopeDto = snapshotMgr.getCaptureScopeDtoFromScopeFileName(selectedFileName);
			this.captureScopeVo = new CaptureScopeVo(captureScopeDto);
			this.removeAllRowsFromTableModel();
			this.bindCaptureScopeDto2Controls();
			this.bindCaptureVo2TableModel();
			if (restoreButton != null){
				boolean deleteThenRestoreEnabled = sysProperties.getDeleteThenRestoreComboActionEnabled();
				if (deleteThenRestoreEnabled){
					restoreButton.setEnabled(false);
				}
			}
		} catch (Exception e) {
			logger.error(className + ".updateSelectedOpenFile() - selectedFileName=" + selectedFileName, e);
			JOptionPane.showMessageDialog(this.getMainFrame(), "Exception or error found! Refer to console log for detail information!");
		}

	}
	public FileSelectionView getFileSelectionView() {
		try{
			if (fileSelectionView == null){
				fileSelectionView = new FileSelectionView(this);
			}
		} catch (Exception e){
			logger.error(className + ".getFileSelectionView()", e);
		}
		return fileSelectionView;
	}

	public JButton getVerAndGenDelStatementsButton() {
		return verAndGenDelStatementsButton;
	}

	public void setVerAndGenDelStatementsButton(JButton verAndGenDelStatementsButton) {
		this.verAndGenDelStatementsButton = verAndGenDelStatementsButton;
	}


}
