package it.rate.data;

import it.rate.client.TopUrl;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION) 
public class TopUrlDB implements Comparable<TopUrlDB>
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private String url;
	@Persistent
	private float averadgeRating;
	@Persistent
	private int countOfRatings;
	@Persistent
	private String period;
	@Persistent
	private String type;
	@Persistent
	private Date date;
	

	public TopUrlDB(String url, float rating, int countOfRatings, 
							String period, String type)
	{
		this.url = url;
		this.averadgeRating = rating;
		this.countOfRatings = countOfRatings;
		this.type = type;
		this.period = period;
		this.date = new Date();
	}
	
	public Date getDate()
	{
		return date;
	}


	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public float getAveradgeRating()
	{
		return averadgeRating;
	}
	public void setAveradgeRating(float averadgeRating)
	{
		this.averadgeRating = averadgeRating;
	}
	public int getCountOfRatings()
	{
		return countOfRatings;
	}
	public void setCountOfRatings(int countOfRatings)
	{
		this.countOfRatings = countOfRatings;
	}
	
	public Key getKey()
	{
		return key;
	}
	

	@Override
	public int compareTo(TopUrlDB o)
	{
		int result = 0;
		if (this.getAveradgeRating() < o.getAveradgeRating())
		{
			result = 1;
		} else if (this.getAveradgeRating() > o.getAveradgeRating())
		{
			result = -1;
		} else
		{
			result = 0;
		}
		return result;
	}

}
