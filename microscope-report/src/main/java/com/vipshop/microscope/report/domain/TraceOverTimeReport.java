package com.vipshop.microscope.report.domain;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.micorscope.framework.util.IPAddressUtil;
import com.vipshop.micorscope.framework.util.MathUtil;
import com.vipshop.micorscope.framework.util.TimeStampUtil;
import com.vipshop.microscope.report.factory.MySQLRepository;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * Stat span in trace by 5 minute.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceOverTimeReport extends AbstraceReport {

	private String appName;
	private int appIp;
	private int type;
	private String name;

	private long sum;
	private float avg;

	private int hit;
	private int fail;
	
	private long startTime;
	private long endTime;
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#updateReportInit
	 */
	@Override
	public void updateReportInit(CalendarUtil calendarUtil, Span span) {
		String app = span.getAppName();
		String ipAdress = span.getAppIp();
		String type = span.getSpanType();
		String name = span.getSpanName();
		
		this.setDateBy5Minute(calendarUtil);
		this.setAppName(app);
		this.setAppIp(IPAddressUtil.intIPAddress(ipAdress));
		this.setType(Category.getIntValue(type));
		this.setName(name);
		this.setStartTime(System.currentTimeMillis());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#updateReportNext
	 */
	@Override
	public void updateReportNext(Span span) {
		if (!span.getResultCode().equals("OK")) {
			this.setFail(this.getFail() + 1);
		}
		this.setHit(this.getHit() + 1);
		this.setSum(this.getSum() + span.getDuration());
		this.setEndTime(System.currentTimeMillis());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#saveReport
	 */
	@Override
	public void saveReport() {
		long count = this.getHit();
		long time = this.getSum();
		this.setAvg(MathUtil.calculateAvgDura(count, time));
		
		MySQLRepository.getRepository().save(this);
	}
	
	public static String getKey(CalendarUtil calendar, Span span) {
		String app = span.getAppName();
		String ipAdress = span.getAppIp();
		String type = span.getSpanType();
		String name = span.getSpanName();
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfCurrent5Minute(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		return builder.toString();
	}
	
	public static String getPrevKey(CalendarUtil calendar, Span span) {
		String app = span.getAppName();
		String ipAdress = span.getAppIp();
		String type = span.getSpanType();
		String name = span.getSpanName();
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfPrev5Minute(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		return builder.toString();
	}
	
	public int getRegion(int minute) {
		return 0;
	}
	
	public float getAvg() {
		return avg;
	}

	public void setAvg(float durationCount) {
		avg = durationCount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String app) {
		this.appName = app;
	}

	public int getAppIp() {
		return appIp;
	}

	public void setAppIp(int ipAdress) {
		this.appIp = ipAdress;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hitCount) {
		this.hit = hitCount;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int failCount) {
		this.fail = failCount;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sumDura) {
		this.sum = sumDura;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " TraceOverTimeReport content [appName=" + appName + ", appIPAd=" + appIp + ", type=" + type + ", name=" + name + ", " +
				 					                            "sum=" + sum + ", avg=" + avg + ", hitCount=" + hit + ", failCount=" + fail + "]";
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
