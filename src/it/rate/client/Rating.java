package it.rate.client;


import java.io.Serializable;
import java.util.Date;



/**
 * Class which can be used for one certain URL rating from a user (comments can't have more than one object)
 * or as a URL summary (comments can have more than one object).
 * The same goes for rating. Can be used for an average rating (URL summary) or a certain rating (from one user)
 * @author Waldo
 */
@SuppressWarnings("serial")
public class Rating implements Serializable
{
	
	private String userEmail;
	private String url;
	private String comment;
	private float rating;
	private Date date; 
	private String host; 
	
	public Rating()
	{
		
	}
	
	public Rating(String userEmail, String url, String host, String comment,
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComments(String comment) {
		this.comment = comment;
	}
}
