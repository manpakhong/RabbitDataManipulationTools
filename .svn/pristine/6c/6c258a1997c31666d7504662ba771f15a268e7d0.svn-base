package hksarg.swd.csss.csa.flowtest.models.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsKeyColumnUsageCriteria {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private String tbname;
	public MsKeyColumnUsageCriteria(){
		
	}
	public MsKeyColumnUsageCriteria(SystemColumnInfoCriteria criteria){
		try{
			this.tbname = criteria.getTbname();
		} catch (Exception e){
			logger.error(className + ".MsKeyColumnUsageCriteria() - criteria=" + criteria, e);
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
		builder.append("MsKeyColumnUsageCriteria [tbname=");
		builder.append(tbname);
		builder.append("]");
		return builder.toString();
	}

	
}
