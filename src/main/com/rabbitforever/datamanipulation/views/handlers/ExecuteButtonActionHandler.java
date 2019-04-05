package com.rabbitforever.datamanipulation.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.models.vos.CaptureScopeVo;
import com.rabbitforever.datamanipulation.services.SnapshotMgr;
import com.rabbitforever.datamanipulation.views.MainScreenView;
import com.rabbitforever.datamanipulation.views.validators.MainViewValidator;

public class ExecuteButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainView;
	
	public ExecuteButtonActionHandler(MainScreenView mainView){
		this.mainView = mainView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			MainViewValidator validator = new MainViewValidator();
			
			boolean isCaptureScopeDtoValid = validator.isScopeValid(mainView);
			boolean isCaptureDtoListValid = validator.isCaptureListValid(mainView);
			
			if (!isCaptureScopeDtoValid){
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "Scope Name or Scope Folder Name is/ are missing!");
				return;
			}
			
			if (!isCaptureDtoListValid){
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "No capture list entry!");
				return;
			}
			
			mainView.collectCaptureScopeVoData();
			CaptureScopeVo captureScopeVo = mainView.getCaptureScopeVo();
			captureScopeVo.syncCaptureVoList2CaptureDtoList();
			SnapshotMgr serviceMgr = new SnapshotMgr();
			boolean isCaptured = serviceMgr.captureXmlFiles(captureScopeVo);
			if (isCaptured){
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "scope is being executed!");
			} else {
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "Exception caught! Check console log!");
			}
		} catch (Exception ex){
			logger.error(className + ".actionPerformed()", ex);
		}
	}

}
