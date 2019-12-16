package com.rabbitforever.datamanipulation.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.stream.IDataSetProducer;
import org.dbunit.dataset.stream.StreamingDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.dataset.xml.XmlDataSetWriter;
import org.dbunit.dataset.xml.XmlProducer;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;

public class DtdXmlDataUtils extends DataUtils {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public DtdXmlDataUtils(String dbType) throws Exception {
		super(dbType);

	}

	public DtdXmlDataUtils() throws Exception {
		super();
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
		XmlDataSetWriter xmlDataSetWriter = null;
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
			
			xmlDataSetWriter = new XmlDataSetWriter(bfWriter);
			xmlDataSetWriter.setPrettyPrint(true);
			xmlDataSetWriter.write(partialDataSet);
			
			

			
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
			if (xmlDataSetWriter != null){
				xmlDataSetWriter = null;
			}
			if (sbLog != null){
				sbLog = null;
			}
		}

	}

	@Override
	public void writeFullData2Xml(String tableName, String scopeFolderPath, String outputXmlFileName) throws Exception {
		Connection connection = null;
		IDatabaseConnection dataConnection = null;
		XmlDataSetWriter xmlDataSetWriter = null;
		OutputStreamWriter outputStreamWriter = null;
		FileOutputStream fileOutputStream = null;
		BufferedWriter bfWriter = null;
		IDataSet fullDataSet = null;
		try {
			fileUtils.createDirectoryIfNotExisted(DATASET_FOLDER);
			// full database export
			dataConnection = dbUtils.getDataBaseConnectionNoSchema();
			
			fileOutputStream = new FileOutputStream(scopeFolderPath + "/" + outputXmlFileName);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			bfWriter = new BufferedWriter(outputStreamWriter);
			
			fullDataSet = dataConnection.createDataSet();
			
			xmlDataSetWriter = new XmlDataSetWriter(bfWriter);
			xmlDataSetWriter.setPrettyPrint(true);
			xmlDataSetWriter.write(fullDataSet);
			
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
			if (fileOutputStream != null) {
				fileOutputStream.close();
				fileOutputStream = null;
			}
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
				outputStreamWriter = null;
			}
			if (fullDataSet != null) {
				fullDataSet = null;
			}
			if (xmlDataSetWriter != null){
				xmlDataSetWriter = null;
			}
		}

	}

	@Override
	public void loadDataToDb(File xmlFile) throws Exception {
		IDatabaseConnection connection = null;
		IDataSetProducer producer = null;
		InputSource inputSource = null;
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
			if (dbType.equals(DB_TYPE_ORACLE)) {
				connection = dbUtils.getDataBaseConnectionWithSchema();
				// connection = dbUtils.getDataBaseConnectionWithSchema();
			}

			inputSource = new InputSource(xmlFile.getPath());
			producer = new XmlProducer(inputSource);

			IDataSet dataSet = new StreamingDataSet(producer);

			if (!dbType.equals(DB_TYPE_MSSQL)){
				DatabaseOperation.REFRESH.execute(connection, dataSet);
			} else {
				InsertIdentityOperation insertIdentityOperation = new InsertIdentityOperation(DatabaseOperation.CLEAN_INSERT);
				insertIdentityOperation.execute(connection,dataSet);
			}
		} catch (Exception e) {
			logger.error(className + ".loadDataToDb() - xmlFile=" + xmlFile, e);
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
				connection = null;
			}
			if (inputSource != null){
				inputSource = null;
			}
			if (producer != null) {
				producer = null;
			}
		}
	}

//	@Override
//	public IDataSet getDataSet(String xmlFileName) throws Exception {
//		IDataSetProducer producer = null;
//		InputSource inputSource = null;
//		IDataSet dataSet = null;
//		
//		try {
//			inputSource = new InputSource(DATASET_FOLDER + "/" + xmlFileName);
//			producer = new XmlProducer(inputSource);
//			dataSet = new StreamingDataSet(producer);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if (producer != null) {
//				producer = null;
//			}
//			if (inputSource != null) {
//				inputSource = null;
//			}
//		}
//		return dataSet;
//	}
	
	@Override
	public IDataSet getDataSet(String xmlFileName) throws Exception {
		IDataSet dataSet = null;
		FileInputStream fileInputStream = null;
		XmlDataSet xmlDataSet = null;
		try {
			fileInputStream = new FileInputStream(DATASET_FOLDER + "/" + xmlFileName);
	
			xmlDataSet = new XmlDataSet(fileInputStream);
			dataSet = xmlDataSet;
		} catch (Exception e) {
			logger.error(className + ".getDataSet() - xmlFileName=" + xmlFileName, e);
			throw e;
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
				fileInputStream = null;
			}
			if (xmlDataSet != null) {
				xmlDataSet = null;
			}
		}
		return dataSet;
	}


}
