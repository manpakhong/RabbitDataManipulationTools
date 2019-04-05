package com.rabbitforever.datamanipulation.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.factories.TableTestGlobalVariablesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.models.dtos.FlowTestJobDto;
import com.rabbitforever.datamanipulation.models.vos.CaptureScopeVo;
import com.rabbitforever.datamanipulation.services.SnapshotMgr;
import com.rabbitforever.datamanipulation.services.ThreadJobMgr;
import com.rabbitforever.datamanipulation.views.MainScreenView;
import com.rabbitforever.datamanipulation.views.validators.DoAssertionValidator;

public class DoTablesAssertionButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainScreenView;
	private SysProperties sysProperties;

	public DoTablesAssertionButtonActionHandler(MainScreenView mainScreenView) throws Exception {
		this.mainScreenView = mainScreenView;
		sysProperties = PropertiesFactory.getInstanceOfSysProperties();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			CaptureScopeVo captureScopeVo = mainScreenView.getCaptureScopeVo();
			CaptureScopeDto captureScopeDto = captureScopeVo;

			SnapshotMgr serviceMgr = new SnapshotMgr();
			boolean isSaved = serviceMgr.saveScopeFile(captureScopeDto);
			if (isSaved) {

				DoAssertionValidator valdator = new DoAssertionValidator();
				TableTestGlobalVariablesFactory.resetParams();
				boolean isValidated = valdator.isScopeValid(captureScopeDto);
				boolean isAllActualAssertionTargetSqlValidated = valdator
						.validateAllActualAssertionTargetSql(captureScopeDto);
				boolean isFlatXmlStyle = valdator.isFlatXmlStyle();

				if (isFlatXmlStyle) {
					JOptionPane.showMessageDialog(mainScreenView.getMainFrame(),
							"FlatXmlStyle cannot do assertion! Change sys.properties - flat_xml_style=false to enable the function!");
					return;
				}

				if (!isValidated) {
					JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), "Need to load scope file before doing assertion!");
				} else {

					if (!ThreadJobMgr.getIsStarted()) {
						ThreadJobMgr threadJobMgr = new ThreadJobMgr();
						threadJobMgr.startThreadJobs();
					}

					if (isAllActualAssertionTargetSqlValidated) {
						mainScreenView.getDoScopeAssertionButton().setEnabled(false);
						mainScreenView.getDoTablesAssertionButton().setEnabled(false);
						mainScreenView.getProgressStatusLabel().setText(MainScreenView.PROGRESS_STATUS_PROCESSING);
						
						List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();
						
						if (captureDtoList != null && !captureDtoList.isEmpty()){
							Date nowDateTime = new Date();
							TableTestGlobalVariablesFactory.setCount(captureDtoList.size());
							TableTestGlobalVariablesFactory.setScopeFolderName(captureScopeDto.getScopeFolderName());
							TableTestGlobalVariablesFactory.setStartDateTime(nowDateTime);
							TableTestGlobalVariablesFactory.setScopeFileName(captureScopeDto.getScopeFileName());
							for (CaptureDto captureDto: captureDtoList){
								FlowTestJobDto <CaptureDto> flowTestJob = new FlowTestJobDto <CaptureDto>();
								flowTestJob.setScopeFileName(captureScopeDto.getScopeFileName());
								flowTestJob.setScopeFolderName(captureScopeDto.getScopeFolderName());
								flowTestJob.setStartDateTime(nowDateTime);
								flowTestJob.setContent(captureDto);
								flowTestJob.setMainScreenView(mainScreenView);
								ThreadJobMgr.addPendingFlowTestJob(flowTestJob);
							}
						}
					} else {
						JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), "Need to fill All Actual Table Sqls!");
					}
				}
			} else {
				JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), "Cannot saved!");
			}
		} catch (Exception ex) {
			logger.error(className + ".actionPerformed()", ex);
		}
	}

}
