package hksarg.swd.csss.csa.flowtest.models.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemColumnInfoCriteria {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private String tbname;
	private Integer keyseqGreaterThan;
	
	public SystemColumnInfoCriteria(){
		
	}
	public SystemColumnInfoCriteria(SyscolumnsCriteria syscolumnsCriteria){
		try{
			this.tbname = syscolumnsCriteria.getTbname();
			this.keyseqGreaterThan = syscolumnsCriteria.getKeyseqGreaterThan();
		} catch (Exception e){
			logger.error(className + ".SystemColumnInfoCriteria() - syscolumnsCriteria=" + syscolumnsCriteria, e);
		}
	}
	
	public SystemColumnInfoCriteria(KeycolumnUsageCriteria keycolumnUsageCriteria){
		try{
			this.tbname = keycolumnUsageCriteria.getTbname();
		} catch (Exception e){
			logger.error(className + ".SystemColumnInfoCriteria() - keycolumnUsageCriteria=" + keycolumnUsageCriteria, e);
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
