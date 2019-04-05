package com.rabbitforever.datamanipulation.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dbunit.Assertion;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.IColumnFilter;
import org.dbunit.dataset.filter.ITableFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.models.criteria.AssertionCriteria;
import com.rabbitforever.datamanipulation.models.criteria.DataSetQueryCriteria;
import com.rabbitforever.datamanipulation.models.criteria.TableQueryCriteria;
import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.services.SnapshotMgr;
import com.rabbitforever.datamanipulation.services.ThreadJobMgr;
import com.rabbitforever.datamanipulation.utils.CommonUtils;
import com.rabbitforever.datamanipulation.views.validators.AssertionCriteriaValidator;

public class CaseTestHelper {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private SnapshotMgr snapshotMgr;
	private ThreadJobMgr threadJobMgr;

	public CaseTestHelper() throws Exception {
		snapshotMgr = new SnapshotMgr();
		threadJobMgr = new ThreadJobMgr();
	}

	private IDataSet addIgnoreColumns(IDataSet dataSet, CaptureDto captureDto) throws Exception {
		IDataSet filteredDataSet = null;
		try {
			String tableName = captureDto.getTableName();
			String ignoreColumnsString = captureDto.getActualAssertionTargetIgnoreColumnsList();
			String[] ignoreColumnsStringArray = ignoreColumnsString
					.split(CaptureDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_DELIMITED);
			CommonUtils.trimStringArrayElement(ignoreColumnsStringArray);
			if (captureDto.getActualAssertionTargetIgnoreColumnsList() != null
					&& !captureDto.getActualAssertionTargetIgnoreColumnsList()
							.equals(CaptureDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_BLANK)) {

				Map<String, String[]> ignoreMap = new HashMap<String, String[]>();
				ignoreMap.put(tableName, ignoreColumnsStringArray);
				Map<ITableFilter, IColumnFilter> columnFilterMap = snapshotMgr.createFilterMap(ignoreMap);
				dataSet = snapshotMgr.removeIgnoredColumns(dataSet, columnFilterMap);
			}
			filteredDataSet = dataSet;
		} catch (Exception e) {
			logger.error(className + ".addIgnoreColumns() - dataSet=" + dataSet + ",captureDto=" + captureDto, e);
			throw e;
		}
		return filteredDataSet;
	}

	private IDataSet addIgnoreColumns(IDataSet dataSet, CaptureScopeDto captureScopeDto) throws Exception {
		IDataSet filteredDataSet = null;
		try {
			List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();
			for (CaptureDto captureDto : captureDtoList) {
				String tableName = captureDto.getTableName();
				String ignoreColumnsString = captureDto.getActualAssertionTargetIgnoreColumnsList();
				String[] ignoreColumnsStringArray = ignoreColumnsString
						.split(CaptureDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_DELIMITED);
				CommonUtils.trimStringArrayElement(ignoreColumnsStringArray);
				if (captureDto.getActualAssertionTargetIgnoreColumnsList() != null
						&& !captureDto.getActualAssertionTargetIgnoreColumnsList()
								.equals(CaptureDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_BLANK)) {

					Map<String, String[]> ignoreMap = new HashMap<String, String[]>();
					ignoreMap.put(tableName, ignoreColumnsStringArray);
					Map<ITableFilter, IColumnFilter> columnFilterMap = snapshotMgr.createFilterMap(ignoreMap);
					dataSet = snapshotMgr.removeIgnoredColumns(dataSet, columnFilterMap);
				}
			}

			filteredDataSet = dataSet;
		} catch (Exception e) {
			logger.error(className + ".addIgnoreColumns() - dataSet=" + dataSet + ",captureScopeDto=" + captureScopeDto,
					e);
			throw e;
		}
		return filteredDataSet;
	}

	public void assertActualTableDataSetWithXmlDataSet(String scopeFolderName, CaptureDto captureDto) throws Exception {
		IDataSet expectedDataSet = null;
		IDataSet actualDataSet = null;
		try {
			actualDataSet = snapshotMgr.getDataSetFromActualTable(captureDto);
			expectedDataSet = snapshotMgr.getDataSet(scopeFolderName, captureDto);

			Map<String, String[]> ignoreMap = new HashMap<String, String[]>();

			// String tableName = "TCT_RES_ADDR";
			// String columnName = "LAST_UPD_TS";
			// String [] columnArray = new String[1];
			// columnArray[0] = columnName;
			// ignoreMap.put(tableName, columnArray);
			//
			// Map<ITableFilter, IColumnFilter> columnFilterMap =
			// snapshotMgr.createFilterMap(ignoreMap);

			IDataSet actualFilteredDataSet = addIgnoreColumns(actualDataSet, captureDto);
			IDataSet expectedFilteredDataSet = addIgnoreColumns(expectedDataSet, captureDto);

			Assertion.assertEquals(expectedFilteredDataSet, actualFilteredDataSet);

		} catch (Exception e) {
			logger.error(className + ".addIgnoreColumns() - captureScopeDto=" + captureDto, e);
			throw e;
		} finally {
			if (expectedDataSet != null) {
				expectedDataSet = null;
			}
			if (actualDataSet != null) {
				actualDataSet = null;
			}
		}
	}

	public void assertActualTableDataSetWithXmlDataSet(CaptureScopeDto captureScopeDto) throws Exception {
		IDataSet expectedDataSet = null;
		IDataSet actualDataSet = null;
		try {
			actualDataSet = snapshotMgr.getDataSetFromActualTableAsOnce(captureScopeDto);
			expectedDataSet = snapshotMgr.getDataSetFromXmlAsOnce(captureScopeDto);

			Map<String, String[]> ignoreMap = new HashMap<String, String[]>();

			// String tableName = "TCT_RES_ADDR";
			// String columnName = "LAST_UPD_TS";
			// String [] columnArray = new String[1];
			// columnArray[0] = columnName;
			// ignoreMap.put(tableName, columnArray);
			//
			// Map<ITableFilter, IColumnFilter> columnFilterMap =
			// snapshotMgr.createFilterMap(ignoreMap);

			IDataSet actualFilteredDataSet = addIgnoreColumns(actualDataSet, captureScopeDto);
			IDataSet expectedFilteredDataSet = addIgnoreColumns(expectedDataSet, captureScopeDto);

			Assertion.assertEquals(expectedFilteredDataSet,actualFilteredDataSet);

		} catch (Exception e) {
			logger.error(className + ".addIgnoreColumns() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		} finally {
			if (expectedDataSet != null) {
				expectedDataSet = null;
			}
			if (actualDataSet != null) {
				actualDataSet = null;
			}
		}
	}

	public void assertExpectedXmlResultWithActualTableByDataSet(AssertionCriteria assertionCriteria) throws Exception {
		IDataSet expectedDataSet = null;
		ITable expectedTable = null;
		ITable actualTable = null;
		IDataSet actualDataSet = null;
		try {
			if (!AssertionCriteriaValidator.validate(assertionCriteria)) {
				throw new Exception("assertionCriteria not validated! assertionCriteria=" + assertionCriteria);
			}

			String tableName = assertionCriteria.getTableName();
			String actualDataSql = assertionCriteria.getActualTableSql();

			CaptureScopeDto captureScopeDto = snapshotMgr
					.getCaptureScopeDtoFromScopeFileName("csaChgDhpExpectedScope.scope");

			String xmlFileName = captureScopeDto.getXmlFileNameByTableName(tableName);
			xmlFileName = captureScopeDto.getScopeFolderName() + "/" + xmlFileName;
			expectedDataSet = snapshotMgr.getDataSet(xmlFileName);
			// expectedTable = expectedDataSet.getTable(tableName);

			String actualDataTableName = tableName;
			// Column[] columnIncludingArray =
			// expectedDataSet.getTableMetaData(tableName).getColumns();
			DataSetQueryCriteria dataSetQueryCriteria = new DataSetQueryCriteria();
			dataSetQueryCriteria.setTableName(actualDataTableName);
			dataSetQueryCriteria.setCaptureSql(actualDataSql);
			// tableQueryCriteria.setColumnIncludingArray(columnIncludingArray);
			actualDataSet = snapshotMgr.getDataSet(dataSetQueryCriteria);

			Assertion.assertEquals(expectedDataSet, actualDataSet);

		} catch (Exception e) {
			logger.error(className + ".assertExpectedXmlResultWithActualTableByDataSet() - assertionCriteria="
					+ assertionCriteria, e);
			throw e;
		} finally {
			if (expectedDataSet != null) {
				expectedDataSet = null;
			}
			if (expectedTable != null) {
				expectedTable = null;
			}
			if (actualTable != null) {
				actualTable = null;
			}
		}
	}

	public void assertExpectedXmlResultWithActualTableByTable(AssertionCriteria assertionCriteria) throws Exception {
		IDataSet expectedDataSet = null;
		ITable expectedTable = null;
		ITable actualTable = null;
		try {
			if (!AssertionCriteriaValidator.validate(assertionCriteria)) {
				throw new Exception("assertionCriteria not validated! assertionCriteria=" + assertionCriteria);
			}

			String tableName = assertionCriteria.getTableName();
			String actualDataSql = assertionCriteria.getActualTableSql();

			CaptureScopeDto captureScopeDto = snapshotMgr
					.getCaptureScopeDtoFromScopeFileName("csaChgDhpExpectedScope.scope");

			String xmlFileName = captureScopeDto.getXmlFileNameByTableName(tableName);
			xmlFileName = captureScopeDto.getScopeFolderName() + "/" + xmlFileName;
			expectedDataSet = snapshotMgr.getDataSet(xmlFileName);
			expectedTable = expectedDataSet.getTable(tableName);

			String actualDataTableName = tableName;
			Column[] columnIncludingArray = expectedDataSet.getTableMetaData(tableName).getColumns();
			TableQueryCriteria tableQueryCriteria = new TableQueryCriteria();
			tableQueryCriteria.setTableName(actualDataTableName);
			tableQueryCriteria.setCaptureSql(actualDataSql);
			tableQueryCriteria.setColumnIncludingArray(columnIncludingArray);
			actualTable = snapshotMgr.getTable(tableQueryCriteria);

			Assertion.assertEquals(expectedTable, actualTable);

		} catch (Exception e) {
			logger.error(className + ".assertExpectedXmlResultWithActualTableByTable() - assertionCriteria="
					+ assertionCriteria, e);
			throw e;
		} finally {
			if (expectedDataSet != null) {
				expectedDataSet = null;
			}
			if (expectedTable != null) {
				expectedTable = null;
			}
			if (actualTable != null) {
				actualTable = null;
			}
		}
	}

}
