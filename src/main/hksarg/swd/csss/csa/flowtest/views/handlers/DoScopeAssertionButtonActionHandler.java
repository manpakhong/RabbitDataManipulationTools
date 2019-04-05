package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.bundles.SysProperties;
import hksarg.swd.csss.csa.flowtest.factories.PropertiesFactory;
import hksarg.swd.csss.csa.flowtest.factories.ScopeTestGlobalVariablesFactory;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureScopeDto;
import hksarg.swd.csss.csa.flowtest.models.dtos.FlowTestJobDto;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureScopeVo;
import hksarg.swd.csss.csa.flowtest.services.SnapshotMgr;
import hksarg.swd.csss.csa.flowtest.services.ThreadJobMgr;
import hksarg.swd.csss.csa.flowtest.views.MainScreenView;
import hksarg.swd.csss.csa.flowtest.views.validators.DoAssertionValidator;

public class DoScopeAssertionButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainScreenView;
	private SysProperties sysProperties;

	public DoScopeAssertionButtonActionHandler(MainScreenView mainScreenView) throws Exception {
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
				boolean isUsed = ScopeTestGlobalVariablesFactory.getIsUsed();
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
					JOptionPane.showMessageDialog(mainScreenView.getMainFrame(),
							"Need to load scope file before doing assertion!");
				} else {

					if (!ThreadJobMgr.getIsStarted()) {
						ThreadJobMgr threadJobMgr = new ThreadJobMgr();
						threadJobMgr.startThreadJobs();
					}

					if (isAllActualAssertionTargetSqlValidated) {
						if (mainScreenView.getDoScopeAssertionButton() != null){
							mainScreenView.getDoScopeAssertionButton().setEnabled(false);
						}
						if (mainScreenView.getDoTablesAssertionButton() != null){
							mainScreenView.getDoTablesAssertionButton().setEnabled(false);
						}
						mainScreenView.getProgressStatusLabel().setText(MainScreenView.PROGRESS_STATUS_PROCESSING);
						FlowTestJobDto <CaptureScopeDto> flowTestJob = new FlowTestJobDto <CaptureScopeDto>();
						flowTestJob.setStartDateTime(new Date());
						flowTestJob.setContent(captureScopeDto);
						flowTestJob.setScopeFileName(captureScopeDto.getScopeFileName());
						flowTestJob.setMainScreenView(mainScreenView);
						ThreadJobMgr.addPendingFlowTestJob(flowTestJob);
					} else {
						JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), "Need to fill All Actual Table Sqls!");
					}
				}
			} else {
				JOptionPane.showMessageDialog(mainScreenView.getMainFrame(),
						"Cannot saved!");
			}
		} catch (Exception ex) {
			logger.error(className + ".actionPerformed()", ex);
		}
	}

}
