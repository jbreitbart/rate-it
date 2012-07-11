package it.rate.server;

import it.rate.Constants;
import it.rate.client.TopUrl;
import it.rate.data.RatingDB;
import it.rate.data.TopDomainDayDB;
import it.rate.data.TopDomainMonthDB;
import it.rate.data.TopDomainYearDB;
import it.rate.data.TopURLDayDB;
import it.rate.data.TopURLMonthDB;
import it.rate.data.TopURLYearDB;
import it.rate.util.MemCache;
import it.rate.util.PMF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.KeyFactory;

import net.sf.jsr107cache.Cache;

public class TopsCalculator
{

	public static List<TopUrl> getTopUrlsForDay()
	{
		return getTopsFromCache(MemCache.CACHE_KEY_URL_DAY);
	}

	public static List<TopUrl> getTopUrlsForMonth()
	{
		return getTopsFromCache(MemCache.CACHE_KEY_URL_MONTH);
	}

	public static List<TopUrl> getTopUrlsForYear()
	{
		return getTopsFromCache(MemCache.CACHE_KEY_URL_YEAR);
	}

	public static List<TopUrl> getTopHostsForDay()
	{
		return getTopsFromCache(MemCache.CACHE_KEY_HOST_DAY);
	}

	public static List<TopUrl> getTopHostsForMonth()
	{
		return getTopsFromCache(MemCache.CACHE_KEY_HOST_MONTH);
	}

	public static List<TopUrl> getTopHostsForYear()
	{
		return getTopsFromCache(MemCache.CACHE_KEY_HOST_YEAR);
	}
	
	
	public static void calculateAllTops()
	{
		PersistenceManager pm = PMF.getInstance().getPersistenceManager(); 
		
// get rating to remove from tops
		Query queryTopOfMonthToRemove = pm.newQuery(RatingDB.class,"this.date == dateParam");
		queryTopOfMonthToRemove.declareParameters("String dateParam");
		
		Query queryTopOfYearToRemove = pm.newQuery(RatingDB.class, "this.date == dateParam");
		queryTopOfYearToRemove.declareParameters("String dateParam");
		
		Query queryTopOfDayToRemove = pm.newQuery(RatingDB.class,"this.date == dateParam");
		queryTopOfDayToRemove.declareParameters("String dateParam");
		
		GregorianCalendar cal = new GregorianCalendar();
	    SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy" );
	    //String today = sdf.format(cal.getTime());
	    cal.roll(Calendar.DAY_OF_MONTH, false);	    
		String yesterday =  sdf.format(cal.getTime());
		cal.roll(Calendar.DAY_OF_MONTH, true);	
		cal.roll(Calendar.MONTH, false);	
		String lastMonth = sdf.format(cal.getTime());
		cal.roll(Calendar.MONTH, true);
		cal.roll(Calendar.YEAR, false);
		String lastYear = sdf.format(cal.getTime());
		System.out.println("yesterda: " + yesterday);
		
		//TODO remove for month and year
		List<RatingDB> ratingstoRemovefromDay = (List<RatingDB>) queryTopOfDayToRemove.execute(yesterday);
		List<RatingDB> ratingstoRemovefromMonth = (List<RatingDB>) queryTopOfMonthToRemove.execute(lastMonth);
		List<RatingDB> ratingstoRemovefromYear = (List<RatingDB>) queryTopOfYearToRemove.execute(lastYear);
		
		for(RatingDB oldRating : ratingstoRemovefromDay)
		{
			try
			{

				TopURLDayDB tempUrlDay = pm.getObjectById(TopURLDayDB.class,
						KeyFactory.createKey(TopURLDayDB.class.getSimpleName(),
								oldRating.getUrl()));
				
				if ((tempUrlDay.getCountOfRatings() - 1) == 0)
				{
					pm.deletePersistent(tempUrlDay);
				}
				else
				{
					float newRating = (tempUrlDay.getAveradgeRating()
							* (float) tempUrlDay.getCountOfRatings() - (float) oldRating
								.getRating())
							/ (tempUrlDay.getCountOfRatings() - 1);
					tempUrlDay.setAveradgeRating(newRating);
					tempUrlDay.setCountOfRatings(tempUrlDay.getCountOfRatings() - 1);
					pm.makePersistent(tempUrlDay);
				}

			}
			catch (JDOObjectNotFoundException e)
			{
				
			}
			if (oldRating.getHost().equals(oldRating.getUrl()))
			{
				try
				{
					TopDomainDayDB tempDomainlDay = pm.getObjectById(TopDomainDayDB.class,
							KeyFactory.createKey(TopDomainDayDB.class.getSimpleName(),
									oldRating.getUrl()));
					
					if ((tempDomainlDay.getCountOfRatings() - 1) == 0)
					{
						pm.deletePersistent(tempDomainlDay);
					}
					else
					{
						float newRating = (tempDomainlDay.getAveradgeRating()
								* (float) tempDomainlDay.getCountOfRatings() - (float) oldRating
									.getRating())
								/ (tempDomainlDay.getCountOfRatings() - 1);
						tempDomainlDay.setAveradgeRating(newRating);
						tempDomainlDay.setCountOfRatings(tempDomainlDay.getCountOfRatings() - 1);
						pm.makePersistent(tempDomainlDay);
					}
				}

				catch (JDOObjectNotFoundException e)
				{

				}
			}
		}
		
		
		for(RatingDB oldRating : ratingstoRemovefromMonth)
		{
			try
			{

				TopURLMonthDB tempUrlMonth = pm.getObjectById(TopURLMonthDB.class,
						KeyFactory.createKey(TopURLMonthDB.class.getSimpleName(),
								oldRating.getUrl()));
				
				if ((tempUrlMonth.getCountOfRatings() - 1) == 0)
				{
					pm.deletePersistent(tempUrlMonth);
				}
				else
				{
					float newRating = (tempUrlMonth.getAveradgeRating()
							* (float) tempUrlMonth.getCountOfRatings() - (float) oldRating
								.getRating())
							/ (tempUrlMonth.getCountOfRatings() - 1);
					tempUrlMonth.setAveradgeRating(newRating);
					tempUrlMonth.setCountOfRatings(tempUrlMonth.getCountOfRatings() - 1);
					pm.makePersistent(tempUrlMonth);
				}

			}
			catch (JDOObjectNotFoundException e)
			{
				
			}
			if (oldRating.getHost().equals(oldRating.getUrl()))
			{
				try
				{
					TopDomainMonthDB tempDomainlMonth = pm.getObjectById(TopDomainMonthDB.class,
							KeyFactory.createKey(TopDomainMonthDB.class.getSimpleName(),
									oldRating.getUrl()));
					
					if ((tempDomainlMonth.getCountOfRatings() - 1) == 0)
					{
						pm.deletePersistent(tempDomainlMonth);
					}
					else
					{
						float newRating = (tempDomainlMonth.getAveradgeRating()
								* (float) tempDomainlMonth.getCountOfRatings() - (float) oldRating
									.getRating())
								/ (tempDomainlMonth.getCountOfRatings() - 1);
						tempDomainlMonth.setAveradgeRating(newRating);
						tempDomainlMonth.setCountOfRatings(tempDomainlMonth.getCountOfRatings() - 1);
						pm.makePersistent(tempDomainlMonth);
					}
				}

				catch (JDOObjectNotFoundException e)
				{

				}
			}
		}
		
		
		
		for(RatingDB oldRating : ratingstoRemovefromYear)
		{
			try
			{

				TopURLYearDB tempUrlYear = pm.getObjectById(TopURLYearDB.class,
						KeyFactory.createKey(TopURLYearDB.class.getSimpleName(),
								oldRating.getUrl()));
				
				if ((tempUrlYear.getCountOfRatings() - 1) == 0)
				{
					pm.deletePersistent(tempUrlYear);
				}
				else
				{
					float newRating = (tempUrlYear.getAveradgeRating()
							* (float) tempUrlYear.getCountOfRatings() - (float) oldRating
								.getRating())
							/ (tempUrlYear.getCountOfRatings() - 1);
					tempUrlYear.setAveradgeRating(newRating);
					tempUrlYear.setCountOfRatings(tempUrlYear.getCountOfRatings() - 1);
					pm.makePersistent(tempUrlYear);
				}

			}
			catch (JDOObjectNotFoundException e)
			{
				
			}
			if (oldRating.getHost().equals(oldRating.getUrl()))
			{
				try
				{
					TopDomainYearDB tempDomainlYear = pm.getObjectById(TopDomainYearDB.class,
							KeyFactory.createKey(TopDomainYearDB.class.getSimpleName(),
									oldRating.getUrl()));
					
					if ((tempDomainlYear.getCountOfRatings() - 1) == 0)
					{
						pm.deletePersistent(tempDomainlYear);
					}
					else
					{
						float newRating = (tempDomainlYear.getAveradgeRating()
								* (float) tempDomainlYear.getCountOfRatings() - (float) oldRating
									.getRating())
								/ (tempDomainlYear.getCountOfRatings() - 1);
						tempDomainlYear.setAveradgeRating(newRating);
						tempDomainlYear.setCountOfRatings(tempDomainlYear.getCountOfRatings() - 1);
						pm.makePersistent(tempDomainlYear);
					}
				}

				catch (JDOObjectNotFoundException e)
				{

				}
			}
		}
		pm.close();
		
	}


	private static List<TopUrl> getTopsFromCache(String cacheKey)
	{
		List<TopUrl> tops = new ArrayList<TopUrl>();

		Cache cache = MemCache.getInstance().getCache();
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Query query = null;

		// if an entity for this time is not existing in cache, create one
		if (cache.get(cacheKey) == null)
		{
			try
			{
				if (cacheKey.equals(MemCache.CACHE_KEY_URL_DAY))
				{
					query = pm.newQuery(TopURLDayDB.class);
					List<TopURLDayDB> temp = (List<TopURLDayDB>) query.execute();
					query.closeAll();
					Collections.sort(temp);
					for (int i = 0; i < Constants.TOP_COUNT; i++)
					{
						if (i < temp.size())
						{
							tops.add(new TopUrl(temp.get(i).getKey().getName(),
									temp.get(i).getAveradgeRating(), temp
											.get(i).getCountOfRatings()));
						}
					}

				} else if (cacheKey.equals(MemCache.CACHE_KEY_URL_MONTH))
				{
					query = pm.newQuery(TopURLMonthDB.class);
					List<TopURLMonthDB> temp = (List<TopURLMonthDB>) query.execute();
					query.closeAll();
					Collections.sort(temp);
					for (int i = 0; i < Constants.TOP_COUNT; i++)
					{
						if (i < temp.size())
						{
							tops.add(new TopUrl(temp.get(i).getKey().getName(),
									temp.get(i).getAveradgeRating(), temp
											.get(i).getCountOfRatings()));
						}
					}

				} else if (cacheKey.equals(MemCache.CACHE_KEY_URL_YEAR))
				{
					query = pm.newQuery(TopURLYearDB.class);
					List<TopURLYearDB> temp = (List<TopURLYearDB>) query.execute();
					query.closeAll();
					Collections.sort(temp);
					for (int i = 0; i < Constants.TOP_COUNT; i++)
					{
						if (i < temp.size())
						{
							tops.add(new TopUrl(temp.get(i).getKey().getName(),
									temp.get(i).getAveradgeRating(), temp
											.get(i).getCountOfRatings()));
						}
					}

				} else if (cacheKey.equals(MemCache.CACHE_KEY_HOST_DAY))
				{
					query = pm.newQuery(TopDomainDayDB.class);
					List<TopDomainDayDB> temp = (List<TopDomainDayDB>) query.execute();
					query.closeAll();
					Collections.sort(temp);
					for (int i = 0; i < Constants.TOP_COUNT; i++)
					{
						if (i < temp.size())
						{
							tops.add(new TopUrl(temp.get(i).getKey().getName(),
									temp.get(i).getAveradgeRating(), temp
											.get(i).getCountOfRatings()));
						}
					}

				} else if (cacheKey.equals(MemCache.CACHE_KEY_HOST_MONTH))
				{
					query = pm.newQuery(TopDomainMonthDB.class);
					List<TopDomainMonthDB> temp = (List<TopDomainMonthDB>) query.execute();
					query.closeAll();
					Collections.sort(temp);
					for (int i = 0; i < Constants.TOP_COUNT; i++)
					{
						if (i < temp.size())
						{
							tops.add(new TopUrl(temp.get(i).getKey().getName(),
									temp.get(i).getAveradgeRating(), temp
											.get(i).getCountOfRatings()));
						}
					}

				} else if (cacheKey.equals(MemCache.CACHE_KEY_HOST_YEAR))
				{
					query = pm.newQuery(TopDomainYearDB.class);
					List<TopDomainYearDB> temp = (List<TopDomainYearDB>) query.execute();
					query.closeAll();
					Collections.sort(temp);
					for (int i = 0; i < Constants.TOP_COUNT; i++)
					{
						if (i < temp.size())
						{
							tops.add(new TopUrl(temp.get(i).getKey().getName(),
									temp.get(i).getAveradgeRating(), temp
											.get(i).getCountOfRatings()));
						}
					}

				}
			} finally
			{
				
			cache.put(cacheKey, tops);
				
		}

		} else
		{
			tops = (List<TopUrl>) cache.get(cacheKey);
		}
		
		return tops;
	}
}