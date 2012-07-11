package it.rate.client;

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
	 * 
	 * @param mail
	 *            User e-mail
	 * @param url
	 *            URL to rate
	 * @param comment
	 *            Comment for the url
	 * @param rating
	 *            The rating for the url
	 * @param rating
	 *            A rating object which holds user, the URL, its rating and an
	 *            optional comment
	 * @param canReplace
	 * 			 rating will be replaced if this flag is true 
	 * 
	 * @return ErrorMessage
	 */
	public int rateUrl(String url, String comment, float rating,
			boolean canReplace);

	/**
	 * Returns URL's all rated sub domains
	 * 
	 * @param url
	 * @return An array of sub domains
	 */
	public List<Rating> getSubDomains(String url);

	/**
	 * Gets all user rated URLs (comments should be null here)
	 * 
	 * @return An array of ratings
	 */
	public List<Rating> getAllUserRatedUrls();

	/**
	 * Gets the rating from a user for a certain URL
	 * 
	 * @param user
	 * 			  user id as email
	 * @param url
	 *            The URL
	 * @return URL's rating as float or 0 if not found
	 */
	public float getUsersUrlRating(String user, String url);

	/**
	 * 
	 * @param destinationURL
	 *            a URL user will be redirect after login
	 * @return Returns a URL that can be used to display a login page to the
	 *         user.
	 */
	public String getLoginURL(String destinationURL);

	/**
	 * 
	 * @param destinationURL
	 *            a URL user will be redirect after logout
	 * @return Returns a URL that can be used to display a logout page to the
	 *         user.
	 */
	public String getLogoutURL(String destinationURL);

	/**
	 * 
	 * @return true if there is a user logged in, false otherwise.
	 */
	public boolean isUserLoggedIn();

	/**
	 * 
	 * @return 0 if current user is an admin, -1 if current user isn't an admin,
	 *         -2 if user isn't logged in
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
	public List<TopUrl> getTopUrlsForDay();

	/**
	 * Gets the top URLs for a month
	 * 
	 * @return An array with the top URLs
	 */
	public List<TopUrl> getTopUrlsForMonth();

	/**
	 * Gets the top URLs for a year
	 * 
	 * @return An array with the top URLs
	 */
	public List<TopUrl> getTopUrlsForYear();

	/**
	 * Gets the top Hosts for a day
	 * 
	 * @return An array with the top Hosts
	 */
	public List<TopUrl> getTopHostsForDay();

	/**
	 * Gets the top Hosts for a month
	 * 
	 * @return An array with the top Hosts
	 */
	public List<TopUrl> getTopHostsForMonth();

	/**
	 * Gets the top Hosts for a year
	 * 
	 * @return An array with the top Hosts
	 */
	public List<TopUrl> getTopHostsForYear();

	/**
	 * Like rateUrl just without any conditions for url format and user e-mail
	 * 
	 * @param mail
	 *            User e-mail
	 * @param url
	 *            url to rate
	 * @param comment
	 *            comment about the url
	 * @param rating
	 *            the rating for the url
	 * @return ErrorMessage
	 */
	public int ratingTest(String mail, String url, String comment, float rating);

	/**
	 * Clears all ratings from DB
	 */
	public void clearDB();

}
