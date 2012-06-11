package it.rate.server;

import it.rate.client.TopUrl;
import it.rate.data.RatingDB;
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
	
	
	public static void calculateAllTops()
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Query queryTops = pm.newQuery(TopUrlDB.class);
		List<TopUrlDB> resultTops = (List<TopUrlDB>) queryTops.execute();
		
		//clear TopUrlDB
		if(!resultTops.isEmpty())
		{
			for(TopUrlDB temp : resultTops)
			{
				pm.deletePersistent(temp);
			}
		}
		
		Query query = pm
				.newQuery(RatingDB.class);

		List<RatingDB> result = (List<RatingDB>) query.execute();
		if(!result.isEmpty())
		{
			for(RatingDB tempRating : result)
			{
				calculateTops(tempRating);
			}
		}
	}

	public static void calculateTops(RatingDB rating)
	{
        String url = rating.getUrl();
		if (rating.getUrl().startsWith("http://"))
		{
			url = url.substring("http://".length());
		}
		if (url.equals(rating.getHost()))
		{
			if ((rating.getDate().getYear() == new Date().getYear())
					&&(rating.getDate().getMonth() == new Date().getMonth()) 
						&& (rating.getDate().getDate() == new Date().getDate()))
			{
				calculate(rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_DAY);
			}
			if ((rating.getDate().getYear() == new Date().getYear())
					&&(rating.getDate().getMonth() == new Date().getMonth()))
			{
				calculate(rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_MONTH);
			}
			if (rating.getDate().getYear() == new Date().getYear())
			{
				calculate(rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_YEAR);
			}
			
		}
		//calculate tops only for current period
		if ((rating.getDate().getYear() == new Date().getYear())
				&&(rating.getDate().getMonth() == new Date().getMonth()) 
					&& (rating.getDate().getDate() == new Date().getDate()))
		{
			calculate(rating, TopUrl.TYPE_URL, TopUrl.PERIOD_DAY);
		}
		if ((rating.getDate().getYear() == new Date().getYear())
				&&(rating.getDate().getMonth() == new Date().getMonth()))
		{
			calculate(rating, TopUrl.TYPE_URL, TopUrl.PERIOD_MONTH);
		}
		if (rating.getDate().getYear() == new Date().getYear())
		{
			calculate(rating, TopUrl.TYPE_URL, TopUrl.PERIOD_YEAR);
		}
		
	}

	private static void calculate(RatingDB rating, String linkType,
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
				linkType, period, rating.getUrl());

		if (result.isEmpty())
		{
			try
			{
				pm.makePersistent(new TopUrlDB(rating.getUrl(), rating.getRating(), 1, period, linkType));
			} finally
			{
				pm.close();
			}
		} else
		{
			TopUrlDB topToReplace = pm.getObjectById(TopUrlDB.class, result
					.get(0).getKey());

			float ratingValue = result.get(0).getAveradgeRating();
			float newRating = (ratingValue
					* ((float) result.get(0).getCountOfRatings()) + rating
						.getRating())
					/ ((float) (result.get(0).getCountOfRatings() + 1));
			topToReplace.setAveradgeRating(newRating);
			topToReplace
					.setCountOfRatings(result.get(0).getCountOfRatings() + 1);
			try
			{
				pm.makePersistent(topToReplace);
			} finally
			{
				pm.close();
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
