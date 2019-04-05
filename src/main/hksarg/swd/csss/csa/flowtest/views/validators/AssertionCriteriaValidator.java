package hksarg.swd.csss.csa.flowtest.views.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.criteria.AssertionCriteria;

public class AssertionCriteriaValidator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public AssertionCriteriaValidator() {
	}

	public static boolean validate(AssertionCriteria assertionCriteria) {
		boolean isValidated = true;
		String actualTableSql = null;
		String tableName = null;
		if (assertionCriteria == null){
			isValidated = false;
		} else {
			actualTableSql = assertionCriteria.getActualTableSql();
			tableName = assertionCriteria.getTableName();
		}
		if (actualTableSql == null) {
			isValidated = false;
		}
		if (tableName == null) {
			isValidated = false;
		}
		
		return isValidated;
	}
}
