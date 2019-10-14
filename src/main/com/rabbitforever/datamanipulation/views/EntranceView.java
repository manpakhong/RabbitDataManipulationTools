package com.rabbitforever.datamanipulation.views;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.PropertyConfigurator;
import org.apache.maven.doxia.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.helpers.UiHelper;
import com.rabbitforever.datamanipulation.views.handlers.GoToAssertionScreenActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.GoToCaptureScreenActionHandler;
import com.rabbitforever.datamanipulation.views.handlers.GoToRestoreScreenActionHandler;

public class EntranceView {
	private static final Logger logger = LoggerFactory.getLogger(EntranceView.class);
	private static String className = EntranceView.class.getName();
	protected JFrame entranceFrame = null;
	protected JPanel entrancePanel = null;
	protected JPanel northEntrancePanel = null;
	protected JPanel southEntrancePanel = null;
	protected JButton goToCaptureMainButton = null;
	protected JButton goToRestoreMainButton = null;
	protected JButton gotoAssertionMainButton = null;
	protected CaptureMainView captureMainView;
	protected RestoreMainView restoreMainView;
	protected AssertionMainView assertionMainView;

	public static final int FILE_SELECTION_FRAME_WIDTH = 1000;
	public static final int FILE_SELECTION_FRAME_HEIGHT = 500;
	private SysProperties sysProperties = null;

	public RestoreMainView getRestoreMainView() {

		if (restoreMainView == null) {
			restoreMainView = new RestoreMainView();
		}
		return restoreMainView;
	}

	public CaptureMainView getCaptureMainView() {
		if (captureMainView == null) {
			captureMainView = new CaptureMainView();
		}
		return captureMainView;
	}

	public AssertionMainView getAssertionMainView() {
		if (assertionMainView == null) {
			assertionMainView = new AssertionMainView();
		}
		return assertionMainView;
	}

	public EntranceView() {
		initParams();
		init();
	}

	private void initParams() {
		try {
			sysProperties = PropertiesFactory.getInstanceOfSysProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render() {
		entranceFrame.setVisible(true);
	}

	private void init() {
		entranceFrame = new JFrame("Entrance Screen");

		entrancePanel = new JPanel();

		entrancePanel.setLayout(new GridLayout());
		//
		// northEntrancePanel = new JPanel();
		// southEntrancePanel = new JPanel();

		Color color = new Color(sysProperties.getColorRgbR(), sysProperties.getColorRgbG(), sysProperties.getColorRgbG());
		
		goToCaptureMainButton = new JButton();
		goToCaptureMainButton.setText("Go to capture screen");
		goToCaptureMainButton.setBackground(color);
		entrancePanel.add(goToCaptureMainButton);

		goToRestoreMainButton = new JButton();
		goToRestoreMainButton.setText("Go to restore screen");
		goToRestoreMainButton.setBackground(color);
		entrancePanel.add(goToRestoreMainButton);

		gotoAssertionMainButton = new JButton();
		gotoAssertionMainButton.setText("Go to assertion screen");
		gotoAssertionMainButton.setBackground(color);
		entrancePanel.add(gotoAssertionMainButton);


		
		UiHelper.setColor(entrancePanel, color);
		
		addEventHandlers();

		entranceFrame.add(entrancePanel);
		entranceFrame.setSize(FILE_SELECTION_FRAME_WIDTH, FILE_SELECTION_FRAME_HEIGHT);
		entranceFrame.setLocationRelativeTo(null);
		entranceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		entranceFrame.setBackground(color);
		// entrancePanel.add(northEntrancePanel, BorderLayout.NORTH);
		// entrancePanel.add(southEntrancePanel, BorderLayout.CENTER);

	}

	private void addEventHandlers() {
		try {
			GoToCaptureScreenActionHandler goToCaptureScreenActionHandler = new GoToCaptureScreenActionHandler(this);
			goToCaptureMainButton.addActionListener(goToCaptureScreenActionHandler);

			GoToRestoreScreenActionHandler goToRestoreScreenActionHandler = new GoToRestoreScreenActionHandler(this);
			goToRestoreMainButton.addActionListener(goToRestoreScreenActionHandler);

			GoToAssertionScreenActionHandler goToAssertionScreenActionHandler = new GoToAssertionScreenActionHandler(
					this);
			gotoAssertionMainButton.addActionListener(goToAssertionScreenActionHandler);
		} catch (Exception e) {
			logger.error(className + ".addEventHandlers()", e);
		}
	}

	public static void main(String[] args) {
		try {
//			Properties logProperties = new Properties();
//			String current = System.getProperty("user.dir");
//			System.out.println("Current working directory in Java : " + current);
//
//			logProperties.load(new FileInputStream(current + "/log4j.properties"));
//			PropertyConfigurator.configure(logProperties);
			System.out.println("logger initialized...");
			if (logger.isDebugEnabled()){
				logger.debug("logger test ok!");
			}
			EntranceView entranceView = new EntranceView();
			entranceView.render();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
