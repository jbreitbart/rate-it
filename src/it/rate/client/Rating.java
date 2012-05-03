package it.rate.client;


import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Class which can be used for one certain URL rating from a user (comments can't have more than one object)
 * or as a URL summary (comments can have more than one object).
 * The same goes for rating. Can be used for an average rating (URL summary) or a certain rating (from one user)
 * @author Waldo
 */
public class Rating {
	
	private String user;
	private String url;
	private String comment;
	private float rating;
	private Date date; 
	
//	public Rating(String user, String url, String comment, float rating)
//	{
//		this.user = user;
//		this.url = url;
//		this.comment = comment;
//		this.rating = rating;
//		this.date = new Date();
//	}
	
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
