package com.rabbitforever.datamanipulation.models.dtos;

import java.util.ArrayList;
import java.util.List;

public class CaptureScopeDto {
	public final static String SCOPE_FILE_NAME_CONTENT_DELIMITED = ";";
	public final static int FILE_LINE_XML_FILES_LIST = 0;
	public final static int FILE_LINE_TABLE_LIST = 1;
	public final static int FILE_LINE_SCOPE_FOLDER = 2;
	public final static int FILE_LINE_SCOPE_FILE_NAME = 3;
	public final static int FILE_LINE_SQL_BEGINNING_LINE = 4;
	public final static String ACTUAL_ASSERTION_TARGET_SQL_SEPARATOR = "-----------------------------------------------------------------------------------------------";
	public final static String ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_SEPARATOR = "...................................................................................";
	protected List<CaptureDto> captureDtoList;
	protected String scopeFileName;
	protected String scopeFolderName;

	public CaptureScopeDto(){
		captureDtoList = new ArrayList<CaptureDto>();
	}
	
	public String getScopeFolderName() {
		return scopeFolderName;
	}

	public String getXmlFileNameByTableName(String tableName){
		String xmlFileName = null;
		if (captureDtoList != null && !captureDtoList.isEmpty()){
			for (CaptureDto captureDto: captureDtoList){
				String tableNameLoop = captureDto.getTableName();
				if (tableNameLoop != null && tableNameLoop.equals(tableName)){
					xmlFileName = captureDto.getOutputXmlFileName();
				}
			}
		}
		return xmlFileName;
	}
	
	
	public List<String> getOutputXmlFileNameList(){
		List<String> outputXmlFileNameList = null;
		if (captureDtoList != null && !captureDtoList.isEmpty()){
			outputXmlFileNameList = new ArrayList<String>();
			for (CaptureDto captureDto: captureDtoList){
				outputXmlFileNameList.add(captureDto.getOutputXmlFileName());
			}
		}
		return outputXmlFileNameList;
	}
	
	public void setScopeFolderName(String scopeFolderName) {
		this.scopeFolderName = scopeFolderName;
	}

	public String getScopeFileName() {
		return scopeFileName;
	}
	public void setScopeFileName(String scopeFileName) {
		this.scopeFileName = scopeFileName;
	}
	public List<CaptureDto> getCaptureDtoList() {
		return captureDtoList;
	}
	
	public void addCaptureDto(CaptureDto captureDto){
		if (captureDto != null){
			captureDtoList.add(captureDto);
		}
	}
	
}
