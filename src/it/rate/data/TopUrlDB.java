package it.rate.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TopUrlDB
{
	

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String url;
	@Persistent
	private float averageRating;
	@Persistent
	private int countOfRatings;
	@Persistent
	private Date date;

	public TopUrlDB()
	{
		
	}
	
	public TopUrlDB(String url, float averageRating, int countOfRatings)
	{
		this.url = url;
		this.averageRating = averageRating;
		this.countOfRatings = countOfRatings;
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
	
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public float getAverageRating()
	{
		return averageRating;
	}

	public void setAverageRating(float averageRating)
	{
		this.averageRating = averageRating;
	}

	public int getCountOfRatings()
	{
		return countOfRatings;
	}

	public void setCountOfRatings(int countOfRatings)
	{
		this.countOfRatings = countOfRatings;
	}


}
