package com.rabbitforever.datamanipulation.models.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyscolumnsCriteria {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private String tbname;
	private Integer keyseqGreaterThan;
	
	public SyscolumnsCriteria(){
		
	}
	public SyscolumnsCriteria(SystemColumnInfoCriteria criteria){
		try{
			this.tbname = criteria.getTbname();
			this.keyseqGreaterThan = criteria.getKeyseqGreaterThan();
		} catch (Exception e){
			logger.error(className + ".SyscolumnsCriteria() - criteria=" + criteria, e);
		}
	}
	
	public String getTbname() {
		return tbname;
	}
	public void setTbname(String tbname) {
		this.tbname = tbname;
	}
	public Integer getKeyseqGreaterThan() {
		return keyseqGreaterThan;
	}
	public void setKeyseqGreaterThan(Integer keyseqGreaterThan) {
		this.keyseqGreaterThan = keyseqGreaterThan;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SyscolumnsCriteria [tbname=");
		builder.append(tbname);
		builder.append(", keyseqGreaterThan=");
		builder.append(keyseqGreaterThan);
		builder.append("]");
		return builder.toString();
	}	
}
