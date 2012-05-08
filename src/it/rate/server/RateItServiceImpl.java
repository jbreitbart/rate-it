package it.rate.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import it.rate.client.RateItService;
import it.rate.client.Rating;
import it.rate.client.TopUrl;
import it.rate.data.RatingDB;
import it.rate.util.PMF;

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
	public boolean rateUrl(String url, String comment, float rating)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		User user = UserServiceFactory.getUserService().getCurrentUser();
		URL link;
		boolean isAddSucces = false;
		if (user != null)
		{

			if (!url.startsWith("http://"))
			{
				url = "http://" + url;
			}

			try
			{
				link = new URL(url);
				String host = link.getHost();

				Query query = pm
						.newQuery(
								RatingDB.class,
								("this.userEmail == userEmailParam && this.url == urlParam"));
				query.declareParameters("String userEmailParam, String urlParam");
				try
				{
					List<RatingDB> result = (List<RatingDB>) query.execute(
							user.getEmail(), url);

					if (result.isEmpty())
					{
						RatingDB newRating = new RatingDB(user.getEmail(), url,
								host, comment, rating);
						try
						{
							pm.makePersistent(newRating);
							isAddSucces = true;
						} finally
						{
							pm.close();
						}
					}
				} finally
				{
					// query.closeAll();
				}

			} catch (MalformedURLException e)
			{
				isAddSucces = false;
			}
		}

		return isAddSucces;
	}

	@Override
	public List<Rating> getSubDomains(String host)
	{

		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> result = null;
		Query query = pm.newQuery(RatingDB.class, ("this.host == url"));
		query.declareParameters("String url");

		try
		{
			result = (List<Rating>) query.execute(host);
		} finally
		{
			query.closeAll();
		}

		// for (Rating r: result)
		// {
		// System.out.println("host: " + r.getHost() + " subdomain: " +
		// r.getUrl());
		// }

		return result;

	}

	@Override
	public List<TopUrl> getTopUrlsForPeriod(Date startDate, Date endDate,
			int countOfUrls)
	{

		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> result = null;
		HashSet<String> selectedUrls = new HashSet<String>();
		List<TopUrl> topUrls = new LinkedList<TopUrl>();
		List<TopUrl> topNUrls = new LinkedList<TopUrl>();

		Query query = pm.newQuery(RatingDB.class);

		try
		{
			// get all ratings of url
			result = (List<Rating>) query.execute();

		} finally
		{
			query.closeAll();
		}

		for (Rating r : result)
		{
			// select entries from certain period (>= startDate && <= endDate)
			if ((r.getDate().compareTo(startDate) == 0)
					|| (r.getDate().compareTo(endDate) == 0)
					|| ((r.getDate().compareTo(startDate) > 0) && (r.getDate()
							.compareTo(endDate) < 0)))
			{
				if (!selectedUrls.contains(r.getUrl()))
				{
					selectedUrls.add(r.getUrl());
				}
			}
		}

		// calculate the average values for each entry
		for (String tempRatedUrl : selectedUrls)
		{
			float averageValue = 0;
			query = pm.newQuery(RatingDB.class, "this.url == urlParam");
			query.declareParameters("String urlParam");

			try
			{
				result = (List<Rating>) query.execute(tempRatedUrl);
			} finally
			{
				query.closeAll();
			}

			for (Rating rat : result)
			{
				averageValue = averageValue + rat.getRating();
			}

			averageValue = averageValue / result.size();
			topUrls.add(new TopUrl(tempRatedUrl, averageValue));
		}
		Collections.sort(topUrls);
		int i = 0;
		for (TopUrl topUrl : topUrls)
		{
			if (i < countOfUrls)
			{
				topNUrls.add(topUrl);
				i++;
			} else
			{
				break;
			}
		}

//		for (TopUrl n : topNUrls)
//		{
//			System.out.println(n.getUrl() + " : " + n.getAveradgeRating());
//		}
		return topNUrls;
	}

	@Override
	public float getAverageRatingForPeriod(String url, Date startDate,
			Date endDate)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> result = null;

		float advRating = 0;

		Query query = pm.newQuery(RatingDB.class, "url == urlParam");
		query.declareParameters("String urlParam");
		try
		{
			// get all ratings of url
			result = (List<Rating>) query.execute(url);

		} finally
		{
			query.closeAll();
		}

		for (Rating r : result)
		{
			// select entries from certain period (>= startDate && <= endDate)
			if ((r.getDate().compareTo(startDate) == 0)
					|| (r.getDate().compareTo(endDate) == 0)
					|| ((r.getDate().after(startDate)) && (r.getDate()
							.before(endDate))))
			{
				advRating = advRating + r.getRating();
			}
		}

		if (result.size() != 0)
		{
			advRating = advRating / result.size();
		}

		// System.out.println("++start : " + startDate.toString() + " end : "
		// +endDate.toString());
		// for(Rating r : result)
		// {
		// System.out.println("start : " + r.getDate().toString());
		// }
		// System.out.println("url: " + url + " ADVRating: " + advRating);

		return advRating;
	}

	@Override
	public List<Rating> getAllUserRatedUrls()
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> allUrls = null;

		Query query = pm.newQuery(RatingDB.class);

		try
		{
			// get all rated urls of all users
			allUrls = (List<Rating>) query.execute();

		} finally
		{
			query.closeAll();
		}

//		for (Rating r : allUrls)
//		{
//			System.out.println("user : " + r.getUserEmail() + " url : "
//					+ r.getUrl() + " rating : " + r.getRating());
//		}

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
		List<RatingDB> userRatedUrl = null;
		float userRating = 0;

		Query query = pm.newQuery(RatingDB.class,
				"(this.userEmail == userParam && this.url == urlParam)");
		query.declareParameters("String userParam, String urlParam");

		try
		{
			// get user rating of url
			userRatedUrl = (List<RatingDB>) query.execute(user, url);

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

	@Override
	public String getLoginURL(String destinationURL)
	{
//		System.out.println(UserServiceFactory.getUserService().createLoginURL(
//				destinationURL));
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

}
