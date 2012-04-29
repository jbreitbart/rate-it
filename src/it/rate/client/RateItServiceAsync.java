package it.rate.client;

import java.util.List;

import it.rate.Rating;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>RateItService</code>.
 */
public interface RateItServiceAsync {

	void rateUrl(Rating rating, AsyncCallback<Void> callback);

	void getSubDomains(String url, AsyncCallback<List<Rating>> callback);

	void getTopUrlsForPeriod(String startDate, String endDate,
			AsyncCallback<List<Rating>> callback);

	void getAverageRatingForPeriod(String url, String startDate,
			String endDate, AsyncCallback<Float> callback);

	void getAllUserRatedUrls(AsyncCallback<List<Rating>> callback);

	void getUsersUrlRating(String url, AsyncCallback<Float> callback);

}
