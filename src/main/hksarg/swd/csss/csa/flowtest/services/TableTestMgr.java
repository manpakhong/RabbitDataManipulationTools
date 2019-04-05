package hksarg.swd.csss.csa.flowtest.services;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.factories.ScopeTestGlobalVariablesFactory;
import hksarg.swd.csss.csa.flowtest.factories.TableTestGlobalVariablesFactory;
import hksarg.swd.csss.csa.flowtest.helpers.CaseTestHelper;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureDto;
import junit.framework.TestCase;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableTestMgr extends TestCase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private CaptureDto captureDto;
	private CaseTestHelper helper;
	private SnapshotMgr snapshotMgr;
	private String scopeFolderName;

	
	@After
	public void tearDown() throws Exception {
		ScopeTestGlobalVariablesFactory.setIsUsed(false);
	}
	
	@Before
	public void setUp() throws Exception {
		try {
			TableTestGlobalVariablesFactory.setIsUsed(true);
			TableTestGlobalVariablesFactory.setIsCurrentOk(false);
			TableTestGlobalVariablesFactory.setCurrentResult("");
			snapshotMgr = new SnapshotMgr();
			helper = new CaseTestHelper();
			captureDto = TableTestGlobalVariablesFactory.getCaptureDto();
			scopeFolderName = TableTestGlobalVariablesFactory.getScopeFolderName();
		} catch (Exception e) {
			logger.error(className + ".setup() - ", e);
			throw e;
		}
	}
	
	@Test
	public void testTable() throws Exception{
		IDataSet expectedDataSet = null;
		ITable expectedTable = null;
		ITable actualTable = null;
		try {
			helper.assertActualTableDataSetWithXmlDataSet(scopeFolderName, captureDto);
			TableTestGlobalVariablesFactory.setIsCurrentOk(true);
		}catch (AssertionError ae){
			TableTestGlobalVariablesFactory.setIsOk(false);
			TableTestGlobalVariablesFactory.setIsCurrentOk(false);
			TableTestGlobalVariablesFactory.addResult(ae.getMessage());
			TableTestGlobalVariablesFactory.setCurrentResult(ae.getMessage());
			if (logger.isDebugEnabled()){
				logger.info(className + ".testTable() - GlobalVariablesFactory.result=" + TableTestGlobalVariablesFactory.getResultList()); 
			}
//			ae.printStackTrace();
		}
		catch (Exception e) {
			logger.error(className + ".testTable() - captureScopeDto=" + captureDto,e);
			TableTestGlobalVariablesFactory.setIsOk(false);
			TableTestGlobalVariablesFactory.setIsCurrentOk(false);
			TableTestGlobalVariablesFactory.addResult(e.getMessage());
			TableTestGlobalVariablesFactory.setCurrentResult(e.getMessage());
//			throw e;
		} finally {
			if (expectedDataSet != null){
				expectedDataSet = null;
			}
			if (expectedTable != null){
				expectedTable = null;
			}
			if (actualTable != null){
				actualTable = null;
			}
			TableTestGlobalVariablesFactory.setIsUsed(false);
		}

	}
}
