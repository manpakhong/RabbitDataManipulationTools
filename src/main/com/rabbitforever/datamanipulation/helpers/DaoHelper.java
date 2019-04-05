package com.rabbitforever.datamanipulation.helpers;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.models.dtos.PreparedStatementParamDto;
import com.rabbitforever.datamanipulation.models.dtos.PreparedStatementParamListDto;
import com.rabbitforever.datamanipulation.utils.CalendarUtils;

public class DaoHelper {
	private static final Logger log = LoggerFactory.getLogger(DaoHelper.class);
	private List<PreparedStatementParamListDto> preparedStatementParamListDtoList;
	public DaoHelper(){
		init();
	}
	
	public void addPreparedStatementParamListDto(PreparedStatementParamListDto preparedStatementParamListDto){
		this.preparedStatementParamListDtoList.add(preparedStatementParamListDto);
	}
	private void init(){
		preparedStatementParamListDtoList = new ArrayList<PreparedStatementParamListDto>();
	}
	public PreparedStatement getSingleRecordPreparedStatement(Connection conn, String sql){
		PreparedStatement preparedStmt = null;
		try{
			preparedStmt = getSingleRecordPreparedStatement(conn, sql, false);
		} catch (Exception e){
			log.error("DaoHelper.getBatchUpdatePreparedStatement() - Exception:", e);
		}
		return preparedStmt;
	}
	public PreparedStatement getSingleRecordPreparedStatement(Connection conn, String sql, Boolean isReturnGeneratedKey){
		PreparedStatement preparedStmt = null;
		try{
			if (!isReturnGeneratedKey){
				preparedStmt = conn.prepareStatement(sql);
			} else {
				preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			}
			if (conn != null && sql != null && !sql.isEmpty() 
					&& preparedStatementParamListDtoList != null 
					&& preparedStatementParamListDtoList.size() > 0){
				if (preparedStatementParamListDtoList.size() != 1){
					log.warn("DaoHelper.getSelectPreparedStatement() - select preparement should be one set of Params!!!!! sql:" + sql);
				}
				PreparedStatementParamListDto preparedStatementParamListDto = preparedStatementParamListDtoList.get(0);
				if (preparedStatementParamListDto != null){ // one batch
					List<PreparedStatementParamDto> paramList = preparedStatementParamListDto.getPreparedStatementParamDtoList();
					if (paramList != null && paramList.size() > 0){
						loopParamList2PreparedStmt(paramList, preparedStmt);
					} // end if (paramList != null && paramList.size() > 0)
				} // end one batch
			}
		} catch (Exception e){
			log.error("DaoHelper.getBatchUpdatePreparedStatement() - Exception:", e);
		}
		return preparedStmt;
	}
	private void loopParamList2PreparedStmt(List<PreparedStatementParamDto> paramList, PreparedStatement preparedStmt){
		try{
			for (int i =0; i <paramList.size(); i++){
				PreparedStatementParamDto prepareStatementParamDto = paramList.get(i);
				int index = i + 1;
				if (prepareStatementParamDto.getObject() != null){
					if (prepareStatementParamDto.getObject() instanceof Integer){
						Integer value = (Integer) prepareStatementParamDto.getObject();
						preparedStmt.setInt(index, value);
					} else if (prepareStatementParamDto.getObject() instanceof String){
						String value = (String) prepareStatementParamDto.getObject();
						preparedStmt.setString(index, value);
					} else if (prepareStatementParamDto.getObject() instanceof Timestamp){
						Timestamp timestamp = (Timestamp) prepareStatementParamDto.getObject();
						preparedStmt.setTimestamp(index, timestamp);
					} else if (prepareStatementParamDto.getObject() instanceof Date){
						Date value = (Date) prepareStatementParamDto.getObject();
						preparedStmt.setDate(index, CalendarUtils.convertJavaDate2SqlDate(value));
					} else if (prepareStatementParamDto.getObject() instanceof Boolean){
						Boolean value = (Boolean) prepareStatementParamDto.getObject();
						preparedStmt.setBoolean(index, value);
					}
				} else {
					preparedStmt.setObject(index, null);
				}
			} // end for (int i =0; i <paramList.size(); i++)
		} catch (Exception e){
			log.error("DaoHelper.loopParamList2PreparedStmt() - Exception:", e);
		}
	}
	
	public PreparedStatement getBatchModePreparedStatement(Connection conn, String sql){
		PreparedStatement preparedStmt = null;
		try{
			preparedStmt = conn.prepareStatement(sql);		
			if (conn != null && sql != null && !sql.isEmpty() 
					&& preparedStatementParamListDtoList != null 
					&& preparedStatementParamListDtoList.size() > 0){
				for (PreparedStatementParamListDto preparedStatementParamListDto: preparedStatementParamListDtoList){
					if (preparedStatementParamListDto != null){ // one batch
						List<PreparedStatementParamDto> paramList = preparedStatementParamListDto.getPreparedStatementParamDtoList();
						if (paramList != null && paramList.size() > 0){
							loopParamList2PreparedStmt(paramList, preparedStmt);
							preparedStmt.addBatch();
						} // end if (paramList != null && paramList.size() > 0)
					} // end one batch
				} //end for (PreparedStatementParamListDto preparedStatementParamListDto: preparedStatementParamListDtoList)
			}
		} catch (Exception e){
			log.error("DaoHelper.getPrepareStatementParams() - Exception:", e);
		}
		return preparedStmt;
	}

}
