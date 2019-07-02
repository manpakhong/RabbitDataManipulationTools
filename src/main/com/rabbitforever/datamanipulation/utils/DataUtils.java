package com.rabbitforever.datamanipulation.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.CompositeTable;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.FilteredTableMetaData;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.filter.DefaultTableFilter;
import org.dbunit.dataset.filter.IColumnFilter;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.DbUtilsFactory;
import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.DbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.criteria.DataSetQueryCriteria;
import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.models.dtos.DeleteDto;

public abstract class DataUtils {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public static final String DB_TYPE_MYSQL = "mysql";
	public static final String DB_TYPE_DB2 = "db2";
	public static final String DB_TYPE_MSSQL = "mssql";
	public static final String DB_TYPE_ORACLE = "oracle";
	protected String DATASET_FOLDER;
	protected SysProperties sysProperties;
	protected DbProperties dbProperties;
	protected FileUtils fileUtils;
	protected DbUtils dbUtils;
	protected String dbType;
	protected String databaseType;

	
	public DataUtils(String dbType) throws Exception {
		this.dbType = dbType;
		init();
	}

	public DataUtils() throws Exception {
		this.dbType = DB_TYPE_DB2;
		init();
	}

	private void init() throws Exception {
		fileUtils = new FileUtils();
		sysProperties = new SysProperties();
		DATASET_FOLDER = sysProperties.getTestFolderRoot();
		databaseType = sysProperties.getDatabaseType();
		if (databaseType.equals(SysProperties.DATABASE_TYPE_DB2)) {
			dbUtils = DbUtilsFactory.getInstanceOfDb2DbUtils();
			dbProperties = PropertiesFactory.getInstanceOfDb2DbProperties();
		} else if (databaseType.equals(SysProperties.DATABASE_TYPE_MYSQL)) {
			dbUtils = DbUtilsFactory.getInstanceOfMySqlDbUtils();
			dbProperties = PropertiesFactory.getInstanceOfMySqlDbProperties();
		} else if (databaseType.equals(SysProperties.DATABASE_TYPE_MSSQL)) {
			dbUtils = DbUtilsFactory.getInstanceOfMsSqlDbUtils();
			dbProperties = PropertiesFactory.getInstanceOfMsSqlDbProperties();
		}else if (databaseType.equals(SysProperties.DATABASE_TYPE_ORACLE)) {
			dbUtils = DbUtilsFactory.getInstanceOfOracleDbUtils();
			dbProperties = PropertiesFactory.getInstanceOfOracleDbProperties();
		}

	}

	public Map<ITableFilter, IColumnFilter> createFilterMap(Map<String, String[]> ignoreMap) {
		HashMap<ITableFilter, IColumnFilter> filterMap = new HashMap<ITableFilter, IColumnFilter>();

		for (Map.Entry<String, String[]> e : ignoreMap.entrySet()) {
			DefaultTableFilter tables = new DefaultTableFilter();
			DefaultColumnFilter columnFilter = new DefaultColumnFilter();

			tables.includeTable(e.getKey());

			for (String columnPattern : e.getValue()) {
				columnFilter.excludeColumn(columnPattern);
			}

			filterMap.put(tables, columnFilter);
		}

		return filterMap;
	}

	public IDataSet removeIgnoredColumns(IDataSet dataSet, Map<ITableFilter, IColumnFilter> columnFilterMap)
			throws DataSetException {
		DefaultDataSet filteredDataSet = new DefaultDataSet();


		ITableIterator iterator = dataSet.iterator();
		while (iterator.next()) {
			ITable table = iterator.getTable();

			for (Map.Entry<ITableFilter, IColumnFilter> e : columnFilterMap.entrySet()) {
				if (e.getKey().accept(table.getTableMetaData().getTableName())) {
					table = filterTable(table, e.getValue());
				}
			}

			filteredDataSet.addTable(table);
		}

		return filteredDataSet;
	}

	private ITable filterTable(ITable table, IColumnFilter filter) throws DataSetException {
		FilteredTableMetaData metaData = new FilteredTableMetaData(table.getTableMetaData(), filter);

		return new CompositeTable(metaData, table);
	}

	public abstract void writeData2Xml(CaptureDto captureDto, String scopeFolderPath) throws Exception;
	
	public abstract void writeData2Xml(String outputXmlFileName, String tableName,String sql, String scopeFolderPath) throws Exception;

	// public void writeData2Xml(CaptureDto captureDto) throws Exception {
	// String outputXmlFileName = captureDto.getOutputXmlFileName();
	// QueryDataSet partialDataSet = null;
	// try {
	// fileUtils.createDirectoryIfNotExisted(DATASET_FOLDER);
	// partialDataSet = getDataSet(captureDto);
	// FlatXmlDataSet.write(partialDataSet, new FileOutputStream(DATASET_FOLDER
	// + "/" + outputXmlFileName));
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw e;
	// } finally {
	// if (partialDataSet != null) {
	// partialDataSet = null;
	// }
	// }
	// }
	
	public void writeBackupData2Xml(CaptureScopeDto captureScopeDto) throws Exception {
		String scopeFolderName = captureScopeDto.getScopeFolderName();
		List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();
		String backupFolderName = sysProperties.getDeleteThenRestoreBackupFolderName();
		String schema = dbProperties.getSchema();
		try {
			fileUtils.createDirectoryIfNotExisted(DATASET_FOLDER);

			String scopeFolderPath = DATASET_FOLDER;
			String backupFolderFullPath = scopeFolderPath + "/" + scopeFolderName + "/" + backupFolderName;
			String timestamp = CommonUtils.genTimestampString();
			String backupFolderTsFullPath = backupFolderFullPath + "/" + timestamp;
			
			if (scopeFolderName != null && !scopeFolderName.isEmpty()) {
				scopeFolderPath += "/" + scopeFolderName;
				fileUtils.createDirectoryIfNotExisted(scopeFolderPath);
				if (backupFolderName != null && !backupFolderName.isEmpty()){
					fileUtils.createDirectoryIfNotExisted(backupFolderFullPath);
				}
				if (backupFolderTsFullPath != null && !backupFolderTsFullPath.isEmpty()){
					fileUtils.createDirectoryIfNotExisted(backupFolderTsFullPath);
				}
			}
			for (CaptureDto captureDto : captureDtoList) {
				DeleteDto deleteDto = captureDto.getDeleteDto();
				if (deleteDto != null && deleteDto.getIsValid() != null && deleteDto.getIsValid()){
					String outputXmlFileName = captureDto.getOutputXmlFileName();
					String tableName = captureDto.getTableName();
					String backupSql = captureDto.getBackupSql();
	
					writeData2Xml(outputXmlFileName, tableName, backupSql, backupFolderTsFullPath);
				}
			}
		} catch (Exception e) {
			logger.error(className + ".writeData2Xml() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		}
	}
	
	
	public void writeData2Xml(CaptureScopeDto captureScopeDto) throws Exception {
		String scopeFolderName = captureScopeDto.getScopeFolderName();
		List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();
		try {
			fileUtils.createDirectoryIfNotExisted(DATASET_FOLDER);

			String scopeFolderPath = DATASET_FOLDER;
			if (scopeFolderName != null && !scopeFolderName.isEmpty()) {
				scopeFolderPath += "/" + scopeFolderName;
				fileUtils.createDirectoryIfNotExisted(scopeFolderPath);
			}

			for (CaptureDto captureDto : captureDtoList) {
				writeData2Xml(captureDto, scopeFolderPath);
			}
		} catch (Exception e) {
			logger.error(className + ".writeData2Xml() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		}
	}

	public List<String> getAllDependentTableNameList(String rootTable) throws Exception {
		List<String> allDependentTableNameList = null;
		try {
			IDatabaseConnection connection = dbUtils.getDataBaseConnectionWithSchema();
			String[] depTableNames = TablesDependencyHelper.getAllDependentTables(connection, rootTable);
			if (depTableNames != null) {
				allDependentTableNameList = new ArrayList<String>();
				for (String depTableName : depTableNames) {
					allDependentTableNameList.add(depTableName);
				}
			}
		} catch (Exception e) {
			logger.error(className + ".getAllDependentTableNameList() - rootTable=" + rootTable, e);
			throw e;
		}
		return allDependentTableNameList;
	}

	public QueryDataSet getDataSetForAssertion(DataSetQueryCriteria dataSetQueryCriteria) throws Exception {
		QueryDataSet queryDataSet = null;
		try {
			dataSetQueryCriteria.setGoingToConnectionAfterQuery(false);
			queryDataSet = getDataSet(dataSetQueryCriteria);
		} catch (Exception e) {
			logger.error(className + ".getDataSetForAssertion() - dataSetQueryCriteria=" + dataSetQueryCriteria, e);
		}
		return queryDataSet;
	}

	private QueryDataSet getDataSet(DataSetQueryCriteria dataSetQueryCriteria) throws Exception {
		QueryDataSet partialDataSet = null;
		IDatabaseConnection connection = null;
		try {
			String tableName = dataSetQueryCriteria.getTableName();
			String sql = dataSetQueryCriteria.getCaptureSql();

			connection = dbUtils.getDataBaseConnectionNoSchema();
			// partial database export
			partialDataSet = new QueryDataSet(connection);
			partialDataSet.addTable(tableName, sql);
		} catch (Exception e) {
			logger.error(className + ".getDataSet() - dataSetQueryCriteria=" + dataSetQueryCriteria, e);
			throw e;
		} finally {
			if (dataSetQueryCriteria.isGoingToConnectionAfterQuery()) {
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} else {
				// to be closed after get ITable by calling function
				dataSetQueryCriteria.setConnection(connection);
			}
		}
		return partialDataSet;
	}

	public abstract void writeFullData2Xml(String tableName, String scopeFolderPath, String outputXmlFileName)
			throws Exception;

	public void removeDataFromDb(String xmlFileName) throws Exception {
		File file = null;
		try {
			file = new File(DATASET_FOLDER + "/" + xmlFileName);
			removeDataFromDb(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (file != null) {
				file = null;
			}
		}
	}

	public void removeDataFromDb(File xmlFile) throws Exception {
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

			DatabaseOperation.DELETE.execute(connection, dataSet);
		} catch (Exception e) {
			logger.error(className + ".removeDataFromDb() - xmlFile=" + xmlFile, e);
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

	public void loadDataToDb(String xmlFileName) throws Exception {
		File file = null;
		try {
			file = new File(DATASET_FOLDER + "/" + xmlFileName);
			loadDataToDb(file);
		} catch (Exception e) {
			logger.error(className + ".loadDataToDb() - xmlFileName=" + xmlFileName, e);
			throw e;
		} finally {
			if (file != null) {
				file = null;
			}
		}
	}

	public abstract void loadDataToDb(File xmlFile) throws Exception;

	public abstract IDataSet getDataSet(String xmlFileName) throws Exception;

}
