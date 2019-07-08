package com.rabbitforever.datamanipulation.models.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleKeyColumnUsageCriteria {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private String tbname;
	public OracleKeyColumnUsageCriteria(){
		
	}
	public OracleKeyColumnUsageCriteria(SystemColumnInfoCriteria criteria){
		try{
			this.tbname = criteria.getTbname();
		} catch (Exception e){
			logger.error(className + ".OracleKeyColumnUsageCriteria() - criteria=" + criteria, e);
		}
	}
	
	
	public String getTbname() {
		return tbname;
	}

	public void setTbname(String tbname) {
		this.tbname = tbname;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OracleKeyColumnUsageCriteria [tbname=");
		builder.append(tbname);
		builder.append("]");
		return builder.toString();
	}

	
}
