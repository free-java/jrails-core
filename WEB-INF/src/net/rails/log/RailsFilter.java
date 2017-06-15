package net.rails.log;

import java.util.Properties;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import net.rails.log.LogPoint;

public final class RailsFilter extends Filter {

	private final Properties properties = LogPoint.getConfigure();
	private int decide = Filter.DENY;
	
	@Override
	public int decide(LoggingEvent event) {
		String level = event.getLevel().toString();
		boolean use = level.equals("INFO") || level.equals("DEBUG");
		if(use){
			boolean isMarkJobSystem = Boolean.parseBoolean(properties.getProperty(LogPoint.JOB_SYSTEM));
			boolean isMarkJobApp = Boolean.parseBoolean(properties.getProperty(LogPoint.JOB_APP));
     		String tn = event.getThreadName();
			boolean isRunningJobSystem = tn.startsWith("SYSTEM_SCHEDULER-");
			boolean isRunningJobApp = tn.startsWith("APP_SCHEDULE-");

			if(isRunningJobSystem || isRunningJobApp){
				if(isMarkJobSystem && isRunningJobSystem){
					decide = Filter.NEUTRAL;
				}
				if(isMarkJobApp && isRunningJobApp){
					decide = Filter.NEUTRAL;
				}
				if(decide == Filter.NEUTRAL){
					decideMarks(event);
				}
			}else{
				decideMarks(event);
			}
			return decide;
		}
		decide = Filter.NEUTRAL;
		return decide;
	}
	
	private void decideMarks(LoggingEvent event){
		boolean isMarkApp = Boolean.parseBoolean(properties.getProperty(LogPoint.APP));
		boolean isMarkSql = Boolean.parseBoolean(properties.getProperty(LogPoint.SQL));
		boolean isMarkSqlConn = Boolean.parseBoolean(properties.getProperty(LogPoint.SQL_CONN));
		boolean isMarkSqlCache = Boolean.parseBoolean(properties.getProperty(LogPoint.SQL_CACHE));
		boolean isMarkSqlRows = Boolean.parseBoolean(properties.getProperty(LogPoint.SQL_ROWS));
		boolean isMarkSqlResult = Boolean.parseBoolean(properties.getProperty(LogPoint.SQL_RESULT));
		boolean isMarkWeb = Boolean.parseBoolean(properties.getProperty(LogPoint.WEB));
		
		if(isMarkApp && LogPoint.isMarkApp()) {
			decide = Filter.NEUTRAL;
		} 
		if(isMarkSql && LogPoint.isMarkSql()) {
			decide = Filter.NEUTRAL;
		} 
		if(isMarkSqlConn && LogPoint.isMarkSqlConn()){
			decide = Filter.NEUTRAL;
		}
		if(isMarkSqlCache && LogPoint.isMarkSqlCache()){
			decide = Filter.NEUTRAL;
		}
		if(isMarkSqlRows && LogPoint.isMarkSqlRows()){
			decide = Filter.NEUTRAL;
		}
		if(isMarkSqlResult && LogPoint.isMarkSqlResult()){
			decide = Filter.NEUTRAL;
		}
		if(isMarkWeb && LogPoint.isMarkWeb()){
			decide = Filter.NEUTRAL;
		}
	}

}
