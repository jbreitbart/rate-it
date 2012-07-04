package it.rate.server;

import it.rate.client.RateItService;
import it.rate.client.Rating;
import it.rate.client.TopUrl;
import it.rate.data.RatingDB;
import it.rate.util.ErrorMessage;
import it.rate.util.MemCache;
import it.rate.util.PMF;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RateItServiceImpl extends RemoteServiceServlet implements
		RateItService
{

	@Override
	public int rateUrl(String url, String comment, float rating,
			boolean canReplace)
	{
		int result = ErrorMessage.RATE_SUCCESS;
		try {

			result = RateTask.rateUrl(url, comment, rating, canReplace);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int ratingTest(String mail, String url, String comment, float rating)
	{
		int result = ErrorMessage.RATE_SUCCESS;
		try {
			result = RateTask.rateUrl(url, comment, rating, mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Rating> getSubDomains(String host)
	{
		if (host.startsWith("http://"))
		{
			host = host.substring(7);
			if (host.indexOf("/") != -1)
			{
				host = host.substring(0, host.indexOf("/"));
			}
		}
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<RatingDB> result = null;
		List<Rating> returnResult = new LinkedList<Rating>();
		Query query = pm.newQuery(RatingDB.class, ("this.host == url"));
		query.declareParameters("String url");

		try
		{
			result = (List<RatingDB>) query.execute(host);
		} finally
		{
			query.closeAll();
		}

		for (RatingDB r : result)
		{
			returnResult.add(new Rating(r.getUserEmail(), r.getUrl(), r
					.getHost(), r.getComment().getValue(), r.getRating()));
		}

		return returnResult;

	}



	@Override
	public List<Rating> getAllUserRatedUrls()
	{

		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> allUrls = new ArrayList<Rating>();
		List<RatingDB> result = null;
		Query query = pm.newQuery(RatingDB.class,
				"this.userEmail == userEmailParam");
		query.declareParameters("String userEmailParam");

		User user = UserServiceFactory.getUserService().getCurrentUser();

		if (user != null)
		{
			try
			{
				// get all rated urls of all users
				result = (List<RatingDB>) query.execute(user.getEmail());

			} finally
			{
				query.closeAll();
			}

			for (RatingDB r : result)
			{
				allUrls.add(new Rating(r.getUserEmail(), r.getUrl(), r
						.getHost(), r.getComment().getValue(), r.getRating()));
			}
		}
		return allUrls;

	}

	/**
	 * Gets the rating from a user for a certain URL
	 * 
	 * @param url
	 *            The URL
	 * @return URL's rating as float or 0 if not found
	 */
	@Override
	public float getUsersUrlRating(String user, String url)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		float userRating = 0;
		try
		{
			RatingDB tempRating = pm.getObjectById(
					RatingDB.class,
					KeyFactory.createKey(
							RatingDB.class.getSimpleName(),
							user + "_" + url));
			userRating = tempRating.getRating();
		}
		catch(JDOObjectNotFoundException e)
		{
			userRating = -1;
		}
		return userRating;
	}

	@Override
	public String getLoginURL(String destinationURL)
	{
		return UserServiceFactory.getUserService().createLoginURL(
				destinationURL);
	}

	@Override
	public String getLogoutURL(String destinationURL)
	{

		return UserServiceFactory.getUserService().createLogoutURL(
				destinationURL);
	}

	@Override
	public boolean isUserLoggedIn()
	{
		return UserServiceFactory.getUserService().isUserLoggedIn();
	}

	@Override
	public String getCurrentUserEmail()
	{

		if (UserServiceFactory.getUserService().getCurrentUser() != null)
		{
			return UserServiceFactory.getUserService().getCurrentUser()
					.getEmail();

		} else
		{
			return null;
		}

	}

	@Override
	public int isCurUserAdmin()
	{
		int result;
		try
		{
			if (UserServiceFactory.getUserService().isUserAdmin() == true)
			{
				result = 0;
			}
			else
			{
				result = -1;
			}
		} catch (IllegalStateException e)
		{
			result = -2;
		}
		return result;
	}

	@Override
	public void clearServerCache()
	{
		MemCache.getInstance().getCache().clear();
	}

	
	@Override
	public List<TopUrl> getTopUrlsForDay()
	{
		return TopsCalculator.getTopUrlsForDay();		
	}

	@Override
	public List<TopUrl> getTopUrlsForMonth()
	{
		return TopsCalculator.getTopUrlsForMonth();
	}

	@Override
	public List<TopUrl> getTopUrlsForYear()
	{
		return TopsCalculator.getTopUrlsForYear();
	}

	@Override
	public List<TopUrl> getTopHostsForDay()
	{
		return TopsCalculator.getTopHostsForDay();
	}

	@Override
	public List<TopUrl> getTopHostsForMonth()
	{
		return TopsCalculator.getTopHostsForMonth();
	}

	@Override
	public List<TopUrl> getTopHostsForYear()
	{
		return TopsCalculator.getTopHostsForYear();
	}

	@Override
	public void recalculateTops()
	{
		TopsCalculator.calculateAllTops();
		
	}
	
	@Override
	public void clearDB(){
		ClearDB.clearDB();
	}

}
