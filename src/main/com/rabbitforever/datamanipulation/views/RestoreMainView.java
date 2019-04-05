package com.rabbitforever.datamanipulation.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.helpers.UiHelper;
import com.rabbitforever.datamanipulation.models.vos.CaptureScopeVo;
import com.rabbitforever.datamanipulation.models.vos.CaptureVo;
import com.rabbitforever.datamanipulation.ui.CaptureVoJTable;
import com.rabbitforever.datamanipulation.views.handlers.BrowserFileListActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.CloseButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.MainListSelectionHandler;
import com.rabbitforever.datamanipulation.views.handlers.MainListTableModelHandler;
import com.rabbitforever.datamanipulation.views.handlers.RestoreButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.SaveButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.VerAndGenDelStatementsButtonActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.ViewButtonActionHandler;

public class RestoreMainView extends MainScreenView {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private boolean isDelStmtGenButtonEnabled;
	private boolean deleteThenRestoreComboActionEnabled;

	public RestoreMainView() {
		initParams();
		init();
	}

	public static void main(String[] args) {
		RestoreMainView mainView = new RestoreMainView();
		mainView.render();
	}

	private void initParams() {
		try {
			isDelStmtGenButtonEnabled = sysProperties.getDeleteStatementGenerateButtonEnabled();
			deleteThenRestoreComboActionEnabled = sysProperties.getDeleteThenRestoreComboActionEnabled();
			this.currentMode = MODE_VIEW;

		} catch (Exception e) {
			logger.error(className + ".initParams()", e);
			JOptionPane.showMessageDialog(this.getMainFrame(),
					"Missing sysProperties file! or file not in system format!");
		}
	}

	@Override
	protected void init() {
		captureScopeVo = new CaptureScopeVo();

		mainFrame = new JFrame("Restore Main Screen");
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());

		// JLabel label = new JLabel("This is a label!");
		// mainPanel.add(label);

		scopeNameLabel = new JLabel();
		scopeNameLabel.setText("scope file name:");
		mainPanel.add(scopeNameLabel);

		scopeNameTextField = new JTextField();
		scopeNameTextField.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		mainPanel.add(scopeNameTextField);

		scopeFolderLabel = new JLabel();
		scopeFolderLabel.setText("scope folder:");
		mainPanel.add(scopeFolderLabel);

		scopeFolderTextField = new JTextField();
		scopeFolderTextField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
		mainPanel.add(scopeFolderTextField);

		// addButton = new JButton();
		// addButton.setText("Add");
		// mainPanel.add(addButton);
		//
		// editButton = new JButton();
		// editButton.setText("Edit");
		// mainPanel.add(editButton);
		//
		//
		// deleteButton = new JButton();
		// deleteButton.setText("Delete");
		// mainPanel.add(deleteButton);

		browseFileListButton = new JButton();
		browseFileListButton.setText("Browse file list");
		mainPanel.add(browseFileListButton);

		viewButton = new JButton();
		viewButton.setText("View");
		mainPanel.add(viewButton);

		saveButton = new JButton();
		saveButton.setText("Save");
		mainPanel.add(saveButton);

		restoreButton = new JButton();
		restoreButton.setText("Restore");
		mainPanel.add(restoreButton);

		if (isDelStmtGenButtonEnabled) {
			verAndGenDelStatementsButton = new JButton();
			verAndGenDelStatementsButton.setText("Generate Del. Stmts");
			mainPanel.add(verAndGenDelStatementsButton);
		} else {
			if (deleteThenRestoreComboActionEnabled) {
				StringBuilder sbMessage = new StringBuilder();
				sbMessage.append("deleteThenRestoreComboActionEnabled=true, but isDelStmtGenButtonEnabled=false!");
				sbMessage.append(
						"\nRestore button will not be enabled until you change sys.properties isDelStmtGenButtonEnabled=true");
				JOptionPane.showMessageDialog(this.mainFrame, sbMessage.toString());
			}
		}

		if (restoreButton != null) {
			boolean deleteThenRestoreEnabled = sysProperties.getDeleteThenRestoreComboActionEnabled();
			if (deleteThenRestoreEnabled) {
				restoreButton.setEnabled(false);
			}
		}

		closeButton = new JButton();
		closeButton.setText("Close");
		mainPanel.add(closeButton);

		mainTable = new CaptureVoJTable(this);

		// String [] columnNames = {
		// "is Valid delete",
		// "Table Name",
		// "xml File Name",
		// "Capture Sql",
		// "Actual Table Assertion Sql",
		// "Actual Table Ass. Ignore Cols"
		// };

		tableModel = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Boolean.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				case 5:
					return String.class;

				default:
					return String.class;
				}
			}
		};
		// tableModel.setColumnIdentifiers(columnNames);
		tableModel.addColumn("is Valid delete");
		tableModel.addColumn("Table Name");
		tableModel.addColumn("xml File Name");
		tableModel.addColumn("Capture Sql");
		tableModel.addColumn("Actual Table Assertion Sql");
		tableModel.addColumn("Actual Table Ass. Ignore Cols");

		mainTable.setModel(tableModel);
		final TableCellRenderer renderer = mainTable.getDefaultRenderer(Object.class);

		mainTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				
				
				Component c = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				Object col1Value =table.getValueAt(row, 0);
				Boolean isCellEditable = table.isCellEditable(row, 0);
				Boolean isSelectedValid = null;
				if (col1Value != null){
					isSelectedValid = (Boolean) col1Value;
				}
				if (!isCellEditable){
					c.setForeground(Color.BLACK);
//					c.setEnabled(false);				
				} else {
					c.setForeground(Color.RED);
//					c.setEnabled(false);			
				}

				return c;
			}

		});
		removeAllRowsFromTableModel();
		bindCaptureVo2TableModel();

		JScrollPane scroll = new JScrollPane(mainTable);
		scroll.setPreferredSize(new Dimension(MAINTABLE_WIDTH, MAINTABLE_HEIGHT));
		mainPanel.add(scroll);

		addEventHandlers();

		mainFrame.add(mainPanel);
		mainFrame.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		UiHelper.setColor(mainPanel);
	}


	
	protected void addEventHandlers() {
		try {
			MainListSelectionHandler mainListSelectionHandler = new MainListSelectionHandler(this);
			mainTable.getSelectionModel().addListSelectionListener(mainListSelectionHandler);

			MainListTableModelHandler mainListTableModelHandler = new MainListTableModelHandler(this);
			mainTable.getModel().addTableModelListener(mainListTableModelHandler);

			// AddButtonActionHandler addButtonActionHandler = new
			// AddButtonActionHandler(this);
			// addButton.addActionListener(addButtonActionHandler);
			//
			// EditButtonActionHandler editButtonActionHandler = new
			// EditButtonActionHandler(this);
			// editButton.addActionListener(editButtonActionHandler);
			//
			// DeleteButtonActionHandler deleteButtonActionHandler = new
			// DeleteButtonActionHandler(this);
			// deleteButton.addActionListener(deleteButtonActionHandler);

			BrowserFileListActionHandler browserFileListActionHandler = new BrowserFileListActionHandler(this);
			browseFileListButton.addActionListener(browserFileListActionHandler);

			ViewButtonActionHandler viewButtonActionHandler = new ViewButtonActionHandler(this);
			viewButton.addActionListener(viewButtonActionHandler);

			SaveButtonActionHandler saveButtonActionHandler = new SaveButtonActionHandler(this);
			saveButton.addActionListener(saveButtonActionHandler);

			RestoreButtonActionHandler restoreButtonActionHandler = new RestoreButtonActionHandler(this);
			restoreButton.addActionListener(restoreButtonActionHandler);

			if (isDelStmtGenButtonEnabled) {
				VerAndGenDelStatementsButtonActionHandler verAndGenDelStatementsButtonActionHandler = new VerAndGenDelStatementsButtonActionHandler(
						this);
				verAndGenDelStatementsButton.addActionListener(verAndGenDelStatementsButtonActionHandler);
			}
			CloseButtonActionHandler closeButtonActionHandler = new CloseButtonActionHandler(this);
			closeButton.addActionListener(closeButtonActionHandler);

			// ExecuteButtonActionHandler executeButtonActionHandler = new
			// ExecuteButtonActionHandler(this);
			// executeButton.addActionListener(executeButtonActionHandler);
		} catch (Exception e) {
			logger.error(className + ".addEventHandlers()", e);
		}

	}

}
