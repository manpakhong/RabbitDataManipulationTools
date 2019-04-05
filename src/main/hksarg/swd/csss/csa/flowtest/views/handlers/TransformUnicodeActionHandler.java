package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.vos.CaptureScopeVo;
import hksarg.swd.csss.csa.flowtest.services.SnapshotMgr;
import hksarg.swd.csss.csa.flowtest.views.MainScreenView;
import hksarg.swd.csss.csa.flowtest.views.validators.MainViewValidator;

public class TransformUnicodeActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainView;

	public TransformUnicodeActionHandler(MainScreenView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SnapshotMgr snapshotMgr = null;
		try {
			snapshotMgr = new SnapshotMgr();
			CaptureScopeVo captureScopeVo = mainView.getCaptureScopeVo();
			MainViewValidator validator = new MainViewValidator();
			boolean isValid = validator.isScopeValid(mainView);
			if (isValid) {
				boolean areTransformed = snapshotMgr.transformUnicodeFiles(captureScopeVo);
				if (areTransformed) {
					JOptionPane.showMessageDialog(mainView.getMainFrame(), "snapshot file(s) is/are transformed!");
				}
			} else {
				JOptionPane.showMessageDialog(mainView.getMainFrame(), "No snapshot file(s) found!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (snapshotMgr != null){
				snapshotMgr = null;
			}
		}
	}

}
