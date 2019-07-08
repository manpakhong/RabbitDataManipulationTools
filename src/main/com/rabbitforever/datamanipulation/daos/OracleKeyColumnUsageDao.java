package com.rabbitforever.datamanipulation.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.helpers.DaoHelper;
import com.rabbitforever.datamanipulation.models.criteria.KeycolumnUsageCriteria;
import com.rabbitforever.datamanipulation.models.criteria.MsKeyColumnUsageCriteria;
import com.rabbitforever.datamanipulation.models.criteria.OracleKeyColumnUsageCriteria;
import com.rabbitforever.datamanipulation.models.dtos.PreparedStatementParamListDto;
import com.rabbitforever.datamanipulation.models.eos.KeycolumnUsageEo;
import com.rabbitforever.datamanipulation.models.eos.MsKeyColumnUsageEo;
import com.rabbitforever.datamanipulation.models.eos.OracleKeyColumnUsageEo;

public class OracleKeyColumnUsageDao extends SqlBaseDao<OracleKeyColumnUsageEo>{
	private static final String CONSTRAINT_NAME_PRIMARY = "%PK_%";
	private static final String CONSTRAINT_TYPE_PRIMARY_KEY = "P";
	public OracleKeyColumnUsageDao() throws Exception {
		super();
	}
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();

	private StringBuilder getSelectSql(){
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select ");
		sbSql.append("(select distinct tablespace_name from user_tables where table_name = cols.table_name) as TABLESPACE_NAME, ");
		sbSql.append("cols.OWNER, ");
		sbSql.append("cols.TABLE_NAME, ");
		sbSql.append("(select distinct DATA_TYPE from " + systemSchema + " where column_name = cols.column_name) as data_type, ");
		sbSql.append("cols.COLUMN_NAME, ");
		sbSql.append("cons.CONSTRAINT_TYPE, ");
		sbSql.append("cons.CONSTRAINT_NAME, ");
		sbSql.append("cols.POSITION ");
		sbSql.append("from ");
		sbSql.append("all_constraints cons, all_cons_columns cols ");

		return sbSql;
	}
	
	
	

	@Override
	public List<OracleKeyColumnUsageEo> select(Object criteriaObj) throws Exception {
		List<OracleKeyColumnUsageEo> results = null;
		StringBuilder sbSql = null;
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		DaoHelper daoHelper = null;
		try{
			sbSql = new StringBuilder();
			daoHelper = new DaoHelper();
			PreparedStatementParamListDto preparedStatementParamListDto = new PreparedStatementParamListDto();
			results = new ArrayList<OracleKeyColumnUsageEo>();
			OracleKeyColumnUsageCriteria criteria = (OracleKeyColumnUsageCriteria) criteriaObj;
			if (this.conn == null){
				this.conn = dbUtils.getConnection();
			}
			conn = this.conn;
			String selectSql = getSelectSql().toString();
			sbSql.append(selectSql);

			
			if (criteria != null){
				int count = 1;
				
				if (criteria.getTbname() != null && !criteria.getTbname().isEmpty()){
					if (count == 1){
						sbSql.append("where ");
					} else {
						sbSql.append("and ");
					}
					sbSql.append("cols.TABLE_NAME = ? ");
					String value = criteria.getTbname();
					value = value.toUpperCase();
					preparedStatementParamListDto.putSequenceAndValue(count, value);
					count++;
				}
				{
					if (count == 1){
						sbSql.append("where ");
					} else {
						sbSql.append("and ");
					}
					sbSql.append("cons.CONSTRAINT_TYPE = ? ");
					String value = CONSTRAINT_TYPE_PRIMARY_KEY;
					preparedStatementParamListDto.putSequenceAndValue(count, value);
					count++;
				}
				
				{
					if (count == 1){
						sbSql.append("where ");
					} else {
						sbSql.append("and ");
					}
					sbSql.append("cons.constraint_name = cols.constraint_name ");

				}
				
				{
					if (count == 1){
						sbSql.append("where ");
					} else {
						sbSql.append("and ");
					}
					sbSql.append("cons.owner = cols.owner ");

				}
				
				
				sbSql.append("ORDER BY cols.POSITION ");
				
			}
			daoHelper.addPreparedStatementParamListDto(preparedStatementParamListDto);
			preparedStmt = daoHelper.getSingleRecordPreparedStatement(conn, sbSql.toString());
			
			rs = preparedStmt.executeQuery();
			while (rs.next()){
				OracleKeyColumnUsageEo rsObj = new OracleKeyColumnUsageEo();
				rsObj.setColumnName(rs.getString("COLUMN_NAME"));
				rsObj.setConstraintCatalog(rs.getString("CONSTRAINT_TYPE"));
				rsObj.setDataType(rs.getString("DATA_TYPE"));
				Integer ordinalPositionLong = rs.getInt("POSITION");
				rsObj.setOrdinalPosition(ordinalPositionLong);
				rsObj.setTableCatalog(rs.getString("TABLESPACE_NAME"));
				rsObj.setTablename(rs.getString("TABLE_NAME"));
				rsObj.setTableSchema(rs.getString("OWNER"));
				
				if (!rsObj.getConstraintCatalog().equals("P")) {
					int a = 3;
				}
				results.add(rsObj);
			}
			
		} catch (Exception e){
			logger.error(className + ".select() - criteriaObj=" + criteriaObj, e);
			throw e;
		} finally{
			sbSql = null;
			if (preparedStmt != null){
				preparedStmt.close();
				preparedStmt = null;
			}
			if (rs != null){
				rs.close();
				rs = null;
			}
			if (this.conn != null){
				this.conn.close();
				this.conn = null;
			}
		}
		return results;
	}

	@Override
	public void update(OracleKeyColumnUsageEo eo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OracleKeyColumnUsageEo create(OracleKeyColumnUsageEo eo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(OracleKeyColumnUsageEo eo) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
