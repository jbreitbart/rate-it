package it.rate.client;

import it.rate.Rating;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rate")
public interface RateItService extends RemoteService {
	
	/**
	 * Adds a rating for a URL in the DB
	 * @param rating A rating object which holds the URL, its rating and an optional comment
	 */
	public void rateUrl(Rating rating);	// server has to check if user hasn't already rated that URL
										// and whether the URL has to be added to the DB
										// if false is returned, ask for changing users rating and send again

	/**
	 * Returns URL's all rated sub domains
	 * @param url
	 * @return An array of sub domains
	 */
	public Rating[] getSubDomains(String url);
	
	/**
	 * Gets the top URLs for a certain time period
	 * @return An array with the top URLs
	 */
	public Rating[] getTopUrlsForPeriod(String startDate, String endDate);
	
	/**
	 * Fetches average URL rating for a certain time period
	 * @param url The URL as string object
	 * @param date The date for the average rating
	 * @return Average rating
	 */
	public float getAverageRatingForPeriod(String url, String startDate, String endDate);
	
	/**
	 * Gets all user rated URLs (comments should be null here)
	 * @return An array of ratings
	 */
	public Rating[] getAllUserRatedUrls();
	
	/**
	 * Gets the rating from a user for a certain URL
	 * @param url The URL
	 * @return URL's rating
	 */
	public float getUsersUrlRating(String url);
	
}
