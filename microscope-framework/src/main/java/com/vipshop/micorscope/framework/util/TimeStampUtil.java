package com.vipshop.micorscope.framework.util;

import java.util.concurrent.TimeUnit;

public class TimeStampUtil {
	
	@SuppressWarnings("deprecation")
	public static long timestampOfCurrentHour(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      0, 
							      0).getTime();
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfPrevHour(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      0, 
							      0).getTime() - (1000 * 60 * 60);
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfCurrent5Minute(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      (calendar.currentMinute() / 5) * 5, 
							      0).getTime();
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfPrev5Minute(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      (calendar.currentMinute() / 5) * 5, 
							      0).getTime() - (1000 * 60 * 5);
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfCurrentMinute(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      calendar.currentMinute(), 
							      0).getTime();
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfPrevMinute(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      calendar.currentMinute(), 
							      0).getTime() - (1000 * 60);
	}
	
	public static long currentTimeMicros() {
		return TimeUnit.MILLISECONDS.toMicros(System.currentTimeMillis());
	}
	
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
}