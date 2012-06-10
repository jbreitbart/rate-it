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

	void getTopUrlsForPeriod(Date startDate, Date endDate, int countOfUrls,
			AsyncCallback<List<TopUrl>> callback);
	
	void getTopHostsForPeriod(Date startDate, Date endDate, int countOfUrls,
			AsyncCallback<List<TopUrl>> callback);

	void getAverageRatingForPeriod(String url, Date startDate,
			Date endDate, AsyncCallback<Float> callback);

	void getAllUserRatedUrls(AsyncCallback<List<Rating>> callback);

	void getUsersUrlRating(String user, String url, AsyncCallback<Float> callback);

	void getLoginURL (String destinationURL, AsyncCallback<String> callback);
	
	void getLogoutURL (String destinationURL, AsyncCallback<String> callback);
	
	void isUserLoggedIn(AsyncCallback<Boolean> callback);
	
	void getCurrentUserEmail( AsyncCallback<String> callback);
	
	void isCurUserAdmin(AsyncCallback<Integer> callback);
	
	void clearServerCache(AsyncCallback<Void> calback);
	
}
