package hksarg.swd.csss.csa.flowtest.views;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.helpers.UiHelper;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureDto;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureScopeDto;
import hksarg.swd.csss.csa.flowtest.services.SnapshotMgr;
import hksarg.swd.csss.csa.flowtest.views.handlers.CancelButtonActionHandler;
import hksarg.swd.csss.csa.flowtest.views.handlers.FileListSelectionHandler;
import hksarg.swd.csss.csa.flowtest.views.handlers.SelectButtonActionHandler;

public class FileSelectionView {
	private final static Logger logger = LoggerFactory.getLogger(FileSelectionView.class);
	private final static String className = FileSelectionView.class.getName();
	protected JFrame fileSelectionFrame = null;
	protected JPanel fileSelectionPanel = null;
	protected JButton selectButton = null;
	protected JButton cancelButton = null;
	protected JLabel fileListLabel = null;
	protected JTable fileListTable = null;
	protected DefaultTableModel tableModel;
	protected List<String> scopeFileList = null;
	protected CaptureScopeDto captureScopeDto;
	protected CaptureDto selectedCaptureDto;
	protected String selectedFileName;
	protected SnapshotMgr snapshotMgr;
	protected MainScreenView mainScreenView;
	public final static int LABEL_WIDTH = 100;
	public final static int LABEL_HEIGHT = 20;
	public final static int TEXT_FIELD_WIDTH = 200;
	public final static int TEXT_FIELD_HEIGHT = 20;

	public static final int FILE_SELECTION_FRAME_WIDTH = 1000;
	public static final int FILE_SELECTION_FRAME_HEIGHT = 500;

	public FileSelectionView() throws Exception {
		initParams();
		init();
	}

	public FileSelectionView(MainScreenView mainScreenView) throws Exception {
		this.mainScreenView = mainScreenView;
		initParams();
		init();
	}

	public JButton getSelectButton() {
		return selectButton;
	}

	public void setSelectButton(JButton selectButton) {
		this.selectButton = selectButton;
	}

	public String getSelectedFileName() {
		return selectedFileName;
	}

	public void setSelectedFileName(String selectedFileName) {
		this.selectedFileName = selectedFileName;
	}

	public JFrame getFileSelectionFrame() {
		return fileSelectionFrame;
	}

	public void setFileSelectionFrame(JFrame fileSelectionFrame) {
		this.fileSelectionFrame = fileSelectionFrame;
	}

	public JPanel getFileSelectionPanel() {
		return fileSelectionPanel;
	}

	public void setFileSelectionPanel(JPanel fileSelectionPanel) {
		this.fileSelectionPanel = fileSelectionPanel;
	}

	public JLabel getFileListLabel() {
		return fileListLabel;
	}

	public void setFileListLabel(JLabel fileListLabel) {
		this.fileListLabel = fileListLabel;
	}

	public JTable getFileListTable() {
		return fileListTable;
	}

	public void setFileListTable(JTable fileListTable) {
		this.fileListTable = fileListTable;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public List<String> getScopeFileList() {
		return scopeFileList;
	}

	public void setScopeFileList(List<String> scopeFileList) {
		this.scopeFileList = scopeFileList;
	}

	public CaptureScopeDto getCaptureScopeDto() {
		return captureScopeDto;
	}

	public void setCaptureScopeDto(CaptureScopeDto captureScopeDto) {
		this.captureScopeDto = captureScopeDto;
	}

	public CaptureDto getSelectedCaptureDto() {
		return selectedCaptureDto;
	}

	public void setSelectedCaptureDto(CaptureDto selectedCaptureDto) {
		this.selectedCaptureDto = selectedCaptureDto;
	}

	public MainScreenView getMainScreenView() {
		return mainScreenView;
	}

	public void setMainScreenView(MainScreenView mainScreenView) {
		this.mainScreenView = mainScreenView;
	}

	private void initParams() throws Exception {
		snapshotMgr = new SnapshotMgr();
	}

	private void refreshScreenFileList() {
		loadData2FileList();
		removeAllRowsFromTableModel();
		bindFileNameList2TableModel();
	}

	private void loadData2FileList() {
		scopeFileList = snapshotMgr.getScopeFileNameList();
	}

	private void init() {
		fileSelectionFrame = new JFrame("File Selection Screen");

		fileSelectionPanel = new JPanel();

		fileSelectionPanel.setLayout(new FlowLayout());

		// JLabel label = new JLabel("This is a label!");
		// mainPanel.add(label);

		fileListLabel = new JLabel();
		fileListLabel.setText("scope file lists:");
		fileSelectionPanel.add(fileListLabel);

		fileListTable = new JTable();
		tableModel = (DefaultTableModel) fileListTable.getModel();

		tableModel.addColumn("File Names");

		// String [] row1 = new String[3];
		// row1[0] = "12";
		// row1[1] = "234";
		// row1[2] = "67";
		// tableModel.addRow(row1);

		fileListTable.setModel(tableModel);

		JScrollPane scroll = new JScrollPane(fileListTable);
		fileSelectionPanel.add(scroll);

		selectButton = new JButton();
		selectButton.setText("Select");
		fileSelectionPanel.add(selectButton);

		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		fileSelectionPanel.add(cancelButton);
		
		UiHelper.setColor(fileSelectionPanel);
		
		addEventHandlers();

		fileSelectionFrame.add(fileSelectionPanel);
		fileSelectionFrame.setSize(FILE_SELECTION_FRAME_WIDTH, FILE_SELECTION_FRAME_HEIGHT);
		fileSelectionFrame.setLocationRelativeTo(null);
		fileSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void removeAllRowsFromTableModel() {
		if (tableModel.getRowCount() > 0) {
			for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
				tableModel.removeRow(i);
			}
		}
	}

	public void bindFileNameList2TableModel() {
		if (scopeFileList != null && !scopeFileList.isEmpty()) {
			for (String fileName : scopeFileList) {
				String[] row = new String[1];
				row[0] = fileName;
				tableModel.addRow(row);
			}
		}
	}

	public void render() {
		refreshScreenFileList();
		fileSelectionFrame.setVisible(true);
	}

	protected void addEventHandlers() {
		FileListSelectionHandler fileListSelectionHandler = new FileListSelectionHandler(this);
		fileListTable.getSelectionModel().addListSelectionListener(fileListSelectionHandler);

		SelectButtonActionHandler selectButtonActionHandler = new SelectButtonActionHandler(this);
		selectButton.addActionListener(selectButtonActionHandler);

		CancelButtonActionHandler cancelButtonActionHandler = new CancelButtonActionHandler(this);
		cancelButton.addActionListener(cancelButtonActionHandler);
		// MainListTableModelHandler mainListTableModelHandler = new
		// MainListTableModelHandler(this);
		// mainTable.getModel().addTableModelListener(mainListTableModelHandler);
	}

	public static void main(String[] args) {
		try {
			FileSelectionView fileSelectionView = new FileSelectionView(null);
			fileSelectionView.render();
		} catch (Exception e) {
			logger.error(className + ".main()", e);
		}
	}
}
