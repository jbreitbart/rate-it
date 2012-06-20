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
	public int rateUrl(String url, String comment, float rating, boolean canReplace);	// server has to check if user hasn't already rated that URL
										// and whether the URL has to be added to the DB
										// if false is returned, ask for changing users rating and send again

	/**
	 * Returns URL's all rated sub domains
	 * @param url
	 * @return An array of sub domains
	 */
	public List<Rating> getSubDomains(String url);
	
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
	 * @return 0 if current user is an admin, -1 if current user isn't an admin, -2 if user isn't logged in
	 */
	public int isCurUserAdmin();
	/**
	 * 
	 * @return E-mail of current logged user as String
	 */
	public String getCurrentUserEmail();
	
	
	/**
	 * clear server cache for all values
	 */
	public void clearServerCache();
	
	/**
	 * recalculate all tops for urls and domains
	 */
	public void recalculateTops();
	
	
	/**
	 * Gets the top URLs for a day
	 * 
	 * @return An array with the top URLs
	 */
	public List<TopUrl> getTopUrlsForDay(int countOfUrls);
	
	/**
	 * Gets the top URLs for a month
	 * 
	 * @return An array with the top URLs
	 */
	public List<TopUrl> getTopUrlsForMonth(int countOfUrls);
	
	/**
	 * Gets the top URLs for a year
	 * 
	 * @return An array with the top URLs
	 */
	public List<TopUrl> getTopUrlsForYear(int countOfUrls);
	
	/**
	 * Gets the top Hosts for a day
	 * 
	 * @return An array with the top Hosts
	 */
	public List<TopUrl> getTopHostsForDay(int countOfUrls);
	
	/**
	 * Gets the top Hosts for a month
	 * 
	 * @return An array with the top Hosts
	 */
	public List<TopUrl> getTopHostsForMonth(int countOfUrls);
	
	/**
	 * Gets the top Hosts for a year
	 * 
	 * @return An array with the top Hosts
	 */
	public List<TopUrl> getTopHostsForYear(int countOfUrls);
	
	
	
}
