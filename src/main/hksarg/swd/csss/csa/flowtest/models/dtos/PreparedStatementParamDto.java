package hksarg.swd.csss.csa.flowtest.models.dtos;

public class PreparedStatementParamDto implements Comparable<PreparedStatementParamDto>{
	private Integer sequence;
	private Object object;
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	@Override
	public int compareTo(PreparedStatementParamDto o) {
		int compareSequence = 0;
		if (o.getSequence() != null){
			// ascending order
			compareSequence =  this.sequence - o.getSequence().intValue();
			
			// descending order
//			compareSequence =  o.getSequence().intValue() - this.sequence;
		}
		return compareSequence;
	}
}
