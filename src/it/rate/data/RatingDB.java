package it.rate.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.jdo.annotations.Extension;
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
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private Text comment;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private float rating;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String date;
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
		
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy" );
		this.date =sdf.format(cal.getTime());
		
		this.key = KeyFactory.createKey(
				RatingDB.class.getSimpleName(),
				userEmail + "_" + url);
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

	public String getDate()
	{
		return date;
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