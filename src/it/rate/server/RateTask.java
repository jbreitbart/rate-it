package it.rate.server;

import it.rate.data.RatingDB;
import it.rate.data.TopDomainDayDB;
import it.rate.data.TopDomainMonthDB;
import it.rate.data.TopDomainYearDB;
import it.rate.data.TopURLDayDB;
import it.rate.data.TopURLMonthDB;
import it.rate.data.TopURLYearDB;
import it.rate.util.ErrorMessage;
import it.rate.util.PMF;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import org.apache.commons.validator.routines.UrlValidator;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class RateTask {
	
	
	public static int rateUrl(String url, String comment, float rating, boolean canReplace) throws IOException {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		User user = UserServiceFactory.getUserService().getCurrentUser();
		URL link;
		int returnMessage = 0;
		
		if (user == null)
		{
			returnMessage = ErrorMessage.USER_NOT_LOGGED_IN;
		} else
		{
			if (!url.startsWith("http://"))
			{
				url = "http://" + url;
			}
			UrlValidator urlValidator = new UrlValidator();
			if (urlValidator.isValid(url))
			{
				try
				{
					link = new URL(url);

					String host = "http://" + link.getHost();

					try
					{

						RatingDB tempRating = pm.getObjectById(
								RatingDB.class,
								KeyFactory.createKey(
										RatingDB.class.getSimpleName(),
										user.getEmail() + "_" + url));

						if (!canReplace)
						{
							returnMessage = ErrorMessage.RATE_EXISTS;
						}
						// replace the rate
						else
						{
							updateTops(url, host, rating, tempRating.getRating(), false);
							tempRating.setRating(rating);
							tempRating.setComments(new Text(comment));
							try
							{

								pm.makePersistent(tempRating);
								returnMessage = ErrorMessage.RATE_SUCCESS;
							} finally
							{
								pm.close();
							}

						}
					} catch (JDOObjectNotFoundException e)
					{

						// add new entity in DB
						RatingDB newRating = new RatingDB(user.getEmail(), url,
								host, new Text(comment), rating);

						try
						{
							pm.makePersistent(newRating);
							returnMessage = ErrorMessage.RATE_SUCCESS;
						} finally
						{
							updateTops(url, host, rating, 0, true);
							pm.close();
						}
					}
				} catch (MalformedURLException e1)
				{
					returnMessage = ErrorMessage.URL_NOT_CORRECT;
				}

			} else
			{
				returnMessage = ErrorMessage.URL_NOT_CORRECT;

			}

		}

		return new Integer(returnMessage);
	}
	
		/**
	 * Puts a DB entry without checking url and user
	 * 
	 * @param url
	 * @param comment
	 * @param rating
	 * @param userEmail
	 * @return result of putting entry to DB
	 * @throws IOException
	 */
	public static int rateUrl(String url, String comment, float rating,
			String userEmail) throws IOException {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		int returnMessage = 0;

		try
		{

			RatingDB tempRating = pm.getObjectById(
					RatingDB.class,
					KeyFactory.createKey(
							RatingDB.class.getSimpleName(),
							userEmail + "_" + url));

					tempRating.setRating(rating);
					tempRating.setComments(new Text(comment));
					try {

						pm.makePersistent(tempRating);
						updateTops(url, url, rating, tempRating.getRating(), false);
						returnMessage = ErrorMessage.RATE_SUCCESS;
					} finally {
						pm.close();
					}
		}
		catch(JDOObjectNotFoundException e)
		{

				// add new entity in DB
				RatingDB newRating = new RatingDB(userEmail, url, url,
						new Text(comment), rating);
				try {
					pm.makePersistent(newRating);
					updateTops(url, url, rating, 0, true);
					returnMessage = ErrorMessage.RATE_SUCCESS;
				} finally {
					pm.close();
				}

		}
		
		return new Integer(returnMessage);
	}
	
	
	private static void updateTops(String url, String domain, float rating, float oldRating, boolean isNew)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		TopURLDayDB tempUrlDay;
		TopURLMonthDB tempUrlMonth;
		TopURLYearDB tempUrlYear;
		TopDomainDayDB tempDomainDay;
		TopDomainMonthDB tempDominMonth;
		TopDomainYearDB tempDomainYear;
		
// Add url to top of day
		try
		{
			tempUrlDay = pm.getObjectById(TopURLDayDB.class, KeyFactory.createKey(
					TopURLDayDB.class.getSimpleName(), url));
			if (isNew)
			{
				float ratingValue = tempUrlDay.getAveradgeRating();
				float newRating = (ratingValue
						* ((float) tempUrlDay.getCountOfRatings()) + rating)
						/ ((float) (tempUrlDay.getCountOfRatings() + 1));

				tempUrlDay.setAveradgeRating(newRating);
				tempUrlDay.setCountOfRatings(tempUrlDay.getCountOfRatings() + 1);
			} else
			{
				float newRating = (tempUrlDay.getAveradgeRating()
						* (float) tempUrlDay.getCountOfRatings() - (float) oldRating + (float) rating)
						/ (float) (tempUrlDay.getCountOfRatings());
				tempUrlDay.setAveradgeRating(newRating);

			}
			pm.makePersistent(tempUrlDay);

		} catch (JDOObjectNotFoundException e)
		{
			tempUrlDay = new TopURLDayDB(url, rating, 1);
			pm.makePersistent(tempUrlDay);
	
		}
		
// Add url to top of month
		try
		{
			tempUrlMonth = pm.getObjectById(TopURLMonthDB.class, KeyFactory.createKey(
					TopURLMonthDB.class.getSimpleName(), url));
			if (isNew)
			{
				float ratingValue = tempUrlMonth.getAveradgeRating();
				float newRating = (ratingValue
						* ((float) tempUrlMonth.getCountOfRatings()) + rating)
						/ ((float) (tempUrlMonth.getCountOfRatings() + 1));

				tempUrlMonth.setAveradgeRating(newRating);
				tempUrlMonth.setCountOfRatings(tempUrlMonth.getCountOfRatings() + 1);
			} else
			{
				float newRating = (tempUrlMonth.getAveradgeRating()
						* (float) tempUrlMonth.getCountOfRatings() - (float) oldRating + (float) rating)
						/ (float) (tempUrlMonth.getCountOfRatings());
				tempUrlMonth.setAveradgeRating(newRating);

			}
		
			pm.makePersistent(tempUrlMonth);
	

		} catch (JDOObjectNotFoundException e)
		{
			tempUrlMonth = new TopURLMonthDB(url, rating, 1);
			pm.makePersistent(tempUrlMonth);
		}
// Add url to top of year
		try
		{
			tempUrlYear = pm.getObjectById(TopURLYearDB.class, KeyFactory.createKey(
					TopURLYearDB.class.getSimpleName(), url));
			if (isNew)
			{
				float ratingValue = tempUrlYear.getAveradgeRating();
				float newRating = (ratingValue
						* ((float) tempUrlYear.getCountOfRatings()) + rating)
						/ ((float) (tempUrlYear.getCountOfRatings() + 1));

				tempUrlYear.setAveradgeRating(newRating);
				tempUrlYear.setCountOfRatings(tempUrlYear.getCountOfRatings() + 1);
			} else
			{
				float newRating = (tempUrlYear.getAveradgeRating()
						* (float) tempUrlYear.getCountOfRatings() - (float) oldRating + (float) rating)
						/ (float) (tempUrlYear.getCountOfRatings());
				tempUrlYear.setAveradgeRating(newRating);

			}
			pm.makePersistent(tempUrlYear);


		} catch (JDOObjectNotFoundException e)
		{
			tempUrlYear = new TopURLYearDB(url, rating, 1);
			pm.makePersistent(tempUrlYear);
		}
		
		  
		if (url.equals(domain))
		{
// Add domain to top of day
			try
			{
				tempDomainDay = pm.getObjectById(TopDomainDayDB.class, KeyFactory.createKey(
						TopDomainDayDB.class.getSimpleName(), url));
				if (isNew)
				{
					float ratingValue = tempDomainDay.getAveradgeRating();
					float newRating = (ratingValue
							* ((float) tempDomainDay.getCountOfRatings()) + rating)
							/ ((float) (tempDomainDay.getCountOfRatings() + 1));

					tempDomainDay.setAveradgeRating(newRating);
					tempDomainDay.setCountOfRatings(tempDomainDay.getCountOfRatings() + 1);
				} else
				{
					float newRating = (tempDomainDay.getAveradgeRating()
							* (float) tempDomainDay.getCountOfRatings() - (float) oldRating + (float) rating)
							/ (float) (tempDomainDay.getCountOfRatings());
					tempDomainDay.setAveradgeRating(newRating);

				}
				pm.makePersistent(tempDomainDay);

			} catch (JDOObjectNotFoundException e)
			{
				tempDomainDay = new TopDomainDayDB(url, rating, 1);
				pm.makePersistent(tempDomainDay);
		
			}
			
// Add domain to top of month
			try
			{
				tempDominMonth = pm.getObjectById(TopDomainMonthDB.class, KeyFactory.createKey(
						TopDomainMonthDB.class.getSimpleName(), url));
				if (isNew)
				{
					float ratingValue = tempDominMonth.getAveradgeRating();
					float newRating = (ratingValue
							* ((float) tempDominMonth.getCountOfRatings()) + rating)
							/ ((float) (tempDominMonth.getCountOfRatings() + 1));

					tempDominMonth.setAveradgeRating(newRating);
					tempDominMonth.setCountOfRatings(tempDominMonth.getCountOfRatings() + 1);
				} else
				{
					float newRating = (tempDominMonth.getAveradgeRating()
							* (float) tempDominMonth.getCountOfRatings() - (float) oldRating + (float) rating)
							/ (float) (tempDominMonth.getCountOfRatings());
					tempDominMonth.setAveradgeRating(newRating);

				}
			
				pm.makePersistent(tempDominMonth);
		

			} catch (JDOObjectNotFoundException e)
			{
				tempDominMonth = new TopDomainMonthDB(url, rating, 1);
				pm.makePersistent(tempDominMonth);
			}
// Add domain to top of year
			try
			{
				tempDomainYear = pm.getObjectById(TopDomainYearDB.class, KeyFactory.createKey(
						TopDomainYearDB.class.getSimpleName(), url));
				if (isNew)
				{
					float ratingValue = tempDomainYear.getAveradgeRating();
					float newRating = (ratingValue
							* ((float) tempDomainYear.getCountOfRatings()) + rating)
							/ ((float) (tempDomainYear.getCountOfRatings() + 1));

					tempDomainYear.setAveradgeRating(newRating);
					tempDomainYear.setCountOfRatings(tempDomainYear.getCountOfRatings() + 1);
				} else
				{
					float newRating = (tempDomainYear.getAveradgeRating()
							* (float) tempDomainYear.getCountOfRatings() - (float) oldRating + (float) rating)
							/ (float) (tempDomainYear.getCountOfRatings());
					tempDomainYear.setAveradgeRating(newRating);

				}
				pm.makePersistent(tempDomainYear);


			} catch (JDOObjectNotFoundException e)
			{
				tempDomainYear = new TopDomainYearDB(url, rating, 1);
				pm.makePersistent(tempDomainYear);
			}
		}
		pm.close();
	}
}
