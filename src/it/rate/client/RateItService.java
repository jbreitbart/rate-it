package it.rate.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rate")
public interface RateItService extends RemoteService {
	
	/**
	 * Adds a rating for a URL in the DB
	 * @param rating A rating object which holds user, the URL, its rating and an optional comment
	 */
	public boolean rateUrl(String url, String comment, float rating, boolean canReplace);	// server has to check if user hasn't already rated that URL
										// and whether the URL has to be added to the DB
										// if false is returned, ask for changing users rating and send again

	/**
	 * Returns URL's all rated sub domains
	 * @param url
	 * @return An array of sub domains
	 */
	public List<Rating> getSubDomains(String url);
	
	/**
	 * Gets the top URLs for a certain time period
	 * @param startDate The start date for the average rating
	 * @param endDate The end date for the average rating
	 * @return An array with the top URLs
	 */
	public List<TopUrl> getTopUrlsForPeriod(Date startDate, Date endDate, int countOfUrls);
	
	/**
	 * Gets the top Hosts for a certain time period
	 * @param startDate The start date for the average rating
	 * @param endDate The end date for the average rating
	 * @return An array with the top Hosts
	 */
	public List<TopUrl> getTopHostsForPeriod(Date startDate, Date endDate, int countOfUrls);
	
	/**
	 * Fetches average URL rating for a certain time period
	 * @param url The URL as string object
	 * @param startDate The start date for the average rating
	 * @param endDate The end date for the average rating
	 * @return Average rating
	 */
	public float getAverageRatingForPeriod(String url, Date startDate, Date endDate);
	
	/**
	 * Gets all user rated URLs (comments should be null here)
	 * @return An array of ratings
	 */
	public List<Rating> getAllUserRatedUrls();
	
	/**
	 * Gets the rating from a user for a certain URL
	 * @param url The URL
	 * @return URL's rating as float or 0 if not found
	 */
	public float getUsersUrlRating(String user, String url);
	
	/**
	 * 
	 * @param destinationURL a URL user will be redirect after login
	 * @return Returns a URL that can be used to display a login page to the user.
	 */
	public String getLoginURL (String destinationURL);
	
	/**
	 * 
	 * @param destinationURL a URL user will be redirect after logout
	 * @return Returns a URL that can be used to display a logout page to the user.
	 */
	public String getLogoutURL (String destinationURL);
	
	/**
	 * 
	 * @return true if there is a user logged in, false otherwise.
	 */
	public boolean isUserLoggedIn();
	
	/**
	 * 
	 * @return E-mail of current logged user as String
	 */
	public String getCurrentUserEmail();
	
	
}
