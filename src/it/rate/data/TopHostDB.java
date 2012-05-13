package it.rate.data;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TopHostDB implements Serializable, Comparable<TopHostDB>
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

	public TopHostDB()
	{

	}

	public TopHostDB(String url, float averageRating, int countOfRatings)
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

	public String getURL()
	{
		return url;
	}

	public void setURL(String url)
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

	@Override
	public int compareTo(TopHostDB o) {
		int result = 0;
		if (this.getAverageRating() < o.getAverageRating())
		{
			result = 1;
		}
		else if (this.getAverageRating() > o.getAverageRating())
		{
			result = -1;
		}
		else
		{
			result = 0;
		}
		return result;
	}

}
