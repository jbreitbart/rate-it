package it.rate.data;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION) 
public class TopURLMonthDB implements Comparable<TopURLMonthDB>
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	protected Key key;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	protected float averadgeRating;
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	protected int countOfRatings;

	public TopURLMonthDB(String url, float averadgeRating, int countOfRatings)
	{
		this.averadgeRating = averadgeRating;
		this.countOfRatings = countOfRatings;
		this.key = KeyFactory.createKey(
				TopURLMonthDB.class.getSimpleName(), url);
	}
	
	public Key getKey() {
		return key;
	}

	public float getAveradgeRating() {
		return averadgeRating;
	}

	public void setAveradgeRating(float averadgeRating) {
		this.averadgeRating = averadgeRating;
	}

	public int getCountOfRatings() {
		return countOfRatings;
	}

	public void setCountOfRatings(int countOfRatings) {
		this.countOfRatings = countOfRatings;
	}

	@Override
	public int compareTo(TopURLMonthDB o) {
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
