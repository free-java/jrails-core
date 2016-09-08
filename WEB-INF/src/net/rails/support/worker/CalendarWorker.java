package net.rails.support.worker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class CalendarWorker {

	private Calendar source;
	
	public CalendarWorker(long millis) {
		super();
		source = Calendar.getInstance();
		source.setTimeInMillis(millis);
	}
	
	public CalendarWorker(String pattern,String str) throws ParseException {
		super();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		source.setTimeInMillis(sdf.parse(str).getTime());		
	}
	
	public String format(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(source.getTime());
	}
	
	public boolean check(String pattern,String text){
		
		return false;
	}
	
	public Calendar getSource(){
		return source;
	}

}
