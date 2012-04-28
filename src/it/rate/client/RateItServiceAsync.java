package it.rate.client;

import it.rate.Rating;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>RateItService</code>.
 */
public interface RateItServiceAsync {

	void rateUrl(Rating rating, AsyncCallback<Void> callback);

	void getSubDomains(String url, AsyncCallback<Rating[]> callback);

	void getTopUrlsForPeriod(String startDate, String endDate,
			AsyncCallback<Rating[]> callback);

	void getAverageRatingForPeriod(String url, String startDate,
			String endDate, AsyncCallback<Float> callback);

	void getAllUserRatedUrls(AsyncCallback<Rating[]> callback);

	void getUsersUrlRating(String url, AsyncCallback<Float> callback);

}
