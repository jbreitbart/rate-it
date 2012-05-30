package it.rate.server;

import it.rate.client.RateItService;
import it.rate.client.Rating;
import it.rate.client.TopUrl;
import it.rate.data.RatingDB;
import it.rate.util.ErrorMessage;
import it.rate.util.MemCache;
import it.rate.util.PMF;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;

import org.apache.commons.validator.routines.UrlValidator;

import com.google.appengine.api.datastore.Text;
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
							RatingDB newRating = new RatingDB(user.getEmail(),
									url, host, new Text(comment), rating);

							try
							{
								pm.makePersistent(newRating);
								returnMessage = ErrorMessage.RATE_SUCCESS;
							} finally
							{
								pm.close();
							}
						} else
						{
							if (!canReplace)
							{
								returnMessage = ErrorMessage.RATE_EXISTS;
							}
							// replace the rate
							else
							{
								RatingDB ratingToReplace = pm.getObjectById(
										RatingDB.class, result.get(0).getKey());
								ratingToReplace.setRating(rating);
								ratingToReplace.setComments(new Text(comment));
								try
								{

									pm.makePersistent(ratingToReplace);
									returnMessage = ErrorMessage.RATE_SUCCESS;
								} finally
								{
									pm.close();
								}

							}
						}

					} finally
					{
						query.closeAll();
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
		List<Rating> result = null;
		List<Rating> returnResult = new LinkedList<Rating>();
		Query query = pm.newQuery(RatingDB.class, ("this.host == url"));
		query.declareParameters("String url");

		try
		{
			result = (List<Rating>) query.execute(host);
		} finally
		{
			query.closeAll();
		}

		for (Rating r : result)
		{
			returnResult.add(new Rating(r.getUserEmail(), r.getUrl(), r
					.getHost(), r.getComment(), r.getRating()));
		}

		return returnResult;

	}

	@Override
	public List<TopUrl> getTopUrlsForPeriod(Date startDate, Date endDate,
			int countOfUrls)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<RatingDB> result = null;
		Map<String, TopUrl> tempUrls = new HashMap<String, TopUrl>();
		List<TopUrl> topUrls = new ArrayList<TopUrl>();
		List<TopUrl> tempTopUrls = new ArrayList<TopUrl>();

		Query query = pm.newQuery(RatingDB.class,
				"this.date >= startDateParam && this.date <= endDateParam");
		query.declareParameters("java.util.Date startDateParam, java.util.Date endDateParam");

		try
		{
			// get all ratings of url
			result = (List<RatingDB>) query.execute(startDate, endDate);
		} finally
		{

			query.closeAll();
		}

		// format start and end date to a key for cache (it looks like
		// "dd.mm.yyy-dd.mm.yyyy-top_urls")
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM,
				Locale.GERMANY);
		String cacheKey = df.format(startDate) + "-" + df.format(endDate)
				+ "-top_urls";
		System.out.println(cacheKey);

		Cache cache = MemCache.getInstance().getCache();

		// if an entity for this time is not existing in cache, create one
		if (cache.get(cacheKey) == null)
		{
			for (RatingDB rating : result)
			{
				if (tempUrls.containsKey(rating.getUrl()))
				{
					String url = rating.getUrl();
					float ratingValue = tempUrls.get(url).getAveradgeRating();
					int countOfRating = tempUrls.get(url).getCountOfRatings();
					float newRating = (ratingValue * countOfRating + rating
							.getRating()) / (countOfRating + 1);
					tempUrls.put(url, new TopUrl(url, newRating,
							countOfRating + 1));

				} else
				{
					tempUrls.put(rating.getUrl(), new TopUrl(rating.getUrl(),
							rating.getRating(), 1));
				}
			}
			cache.put(cacheKey, new ArrayList<TopUrl>(tempUrls.values()));

		}
		tempTopUrls = (List<TopUrl>) cache.get(cacheKey);
		
		Collections.sort(tempTopUrls);
		System.out.println("top url" + topUrls);
		int i = 0;
		for (TopUrl temp : tempTopUrls)
		{
			if (i > countOfUrls)
			{
				break;
			}
			topUrls.add(temp);
			i++;
			System.out.println("hallo " + temp.getUrl());
		}
		return topUrls;
	}

	@Override
	public List<TopUrl> getTopHostsForPeriod(Date startDate, Date endDate,
			int countOfUrls)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<RatingDB> result = null;
		Map<String, TopUrl> tempHosts = new HashMap<String, TopUrl>();
		List<TopUrl> topHosts = new ArrayList<TopUrl>();
		List<TopUrl> tempTopHosts = new ArrayList<TopUrl>();

		Query query = pm.newQuery(RatingDB.class,
				"this.date >= startDateParam && this.date <= endDateParam");
		query.declareParameters("java.util.Date startDateParam, java.util.Date endDateParam");

		try
		{
			// get all ratings of url
			result = (List<RatingDB>) query.execute(startDate, endDate);
		} finally
		{

			query.closeAll();
		}

		// format start and end date to a key for cache (it looks like
		// "dd.mm.yyy-dd.mm.yyyy-top_urls")
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM,
				Locale.GERMANY);
		String cacheKey = df.format(startDate) + "-" + df.format(endDate)
				+ "-top_hosts";
		System.out.println(cacheKey);

		Cache cache = MemCache.getInstance().getCache();

		// if an entity for this time is not existing in cache, create one
		if (cache.get(cacheKey) == null)
		{
			for (RatingDB rating : result)
			{
				if (tempHosts.containsKey(rating.getHost()))
				{
					String host = rating.getHost();
					float ratingValue = tempHosts.get(host).getAveradgeRating();
					int countOfRating = tempHosts.get(host).getCountOfRatings();
					float newRating = (ratingValue * countOfRating + rating
							.getRating()) / (countOfRating + 1);
					tempHosts.put(host, new TopUrl(host, newRating,
							countOfRating + 1));

				} else
				{
					tempHosts.put(rating.getHost(), new TopUrl(
							rating.getHost(), rating.getRating(), 1));
				}
			}
			cache.put(cacheKey, new ArrayList<TopUrl>(tempHosts.values()));

		}
		tempTopHosts = (List<TopUrl>) cache.get(cacheKey);

		Collections.sort(tempTopHosts);
		System.out.println("top host" + topHosts);
		int i = 0;
		for (TopUrl temp : tempTopHosts)
		{
			if (i > countOfUrls)
			{
				break;
			}
			topHosts.add(temp);
			i++;
			System.out.println("hallo " + temp.getUrl());
		}

		return topHosts;
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
