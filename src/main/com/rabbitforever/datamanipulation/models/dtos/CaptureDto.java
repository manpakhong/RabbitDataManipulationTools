package com.rabbitforever.datamanipulation.models.dtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptureDto{
	public final static String ACTUAL_ASSERTION_TARGET_SQL_BLANK = "-BLANK-";
	public final static String ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_BLANK = "-BLANK-";
	public final static String ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_DELIMITED = ",";
	protected String tableName;
	protected String captureSql;
	protected String actualAssertionTargetSql;
	protected String actualAssertionTargetIgnoreColumnsList;
	protected String backupSql;
	protected String outputXmlFileName;
	private DeleteDto deleteDto;
	
	public DeleteDto getDeleteDto() {
		return deleteDto;
	}
	public void setDeleteDto(DeleteDto deleteDto) {
		this.deleteDto = deleteDto;
	}
	
	public String getOutputXmlFileName() {
		return outputXmlFileName;
	}
	public void setOutputXmlFileName(String outputXmlFileName) {
		this.outputXmlFileName = outputXmlFileName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCaptureSql() {
		return captureSql;
	}
	public void setCaptureSql(String captureSql) {
		this.captureSql = captureSql;
	}
	public String getActualAssertionTargetSql() {
		return actualAssertionTargetSql;
	}
	public void setActualAssertionTargetSql(String actualAssertionTargetSql) {
		this.actualAssertionTargetSql = actualAssertionTargetSql;
	}
	
	
	public String getActualAssertionTargetIgnoreColumnsList() {
		return actualAssertionTargetIgnoreColumnsList;
	}
	public void setActualAssertionTargetIgnoreColumnsList(String actualAssertionTargetIgnoreColumnsList) {
		this.actualAssertionTargetIgnoreColumnsList = actualAssertionTargetIgnoreColumnsList;
	}
	
	
	public String getBackupSql() {
		return backupSql;
	}
	public void setBackupSql(String backupSql) {
		this.backupSql = backupSql;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CaptureDto [tableName=");
		builder.append(tableName);
		builder.append(", captureSql=");
		builder.append(captureSql);
		builder.append(", actualAssertionTargetSql=");
		builder.append(actualAssertionTargetSql);
		builder.append(", actualAssertionTargetIgnoreColumnsList=");
		builder.append(actualAssertionTargetIgnoreColumnsList);
		builder.append(", backupSql=");
		builder.append(backupSql);
		builder.append(", outputXmlFileName=");
		builder.append(outputXmlFileName);
		builder.append(", deleteDto=");
		builder.append(deleteDto);
		builder.append("]");
		return builder.toString();
	}
}
