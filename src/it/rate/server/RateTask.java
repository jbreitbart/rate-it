package it.rate.server;

import it.rate.data.RatingDB;
import it.rate.util.ErrorMessage;
import it.rate.util.PMF;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import org.apache.commons.validator.routines.UrlValidator;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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

					String host = link.getHost();

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
					returnMessage = ErrorMessage.RATE_SUCCESS;
				} finally {
					pm.close();
				}

		}
		
		return new Integer(returnMessage);
	}
	
	//private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	public static int lowLevelRate(String url, String comment, float rating,
			String userEmail){
		try{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Entity user = new Entity("Rating");
			user.setProperty("url", url);
			user.setProperty("userEmail", userEmail);
			user.setProperty("rating", rating);
			user.setProperty("comment", new Text(comment));
			datastore.put(user);
		} catch (Exception e){
			e.printStackTrace();
			return ErrorMessage.DB_ERROR;
		}
		return ErrorMessage.RATE_SUCCESS;
	}
}
