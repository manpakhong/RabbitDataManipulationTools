package hksarg.swd.csss.csa.flowtest.services;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.bundles.DbProperties;
import hksarg.swd.csss.csa.flowtest.daos.SyscolumnsDao;
import hksarg.swd.csss.csa.flowtest.factories.PropertiesFactory;
import hksarg.swd.csss.csa.flowtest.models.criteria.SystemColumnInfoCriteria;
import hksarg.swd.csss.csa.flowtest.models.dtos.DeleteDto;
import hksarg.swd.csss.csa.flowtest.models.eos.SyscolumnsEo;
import hksarg.swd.csss.csa.flowtest.services.DataManupilateMgr;
import junit.framework.TestCase;

public class DataManupilateMgrTest extends TestCase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private SyscolumnsDao dao;
	private DbProperties dbProperties;
	private String schema;
	private DataManupilateMgr dataManupilateMgr;
	@BeforeClass
	public void setUpClass() throws Exception {
	}

	@AfterClass
	public void tearDownClass() {
		try {

		} catch (Exception e) {
			logger.error(className + ".tearDownClass() -", e);
		}
	}

	@Before
	public void setUp() throws Exception {
		try {
			dao = new SyscolumnsDao();
			dbProperties = PropertiesFactory.getInstanceOfDb2DbProperties();
			schema = dbProperties.getSchema();
			dataManupilateMgr = new DataManupilateMgr();
		} catch (Exception e) {
			logger.error(className + ".setUp() -", e);
			throw e;
		}
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}
	
	@Test
	public void testRegExpList() {
		try{
			String selectSql = "select CASE_KEY, GRT_TYPE, PAY_TYPE, FR_DATE, TO_DATE, REC_STS, PAY_MTH_FR_DATE, PAY_MTH_TO_DATE, PAY_ARGM, PAY_NUM, RSON_CODE, PAY_ACC_NAME1, PAY_BANK_CODE1, PAY_BRN_CODE1, PAY_ACC_NUM1, PAY_MODE2, PAY_BANK_SEQ2, PAY_ACC_NAME2, PAY_BANK_CODE2, PAY_BRN_CODE2, PAY_ACC_NUM2, ID_NUM2, ID_TYPE2, PURPOSE2, OTH_PURPOSE2, BANK_PAY_AMT2, PAY_MODE3, PAY_BANK_SEQ3, PAY_ACC_NAME3, PAY_BANK_CODE3, PAY_BRN_CODE3, PAY_ACC_NUM3, ID_NUM3, ID_TYPE3, PURPOSE3, OTH_PURPOSE3, BANK_PAY_AMT3, PAY_MODE4, PAY_BANK_SEQ4, PAY_ACC_NAME4, PAY_BANK_CODE4, PAY_BRN_CODE4, PAY_ACC_NUM4, ID_NUM4, ID_TYPE4,    PURPOSE4, OTH_PURPOSE4, BANK_PAY_AMT4, PAY_MODE5, PAY_BANK_SEQ5, PAY_ACC_NAME5, PAY_BANK_CODE5, PAY_BRN_CODE5, PAY_ACC_NUM5, ID_NUM5, ID_TYPE5,    PURPOSE5, OTH_PURPOSE5, BANK_PAY_AMT5, SIMP_REQ_IND, AUTH_SIMP_AMT, PEND_SIMP_AMT_ARR, PEND_SIMP_DATE_ARR, PAY_SLIP_GEN_DATE, PAY_TAPE_GEN_DATE,    TOT_ASS_AMT, PAY_INSTR_IND, LAST_UPD_UID as LAST_UPD_LOGIN_ID, LAST_UPD_TS, 'TEST' as LAST_UPD_POST from dbaprd.tpy_pay_instr where CASE_KEY='2016-01-03' and GRT_TYPE='test for no' and PAY_TYPE = 'A' AND to_date = '2016-01-03' and rec_sts = 'a' and fr_date= '2016-01-05' order by case_key, grt_type, pay_type, fr_date, to_date, rec_sts";
			String tableName = "tpy_pay_instr";
			DeleteDto deleteDto = dataManupilateMgr.generateDeleteStatementWithVerification(selectSql, tableName);
			StringBuilder sbResult = new StringBuilder();
			if (deleteDto.getIsValid()){
				sbResult.append(deleteDto.getDeleteSql());
			} else {
				sbResult.append(deleteDto.getMessage());
			}
			System.out.println(sbResult);
		} catch (Exception e){
			logger.error(className + ".testRegExpList() -", e);
			assertTrue(false);
		}
	}
	
}
