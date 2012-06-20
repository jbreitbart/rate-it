package it.rate.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>RateItService</code>.
 */
public interface RateItServiceAsync {

	void rateUrl(String url, String comment, float rating, boolean canReplace, AsyncCallback<Integer> callback);

	void getSubDomains(String url, AsyncCallback<List<Rating>> callback);

	void getAllUserRatedUrls(AsyncCallback<List<Rating>> callback);

	void getUsersUrlRating(String user, String url, AsyncCallback<Float> callback);

	void getLoginURL (String destinationURL, AsyncCallback<String> callback);
	
	void getLogoutURL (String destinationURL, AsyncCallback<String> callback);
	
	void isUserLoggedIn(AsyncCallback<Boolean> callback);
	
	void getCurrentUserEmail( AsyncCallback<String> callback);
	
	void isCurUserAdmin(AsyncCallback<Integer> callback);
	
	void clearServerCache(AsyncCallback<Void> callback);
	void recalculateTops(AsyncCallback<Void> callback);
	
    void getTopUrlsForDay(int countOfUrls, AsyncCallback<List<TopUrl>> callback);
    void getTopUrlsForMonth(int countOfUrls, AsyncCallback<List<TopUrl>> callback);
    void getTopUrlsForYear(int countOfUrls, AsyncCallback<List<TopUrl>> callback);
	
    void getTopHostsForDay(int countOfUrls, AsyncCallback<List<TopUrl>> callback);
    void getTopHostsForMonth(int countOfUrls, AsyncCallback<List<TopUrl>> callback);
    void getTopHostsForYear(int countOfUrls, AsyncCallback<List<TopUrl>> callback);
	
}
