package shared;

import java.util.Calendar;

public class Date implements Comparable<Date>
{
	private int day;
	private int month;
	private int year;
	
	/**
	 * create a date-object with current timestamp
	 */
	public Date()
	{
		this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		this.month = Calendar.getInstance().get(Calendar.MONTH);
		this.year = Calendar.getInstance().get(Calendar.YEAR);
	}
	
	
	/**
	 * 
	 * @param day
	 * @param month
	 * @param year
	 */
	public Date(int day, int month, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
		
	}
	
	/**
	 * 
	 * @param date
	 * @return 	o1.compareTo( o2 ) < 0   <=>  o1 < o2
	 *		    o1.compareTo( o2 ) == 0  <=>  o1 == o2
	 *			o1.compareTo( o2 ) > 0   <=>  o1 > o2
	 *			(for Example: 12.12.2012 > 11.11.2011)
	 */
	@Override
	public int compareTo(Date date)
	{
		int result;
		
		if (this.getYear() > date.getYear())
		{
			result = 1;
		}
		else if (this.getYear() < date.getYear())
		{
			result = -1;
		}
		else if (this.getMonth() > date.getMonth())
		{
			result = 1;
		}
		else if (this.getMonth() < date.getMonth())
		{
			result = -1;
		}
		else if(this.getDay() > date.getDay())
		{
			result = 1;
		}
		else if (this.getDay() < date.getDay())
		{
			result = -1;
		}
		else
		{
			result = 0;
		}
		
		return result;
	}
	
	
	
	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

}
