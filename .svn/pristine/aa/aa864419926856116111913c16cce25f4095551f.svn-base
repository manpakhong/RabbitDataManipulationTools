package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.FileSelectionView;

public class CancelButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private FileSelectionView fileSelectionView;
	public CancelButtonActionHandler(FileSelectionView fileSelectionView) {
		this.fileSelectionView = fileSelectionView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame fileSelectFrame = fileSelectionView.getFileSelectionFrame();
		fileSelectFrame.dispose();
	}

}
