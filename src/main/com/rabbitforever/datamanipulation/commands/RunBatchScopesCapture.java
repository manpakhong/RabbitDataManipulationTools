package com.rabbitforever.datamanipulation.commands;

import java.io.File;
import java.util.Date;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.models.vos.CaptureScopeVo;
import com.rabbitforever.datamanipulation.services.SnapshotMgr;

public class RunBatchScopesCapture {

	public static void main(String[] args) {
		try{
			SysProperties sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			String testFolderRoot = sysProperties.getTestFolderRoot();
			// getCaptureScopeDtoFromScopeFileName();
			File folder = new File(testFolderRoot);
			File[] fileArray = folder.listFiles();
			doBatchCapture(fileArray);
		} catch (Exception e){
			e.printStackTrace();
		}

	}
	public static void doBatchCapture(File[] files) {
		try{
			SysProperties sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			String testFolderRoot = sysProperties.getTestFolderRoot();
			SnapshotMgr snapshotMgr = new SnapshotMgr();
			Date startDate = new Date();
			System.out.println("batch start time:" + startDate.toString());
		    for (File file : files) {
		    	Date fileStartDate = new Date();
		    	System.out.println(file + "-fileStartDate:" + fileStartDate.toString());
		        if (file.isDirectory()) {
		            //System.out.println("Directory: " + file.getName());
		            //showFiles(file.listFiles()); // Calls same method again.
		        } else {
		        	String fullPath = file.getName();
		            // System.out.println("doing file: " + fullPath);
		            CaptureScopeDto captureScopeDto = snapshotMgr.getCaptureScopeDtoFromScopeFileName(fullPath);
		            CaptureScopeVo captureScopeVo = new CaptureScopeVo(captureScopeDto);
		            snapshotMgr.captureXmlFiles(captureScopeVo);
		        }
		        Date fileEndDate = new Date();
		    	System.out.println(file + "-fileEndDate:" + fileEndDate.toString());
		    }
		    Date endDate = new Date();
		    System.out.println("batch end time:" + endDate.toString());
		    System.out.println("batch start time:" + startDate.toString() + ",batch end time:" + endDate.toString());
		    System.out.println("finished!");
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
