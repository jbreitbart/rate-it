package it.rate.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TopUrl implements Comparable<TopUrl>, Serializable
{
	private String url;
	private float averadgeRating;
	
	public TopUrl()
	{
		
	}
	
	public TopUrl(String url, float rating)
	{
		this.url = url;
		this.averadgeRating = rating;
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

	@Override
	public int compareTo(TopUrl o)
	{
		int result = 0;
		if (this.getAveradgeRating() < o.getAveradgeRating())
		{
			result = 1;
		}
		else if (this.getAveradgeRating() > o.getAveradgeRating())
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
