package it.rate.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import it.rate.client.Rating;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

/**
 * This class represent Rated-domains in DB
 * 
 * @author Vladimir 
 */

@PersistenceCapable(identityType = IdentityType.APPLICATION) 
public class RatingDB 
{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private String userEmail;
	@Persistent
	private String url;
	@Persistent
	private Text comment;
	@Persistent
	private float rating;
	@Persistent
	private Date date;
	@Persistent
	private String host; 	
	

	public RatingDB(String userEmail, String url, String host, Text comment,
			float rating)
	{
		this.userEmail = userEmail;
		this.url = url;
		this.host = host;
		this.comment = comment;
		this.rating = rating;
		this.date = new Date();
	}
	
	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}
	
	public String getUserEmail()
	{
		return this.userEmail;
	}

	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Key getKey()
	{
		return key;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public float getRating()
	{
		return rating;
	}

	public void setRating(float rating)
	{
		this.rating = rating;
	}

	public Text getComment()
	{
		return comment;
	}

	public void setComments(Text comment)
	{
		this.comment = comment;
	}
}