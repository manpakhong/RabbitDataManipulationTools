package com.rabbitforever.datamanipulation.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.helpers.UiHelper;
import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;
import com.rabbitforever.datamanipulation.models.vos.CaptureVo;
import com.rabbitforever.datamanipulation.views.handlers.CheckDependentTablesActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.OkButtonActionHandler;

public class EditView {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private JFrame editFrame = null;
	private JPanel editPanel = null;
	private JPanel editPanelWest = null;
	private JPanel editPanelCenter = null;
	private JPanel editPanelSouth = null;
	private JButton oKButton = null;
	private JButton checkDependentTablesButton = null;
	private JLabel tableNameLabel = null;
	private JTextField tableNameTextField = null;
	private JLabel outputXmlFileNameLabel = null;
	private JTextField outputXmlFileNameTextField = null;
	private JLabel captureSqlLabel = null;
	private JTextArea captureSqlTextArea = null;
	private JLabel actualAssertionTargetSqlLabel = null;
	private JTextArea actualAssertionTargetSqlTextArea = null;
	private JLabel actualAssertionTargetIgnoreColumnsListLabel = null;
	private JTextArea actualAssertionTargetIgnoreColumnsListTextArea = null;

	private MainScreenView mainView = null;
	private CaptureVo captureVo;
	protected SysProperties sysProperties;
	private final static int WEST_PANEL_ROW_SIZE = 5;
	private final static int WEST_PANEL_COL_SIZE = 1;
	private final static int EAST_PANEL_ROW_SIZE = 5;
	private final static int EAST_PANEL_COL_SIZE = 1;
	private final static int SOUTH_PANEL_ROW_SIZE = 2;
	private final static int SOUTH_PANEL_COL_SIZE = 1;

	private final static int LABEL_WIDTH = 200;
	private final static int LABEL_HEIGHT = 20;

	private final static int TEXT_FIELD_WIDTH = 1000;
	private final static int TEXT_FIELD_HEIGHT = 20;

	private final static int TEXT_AREA_WIDTH = 1000;
	private final static int TEXT_AREA_HEIGHT = 300;

	public final static int PANEL_WIDTH = 1500;
	public final static int PANEL_HEIGHT = 600;

	public EditView() {
		this.captureVo = new CaptureVo();
		init();
	}

	public EditView(MainScreenView mainView) {
		this.mainView = mainView;
		if (mainView.getSelectedCaptureVo() != null) {
			captureVo = mainView.getSelectedCaptureVo();
		} else {
			captureVo = new CaptureVo();
		}
		init();
	}

	public MainScreenView getMainView() {
		return mainView;
	}

	public void setMainView(CaptureMainView mainView) {
		this.mainView = mainView;
	}

	private void init() {
		try {
			sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			editFrame = new JFrame("Edit Screen");
			editPanel = new JPanel(new BorderLayout());
			editPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
			editPanelWest = new JPanel(new GridLayout(WEST_PANEL_ROW_SIZE, WEST_PANEL_COL_SIZE));
			editPanelCenter = new JPanel(new GridLayout(EAST_PANEL_ROW_SIZE, EAST_PANEL_COL_SIZE));
			editPanelSouth = new JPanel(new GridLayout(SOUTH_PANEL_ROW_SIZE, SOUTH_PANEL_COL_SIZE));
			tableNameLabel = new JLabel();
			tableNameLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
			tableNameLabel.setText("Table Name:");
			editPanelWest.add(tableNameLabel);

			tableNameTextField = new JTextField();
			tableNameTextField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
			editPanelCenter.add(tableNameTextField);

			outputXmlFileNameLabel = new JLabel();
			outputXmlFileNameLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
			outputXmlFileNameLabel.setText("Xml file Name:");
			editPanelWest.add(outputXmlFileNameLabel);

			outputXmlFileNameTextField = new JTextField();
			outputXmlFileNameTextField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
			editPanelCenter.add(outputXmlFileNameTextField);

			captureSqlLabel = new JLabel();
			captureSqlLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
			captureSqlLabel.setText("Capture Sql:");
			editPanelWest.add(captureSqlLabel);

			captureSqlTextArea = new JTextArea();
			captureSqlTextArea.setPreferredSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
			captureSqlTextArea.setLineWrap(true);
			JScrollPane captureSqlScrollPane = new JScrollPane(captureSqlTextArea);
			
			editPanelCenter.add(captureSqlScrollPane);

			actualAssertionTargetSqlLabel = new JLabel();
			actualAssertionTargetSqlLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
			actualAssertionTargetSqlLabel.setText("Actual Table Assertion Sql:");
			editPanelWest.add(actualAssertionTargetSqlLabel);

			actualAssertionTargetSqlTextArea = new JTextArea();
			actualAssertionTargetSqlTextArea.setPreferredSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
			actualAssertionTargetSqlTextArea.setLineWrap(true);
			JScrollPane actualAssertionTargetSqlScrollPane = new JScrollPane(actualAssertionTargetSqlTextArea);
			editPanelCenter.add(actualAssertionTargetSqlScrollPane);

			actualAssertionTargetIgnoreColumnsListLabel = new JLabel();
			actualAssertionTargetIgnoreColumnsListLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
			actualAssertionTargetIgnoreColumnsListLabel.setText("Actual Table Ass. ignore cols:");
			editPanelWest.add(actualAssertionTargetIgnoreColumnsListLabel);

			actualAssertionTargetIgnoreColumnsListTextArea = new JTextArea();
			actualAssertionTargetIgnoreColumnsListTextArea
					.setPreferredSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
			editPanelCenter.add(actualAssertionTargetIgnoreColumnsListTextArea);

			oKButton = new JButton();
			oKButton.setText("Ok");
			editPanelSouth.add(oKButton);

			checkDependentTablesButton = new JButton();
			checkDependentTablesButton.setText("Check Dependent Tables");
			editPanelSouth.add(checkDependentTablesButton);

			editPanel.add(editPanelWest, BorderLayout.WEST);
			editPanel.add(editPanelCenter, BorderLayout.CENTER);
			editPanel.add(editPanelSouth, BorderLayout.SOUTH);

			addEventHandlers();

			editFrame.add(editPanel);
			editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			editFrame.pack();
			editFrame.setLocationRelativeTo(null);
			Color color = new Color(sysProperties.getColorRgbR(), sysProperties.getColorRgbG(), sysProperties.getColorRgbG());

			UiHelper.setColor(editPanel,color);
			UiHelper.setColor(editPanelWest,color);
			UiHelper.setColor(editPanelCenter,color);
			UiHelper.setColor(editPanelSouth,color);
			bindData2Controls();
		} catch (Exception e) {
			logger.error(className + ".init()", e);
		}

	}

	private void addEventHandlers() {
		try {
			OkButtonActionHandler okButtonActionHandler = new OkButtonActionHandler(this);
			oKButton.addActionListener(okButtonActionHandler);

			CheckDependentTablesActionHandler checkDependentTablesActionHandler = new CheckDependentTablesActionHandler(
					this);
			checkDependentTablesButton.addActionListener(checkDependentTablesActionHandler);
		} catch (Exception e) {
			logger.error(className + ".addEventHandlers()", e);
		}
	}

	private void bindData2Controls() {
		if (captureVo != null) {
			if (captureVo.getTableName() != null) {
				tableNameTextField.setText(captureVo.getTableName());
			}
			if (captureVo.getOutputXmlFileName() != null) {
				outputXmlFileNameTextField.setText(captureVo.getOutputXmlFileName());
			}
			if (captureVo.getCaptureSql() != null) {
				captureSqlTextArea.setText(captureVo.getCaptureSql());
			}
			if (captureVo.getActualAssertionTargetSql() != null) {
				actualAssertionTargetSqlTextArea.setText(captureVo.getActualAssertionTargetSql());
			}
			if (captureVo.getActualAssertionTargetIgnoreColumnsList() != null) {
				actualAssertionTargetIgnoreColumnsListTextArea
						.setText(captureVo.getActualAssertionTargetIgnoreColumnsList());
			}
		}
	}

	public void render() {
		editFrame.setVisible(true);
	}

	public JFrame getEditFrame() {
		return editFrame;
	}

	public void setEditFrame(JFrame editFrame) {
		this.editFrame = editFrame;
	}

	public JPanel getEditPanel() {
		return editPanel;
	}

	public void setEditPanel(JPanel editPanel) {
		this.editPanel = editPanel;
	}

	public JPanel getEditPanelWest() {
		return editPanelWest;
	}

	public void setEditPanelWest(JPanel editPanelWest) {
		this.editPanelWest = editPanelWest;
	}

	public JPanel getEditPanelCenter() {
		return editPanelCenter;
	}

	public void setEditPanelCenter(JPanel editPanelCenter) {
		this.editPanelCenter = editPanelCenter;
	}

	public JPanel getEditPanelSouth() {
		return editPanelSouth;
	}

	public void setEditPanelSouth(JPanel editPanelSouth) {
		this.editPanelSouth = editPanelSouth;
	}

	public JButton getoKButton() {
		return oKButton;
	}

	public void setoKButton(JButton oKButton) {
		this.oKButton = oKButton;
	}

	public JLabel getTableNameLabel() {
		return tableNameLabel;
	}

	public void setTableNameLabel(JLabel tableNameLabel) {
		this.tableNameLabel = tableNameLabel;
	}

	public JTextField getTableNameTextField() {
		return tableNameTextField;
	}

	public void setTableNameTextField(JTextField tableNameTextField) {
		this.tableNameTextField = tableNameTextField;
	}

	public JLabel getOutputXmlFileNameLabel() {
		return outputXmlFileNameLabel;
	}

	public void setOutputXmlFileNameLabel(JLabel outputXmlFileNameLabel) {
		this.outputXmlFileNameLabel = outputXmlFileNameLabel;
	}

	public JTextField getOutputXmlFileNameTextField() {
		return outputXmlFileNameTextField;
	}

	public void setOutputXmlFileNameTextField(JTextField outputXmlFileNameTextField) {
		this.outputXmlFileNameTextField = outputXmlFileNameTextField;
	}

	public JLabel getCaptureSqlLabel() {
		return captureSqlLabel;
	}

	public void setCaptureSqlLabel(JLabel captureSqlLabel) {
		this.captureSqlLabel = captureSqlLabel;
	}

	public JTextArea getCaptureSqlTextArea() {
		return captureSqlTextArea;
	}

	public void setCaptureSqlTextArea(JTextArea captureSqlTextArea) {
		this.captureSqlTextArea = captureSqlTextArea;
	}

	public JLabel getActualAssertionTargetSqlLabel() {
		return actualAssertionTargetSqlLabel;
	}

	public void setActualAssertionTargetSqlLabel(JLabel actualAssertionTargetSqlLabel) {
		this.actualAssertionTargetSqlLabel = actualAssertionTargetSqlLabel;
	}

	public JTextArea getActualAssertionTargetSqlTextArea() {
		return actualAssertionTargetSqlTextArea;
	}

	public void setActualAssertionTargetSqlTextArea(JTextArea actualAssertionTargetSqlTextArea) {
		this.actualAssertionTargetSqlTextArea = actualAssertionTargetSqlTextArea;
	}

	public JLabel getActualAssertionTargetIgnoreColumnsListLabel() {
		return actualAssertionTargetIgnoreColumnsListLabel;
	}

	public void setActualAssertionTargetIgnoreColumnsListLabel(JLabel actualAssertionTargetIgnoreColumnsListLabel) {
		this.actualAssertionTargetIgnoreColumnsListLabel = actualAssertionTargetIgnoreColumnsListLabel;
	}

	public JTextArea getActualAssertionTargetIgnoreColumnsListTextArea() {
		return actualAssertionTargetIgnoreColumnsListTextArea;
	}

	public void setActualAssertionTargetIgnoreColumnsListTextArea(
			JTextArea actualAssertionTargetIgnoreColumnsListTextArea) {
		this.actualAssertionTargetIgnoreColumnsListTextArea = actualAssertionTargetIgnoreColumnsListTextArea;
	}

	public CaptureVo getCaptureVo() {
		return captureVo;
	}

	public void setCaptureVo(CaptureVo captureVo) {
		this.captureVo = captureVo;
	}

}
