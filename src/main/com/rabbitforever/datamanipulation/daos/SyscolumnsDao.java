package com.rabbitforever.datamanipulation.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.helpers.DaoHelper;
import com.rabbitforever.datamanipulation.models.criteria.SyscolumnsCriteria;
import com.rabbitforever.datamanipulation.models.criteria.SystemColumnInfoCriteria;
import com.rabbitforever.datamanipulation.models.dtos.PreparedStatementParamListDto;
import com.rabbitforever.datamanipulation.models.eos.SyscolumnsEo;
import com.rabbitforever.datamanipulation.utils.CommonUtils;

public class SyscolumnsDao extends SqlBaseDao<SyscolumnsEo>{
	public SyscolumnsDao() throws Exception {
		super();
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private final String selectSql = "select * from " + systemSchema + ".syscolumns ";
	private final String updateSql = "update " + systemSchema + ".syscolumns ";
	private final String insertSql = "insert into " + systemSchema + ".syscolumns ";
	private final String deleteSql = "delete from " + systemSchema + ".syscolumns ";
	
	public List<SyscolumnsEo> select(SyscolumnsCriteria criteria) throws Exception {
		List<SyscolumnsEo> syscolumnsEoList = null;
		try{
			syscolumnsEoList = select((Object)criteria);
		} catch (Exception e){
			logger.error(className + ".select() - ", e);
			throw e;
		}
		return syscolumnsEoList;
	}

	@Override
	public List<SyscolumnsEo> select(Object criteriaObj) throws Exception{
		List<SyscolumnsEo> results = null;
		StringBuilder sbSql = null;
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		DaoHelper daoHelper = null;
		try{
			sbSql = new StringBuilder();
			daoHelper = new DaoHelper();
			PreparedStatementParamListDto preparedStatementParamListDto = new PreparedStatementParamListDto();
			results = new ArrayList<SyscolumnsEo>();
			SyscolumnsCriteria criteria = (SyscolumnsCriteria) criteriaObj;
			if (this.conn == null){
				this.conn = dbUtils.getConnection();
			}
			conn = this.conn;

			if (criteria != null){
				int count = 0;
				if (criteria.getTbname() != null && !criteria.getTbname().isEmpty()){
					if (count == 0){
						sbSql.append("where ");
					} else {
						sbSql.append("and ");
					}
					sbSql.append("TBNAME = ? ");
					String value = criteria.getTbname();
					value = value.toUpperCase();
					preparedStatementParamListDto.putSequenceAndValue(count, value);
					count++;
				}
				if (criteria.getKeyseqGreaterThan() != null){
					if (count == 0){
						sbSql.append("where ");
					} else {
						sbSql.append("and ");
					}
					sbSql.append("KEYSEQ > ? ");
					Integer value = criteria.getKeyseqGreaterThan();
					preparedStatementParamListDto.putSequenceAndValue(count, value);
					count++;
				}
				
				sbSql.append("ORDER BY KEYSEQ ");
				
			}
			daoHelper.addPreparedStatementParamListDto(preparedStatementParamListDto);
			preparedStmt = daoHelper.getSingleRecordPreparedStatement(conn, selectSql + sbSql.toString());
			
			rs = preparedStmt.executeQuery();
			while (rs.next()){
				SyscolumnsEo rsObj = new SyscolumnsEo();
				rsObj.setTbcreator((String) rs.getObject("TBCREATOR"));
				rsObj.setColType((String) rs.getObject("COLTYPE"));
				rsObj.setTbname((String) rs.getObject("TBNAME"));
				rsObj.setName((String) rs.getObject("NAME"));
				rsObj.setKeyseq(CommonUtils.number2Integer((Number) rs.getObject("KEYSEQ")));

				results.add(rsObj);
			}
			
		} catch (Exception e){
			logger.error(className + ".select() - ", e);
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
	public void update(SyscolumnsEo eo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SyscolumnsEo create(SyscolumnsEo eo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(SyscolumnsEo eo) {
		// TODO Auto-generated method stub
		
	}
	
	
}
