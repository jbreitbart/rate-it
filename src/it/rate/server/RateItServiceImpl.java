package it.rate.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import it.rate.client.RateItService;
import it.rate.data.Rating;
import it.rate.util.PMF;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RateItServiceImpl extends RemoteServiceServlet implements
		RateItService
{

	@Override
	public void rateUrl(String user, String url, String comment, float rating)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Query query = pm.newQuery(Rating.class, "(user == " + user
				+ " ) && ( url == " + url + " )");

		try
		{
			List<Rating> result = (List<Rating>) query.execute();
			// if DB doesn't contain a rating-object then
			// add this Rating-object to DataStore
			if (result.isEmpty())
			{
				try
				{
					pm.makePersistent(new Rating(user, url, comment, rating));
				} finally
				{
					pm.close();
				}

			}

		} finally
		{
			query.closeAll();
		}

	}

	@Override
	public List<Rating> getSubDomains(String url)
	{

		/*
		 * Testdata START
		 */
		List<Rating> subDomains = new ArrayList<Rating>();
		subDomains.add(new Rating("test", "test.com", "test comment",
				(float) 5.0));
		subDomains.add(new Rating("test2", "test.com", "test2 comment",
				(float) 5.0));
		/*
		 * Testdata END
		 */
		return subDomains;
	}

	@Override
	public List<Rating> getTopUrlsForPeriod(String startDate, String endDate)
	{
		/*
		 * Testdata START
		 */
		List<Rating> topUrls = new ArrayList<Rating>();
		topUrls.add(new Rating("test", "test.com", "test comment", (float) 5.0));
		topUrls.add(new Rating("test2", "test.com", "test2 comment",
				(float) 5.0));
		/*
		 * Testdata END
		 */
		return topUrls;
	}

	@Override
	public float getAverageRatingForPeriod(String url, String startDate,
			String endDate)
	{

		return (float) 5.0;
	}

	@Override
	public List<Rating> getAllUserRatedUrls()
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> allUrls = null;

		Query query = pm.newQuery(Rating.class);

		try
		{
			// get all rated urls of all users
			allUrls = (List<Rating>) query.execute();

		} finally
		{
			query.closeAll();
		}

		return allUrls;

	}

	@Override
	public float getUsersUrlRating(String user, String url)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> userRatedUrl = null;
		float userRating = 0;

		Query query = pm.newQuery(Rating.class, "(user == " + user
				+ ") && (url == " + url + ")");

		try
		{
			// get user rating of url
			userRatedUrl = (List<Rating>) query.execute();

			if ((!userRatedUrl.isEmpty()) && (userRatedUrl.size() == 1))
			{
				userRating = userRatedUrl.get(0).getRating();
			}

		} finally
		{
			query.closeAll();
		}
		return userRating;
	}

}
