package com.rabbitforever.datamanipulation.models.dtos;


import java.util.ArrayList;
import java.util.List;

public class PreparedStatementParamListDto {
	private List<PreparedStatementParamDto> preparedStatementParamDtoList;
	public PreparedStatementParamListDto(){
		init();
	}
	private void init(){
		preparedStatementParamDtoList = new ArrayList<PreparedStatementParamDto>();
	}
	public void putSequenceAndValue(Integer sequence, Object value){
		PreparedStatementParamDto preparedStatementParamDto = new PreparedStatementParamDto();
		preparedStatementParamDto.setObject(value);
		preparedStatementParamDto.setSequence(sequence);
		preparedStatementParamDtoList.add(preparedStatementParamDto);
	}
	public List<PreparedStatementParamDto> getPreparedStatementParamDtoList() {
		return preparedStatementParamDtoList;
	}
	
}
