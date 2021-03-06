package com.rabbitforever.datamanipulation.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.Icon;
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
import com.rabbitforever.datamanipulation.utils.FileUtils;
import com.rabbitforever.datamanipulation.views.handlers.AddButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.BrowserFileListActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.CloseButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.DeleteButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.EditButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.ExecuteButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.MainListSelectionHandler;
import com.rabbitforever.datamanipulation.views.handlers.MainListTableModelHandler;
import com.rabbitforever.datamanipulation.views.handlers.MoveSelectedRecordDownButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.MoveSelectedRecordUpButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.SaveButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.TransformUnicodeActionHandler;

public class CaptureMainView extends MainScreenView {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private FileUtils fileUtils;
	public CaptureMainView() {

		init();
		initControls();
	}

	protected void init(){
		try{
			fileUtils = new FileUtils();
		} catch (Exception e){
			logger.error(className + ".init() - ", e);
		}
	}
	protected void initControls() {
		captureScopeVo = new CaptureScopeVo();
		mainFrame = new JFrame("Capture Main Screen");

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		JPanel upperPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		// JLabel label = new JLabel("This is a label!");
		// mainPanel.add(label);

		scopeNameLabel = new JLabel();
		scopeNameLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		scopeNameLabel.setText("scope file name:");
		middlePanel.add(scopeNameLabel);

		scopeNameTextField = new JTextField();
		scopeNameTextField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
		middlePanel.add(scopeNameTextField);

		scopeFolderLabel = new JLabel();
		scopeFolderLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		scopeFolderLabel.setText("scope folder:");
		middlePanel.add(scopeFolderLabel);

		scopeFolderTextField = new JTextField();
		scopeFolderTextField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
		middlePanel.add(scopeFolderTextField);

		browseFileListButton = new JButton();
		browseFileListButton.setText("Browse file list");
		upperPanel.add(browseFileListButton);

		addButton = new JButton();
		addButton.setText("Add");
		upperPanel.add(addButton);

		editButton = new JButton();
		editButton.setText("Edit");
		upperPanel.add(editButton);

		deleteButton = new JButton();
		deleteButton.setText("Delete");
		upperPanel.add(deleteButton);

		saveButton = new JButton();
		saveButton.setText("Save");
		upperPanel.add(saveButton);

		executeButton = new JButton();
		executeButton.setText("Execute");
		upperPanel.add(executeButton);

		transformUnicodeButton = new JButton();
		transformUnicodeButton.setText("Transform Unicode");
		upperPanel.add(transformUnicodeButton);

		closeButton = new JButton();
		closeButton.setText("Close");
		upperPanel.add(closeButton);

		mainPanel.add(upperPanel);
		mainPanel.add(middlePanel);

		moveSelectedRecordUpButton = new JButton();
		Icon upImage = fileUtils.readImage("up.png");

		if (upImage != null){

			moveSelectedRecordUpButton.setIcon(upImage);
		} else {
			moveSelectedRecordUpButton.setText("Move Up");
		}


		middlePanel.add(moveSelectedRecordUpButton);
		
		moveSelectedRecordDownButton = new JButton();
		Icon downImage = fileUtils.readImage("down.png");
		if (downImage != null){
			moveSelectedRecordDownButton.setIcon(downImage);
		} else {
			moveSelectedRecordDownButton.setText("Move Down");
		}

		
		middlePanel.add(moveSelectedRecordDownButton);
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
		bottomPanel.add(scroll);

		mainPanel.add(bottomPanel);

		mainFrame.add(mainPanel);
		mainFrame.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Color color = new Color(sysProperties.getColorRgbR(), sysProperties.getColorRgbG(), sysProperties.getColorRgbG());
		
		UiHelper.setColor(mainPanel,color);
		UiHelper.setColor(upperPanel,color);
		UiHelper.setColor(middlePanel,color);
		UiHelper.setColor(bottomPanel,color);

		addEventHandlers();

	}


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

			ExecuteButtonActionHandler executeButtonActionHandler = new ExecuteButtonActionHandler(this);
			executeButton.addActionListener(executeButtonActionHandler);

			SaveButtonActionHandler saveButtonActionHandler = new SaveButtonActionHandler(this);
			saveButton.addActionListener(saveButtonActionHandler);

			TransformUnicodeActionHandler transformUnicodeActionHandler = new TransformUnicodeActionHandler(this);
			transformUnicodeButton.addActionListener(transformUnicodeActionHandler);

			CloseButtonActionHandler closeButtonActionHandler = new CloseButtonActionHandler(this);
			closeButton.addActionListener(closeButtonActionHandler);
			
			MoveSelectedRecordUpButtonActionHandler moveSelectedRecordUpButtonActionHandler = new MoveSelectedRecordUpButtonActionHandler(this);
			moveSelectedRecordUpButton.addActionListener(moveSelectedRecordUpButtonActionHandler);
			
			MoveSelectedRecordDownButtonActionHandler moveSelectedRecordDownButtonActionHandler = new MoveSelectedRecordDownButtonActionHandler(this);
			moveSelectedRecordDownButton.addActionListener(moveSelectedRecordDownButtonActionHandler);
			
		} catch (Exception e) {
			logger.error(className + ".addEventHandlers()", e);
		}

	}

	public static void main(String[] args) {
		CaptureMainView mainView = new CaptureMainView();
		mainView.render();
	}

}
