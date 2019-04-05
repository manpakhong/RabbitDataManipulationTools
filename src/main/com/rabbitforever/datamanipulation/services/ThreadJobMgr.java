package com.rabbitforever.datamanipulation.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.ExecutorsFactory;
import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.factories.TableTestGlobalVariablesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.dtos.FlowTestJobDto;
import com.rabbitforever.datamanipulation.threads.FlowTestJobsExecutor;
import com.rabbitforever.datamanipulation.utils.CommonUtils;
import com.rabbitforever.datamanipulation.utils.DateUtils;
import com.rabbitforever.datamanipulation.utils.FileUtils;
import com.rabbitforever.datamanipulation.views.MainScreenView;

public class ThreadJobMgr {
	private final static Logger logger = LoggerFactory.getLogger(ThreadJobMgr.class);
	private final static String SEPARATOR_CHAR = ">";
	private final static String className = ThreadJobMgr.class.getName();
	private static boolean isStarted;
	private static BlockingQueue<FlowTestJobDto> mgrQueue;
	private FileUtils fileUtils;
	private SysProperties sysProperties;
	public ThreadJobMgr() throws Exception{
		try{
			init();
		} catch (Exception e){
			logger.error(className + ".ThreadJobMgr() - ", e);
			throw e;
		}
	}
	
	private void init() throws Exception {
		try{
			fileUtils = new FileUtils();
			sysProperties = PropertiesFactory.getInstanceOfSysProperties();
		} catch (Exception e){
			logger.error(className + ".init() - ", e);
			throw e;
		}
	}

	private void writeAssertionResult2LogFiles(String strResult, String scopeFileName) throws Exception{
		try{
			boolean resultLogEnabled = sysProperties.getResultLogEnabled();
			boolean resultLogNetworkDriveEnabled = sysProperties.getResultLogNetworkDriveEnabled();
			String resultLogFolderRoot = sysProperties.getResultLogFolderRoot();
			String dateTimeString = DateUtils.getDateTimeString();
			String resultLogPrefix = sysProperties.getResultLogPrefix();
			String resultLogFileNameExt = sysProperties.getResultLogFileNameExt();
			String logFileName = resultLogPrefix + "_" + scopeFileName + "_" + dateTimeString + resultLogFileNameExt; 
			String localLogFileName = resultLogFolderRoot + "/" + logFileName;
			String resultLogNetworkDriveFolderRootPath = sysProperties.getResultLogNetworkDriveFolderRootPath();
			String networkLogFileName = resultLogNetworkDriveFolderRootPath + "/" + logFileName;
			
			if (resultLogEnabled){
				boolean isLocalLogDirExisted = fileUtils.isDirExisted(resultLogFolderRoot);
				if (!isLocalLogDirExisted){
					fileUtils.createDirectoryIfNotExisted(resultLogFolderRoot);
				}
				fileUtils.writeText2File(strResult, localLogFileName);
				if (logger.isInfoEnabled()){
					logger.info(className + ".writeAssertionResult2LogFiles() - " + strResult);
				}
				if (resultLogNetworkDriveEnabled){
					boolean isNetworkLogDirExisted = fileUtils.isDirExisted(resultLogNetworkDriveFolderRootPath);
					if (!isNetworkLogDirExisted){
						fileUtils.createDirectoryIfNotExisted(resultLogNetworkDriveFolderRootPath);
					}
					fileUtils.writeText2File(strResult, networkLogFileName);
					if (logger.isInfoEnabled()){
						logger.info(className + ".writeAssertionResult2LogFiles() - " + strResult);
					}
				}
				
			}
		} catch (Exception e){
			logger.error(className + ".writeAssertionResult2LogFiles() - strResult=" + strResult, e);
			throw e;
		}
	}
	
	private void showFinalResultToInterfaceAfterAllTableUnitTest(FlowTestJobDto flowTestJob){
		try{
			boolean isOk = TableTestGlobalVariablesFactory.getIsOk();
			List<String> resultList = TableTestGlobalVariablesFactory.getResultList();
			MainScreenView mainScreenView = flowTestJob.getMainScreenView();
			Date startDateTime = TableTestGlobalVariablesFactory.getStartDateTime();
			Date endDateTime = TableTestGlobalVariablesFactory.getEndDateTime();
			String scopeFileName = TableTestGlobalVariablesFactory.getScopeFileName();
			
			
			String scopeNameLine = "Scope Name: " + scopeFileName + "\n";
			
			StringBuilder sbResultHeader = new StringBuilder();
			sbResultHeader.append(scopeNameLine);
			sbResultHeader.append("Thread id:" + flowTestJob.getThreadId());
			sbResultHeader.append(", ");
			sbResultHeader.append("Job id:" + flowTestJob.getJobId());
			sbResultHeader.append(", ("+ startDateTime + " - " + endDateTime +")");
			String separateLine = CommonUtils.repeatString(SEPARATOR_CHAR, sbResultHeader.length() - scopeNameLine.length());
			sbResultHeader.append("\n" + separateLine);
			
			if (!isOk){
				if (resultList != null && !resultList.isEmpty() && resultList.size() > 0){
					StringBuilder sbResult = new StringBuilder();
					for (int i =0; i < resultList.size(); i++){
						String resultLine = resultList.get(i);
						sbResult.append(resultLine);
						if (i != resultList.size()){
							sbResult.append("\n");
						}
						
					}
					JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), sbResultHeader.toString() + "\n" +  sbResult.toString());
					writeAssertionResult2LogFiles(sbResultHeader.toString() + "\n" +  sbResult.toString(), scopeFileName);
				} else {
					logger.warn(className + ".showFinalResultToInterfaceAfterAllTableUnitTest() - isOk is false, resultList must have contents, resultList=" + resultList + ",flowTestJob=" + flowTestJob);
				}
			} else {
				String resultStr = sbResultHeader.toString() + "\n" +   "\nAssertions are passed!";
				JOptionPane.showMessageDialog(mainScreenView.getMainFrame(),  resultStr);
				writeAssertionResult2LogFiles(resultStr, scopeFileName);
			}
			
			mainScreenView.getDoScopeAssertionButton().setEnabled(true);
			mainScreenView.getDoTablesAssertionButton().setEnabled(true);
			mainScreenView.getProgressStatusLabel().setText(MainScreenView.PROGRESS_STATUS_STAND_BY);
		} catch (Exception e){
			logger.error(className + ".showFinalResultToInterfaceAfterAllTableUnitTest() - flowTestJob=" + flowTestJob, e);
		}
	}
	
	public void doUpdateInterfaceAfterTableUnitTest(FlowTestJobDto flowTestJob){
		
		Boolean isCurrentOk = flowTestJob.getIsOk();
		String result = flowTestJob.getResult();
		MainScreenView mainScreenView = flowTestJob.getMainScreenView();

		int count = TableTestGlobalVariablesFactory.getCount();
		count = count - 1;
		TableTestGlobalVariablesFactory.setCount(count);
		
		if (!isCurrentOk){
			TableTestGlobalVariablesFactory.setIsOk(false);
		}
		
		if (count== 0){
			showFinalResultToInterfaceAfterAllTableUnitTest(flowTestJob);
		} 

	}
	public void doUpdateInterfaceAfterTableUnitTestException(FlowTestJobDto flowTestJob){
		Boolean isCurrentOk = flowTestJob.getIsOk();
		String result = flowTestJob.getResult();
		MainScreenView mainScreenView = flowTestJob.getMainScreenView();

		if (!isCurrentOk){
			TableTestGlobalVariablesFactory.setIsOk(false);
		}
		
		int count = TableTestGlobalVariablesFactory.getCount();
		count = count - 1;
		TableTestGlobalVariablesFactory.setCount(count);
		if (count== 0){
			showFinalResultToInterfaceAfterAllTableUnitTest(flowTestJob);
		} 
	

		mainScreenView.getDoScopeAssertionButton().setEnabled(true);
		mainScreenView.getDoTablesAssertionButton().setEnabled(true);
	}
	
	
	
	public void doUpdateInterfaceAfterScopeUnitTest(FlowTestJobDto flowTestJob){
		try{
			Boolean isOk = flowTestJob.getIsOk();
			String result = flowTestJob.getResult();
			MainScreenView mainScreenView = flowTestJob.getMainScreenView();
			String scopeFileName = flowTestJob.getScopeFileName();
			
			String scopeNameLine = "Scope Name: " + scopeFileName + "\n";
			
			StringBuilder sbResult = new StringBuilder();
			sbResult.append(scopeNameLine);
			sbResult.append("Thread id:" + flowTestJob.getThreadId());
			sbResult.append(", ");
			sbResult.append("Job id:" + flowTestJob.getJobId());
			sbResult.append(", ("+ flowTestJob.getStartDateTime() + "-" + flowTestJob.getEndDateTime() +")");
			String separateLine = CommonUtils.repeatString(SEPARATOR_CHAR, sbResult.length() - scopeNameLine.length());
			sbResult.append("\n" + separateLine);
			
			
			
			if (!isOk){
				sbResult.append("\n" + result);
				JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), sbResult);
				writeAssertionResult2LogFiles(sbResult.toString(), scopeFileName);			
				if (logger.isInfoEnabled()){
					logger.info(className + ".writeAssertionResult2LogFiles() - " + sbResult.toString());
				}
			} else {
				sbResult.append("\nAssertions are passed!");
				JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), sbResult);
				writeAssertionResult2LogFiles(sbResult.toString(), scopeFileName);
				if (logger.isInfoEnabled()){
					logger.info(className + ".writeAssertionResult2LogFiles() - " + sbResult.toString());
				}
			}
		
			mainScreenView.getProgressStatusLabel().setText(MainScreenView.PROGRESS_STATUS_STAND_BY);
			mainScreenView.getDoScopeAssertionButton().setEnabled(true);
			mainScreenView.getDoTablesAssertionButton().setEnabled(true);
		} catch (Exception e){
			logger.error(className + ".doUpdateInterfaceAfterScopeUnitTest() - flowTestJob=" + flowTestJob, e);
		}
	}
	public void doUpdateInterfaceAfterScopeUnitTestException(FlowTestJobDto flowTestJob){
		try{
			Boolean isOk = flowTestJob.getIsOk();
			String result = flowTestJob.getResult();
			String scopeFileName = flowTestJob.getScopeFileName();
			MainScreenView mainScreenView = flowTestJob.getMainScreenView();
			String scopeNameLine = "Scope Name: " + scopeFileName + "\n";
			
			
			StringBuilder sbResult = new StringBuilder();
			sbResult.append(scopeNameLine);
			sbResult.append("Thread id:" + flowTestJob.getThreadId());
			sbResult.append(", ");
			sbResult.append("Job id:" + flowTestJob.getJobId());
			sbResult.append(", ("+ flowTestJob.getStartDateTime() + "-" + flowTestJob.getEndDateTime() +")");
			String separateLine = CommonUtils.repeatString(SEPARATOR_CHAR, sbResult.length() -  scopeNameLine.length());
			sbResult.append("\n" + separateLine);
			
			if (!isOk){
				sbResult.append("\n" + result);
				JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), sbResult);
				writeAssertionResult2LogFiles(sbResult.toString(), scopeFileName);
				if (logger.isInfoEnabled()){
					logger.info(className + ".writeAssertionResult2LogFiles() - " + sbResult.toString());
				}
			} else {
				sbResult.append("\nAssertions are passed!");
				JOptionPane.showMessageDialog(mainScreenView.getMainFrame(), sbResult);
				writeAssertionResult2LogFiles(sbResult.toString(), scopeFileName);
				if (logger.isInfoEnabled()){
					logger.info(className + ".writeAssertionResult2LogFiles() - " + sbResult.toString());
				}
			}
		
			mainScreenView.getProgressStatusLabel().setText(MainScreenView.PROGRESS_STATUS_STAND_BY);
			if (mainScreenView.getDoScopeAssertionButton() != null){
				mainScreenView.getDoScopeAssertionButton().setEnabled(true);
			}
			if (mainScreenView.getDoTablesAssertionButton() != null){
				mainScreenView.getDoTablesAssertionButton().setEnabled(true);
			}
		} catch (Exception e){
			logger.error(className + ".doUpdateInterfaceAfterScopeUnitTestException() - flowTestJob=" + flowTestJob, e);
		}
	}
	
	
	public static void addPendingFlowTestJob(FlowTestJobDto flowTestjob){
		try {
			mgrQueue.put(flowTestjob);
		} catch (InterruptedException e) {
			logger.error(className + ".addPendingFlowTestJob() - ", e);
		}
	}
	
	public static List<FlowTestJobDto> getPendingFlowTestJobList(int size){
		List<FlowTestJobDto> flowTestJobList = new ArrayList<FlowTestJobDto>();
		
		for (int i= 0; i < size; i++){
			if (mgrQueue.size() > 0){
				flowTestJobList.add(mgrQueue.poll());
			}
		}
		return flowTestJobList;
	}

	public static boolean getIsStarted(){
		return isStarted;
	}
	
	public void startThreadJobs(){
		try{
			if (mgrQueue == null){
				mgrQueue = new LinkedBlockingQueue<FlowTestJobDto>();
			}
			FlowTestJobsExecutor<FlowTestJobDto> flowTestJobsProviderExecutor = ExecutorsFactory.getFlowTestJobsProviderExecutorInstance();
			FlowTestJobsExecutor<FlowTestJobDto> flowTestJobsConsumerExecutor = ExecutorsFactory.getFlowTestJobsConsumerExecutorInstance();
			Thread providerThread = new Thread(flowTestJobsProviderExecutor);
			providerThread.start();
			Thread consumerThread = new Thread(flowTestJobsConsumerExecutor);
			consumerThread.start();
			isStarted = true;
		} catch (Exception e){
			logger.error(className + ".startThreadJobs() - ", e);
		}
	}
	

	
	public void pauseThreadJobs(){
		FlowTestJobsExecutor<FlowTestJobDto> flowTestJobsProviderExecutor = ExecutorsFactory.getFlowTestJobsProviderExecutorInstance();
		FlowTestJobsExecutor<FlowTestJobDto> flowTestJobsConsumerExecutor = ExecutorsFactory.getFlowTestJobsConsumerExecutorInstance();
		flowTestJobsProviderExecutor.setRunningEnabled(false);
		flowTestJobsConsumerExecutor.setRunningEnabled(false);
	}
	
	public void playThreadJobs(){
		FlowTestJobsExecutor<FlowTestJobDto> flowTestJobsProviderExecutor = ExecutorsFactory.getFlowTestJobsProviderExecutorInstance();
		FlowTestJobsExecutor<FlowTestJobDto> flowTestJobsConsumerExecutor = ExecutorsFactory.getFlowTestJobsConsumerExecutorInstance();
		flowTestJobsProviderExecutor.setRunningEnabled(true);
		flowTestJobsConsumerExecutor.setRunningEnabled(true);
	}
}
