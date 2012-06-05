package it.rate.view;

import java.util.Date;

public class TimePeriod {
	
	public Date oneYearBack;
	public Date oneMonthBack;
	public Date oneDayBack;
	public Date today;

	@SuppressWarnings("deprecation")
	public TimePeriod(){
		oneYearBack = new Date();
		oneMonthBack = new Date();
		oneDayBack = new Date();
		today = new Date();
		
		today.setTime(new Date().getTime());
		oneYearBack.setTime(today.getTime());
		oneMonthBack.setTime(today.getTime());
		oneDayBack.setTime(today.getTime());
		
		// Years top
		oneYearBack.setYear(today.getYear() - 1);
		// Months top
		if(today.getMonth() == 0){
			oneMonthBack.setYear(today.getYear() - 1);
			oneMonthBack.setMonth(11);
		}
		oneMonthBack.setMonth(today.getMonth() - 1);
		// Todays top
		if(today.getDate() == 1){
			if(today.getMonth() == 0){
				oneDayBack.setYear(today.getYear() - 1);
				oneDayBack.setMonth(11);
			}
			oneDayBack.setMonth(today.getMonth() - 1);
			oneDayBack.setDate(30);
		}
		oneDayBack.setDate(today.getDate() - 1);
		
	}
	
	public void refresh(){
		today = new Date();
	}
}
