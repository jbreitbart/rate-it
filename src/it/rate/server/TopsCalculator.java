package it.rate.server;

import it.rate.client.TopUrl;
import it.rate.data.TopUrlDB;
import it.rate.util.MemCache;
import it.rate.util.PMF;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;

public class TopsCalculator
{

	public static List<TopUrl> getTopUrlsForDay(int countOfUrls)
	{
		return getTops(MemCache.CACHE_KEY_URL_DAY, TopUrl.PERIOD_DAY,
				TopUrl.TYPE_URL, countOfUrls);
	}

	public static List<TopUrl> getTopUrlsForMonth(int countOfUrls)
	{
		return getTops(MemCache.CACHE_KEY_URL_MONTH, TopUrl.PERIOD_MONTH,
				TopUrl.TYPE_URL, countOfUrls);
	}

	public static List<TopUrl> getTopUrlsForYear(int countOfUrls)
	{
		return getTops(MemCache.CACHE_KEY_URL_YEAR, TopUrl.PERIOD_YEAR,
				TopUrl.TYPE_URL, countOfUrls);
	}

	public static List<TopUrl> getTopHostsForDay(int countOfUrls)
	{
		return getTops(MemCache.CACHE_KEY_HOST_DAY, TopUrl.PERIOD_DAY,
				TopUrl.TYPE_DOMAIN, countOfUrls);
	}

	public static List<TopUrl> getTopHostsForMonth(int countOfUrls)
	{
		return getTops(MemCache.CACHE_KEY_HOST_MONTH, TopUrl.PERIOD_MONTH,
				TopUrl.TYPE_DOMAIN, countOfUrls);
	}

	public static List<TopUrl> getTopHostsForYear(int countOfUrls)
	{
		return getTops(MemCache.CACHE_KEY_HOST_YEAR, TopUrl.PERIOD_YEAR,
				TopUrl.TYPE_DOMAIN, countOfUrls);
	}

	public static void calculateTops(String url, float rating)
	{

		URL host;
		try
		{
			host = new URL(url);
			if (url.startsWith("http://"))
			{
				url = url.substring("http://".length());
			}
			if (url.equals(host.getHost()))
			{
				calculate(url, rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_DAY);
				calculate(url, rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_MONTH);
				calculate(url, rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_YEAR);
			}
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calculate(url, rating, TopUrl.TYPE_URL, TopUrl.PERIOD_DAY);
		calculate(url, rating, TopUrl.TYPE_URL, TopUrl.PERIOD_MONTH);
		calculate(url, rating, TopUrl.TYPE_URL, TopUrl.PERIOD_YEAR);

	}

	private static void calculate(String url, float rating, String linkType,
			String period)
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Query topUrlDayQuery = pm
				.newQuery(
						TopUrlDB.class,
						("this.type == typeParam && this.period == periodParam && this.url == urlParam"));
		topUrlDayQuery
				.declareParameters("String typeParam, String periodParam, String urlParam");

		List<TopUrlDB> result = (List<TopUrlDB>) topUrlDayQuery.execute(
				linkType, period, url);

		if (result.isEmpty())
		{
			// create new entity if it does not exist or if it is not current
			// date
			try
			{
				pm.makePersistent(new TopUrlDB(url, rating, 1, period, linkType));
			} finally
			{
				pm.close();
			}
		} else
		{
			if (result.get(0).getDate().getDate() == new Date().getDate())
			{
				TopUrlDB topToReplace = pm.getObjectById(TopUrlDB.class, result
						.get(0).getKey());

				topToReplace.setCountOfRatings(result.get(0)
						.getCountOfRatings() + 1);

				float ratingValue = result.get(0).getAveradgeRating();
				float newRating = (ratingValue
						* ((float) result.get(0).getCountOfRatings()) + rating)
						/ ((float) (result.get(0).getCountOfRatings() + 1));
				topToReplace.setAveradgeRating(newRating);

				try
				{
					pm.makePersistent(topToReplace);
				} finally
				{
					pm.close();
				}
			} else
			{
				TopUrlDB topToReplace = pm.getObjectById(TopUrlDB.class, result
						.get(0).getKey());
				topToReplace.setAveradgeRating(rating);
				topToReplace.setCountOfRatings(1);
				topToReplace.setDate(new Date());
				try
				{
					pm.makePersistent(topToReplace);
				} finally
				{
					pm.close();
				}
			}
		}
	}

	private static List<TopUrl> getTops(String cacheKey, String period,
			String type, int countOfUrls)
	{

		List<TopUrl> result = new ArrayList<TopUrl>();
		List<TopUrlDB> temp = new ArrayList<TopUrlDB>();
		Cache cache = MemCache.getInstance().getCache();

		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Query query = pm.newQuery(TopUrlDB.class,
				"this.period == periodParam && this.type == typeParam");
		query.declareParameters("String periodParam, String typeParam");

		// if an entity for this time is not existing in cache, create one
		if (cache.get(cacheKey) == null)
		{

			try
			{
				// get top urls of day
				temp = (List<TopUrlDB>) query.execute(period, type);
				Collections.sort(temp);
				int i = 0;
				for (TopUrlDB tempTopUrl : temp)
				{
					if (i < countOfUrls)
					{
						result.add(new TopUrl(tempTopUrl.getUrl(), tempTopUrl
								.getAveradgeRating(), tempTopUrl
								.getCountOfRatings()));

					} else
					{
						break;
					}
					i++;
				}
				cache.put(cacheKey, result);
			} finally
			{

				query.closeAll();
			}
		} else
		{
			result = (List<TopUrl>) cache.get(cacheKey);
		}
		return result;
	}
}
