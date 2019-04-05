package com.rabbitforever.datamanipulation.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;

public class FlatXmlDataUtils extends DataUtils {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public FlatXmlDataUtils(String dbType) throws Exception {
		super(dbType);

	}

	public FlatXmlDataUtils() throws Exception {
		super();
	}
	
	public void loadDataToDb(File xmlFile) throws Exception {
		IDatabaseConnection connection = null;
		FlatXmlDataSetBuilder builder = null;
		try {
			if (dbType.equals(DB_TYPE_DB2)) {
				connection = dbUtils.getDataBaseConnectionWithSchema();
			}
			if (dbType.equals(DB_TYPE_MYSQL)) {
				connection = dbUtils.getDataBaseConnectionNoSchema();
				// connection = dbUtils.getDataBaseConnectionWithSchema();
			}
			if (dbType.equals(DB_TYPE_MSSQL)) {
				connection = dbUtils.getDataBaseConnectionNoSchema();
				// connection = dbUtils.getDataBaseConnectionWithSchema();
			}
			builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(true);

			IDataSet dataSet = builder.build(xmlFile);

			DatabaseOperation.REFRESH.execute(connection, dataSet);
		} catch (Exception e) {
			logger.error(className + ".loadDataToDb() - xmlFile=" + xmlFile, e);
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
				connection = null;
			}
			if (builder != null) {
				builder = null;
			}
		}
	}
	
	public void writeFullData2Xml(String tableName, String scopeFolderPath, String outputXmlFileName) throws Exception {
		Connection connection = null;
		IDatabaseConnection dataConnection = null;
		try {
			fileUtils.createDirectoryIfNotExisted(DATASET_FOLDER);
			// full database export
			dataConnection = dbUtils.getDataBaseConnectionNoSchema();
			FlatXmlDataSet.write(dataConnection.createDataSet(),
					new FileOutputStream(scopeFolderPath + "/" + outputXmlFileName));
		} catch (Exception e) {
			logger.error(className + ".writeFullData2Xml() - tableName=" + tableName + ",scopeFolderPath=" + scopeFolderPath + ",outputXmlFileName=" + outputXmlFileName, e);
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
				connection = null;
			}
			if (dataConnection != null) {
				dataConnection.close();
				dataConnection = null;
			}
		}
	}
	
	@Override
	public void writeData2Xml(CaptureDto captureDto, String scopeFolderPath) throws Exception {
		try{
			String outputXmlFileName = captureDto.getOutputXmlFileName();
			String tableName = captureDto.getTableName();
			String sql = captureDto.getCaptureSql();
			writeData2Xml(outputXmlFileName, tableName, sql, scopeFolderPath);
		} catch (Exception e){
			logger.error(className + ".writeData2Xml() - captureDto=" + captureDto + ",scopeFolderPath=" + scopeFolderPath, e);
			throw e;
		}
	}
	
	public void writeData2Xml(String outputXmlFileName, String tableName,String sql, String scopeFolderPath) throws Exception {

		
		IDatabaseConnection connection = null;
		QueryDataSet partialDataSet = null;
		OutputStreamWriter outputStreamWriter = null;
		FileOutputStream fileOutputStream = null;
		BufferedWriter bfWriter = null;
		FlatXmlWriter flatXmlWriter = null;
//		XmlDataSetWriter xmlDataSetWriter = null;
		StringBuilder sbLog = null;
		try {
			sbLog = new StringBuilder();
			sbLog.append("outputXmlFileName=" + outputXmlFileName);
			sbLog.append(",");
			sbLog.append("tableName=" + tableName);
			sbLog.append(",");
			sbLog.append("sql=" + sql);
			sbLog.append(",");
			sbLog.append("scopeFolderPath=" + scopeFolderPath);
			
			if (connection == null) {
				connection = dbUtils.getDataBaseConnectionNoSchema();
			}
			// partial database export

			partialDataSet = new QueryDataSet(connection);
			partialDataSet.addTable(tableName, sql);

			fileOutputStream = new FileOutputStream(scopeFolderPath + "/" + outputXmlFileName);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			bfWriter = new BufferedWriter(outputStreamWriter);
			flatXmlWriter = new FlatXmlWriter(bfWriter);
			flatXmlWriter.setPrettyPrint(true);

//			xmlDataSetWriter = new XmlDataSetWriter(bfWriter);
//			xmlDataSetWriter.setPrettyPrint(true);
			flatXmlWriter.write(partialDataSet);
//			xmlDataSetWriter.write(partialDataSet);
		} catch (Exception e) {
			logger.error(className + ".writeData2Xml() - " + sbLog.toString() + ",scopeFolderPath=" + scopeFolderPath, e);
			throw e;
		}  finally {
			if (connection != null) {
				connection.close();
				connection = null;
			}
			if (fileOutputStream != null) {
				fileOutputStream.close();
				fileOutputStream = null;
			}
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
				outputStreamWriter = null;
			}

			if (partialDataSet != null) {
				partialDataSet = null;
			}
			if (flatXmlWriter != null) {
				flatXmlWriter = null;
			}
//			if (xmlDataSetWriter != null){
//				xmlDataSetWriter = null;
//			}
			if (sbLog != null){
				sbLog = null;
			}
		}
	}
	public IDataSet getDataSet(String xmlFileName) throws Exception {
		File xmlFile = null;
		IDataSet dataSet = null;
		FlatXmlDataSetBuilder builder = null;

		
		try {
			xmlFile = new File(DATASET_FOLDER + "/" + xmlFileName);
			builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(true);
			dataSet = builder.build(xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (xmlFile != null) {
				xmlFile = null;
			}
			if (builder != null) {
				builder = null;
			}
		}
		return dataSet;
	}
}
