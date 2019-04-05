package com.rabbitforever.datamanipulation.factories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;

public class TableTestGlobalVariablesFactory {
	private final static Logger logger = LoggerFactory.getLogger(TableTestGlobalVariablesFactory.class);
	private final static String className = TableTestGlobalVariablesFactory.class.getName();
	private static int count = 0;
	private static boolean isUsed = false;
	private static String scopeFileName = "";
	private static String scopeFolderName = "";
	private static CaptureDto captureDto = null;
	private static List<String> resultList = new ArrayList<String>();
	private static String currentResult = "";
	private static boolean isOk = true;
	private static boolean isCurrentOk = false;
	private static Date startDateTime = null;
	private static Date endDateTime = null;
	
	public static int getCount() {
		return count;
	}
	public static void setCount(int count) {
		TableTestGlobalVariablesFactory.count = count;
	}
	public static boolean getIsUsed() {
		return isUsed;
	}
	public static void setIsUsed(boolean isUsed) {
		TableTestGlobalVariablesFactory.isUsed = isUsed;
	}
	public static String getScopeFolderName() {
		return scopeFolderName;
	}
	public static void setScopeFolderName(String scopeFolderName) {
		TableTestGlobalVariablesFactory.scopeFolderName = scopeFolderName;
	}
	public static CaptureDto getCaptureDto() {
		return captureDto;
	}
	public static void setCaptureDto(CaptureDto captureDto) {
		TableTestGlobalVariablesFactory.captureDto = captureDto;
	}
	public static List<String> getResultList() {
		return resultList;
	}
	public static void setResultList(List<String> resultList) {
		TableTestGlobalVariablesFactory.resultList = resultList;
	}
	public static boolean getIsOk() {
		return isOk;
	}
	public static void setIsOk(boolean isOk) {
		TableTestGlobalVariablesFactory.isOk = isOk;
	}
	public static Logger getLogger() {
		return logger;
	}
	public static void addResult(String result){
		resultList.add(result);
	}
	
	
	public static boolean getIsCurrentOk() {
		return isCurrentOk;
	}
	public static void setIsCurrentOk(boolean isCurrentOk) {
		TableTestGlobalVariablesFactory.isCurrentOk = isCurrentOk;
	}
	public static String getCurrentResult() {
		return currentResult;
	}
	public static void setCurrentResult(String currentResult) {
		TableTestGlobalVariablesFactory.currentResult = currentResult;
	}
	
	public static Date getStartDateTime() {
		return startDateTime;
	}
	public static void setStartDateTime(Date startDateTime) {
		TableTestGlobalVariablesFactory.startDateTime = startDateTime;
	}
	public static Date getEndDateTime() {
		return endDateTime;
	}
	public static void setEndDateTime(Date endDateTime) {
		TableTestGlobalVariablesFactory.endDateTime = endDateTime;
	}
	
	
	public static String getScopeFileName() {
		return scopeFileName;
	}
	public static void setScopeFileName(String scopeFileName) {
		TableTestGlobalVariablesFactory.scopeFileName = scopeFileName;
	}
	public static void resetParams(){
		count = 0;
		isUsed = false;
		scopeFileName = "";
		scopeFolderName = "";
		captureDto = null;
		resultList = new ArrayList<String>();
		isOk = true;
		currentResult = "";
		isCurrentOk = false;
		startDateTime = null;
		endDateTime = null;
		
	}
	public static String getClassname() {
		return className;
	}

	
}
