package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.bundles.SysProperties;
import hksarg.swd.csss.csa.flowtest.factories.PropertiesFactory;
import hksarg.swd.csss.csa.flowtest.models.dtos.DeleteDto;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureScopeVo;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureVo;
import hksarg.swd.csss.csa.flowtest.services.DataManupilateMgr;
import hksarg.swd.csss.csa.flowtest.utils.SystemUtils;
import hksarg.swd.csss.csa.flowtest.views.RestoreMainView;

public class VerAndGenDelStatementsButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private RestoreMainView mainView;
	private SysProperties sysProperties;
	private CaptureScopeVo captureScopeVo;

	public VerAndGenDelStatementsButtonActionHandler(RestoreMainView mainView) {
		try {
			this.mainView = mainView;
		} catch (Exception e) {
			logger.error(className + ".VerAndGenDelStatementsButtonActionHandler() ", e);
		}
	}

	private void initParams() {
		try {
			if (sysProperties == null) {
				sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			}

			captureScopeVo = mainView.getCaptureScopeVo();

		} catch (Exception e) {
			logger.error(className + ".VerAndGenDelStatementsButtonActionHandler() ", e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			initParams();
			String scopeName = mainView.getScopeNameTextField().getText();
			if (scopeName == null || scopeName.isEmpty()) {
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "Please load the scope before process!");
				return;
			}
			DataManupilateMgr dataManupilateMgr = new DataManupilateMgr();
			
			boolean isGenerated = dataManupilateMgr.checkAndGenerateDeleteStatements(captureScopeVo);
			if (isGenerated) {
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "Delete statements are generated!");
			} else {
				JOptionPane.showMessageDialog(mainView.getMainFrame(),
						"Exception found! Delete statements cannot be generated! Check console for the details of exception.");
			}


			Map<Integer, Boolean>editableMap = getEditableMap(captureScopeVo);
			mainView.getMainTable().setEditableMap(editableMap);
			mainView.getRestoreButton().setEnabled(true);
			
			boolean isOpenFolderEnabled = sysProperties.getDeleteStatementGenerateAutoOpenFolder();
			if (isOpenFolderEnabled) {
				openFolder();
			}
			
			boolean isOpenFileEnabled = sysProperties.getDeleteStatementGenerateAutoOpenFile();
			if (isOpenFileEnabled){
				openFile();
			}
			
			mainView.bindCaptureVo2TableModel();
			
		} catch (Exception ex) {
			logger.error(className + ".actionPerformed() - ", ex);
		}
	}
	

	private Map<Integer, Boolean> getEditableMap(CaptureScopeVo captureScopeVo){
		List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
		Map<Integer, Boolean> editableMap = new HashMap<Integer, Boolean>();
		for (int i=0 ; i< captureVoList.size(); i++){
			CaptureVo captureVo = captureVoList.get(i);
			DeleteDto deleteDto = captureVo.getDeleteDto();
			editableMap.put(i, deleteDto.getIsValid());
		}
		return editableMap;
	}
	
	private void openFile(){
		try {
			boolean isWindows = SystemUtils.isWindows();
			if (isWindows) {
				String notePadProgram = sysProperties.getNotePadProgram();
				String testFolder = sysProperties.getTestFolderRoot();
				String deleteSqlFileName = sysProperties.getDeleteStatementResultFilesName();
				String deleteSqlFileExt = sysProperties.getDeleteStatementResultFilesNameExt();
				testFolder = testFolder.replace("./", "");
				String scopeFolderName = captureScopeVo.getScopeFolderName();

				
				String current = System.getProperty("user.dir");
				String command = current + "\\" + notePadProgram;
				String fullPath = testFolder + "\\" + scopeFolderName + "\\" + deleteSqlFileName + deleteSqlFileExt;
				fullPath = fullPath.replace("/","\\");
				Runtime.getRuntime().exec(command + " " + "\"" + fullPath + "\"");

			}
		} catch (Exception e) {
			logger.error(className + ".openFolder() ", e);
		}
	}
	
	private void openFolder() {
		try {
			boolean isWindows = SystemUtils.isWindows();
			if (isWindows) {
				String testFolder = sysProperties.getTestFolderRoot();
				testFolder = testFolder.replace("./", "");
				String scopeFolderName = captureScopeVo.getScopeFolderName();

				String current = System.getProperty("user.dir");

				String fullPath = testFolder + "/" + scopeFolderName + "/";
				fullPath = fullPath.replace("/", "\\");
				Runtime.getRuntime().exec("explorer " + fullPath);

			}
		} catch (Exception e) {
			logger.error(className + ".openFolder() ", e);
		}
	}

}
