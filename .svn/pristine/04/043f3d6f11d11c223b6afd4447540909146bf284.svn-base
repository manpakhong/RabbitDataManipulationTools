package hksarg.swd.csss.csa.flowtest.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.bundles.SysProperties;
import hksarg.swd.csss.csa.flowtest.daos.KeycolumnUsageDao;
import hksarg.swd.csss.csa.flowtest.daos.MsKeyColumnUsageDao;
import hksarg.swd.csss.csa.flowtest.daos.SyscolumnsDao;
import hksarg.swd.csss.csa.flowtest.factories.PropertiesFactory;
import hksarg.swd.csss.csa.flowtest.models.criteria.KeycolumnUsageCriteria;
import hksarg.swd.csss.csa.flowtest.models.criteria.MsKeyColumnUsageCriteria;
import hksarg.swd.csss.csa.flowtest.models.criteria.SyscolumnsCriteria;
import hksarg.swd.csss.csa.flowtest.models.criteria.SystemColumnInfoCriteria;
import hksarg.swd.csss.csa.flowtest.models.dtos.SystemColumnInfoDto;
import hksarg.swd.csss.csa.flowtest.models.dtos.WhereClauseDto;
import hksarg.swd.csss.csa.flowtest.models.eos.KeycolumnUsageEo;
import hksarg.swd.csss.csa.flowtest.models.eos.MsKeyColumnUsageEo;
import hksarg.swd.csss.csa.flowtest.models.eos.SyscolumnsEo;
import hksarg.swd.csss.csa.flowtest.services.DataManupilateMgr;
import hksarg.swd.csss.csa.flowtest.utils.CommonUtils;

public class DataManupilateMgrHelper {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public final int KEY_SEQ_GREATER_THAN_ZERO = DataManupilateMgr.KEY_SEQ_GREATER_THAN_ZERO;
	private final int FIRST_KEY_SEQ = 1;
	private SysProperties sysProperties;
	private final String PERMUTATION_DELIMITED = ",";
	private SyscolumnsDao syscolumnsDao;
	private KeycolumnUsageDao keycolumnUsageDao;
	private MsKeyColumnUsageDao msKeyColumnUsageDao;
	public DataManupilateMgrHelper() {
		init();
	}

	private void init() {
		try {
			sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			syscolumnsDao = new SyscolumnsDao();
			keycolumnUsageDao = new KeycolumnUsageDao();
			msKeyColumnUsageDao = new MsKeyColumnUsageDao();
		} catch (Exception e) {
			logger.error(className + ".init() - ", e);
		}
	}

	public boolean isQuoteNeeded(SystemColumnInfoDto systemColumnInfoDto) {
		boolean isNeeded = true;
		String colType = systemColumnInfoDto.getColType();
		colType = colType.trim();
		colType = colType.toUpperCase();
		String dbType = sysProperties.getDatabaseType();
		if (dbType.equals(SysProperties.DATABASE_TYPE_DB2)){
			if (colType.equals(SyscolumnsEo.COL_TYPE_SMALLINT)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_BIGINT)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_REAL)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_INTEGER)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_DOUBLE)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_DECIMAL)) {
				isNeeded = false;
			}
		} else 
		if (dbType.equals(SysProperties.DATABASE_TYPE_MYSQL)){
			if (colType.equals(SyscolumnsEo.COL_TYPE_SMALLINT)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_BIGINT)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_REAL)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_INTEGER)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_DOUBLE)) {
				isNeeded = false;
			}
			if (colType.equals(SyscolumnsEo.COL_TYPE_DECIMAL)) {
				isNeeded = false;
			}
		}
		return isNeeded;
	}

	private float calculatePercentageOfColumnsMatched(String whereClause, List<SystemColumnInfoDto> systemColumnInfoDtoList) {
		float percentage = 0.0f;
		try {
			int count = 0;
			for (SystemColumnInfoDto systemColumnInfoDto : systemColumnInfoDtoList) {
				boolean found = StringUtils.containsIgnoreCase(whereClause, systemColumnInfoDto.getName());
				if (found) {
					count++;
				}
			}
			percentage = (float) count / systemColumnInfoDtoList.size();

			
		} catch (Exception e) {
			logger.error(className + ".calculatePercentageOfColumnsMatched() - whereClause=" + whereClause
					+ ",systemColumnInfoDtoList=" + systemColumnInfoDtoList, e);
		}
		return percentage;
	}

	public List<SystemColumnInfoDto> getSystemColumnInfoDtoList(SystemColumnInfoCriteria criteriaIn) {
		List<SystemColumnInfoDto> systemColumnInfoDtoList = null;
		try {
			String dbType = sysProperties.getDatabaseType();
			if (dbType != null){
				if (dbType.equals(SysProperties.DATABASE_TYPE_DB2)){
					SyscolumnsCriteria criteria = new SyscolumnsCriteria(criteriaIn);
					List<SyscolumnsEo> syscolumnsEoList = getSyscolumnsEoList(criteria);
					if (syscolumnsEoList != null && !syscolumnsEoList.isEmpty()){
						systemColumnInfoDtoList = new ArrayList<SystemColumnInfoDto>();
						for (SyscolumnsEo syscolumnsEo: syscolumnsEoList){
							SystemColumnInfoDto<SyscolumnsEo> systemColumnInfoDto = new SystemColumnInfoDto<SyscolumnsEo>(syscolumnsEo);
							systemColumnInfoDtoList.add(systemColumnInfoDto);
						}
					}
				}
				if (dbType.equals(SysProperties.DATABASE_TYPE_MYSQL)){
					KeycolumnUsageCriteria criteria = new KeycolumnUsageCriteria(criteriaIn);
					List<KeycolumnUsageEo> keycolumnUsageEoList = getKeycolumnUsageEoList(criteria);
					if (keycolumnUsageEoList != null && !keycolumnUsageEoList.isEmpty()){
						systemColumnInfoDtoList = new ArrayList<SystemColumnInfoDto>();
						for (KeycolumnUsageEo keycolumnUsageEo: keycolumnUsageEoList){
							SystemColumnInfoDto<KeycolumnUsageEo> systemColumnInfoDto = new SystemColumnInfoDto<KeycolumnUsageEo>(keycolumnUsageEo);
							systemColumnInfoDtoList.add(systemColumnInfoDto);
						}
					}
				}
				if (dbType.equals(SysProperties.DATABASE_TYPE_MSSQL)){
					MsKeyColumnUsageCriteria criteria = new MsKeyColumnUsageCriteria(criteriaIn);
					List<MsKeyColumnUsageEo> msKeyColumnUsageEoList = getMsKeyColumnUsageEoList(criteria);
					if (msKeyColumnUsageEoList != null && !msKeyColumnUsageEoList.isEmpty()){
						systemColumnInfoDtoList = new ArrayList<SystemColumnInfoDto>();
						for (MsKeyColumnUsageEo msKeyColumnUsageEo: msKeyColumnUsageEoList){
							SystemColumnInfoDto<MsKeyColumnUsageEo> systemColumnInfoDto = new SystemColumnInfoDto<MsKeyColumnUsageEo>(msKeyColumnUsageEo);
							systemColumnInfoDtoList.add(systemColumnInfoDto);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(className + ".getSystemColumnInfoDtoList() - criteriaIn=" + criteriaIn, e);
		}
		return systemColumnInfoDtoList;
	}

	public List<SyscolumnsEo> getSyscolumnsEoList(SyscolumnsCriteria criteria) {
		List<SyscolumnsEo> syscolumnsEoList = null;
		try {
			syscolumnsEoList = syscolumnsDao.select(criteria);
		} catch (Exception e) {
			logger.error(className + ".getSyscolumnsEoList() - criteria=" + criteria, e);
		}
		return syscolumnsEoList;
	}

	public List<KeycolumnUsageEo> getKeycolumnUsageEoList(KeycolumnUsageCriteria criteria) {
		List<KeycolumnUsageEo> keycolumnUsageEoList = null;
		try {
			keycolumnUsageEoList = keycolumnUsageDao.select(criteria);
		} catch (Exception e) {
			logger.error(className + ".getKeycolumnUsageEoList() - criteria=" + criteria, e);
		}
		return keycolumnUsageEoList;
	}

	public List<MsKeyColumnUsageEo> getMsKeyColumnUsageEoList(MsKeyColumnUsageCriteria criteria) {
		List<MsKeyColumnUsageEo> msKeyColumnUsageEoList = null;
		try {
			msKeyColumnUsageEoList = msKeyColumnUsageDao.select(criteria);
		} catch (Exception e) {
			logger.error(className + ".getMsKeyColumnUsageEoList() - criteria=" + criteria, e);
		}
		return msKeyColumnUsageEoList;
	}
	
	private List<String> getAllPossibleCombination(List<SystemColumnInfoDto> systemColumnInfoDtoList) {
		List<String> allPossibleCombinationList = null;
		try {
			allPossibleCombinationList = new ArrayList<String>();
			if (systemColumnInfoDtoList != null && systemColumnInfoDtoList.size() > 0) {
				String[] columnArray = new String[systemColumnInfoDtoList.size()];
				for (int i = 0; i < systemColumnInfoDtoList.size(); i++) {
					SystemColumnInfoDto systemColumnInfoDto = systemColumnInfoDtoList.get(i);
					columnArray[i] = systemColumnInfoDto.getName();
				}
				String[] results = getPermutationLists(columnArray, systemColumnInfoDtoList.size());
				for (int i = 0; i < results.length; i++) {
					String[] splitedColNameArray = results[i].split(PERMUTATION_DELIMITED);
					if (splitedColNameArray.length == systemColumnInfoDtoList.size()) {
						if (CommonUtils.isNoDuplicationWithinArray(splitedColNameArray)) {
							allPossibleCombinationList.add(results[i]);
						}
					}
				}

			}
		} catch (Exception e) {
			logger.error(className + ".getAllPossibleCombination() - ", e);
		}
		return allPossibleCombinationList;
	}

	private String[] getPermutationLists(String[] elements, int lengthOfList) {
		// initialize our returned list with the number of elements calculated
		// above
		String[] allLists = new String[(int) Math.pow(elements.length, lengthOfList)];

		// lists of length 1 are just the original elements
		if (lengthOfList == 1) {
			return elements;
		} else {
			// the recursion--get all lists of length 3, length 2, all the way
			// up to 1
			String[] allSublists = getPermutationLists(elements, lengthOfList - 1);

			// append the sublists to each element
			int arrayIndex = 0;

			for (int i = 0; i < elements.length; i++) {
				for (int j = 0; j < allSublists.length; j++) {
					// add the newly appended combination to the list
					allLists[arrayIndex] = elements[i] + PERMUTATION_DELIMITED + allSublists[j];
					arrayIndex++;
				}
			}
			return allLists;
		}
	}

	public boolean checkIsFirstKeySeqKeyColumnsCheckEnabled(String whereClause, List<SystemColumnInfoDto> systemColumnInfoDtoList) {
		boolean isFirstKeySeqKeyColumnsFound = false;
		try {
			if (systemColumnInfoDtoList != null && !systemColumnInfoDtoList.isEmpty()){
				for (SystemColumnInfoDto systemColumnInfoDto : systemColumnInfoDtoList) {
					if (systemColumnInfoDto.getKeyseq().intValue() == FIRST_KEY_SEQ) {
						isFirstKeySeqKeyColumnsFound = StringUtils.containsIgnoreCase(whereClause, systemColumnInfoDto.getName());
					}
				}
			}
			isFirstKeySeqKeyColumnsFound = true;
		} catch (Exception e) {
			logger.error(className + ".checkIsFirstKeySeqKeyColumnsCheckEnabled() ", e);
		}
		return isFirstKeySeqKeyColumnsFound;
	}

	public boolean consequentKeyFieldsFoundInWhereClause(String selectSql, String tableName,
			List<SystemColumnInfoDto> systemColumnInfoDtoList) {
		boolean isFound = false;
		try {
			List<String> regExpPatternStringList = generateRegExpPatternCombination(systemColumnInfoDtoList);

			for (String patternString : regExpPatternStringList) {
				Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(selectSql);
				while (matcher.find()) {
					isFound = true;
					logger.info(className + ".consequentKeyFieldsFoundInWhereClause() - whereClause found="
							+ matcher.group() + ",selectSql=" + selectSql, ",tableName=" + tableName);
				}
			}

		} catch (Exception e) {
			logger.error(className + ".consequentKeyFieldsFoundInWhereClause() ", e);
		}
		return isFound;
	}

	public String weightAndReturnMoreSuitableWhereClause(List<String> matchedList, String tableName) {
		String moreSuitableWhereClause = null;

		try {
			SystemColumnInfoCriteria criteria = new SystemColumnInfoCriteria();
			criteria.setKeyseqGreaterThan(KEY_SEQ_GREATER_THAN_ZERO);
			criteria.setTbname(tableName);
			List<SystemColumnInfoDto> systemColumnInfoDtoList = getSystemColumnInfoDtoList(criteria);
			List<WhereClauseDto> whereClauseDtoList = new ArrayList<WhereClauseDto>();
			for (String matchedStr : matchedList) {
				int count = 0;
				for (SystemColumnInfoDto systemColumnInfoDto : systemColumnInfoDtoList) {
					boolean found = StringUtils.containsIgnoreCase(matchedStr, systemColumnInfoDto.getName());
					if (found) {
						count++;
					}
				}
				WhereClauseDto whereClauseDto = new WhereClauseDto();
				whereClauseDto.setNumberOfKeyColumns(count);
				whereClauseDto.setWhereClauseStatement(matchedStr);
				whereClauseDtoList.add(whereClauseDto);
			}

			int comparisionCount = 0;

			for (WhereClauseDto whereClauseDtoLoop : whereClauseDtoList) {
				if (whereClauseDtoLoop.getNumberOfKeyColumns() > comparisionCount) {
					moreSuitableWhereClause = whereClauseDtoLoop.getWhereClauseStatement();
				}
			}

			if (moreSuitableWhereClause == null | moreSuitableWhereClause.isEmpty()) {
				moreSuitableWhereClause = whereClauseDtoList.get(0).getWhereClauseStatement();
			}

		} catch (Exception e) {
			logger.error(className + ".getWhereClauseFromSelectSql()", e);
		}
		return moreSuitableWhereClause;
	}

	private List<String> generateRegExpPatternCombination(List<SystemColumnInfoDto> systemColumnInfoDtoList) {
		List<String> regExpPatternStringList = null;
		try {
			List<String> allCombinationList = getAllPossibleCombination(systemColumnInfoDtoList);
			if (allCombinationList != null && allCombinationList.size() > 0) {
				regExpPatternStringList = new ArrayList<String>();
				for (int i = 0; i < allCombinationList.size(); i++) {
					String regCombination = allCombinationList.get(i);
					String[] columnArray = regCombination.split(PERMUTATION_DELIMITED);
					StringBuilder sbRegExp = new StringBuilder();
					sbRegExp.append("where\\s{1,}");
					for (int n = 0; n < columnArray.length; n++) {
						if (n > 0) {
							sbRegExp.append("\\s{1,}and\\s{1,}");
						}
						sbRegExp.append("((\\w+\\.|)");
						sbRegExp.append(columnArray[n] + "\\s{0,}[=,like]\\s{0,}'{0,1}[a-z,A-Z,0-9,\\s{1,},-,%]{1,}'{0,1})");
					}
					regExpPatternStringList.add(sbRegExp.toString());
				}
			}
		} catch (Exception e) {
			logger.error(className + ".generateRegExpPatternCombination()", e);
		}
		return regExpPatternStringList;
	}

	public boolean isSatisfiedPercentageColumnsMatched(String whereClause, List<SystemColumnInfoDto> systemColumnInfoDtoList) {
		boolean isOk = false;
		try {
			int deletePercentageOfKeyColumnsHit = sysProperties.getDeletePercentageOfKeyColumnsHit();
			float expectedPercentage = (float) deletePercentageOfKeyColumnsHit / 100;
			float actualPercentage = this.calculatePercentageOfColumnsMatched(whereClause, systemColumnInfoDtoList);
			if (actualPercentage >= expectedPercentage) {
				isOk = true;
			}
		} catch (Exception e) {
			logger.error(className + ".isSatisfiedPercentageColumnsMatched() ", e);
		}
		return isOk;
	}
}
