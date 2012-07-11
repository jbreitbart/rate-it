package it.rate.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TopUrl implements Serializable, Comparable<TopUrl>
{
	
	private String url;
	private float averadgeRating;
	private int countOfRatings;

	public TopUrl()
	{

	}

	public TopUrl(String url, float rating, int countOfRatings)
	{
		this.url = url;
		this.averadgeRating = rating;
		this.countOfRatings = countOfRatings;
	}

	public int getCountOfRatings()
	{
		return countOfRatings;
	}

	public void setCountOfRatings(int countOfRatings)
	{
		this.countOfRatings = countOfRatings;
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
		return averadgeRating;
	}

	public void setAveradgeRating(float averadgeRating)
	{
		this.averadgeRating = averadgeRating;
	}

	@Override
	public int compareTo(TopUrl o)
	{
		int result = 0;
		if (this.getAverageRating() < o.getAverageRating())
		{
			result = 1;
		} else if (this.getAverageRating() > o.getAverageRating())
		{
			result = -1;
		} else
		{
			result = 0;
		}
		return result;
	}
}
