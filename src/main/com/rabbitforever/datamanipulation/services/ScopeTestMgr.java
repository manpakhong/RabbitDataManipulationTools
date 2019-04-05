package com.rabbitforever.datamanipulation.services;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.ScopeTestGlobalVariablesFactory;
import com.rabbitforever.datamanipulation.helpers.CaseTestHelper;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;

import junit.framework.TestCase;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScopeTestMgr extends TestCase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private CaptureScopeDto captureScopeDto;
	private CaseTestHelper helper;
	private SnapshotMgr snapshotMgr;


	
	@After
	public void tearDown() throws Exception {
		ScopeTestGlobalVariablesFactory.setIsUsed(false);
	}
	
	@Before
	public void setUp() throws Exception {
		try {
			ScopeTestGlobalVariablesFactory.setIsUsed(true);
			ScopeTestGlobalVariablesFactory.setIsOk(false);
			ScopeTestGlobalVariablesFactory.setResult("");
			snapshotMgr = new SnapshotMgr();
			helper = new CaseTestHelper();
			captureScopeDto = snapshotMgr.getCaptureScopeDtoFromScopeFileName(ScopeTestGlobalVariablesFactory.getScopeName());

		} catch (Exception e) {
			logger.error(className + ".setup() - ", e);
			throw e;
		}
	}
	
	@Test
	public void testAllRelatedTables() throws Exception{
		IDataSet expectedDataSet = null;
		ITable expectedTable = null;
		ITable actualTable = null;
		try {			
			helper.assertActualTableDataSetWithXmlDataSet(captureScopeDto);
			ScopeTestGlobalVariablesFactory.setIsOk(true);
		}catch (AssertionError ae){

			ScopeTestGlobalVariablesFactory.setIsOk(false);
			ScopeTestGlobalVariablesFactory.setResult(ae.getMessage());
			
			if (logger.isDebugEnabled()){
				logger.info(className + ".testAllRelatedTables() - GlobalVariablesFactory.result=" + ScopeTestGlobalVariablesFactory.getResult()); 
			}
//			ae.printStackTrace();
		}
		catch (Exception e) {
			logger.error(className + ".testAllRelatedTables() - captureScopeDto=" + captureScopeDto,e);
			ScopeTestGlobalVariablesFactory.setIsOk(false);
			ScopeTestGlobalVariablesFactory.setResult(e.getMessage());
			throw e;
		} finally {
			ScopeTestGlobalVariablesFactory.setIsUsed(false);
			if (expectedDataSet != null){
				expectedDataSet = null;
			}
			if (expectedTable != null){
				expectedTable = null;
			}
			if (actualTable != null){
				actualTable = null;
			}
		}

	}
}
