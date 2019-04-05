package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.vos.CaptureScopeVo;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureVo;
import hksarg.swd.csss.csa.flowtest.services.SnapshotMgr;
import hksarg.swd.csss.csa.flowtest.ui.CaptureVoJTable;
import hksarg.swd.csss.csa.flowtest.views.RestoreMainView;

public class RestoreButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private RestoreMainView mainView;

	
	public RestoreButtonActionHandler(RestoreMainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String scopeName = mainView.getScopeNameTextField().getText();
			if (scopeName == null || scopeName.isEmpty()) {
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "Please load the scope before process!");
				return;
			}
			CaptureScopeVo captureScopeVo = mainView.getCaptureScopeVo();
			List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
			
			mainView.updateCaptureDtoVo();
			mainView.getRestoreButton().setEnabled(false);
			
			
			SnapshotMgr snapshotMgr = new SnapshotMgr();
			
			List<String> messageList = new ArrayList<String>();
			
			boolean areAllProceduresCompletedWithoutError = snapshotMgr.doRestoreProcedures(captureScopeVo, messageList);
			

			if (areAllProceduresCompletedWithoutError){
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "Restore completed!");
			} else {
				
				StringBuilder messages = new StringBuilder();
				messages.append("Exception found, please check colsole message!\n");
				for (int i = 0; i < messageList.size(); i++){
					String message = messageList.get(i); 
					if (i > 0){
						messages.append("\n");
					}
					messages.append(message);
				}
				
				JOptionPane.showMessageDialog(mainView.getMainFrame(), messages.toString());
			}
			mainView.getRestoreButton().setEnabled(true);
		} catch (Exception ex) {
        	logger.error(className + ".actionPerformed() - ",e);
        	JOptionPane.showMessageDialog(mainView.getMainFrame(), "Exception found, please check colsole message!");
			mainView.getRestoreButton().setEnabled(true);
		}
	}

}
