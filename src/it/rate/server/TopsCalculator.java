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
import java.util.HashSet;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;

public class TopsCalculator
{

	private static HashSet<TopUrlDB> topUrls;
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
			pm.deletePersistentAll(resultTops);
		}
		
		topUrls = new HashSet<TopUrlDB>();
		Query query = pm
				.newQuery(RatingDB.class);

		List<RatingDB> result = (List<RatingDB>) query.execute();
		if(!result.isEmpty())
		{
			for(RatingDB tempRating : result)
			{
				calculateTops(tempRating);
			}
			
			pm.makePersistentAll(topUrls);
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
		
		boolean exist = false;
		
		for(TopUrlDB tempTop : topUrls)
		{
			if(tempTop.getUrl().equals(rating.getUrl())
					&& tempTop.getPeriod().equals(period) 
						&& tempTop.getType().equals(linkType))
			{
				exist = true;
				topUrls.remove(tempTop);
				
				float ratingValue = tempTop.getAveradgeRating();
				float newRating = (ratingValue
						* ((float) tempTop.getCountOfRatings()) + rating
							.getRating())
						/ ((float) (tempTop.getCountOfRatings() + 1));
				
				tempTop.setAveradgeRating(newRating);
				tempTop.setCountOfRatings(tempTop.getCountOfRatings() + 1);
				
				topUrls.add(tempTop);
				break;
				
			}
		}		
		
		if (!exist)
		{
			topUrls.add(new TopUrlDB(rating.getUrl(), rating.getRating(), 1, period, linkType));
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