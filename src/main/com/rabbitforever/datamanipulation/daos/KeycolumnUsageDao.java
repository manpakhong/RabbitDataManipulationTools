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
import com.rabbitforever.datamanipulation.models.criteria.SystemColumnInfoCriteria;
import com.rabbitforever.datamanipulation.models.dtos.PreparedStatementParamListDto;
import com.rabbitforever.datamanipulation.models.eos.KeycolumnUsageEo;
import com.rabbitforever.datamanipulation.models.eos.SyscolumnsEo;
import com.rabbitforever.datamanipulation.utils.CommonUtils;

public class KeycolumnUsageDao extends SqlBaseDao<KeycolumnUsageEo>{
	private static final String CONSTRAINT_NAME_PRIMARY = "PRIMARY";
	public KeycolumnUsageDao() throws Exception {
		super();
	}
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();

	private StringBuilder getSelectSql(){
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select ");
		sbSql.append("c.TABLE_CATALOG, ");
		sbSql.append("c.TABLE_SCHEMA, ");
		sbSql.append("c.TABLE_NAME, ");
		sbSql.append("c.COLUMN_NAME, ");
		sbSql.append("c.DATA_TYPE, ");
		sbSql.append("k.CONSTRAINT_CATALOG, ");
		sbSql.append("k.CONSTRAINT_NAME, ");
		sbSql.append("k.ORDINAL_POSITION ");
		sbSql.append("from ");
		sbSql.append(systemSchema + ".KEY_COLUMN_USAGE k ");
		sbSql.append("inner join ");
		sbSql.append(systemSchema + ".COLUMNS c ");
		sbSql.append("on ");
		sbSql.append("c.TABLE_CATALOG = k.TABLE_CATALOG ");
		sbSql.append("and ");
		sbSql.append("c.TABLE_SCHEMA = k.TABLE_SCHEMA ");
		sbSql.append("and ");
		sbSql.append("c.TABLE_NAME = k.TABLE_NAME ");
		sbSql.append("and ");
		sbSql.append("c.COLUMN_NAME = k.COLUMN_NAME ");
		
		return sbSql;
	}
	
	
	

	@Override
	public List<KeycolumnUsageEo> select(Object criteriaObj) throws Exception {
		List<KeycolumnUsageEo> results = null;
		StringBuilder sbSql = null;
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		DaoHelper daoHelper = null;
		try{
			sbSql = new StringBuilder();
			daoHelper = new DaoHelper();
			PreparedStatementParamListDto preparedStatementParamListDto = new PreparedStatementParamListDto();
			results = new ArrayList<KeycolumnUsageEo>();
			KeycolumnUsageCriteria criteria = (KeycolumnUsageCriteria) criteriaObj;
			if (this.conn == null){
				this.conn = dbUtils.getConnection();
			}
			conn = this.conn;
			String selectSql = getSelectSql().toString();
			sbSql.append(selectSql);

			
			if (criteria != null){
				int count = 0;
				{
					if (count == 0){
						sbSql.append("where ");
					} else {
						sbSql.append("and ");
					}
					sbSql.append("k.CONSTRAINT_NAME = ? ");
					String value = CONSTRAINT_NAME_PRIMARY;
					preparedStatementParamListDto.putSequenceAndValue(count, value);
					count++;
				}
				
				if (criteria.getTbname() != null && !criteria.getTbname().isEmpty()){
					if (count == 0){
						sbSql.append("where ");
					} else {
						sbSql.append("and ");
					}
					sbSql.append("k.TABLE_NAME = ? ");
					String value = criteria.getTbname();
					value = value.toUpperCase();
					preparedStatementParamListDto.putSequenceAndValue(count, value);
					count++;
				}
				
				
				sbSql.append("ORDER BY k.ORDINAL_POSITION ");
				
			}
			daoHelper.addPreparedStatementParamListDto(preparedStatementParamListDto);
			preparedStmt = daoHelper.getSingleRecordPreparedStatement(conn, sbSql.toString());
			
			rs = preparedStmt.executeQuery();
			while (rs.next()){
				KeycolumnUsageEo rsObj = new KeycolumnUsageEo();
				rsObj.setColumnName((String) rs.getObject("COLUMN_NAME"));
				rsObj.setConstraintCatalog((String) rs.getObject("CONSTRAINT_CATALOG"));
				rsObj.setDataType((String) rs.getObject("DATA_TYPE"));
				Long ordinalPositionLong = (Long) rs.getObject("ORDINAL_POSITION");
				rsObj.setOrdinalPosition(ordinalPositionLong.intValue());
				rsObj.setTableCatalog((String) rs.getObject("TABLE_CATALOG"));
				rsObj.setTablename((String) rs.getObject("TABLE_NAME"));
				rsObj.setTableSchema((String) rs.getObject("TABLE_SCHEMA"));
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
	public void update(KeycolumnUsageEo eo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public KeycolumnUsageEo create(KeycolumnUsageEo eo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(KeycolumnUsageEo eo) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
