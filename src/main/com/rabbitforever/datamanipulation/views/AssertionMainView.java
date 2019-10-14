package com.rabbitforever.datamanipulation.views;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.helpers.UiHelper;
import com.rabbitforever.datamanipulation.models.vos.CaptureScopeVo;
import com.rabbitforever.datamanipulation.ui.CaptureVoJTable;
import com.rabbitforever.datamanipulation.views.handlers.AddButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.BrowserFileListActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.CloseButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.DeleteButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.DoScopeAssertionButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.DoTablesAssertionButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.EditButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.MainListSelectionHandler;
import com.rabbitforever.datamanipulation.views.handlers.MainListTableModelHandler;
import com.rabbitforever.datamanipulation.views.handlers.SaveButtonActionHandler;

public class AssertionMainView extends MainScreenView {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public AssertionMainView() {
		super();
	}

	@Override
	protected void init() {
		captureScopeVo = new CaptureScopeVo();

		mainFrame = new JFrame("Assertion Main Screen");

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		JPanel buttonsPanel = new JPanel();
		JPanel statusPanel = new JPanel();
		JPanel inputsPanel = new JPanel();
		JPanel tablePanel = new JPanel();

		// JLabel label = new JLabel("This is a label!");
		// mainPanel.add(label);

		scopeNameLabel = new JLabel();
		scopeNameLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		scopeNameLabel.setText("scope file name:");
		inputsPanel.add(scopeNameLabel);

		scopeNameTextField = new JTextField();
		scopeNameTextField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
		inputsPanel.add(scopeNameTextField);

		scopeFolderLabel = new JLabel();
		scopeFolderLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		scopeFolderLabel.setText("scope folder:");
		inputsPanel.add(scopeFolderLabel);

		scopeFolderTextField = new JTextField();
		scopeFolderTextField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
		inputsPanel.add(scopeFolderTextField);

		browseFileListButton = new JButton();
		browseFileListButton.setText("Browse file list");
		buttonsPanel.add(browseFileListButton);

		addButton = new JButton();
		addButton.setText("Add");
		buttonsPanel.add(addButton);

		editButton = new JButton();
		editButton.setText("Edit");
		buttonsPanel.add(editButton);

		deleteButton = new JButton();
		deleteButton.setText("Delete");
		buttonsPanel.add(deleteButton);

		saveButton = new JButton();
		saveButton.setText("Save");
		buttonsPanel.add(saveButton);

		doScopeAssertionButton = new JButton();
		doScopeAssertionButton.setText("Do Scope Assertion");
		buttonsPanel.add(doScopeAssertionButton);

		doTablesAssertionButton = new JButton();
		doTablesAssertionButton.setText("Do Tables Assertion");
		buttonsPanel.add(doTablesAssertionButton);
		
		
		closeButton = new JButton();
		closeButton.setText("Close");
		buttonsPanel.add(closeButton);

		progressStatusLabel = new JLabel();
		progressStatusLabel.setText(PROGRESS_STATUS_STAND_BY);
		progressStatusLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
		statusPanel.add(progressStatusLabel);

		mainPanel.add(buttonsPanel);
		mainPanel.add(statusPanel);
		mainPanel.add(inputsPanel);

		// String columnNames[] = { "Table Name", "xml File Name", "Sql
		// Statement" };
		// String dataValues[][] = {
		// { "12", "234", "67" },
		// { "-123", "43", "853" },
		// { "93", "89.2", "109" },
		// { "279", "9033", "3092" }
		// };
		// mainTable = new JTable(dataValues,columnNames);
		mainTable = new CaptureVoJTable(this);
		tableModel = (DefaultTableModel) mainTable.getModel();

		tableModel.addColumn("Table Name");
		tableModel.addColumn("xml File Name");
		tableModel.addColumn("Capture Sql");
		tableModel.addColumn("Actual Table Assertion Sql");
		tableModel.addColumn("Actual Table Ass. Ignore Cols");
		// String [] row1 = new String[3];
		// row1[0] = "12";
		// row1[1] = "234";
		// row1[2] = "67";
		// tableModel.addRow(row1);

		removeAllRowsFromTableModel();
		bindCaptureVo2TableModel();

		mainTable.setModel(tableModel);

		JScrollPane scroll = new JScrollPane(mainTable);
		scroll.setPreferredSize(new Dimension(MAINTABLE_WIDTH, MAINTABLE_HEIGHT));
		tablePanel.add(scroll);

		mainPanel.add(tablePanel);

		mainFrame.add(mainPanel);
		mainFrame.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Color color = new Color(sysProperties.getColorRgbR(), sysProperties.getColorRgbG(), sysProperties.getColorRgbG());
		
		UiHelper.setColor(mainPanel,color);
		UiHelper.setColor(buttonsPanel,color);
		UiHelper.setColor(statusPanel,color);
		UiHelper.setColor(inputsPanel,color);
		UiHelper.setColor(tablePanel,color);
		

		
		addEventHandlers();

	}

	@Override
	protected void addEventHandlers() {
		try {
			MainListSelectionHandler mainListSelectionHandler = new MainListSelectionHandler(this);
			mainTable.getSelectionModel().addListSelectionListener(mainListSelectionHandler);

			MainListTableModelHandler mainListTableModelHandler = new MainListTableModelHandler(this);
			mainTable.getModel().addTableModelListener(mainListTableModelHandler);

			BrowserFileListActionHandler browserFileListActionHandler = new BrowserFileListActionHandler(this);
			browseFileListButton.addActionListener(browserFileListActionHandler);

			AddButtonActionHandler addButtonActionHandler = new AddButtonActionHandler(this);
			addButton.addActionListener(addButtonActionHandler);

			EditButtonActionHandler editButtonActionHandler = new EditButtonActionHandler(this);
			editButton.addActionListener(editButtonActionHandler);

			DeleteButtonActionHandler deleteButtonActionHandler = new DeleteButtonActionHandler(this);
			deleteButton.addActionListener(deleteButtonActionHandler);

			SaveButtonActionHandler saveButtonActionHandler = new SaveButtonActionHandler(this);
			saveButton.addActionListener(saveButtonActionHandler);

			CloseButtonActionHandler closeButtonActionHandler = new CloseButtonActionHandler(this);
			closeButton.addActionListener(closeButtonActionHandler);

			DoScopeAssertionButtonActionHandler doScopeAssertionButtonActionHandler = new DoScopeAssertionButtonActionHandler(this);
			doScopeAssertionButton.addActionListener(doScopeAssertionButtonActionHandler);
			
			DoTablesAssertionButtonActionHandler doTablesAssertionButtonActionHandler = new DoTablesAssertionButtonActionHandler(this);
			doTablesAssertionButton.addActionListener(doTablesAssertionButtonActionHandler);
		} catch (Exception e) {
			logger.error(className + ".addEventHandlers()", e);
		}
	}

}
