package hksarg.swd.csss.csa.flowtest.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.helpers.DaoHelper;
import hksarg.swd.csss.csa.flowtest.models.dtos.PreparedStatementParamListDto;

public class ObjectDao extends SqlBaseDao<Object> {
	public ObjectDao() throws Exception {
		super();
	}
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	@Override
	public List<Object> select(Object criteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object eo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object create(Object eo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object eo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public int deleteBySqlStatement(String deleteSql) throws Exception {
		int noOfRecordsUpdated = 0; 
		StringBuilder sbSql = new StringBuilder(deleteSql);
		PreparedStatement preparedStmt = null;
		Connection conn = null;
		try{
			DaoHelper daoHelper = new DaoHelper();
			if (this.conn == null){
				this.conn = dbUtils.getConnection();
			}
			conn = this.conn;

			PreparedStatementParamListDto preparedStatementParamListDto = new PreparedStatementParamListDto();
			int count = 0;
			count ++;
//				preparedStatementParamListDto.putSequenceAndValue(count, CalendarUtils.convertSqlDate2SqlTimestamp(consolidateJob.getCreatedDate()));
//				count ++;
			daoHelper.addPreparedStatementParamListDto(preparedStatementParamListDto);

			conn.setAutoCommit(false);
			preparedStmt = daoHelper.getSingleRecordPreparedStatement(conn, sbSql.toString());
			int numUpdates=preparedStmt.executeUpdate();    
			
//			if (numUpdates == 0){
//				if (logger.isDebugEnabled()){
//					logger.debug(className + ".deleteBySqlStatement() - numUpdates=" + numUpdates);
//				}
//			}
			
			noOfRecordsUpdated=preparedStmt.getUpdateCount();
			conn.commit();
		} catch (Exception e){
			conn.rollback();
			logger.error(className + ".deleteBySqlStatement() - Exception: ", e);
			throw e;
		} finally {
			sbSql = null;
			if (preparedStmt != null){
				preparedStmt.close();
				preparedStmt = null;
			}
			if (this.conn != null){
				this.conn.close();
				this.conn = null;
			}
		}
		return noOfRecordsUpdated;
	}
	
}
