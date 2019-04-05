package com.rabbitforever.datamanipulation.models.dtos;

public class DeleteDto {
	private Boolean isValid;
	private String deleteSql;
	private String Message;
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	public String getDeleteSql() {
		return deleteSql;
	}
	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeleteDto [isValid=");
		builder.append(isValid);
		builder.append(", deleteSql=");
		builder.append(deleteSql);
		builder.append(", Message=");
		builder.append(Message);
		builder.append("]");
		return builder.toString();
	}
	
	
}
