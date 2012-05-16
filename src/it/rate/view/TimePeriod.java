package it.rate.view;

import java.util.Date;

public class TimePeriod {
	
	Date oneYearBack = new Date();
	Date oneMonthBack = new Date();
	Date oneDayBack = new Date();
	Date today = new Date();

	@SuppressWarnings("deprecation")
	public TimePeriod(){
		today.setTime(new Date().getTime());
		today.setYear(today.getYear());
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
}
