package hksarg.swd.csss.csa.flowtest.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	private final static String className = DateUtils.class.getName();
	private DateUtils() {
	}

	public static String getDateTimeString() {
		String dateTimeString = null;
		try {
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
			Date date = new Date();
			dateTimeString = formatter.format(date);
		} catch (Exception e) {
			logger.error(className + ".getDateTimeString()", e);
		}
		return dateTimeString;
	}

	public static Date getDefaultNullableDateTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1111);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 11);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public Date getTodayWithoutTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
		
	public static boolean areTheSameDate(Date date1, Date date2){
		boolean theSame = true;
		if (date1 != null && date2 != null){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			int year1 = cal1.get(Calendar.YEAR);
			int month1 = cal1.get(Calendar.MONTH);
			int day1 = cal1.get(Calendar.DAY_OF_MONTH);
//			int hour1 = cal1.get(Calendar.HOUR_OF_DAY);
//			int minute1 = cal1.get(Calendar.MINUTE);
//			int second1 = cal1.get(Calendar.SECOND);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			int year2 = cal2.get(Calendar.YEAR);
			int month2 = cal2.get(Calendar.MONTH);
			int day2 = cal2.get(Calendar.DAY_OF_MONTH);
//			int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
//			int minute2 = cal2.get(Calendar.MINUTE);
//			int second2 = cal2.get(Calendar.SECOND);
			
			if (year1 != year2){
				theSame = false;
			}
			if (month1 != month2){
				theSame = false;
			}
			if (day1 != day2){
				theSame = false;
			}
//			if (hour1 != hour2){
//				theSame = false;
//			}
//			if (minute1 != minute2){
//				theSame = false;
//			}
//			if (second1 != second2){
//				theSame = false;
//			}
		} else {
			theSame = false;
			logger.warn(className + ".areTheSameDate() - : date1 or date2 value(s) is/ are null!");
		}
		return theSame;
	}
	
	public static boolean areTheSameTime(Date date1, Date date2){
		boolean theSame = true;
		if (date1 != null && date2 != null){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			int year1 = cal1.get(Calendar.YEAR);
			int month1 = cal1.get(Calendar.MONTH);
			int day1 = cal1.get(Calendar.DAY_OF_MONTH);
			int hour1 = cal1.get(Calendar.HOUR_OF_DAY);
			int minute1 = cal1.get(Calendar.MINUTE);
			int second1 = cal1.get(Calendar.SECOND);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			int year2 = cal2.get(Calendar.YEAR);
			int month2 = cal2.get(Calendar.MONTH);
			int day2 = cal2.get(Calendar.DAY_OF_MONTH);
			int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
			int minute2 = cal2.get(Calendar.MINUTE);
			int second2 = cal2.get(Calendar.SECOND);
			
			if (year1 != year2){
				theSame = false;
			}
			if (month1 != month2){
				theSame = false;
			}
			if (day1 != day2){
				theSame = false;
			}
			if (hour1 != hour2){
				theSame = false;
			}
			if (minute1 != minute2){
				theSame = false;
			}
			if (second1 != second2){
				theSame = false;
			}
		} else {
			theSame = false;
			logger.warn(className + ".areTheSameTime() - : date1 or date2 value(s) is/ are null!");
		}
		return theSame;
	}
	
	
	public static Date trimDateTimeMilliSecond2DateTime(Date date){
		Date dateRtn = null;
		if (date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
//			int actualMinOfHour = cal.getActualMinimum(Calendar.HOUR_OF_DAY);
//			int actualMinOfMinute = cal.getActualMinimum(Calendar.MINUTE);
//			int actualMinOfSecond = cal.getActualMinimum(Calendar.SECOND);
			int actualMinOfMilliSecond = cal.getActualMinimum(Calendar.MILLISECOND);
			
//			cal.set(Calendar.HOUR_OF_DAY, actualMinOfHour);
//			cal.set(Calendar.MINUTE, actualMinOfMinute);
//			cal.set(Calendar.SECOND, actualMinOfSecond);
			cal.set(Calendar.MILLISECOND, actualMinOfMilliSecond);
			
			dateRtn = cal.getTime();
		}
		return dateRtn;
	}
	
	public static Date trimDateTime2Date(Date date){
		Date dateRtn = null;
		if (date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			int actualMinOfHour = cal.getActualMinimum(Calendar.HOUR_OF_DAY);
			int actualMinOfMinute = cal.getActualMinimum(Calendar.MINUTE);
			int actualMinOfSecond = cal.getActualMinimum(Calendar.SECOND);
			int actualMinOfMilliSecond = cal.getActualMinimum(Calendar.MILLISECOND);
			
			cal.set(Calendar.HOUR_OF_DAY, actualMinOfHour);
			cal.set(Calendar.MINUTE, actualMinOfMinute);
			cal.set(Calendar.SECOND, actualMinOfSecond);
			cal.set(Calendar.MILLISECOND, actualMinOfMilliSecond);
			
			dateRtn = cal.getTime();
		}
		return dateRtn;
	}
	
	public static boolean isDateEqualsOrAfterAnother(Date date1, Date date2){
		boolean isDate1EqualsOrAfterDate2 = false;
		if (date1 != null && date2 != null){
			
			date1 = trimDateTime2Date(date1);
			date2 = trimDateTime2Date(date2);
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			
			boolean areTheSameDate = areTheSameDate(date1, date2);
			if (areTheSameDate){
				isDate1EqualsOrAfterDate2 = true;
			} else if (cal1.after(cal2)){
				isDate1EqualsOrAfterDate2 = true;
			}
		}
		return isDate1EqualsOrAfterDate2;
	}
	
	public static boolean isDateEqualsOrBeforeAnother(Date date1, Date date2){
		boolean isDate1EqualsOrBeforeDate2 = false;
		if (date1 != null && date2 != null){
			
			date1 = trimDateTime2Date(date1);
			date2 = trimDateTime2Date(date2);
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			
			boolean areTheSameDate = areTheSameDate(date1, date2);
			if (areTheSameDate){
				isDate1EqualsOrBeforeDate2 = true;
			} else if (cal1.before(cal2)){
				isDate1EqualsOrBeforeDate2 = true;
			}
		}
		return isDate1EqualsOrBeforeDate2;
	}
	
	public static boolean isDefaultNullableDateTime(Date date){
		boolean isDefaultNullableDateTime = false;
		if (date != null){
			Date defaultNullableDateTime = getDefaultNullableDateTime();
			isDefaultNullableDateTime = areTheSameTime(defaultNullableDateTime, date);
		}
		
		return isDefaultNullableDateTime;
	}
}
