package it.rate.server;

import it.rate.Constants;
import it.rate.client.TopUrl;
import it.rate.data.RatingDB;
import it.rate.data.TopUrlDB;
import it.rate.util.MemCache;
import it.rate.util.PMF;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;

public class TopsCalculator
{

	private static ArrayList<TopUrlDB> topUrlsDay;
	private static ArrayList<TopUrlDB> topUrlsMonth;
	private static ArrayList<TopUrlDB> topUrlsYear;
	private static ArrayList<TopUrlDB> topDomainsDay;
	private static ArrayList<TopUrlDB> topDomainsMonth;
	private static ArrayList<TopUrlDB> topDomainsYear;
	
	public static List<TopUrl> getTopUrlsForDay()
	{
		return getTops(MemCache.CACHE_KEY_URL_DAY, TopUrl.PERIOD_DAY,
				TopUrl.TYPE_URL);
	}

	public static List<TopUrl> getTopUrlsForMonth()
	{
		return getTops(MemCache.CACHE_KEY_URL_MONTH, TopUrl.PERIOD_MONTH,
				TopUrl.TYPE_URL);
	}

	public static List<TopUrl> getTopUrlsForYear()
	{
		return getTops(MemCache.CACHE_KEY_URL_YEAR, TopUrl.PERIOD_YEAR,
				TopUrl.TYPE_URL);
	}

	public static List<TopUrl> getTopHostsForDay()
	{
		return getTops(MemCache.CACHE_KEY_HOST_DAY, TopUrl.PERIOD_DAY,
				TopUrl.TYPE_DOMAIN);
	}

	public static List<TopUrl> getTopHostsForMonth()
	{
		return getTops(MemCache.CACHE_KEY_HOST_MONTH, TopUrl.PERIOD_MONTH,
				TopUrl.TYPE_DOMAIN);
	}

	public static List<TopUrl> getTopHostsForYear()
	{
		return getTops(MemCache.CACHE_KEY_HOST_YEAR, TopUrl.PERIOD_YEAR,
				TopUrl.TYPE_DOMAIN);
	}
	
	
	public static void calculateAllTops()
	{
		List<TopUrlDB> tempResult = new ArrayList<TopUrlDB>();
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Query queryTops = pm.newQuery(TopUrlDB.class);
		
		List<TopUrlDB> resultTops = (List<TopUrlDB>) queryTops.execute();

		//clear TopUrlDB
		if(!resultTops.isEmpty())
		{
			pm.deletePersistentAll(resultTops);
		}
		
		topUrlsDay = new ArrayList<TopUrlDB>();
		topUrlsMonth = new ArrayList<TopUrlDB>();
		topUrlsYear = new ArrayList<TopUrlDB>();
		topDomainsDay = new ArrayList<TopUrlDB>();
		topDomainsMonth = new ArrayList<TopUrlDB>();
		topDomainsYear = new ArrayList<TopUrlDB>();
		
		Query query = pm
				.newQuery(RatingDB.class);

		List<RatingDB> result = (List<RatingDB>) query.execute();
		if(!result.isEmpty())
		{
			for(RatingDB tempRating : result)
			{
				calculateTops(tempRating);
			}
			
			Collections.sort(topUrlsDay);
			Collections.sort(topUrlsMonth);
			Collections.sort(topUrlsYear);
			Collections.sort(topDomainsDay);
			Collections.sort(topDomainsMonth);
			Collections.sort(topDomainsYear);
			
			for (int i = 0; i < Constants.TOP_COUNT; i++)
			{
				if (i < topUrlsDay.size())
				{
					tempResult.add(topUrlsDay.get(i));
				}
				if (i < topUrlsMonth.size())
				{
					tempResult.add(topUrlsMonth.get(i));
				}
				if (i < topUrlsYear.size())
				{
					tempResult.add(topUrlsYear.get(i));
				}
				if (i < topDomainsDay.size())
				{
					tempResult.add(topDomainsDay.get(i));
				}
				if (i < topDomainsMonth.size())
				{
					tempResult.add(topDomainsMonth.get(i));
				}
				if (i < topDomainsYear.size())
				{
					tempResult.add(topDomainsYear.get(i));
				}
			}
			pm.makePersistentAll(tempResult);
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
				calculate(rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_DAY, topDomainsDay);
			}
			if ((rating.getDate().getYear() == new Date().getYear())
					&&(rating.getDate().getMonth() == new Date().getMonth()))
			{
				calculate(rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_MONTH, topDomainsMonth);
			}
			if (rating.getDate().getYear() == new Date().getYear())
			{
				calculate(rating, TopUrl.TYPE_DOMAIN, TopUrl.PERIOD_YEAR, topDomainsYear);
			}
			
		}
		//calculate tops only for current period
		if ((rating.getDate().getYear() == new Date().getYear())
				&&(rating.getDate().getMonth() == new Date().getMonth()) 
					&& (rating.getDate().getDate() == new Date().getDate()))
		{
			calculate(rating, TopUrl.TYPE_URL, TopUrl.PERIOD_DAY, topUrlsDay);
		}
		if ((rating.getDate().getYear() == new Date().getYear())
				&&(rating.getDate().getMonth() == new Date().getMonth()))
		{
			calculate(rating, TopUrl.TYPE_URL, TopUrl.PERIOD_MONTH, topUrlsMonth);
		}
		if (rating.getDate().getYear() == new Date().getYear())
		{
			calculate(rating, TopUrl.TYPE_URL, TopUrl.PERIOD_YEAR, topUrlsYear);
		}
		
	}

	private static void calculate(RatingDB rating, String linkType,
			String period, List<TopUrlDB> topList)
	{
		
		boolean exist = false;
		
		for(TopUrlDB tempTop : topList)
		{
			if(tempTop.getUrl().equals(rating.getUrl())
					&& tempTop.getPeriod().equals(period) 
						&& tempTop.getType().equals(linkType))
			{
				exist = true;
				topList.remove(tempTop);
				
				float ratingValue = tempTop.getAveradgeRating();
				float newRating = (ratingValue
						* ((float) tempTop.getCountOfRatings()) + rating
							.getRating())
						/ ((float) (tempTop.getCountOfRatings() + 1));
				
				tempTop.setAveradgeRating(newRating);
				tempTop.setCountOfRatings(tempTop.getCountOfRatings() + 1);
				
				topList.add(tempTop);
				break;
				
			}
		}		
		
		if (!exist)
		{
			topList.add(new TopUrlDB(rating.getUrl(), rating.getRating(), 1, period, linkType));
		}
	}

	private static List<TopUrl> getTops(String cacheKey, String period,
			String type)
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
//				Collections.sort(temp);
				int i = 0;
				for (TopUrlDB tempTopUrl : temp)
				{
//					if (i < countOfUrls)
//					{
						result.add(new TopUrl(tempTopUrl.getUrl(), tempTopUrl
								.getAveradgeRating(), tempTopUrl
								.getCountOfRatings()));

//					} else
//					{
//						break;
//					}
//					i++;
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