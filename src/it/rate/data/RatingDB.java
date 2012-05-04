package it.rate.data;

import com.google.appengine.api.datastore.Key;

import it.rate.client.Rating;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

import shared.Date;

/**
 * This class represent Rating-objects in DB
 * 
 *  @author Vladimir
 */

@PersistenceCapable(table = "rating", identityType = IdentityType.APPLICATION)
public class RatingDB extends Rating
{
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private String user;
	@Persistent
	private String url;
	@Persistent
	private String comment;
	@Persistent
	private float rating;
	@Persistent
	private Date date; 
	
	
	public RatingDB(String user, String url, String comment, float rating)
	{
		this.user = user;
		this.url = url;
		this.comment = comment;
		this.rating = rating;
		this.date = new Date();
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Key getKey() {
		return key;
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
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComments(String comment) {
		this.comment = comment;
	}
}