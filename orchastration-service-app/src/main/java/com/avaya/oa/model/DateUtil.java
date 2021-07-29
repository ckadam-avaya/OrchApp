package com.avaya.oa.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * created on 21-10-2020
 */
public class DateUtil {

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static Timestamp getCurrentUTCTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp convertToUTCTime(String strTime) throws IllegalArgumentException {

		if (strTime.contains("T")) {
			strTime = strTime.replace('T', ' ');

		}
		if (strTime.contains("Z")) {
			strTime = strTime.replace("Z", "");
		}

		return Timestamp.valueOf(strTime);
	}

	public static Timestamp prepareStartTime(Timestamp startTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startTime.getTime());
		// adding 1 second to start time
		cal.add(Calendar.SECOND, 1);
		return new Timestamp(cal.getTime().getTime());

	}

	public static Timestamp getTimestampForTimeZone(Timestamp timestamp, String fromZone, String toZone) {
		LocalDateTime ldt = timestamp.toLocalDateTime();
		ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of(fromZone)).withZoneSameInstant(ZoneId.of(toZone));
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static String getTimeStampInFormattedString(String timeZone, Timestamp timestamp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a z");
		Timestamp timestampForTimeZone = DateUtil.getTimestampForTimeZone(timestamp, "UTC", timeZone);
		return formatter.format(timestampForTimeZone.toLocalDateTime().atZone(ZoneId.of(timeZone)));
	}

	public static final List<Long> times = Arrays.asList(TimeUnit.DAYS.toMillis(365), TimeUnit.DAYS.toMillis(30),
			TimeUnit.DAYS.toMillis(7), TimeUnit.DAYS.toMillis(1), TimeUnit.HOURS.toMillis(1),
			TimeUnit.MINUTES.toMillis(1), TimeUnit.SECONDS.toMillis(1));

	public static Timestamp getCurrentUTCTimeWithZeroMS() throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
		String format = simpleDateFormat.format(System.currentTimeMillis());
		Date parse = simpleDateFormat.parse(format);

		return new Timestamp(parse.getTime());
	}

	public static String getCurrentDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		return simpleDateFormat.format(cal.getTime());
	}

    public static String getYesterdayDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return simpleDateFormat.format(cal.getTime());
    }

	public static Timestamp addMinutesToTime(Timestamp startTime, int numberOfMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startTime.getTime());
		cal.add(Calendar.MINUTE, numberOfMinutes);
		return new Timestamp(cal.getTime().getTime());

	}

	public static Timestamp getCurrentUTCTimeInFormat() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		String format = simpleDateFormat.format(System.currentTimeMillis());
		Date parse = simpleDateFormat.parse(format);
		return new Timestamp(parse.getTime());
	}

	public static int getCurrentDateNumberInInterger() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
		String format = simpleDateFormat.format(System.currentTimeMillis());
		int date = Integer.parseInt(format);
		return date;
	}

	public static Timestamp getBeginingOfTheDay(Timestamp currentTime) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(currentTime);
		beginCalendar.set(Calendar.MILLISECOND, 0);
		beginCalendar.set(Calendar.SECOND, 0);
		beginCalendar.set(Calendar.MINUTE, 0);
		beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
		return new Timestamp(beginCalendar.getTimeInMillis());
	}

	public static Timestamp getBeginningOfTheMonth(int monthNumber, int year) {
		Calendar beginCalendar = Calendar.getInstance();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		beginCalendar.setTime(currentTime);
		beginCalendar.set(Calendar.MILLISECOND, 0);
		beginCalendar.set(Calendar.SECOND, 0);
		beginCalendar.set(Calendar.MINUTE, 0);
		beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
		beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
		beginCalendar.set(Calendar.MONTH, monthNumber - 1);
		beginCalendar.set(Calendar.YEAR, year);
		return new Timestamp(beginCalendar.getTimeInMillis());
	}

	public static Timestamp getEndOfTheMonth(Timestamp currentTime) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(currentTime);
		beginCalendar.set(Calendar.SECOND, 59);
		beginCalendar.set(Calendar.MINUTE, 59);
		beginCalendar.set(Calendar.HOUR_OF_DAY, 23);
		beginCalendar.set(Calendar.DAY_OF_MONTH, beginCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Timestamp(beginCalendar.getTimeInMillis());
	}
	
	public static Timestamp getBeginningOfTheMonth(Timestamp currentTime) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(currentTime);
		beginCalendar.set(Calendar.SECOND, 0);
		beginCalendar.set(Calendar.MINUTE, 1);
		beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
		beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
		return new Timestamp(beginCalendar.getTimeInMillis());
	}
	
	public static Timestamp getCurrentUTCTimeWithZeroHMS() throws ParseException {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
	    String format = simpleDateFormat.format(System.currentTimeMillis());
	    Date parse = simpleDateFormat.parse(format);
	    return new Timestamp(parse.getTime());
	}

	public static long getNumberOfWeeksInYear(long year){
		LocalDate date = LocalDate.of((int)year, 1, 1);
		LocalDate middleOfYear = date.withDayOfMonth(1).withMonth(6);
		return  middleOfYear.range(WeekFields.ISO.weekOfWeekBasedYear()).getMaximum();
	}

}
