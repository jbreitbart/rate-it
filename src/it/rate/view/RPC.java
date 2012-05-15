package it.rate.view;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.rate.client.RateItService;
import it.rate.client.RateItServiceAsync;
import it.rate.client.Rating;
import it.rate.client.TopUrl;

public class RPC {
	
	FrontPage fP;
	WidgetUpdate wUpd;
	public List<TopUrl> receivedTopUrls;
	public List<TopUrl> receivedTopDomains;
	public List<Rating> receivedSubDomains;
	public List<Rating> receivedUserRatings;
	public String savedUrl;
	public int savedRating;
	public String savedComment;
	boolean replaceRating = false;
	boolean loggedIn = false;
	
	public RPC (FrontPage frontPage, WidgetUpdate widgetUpdate){
		this.fP = frontPage;
		this.wUpd = widgetUpdate;
	}
	
	/**
	 * Method which receives all sub domains for a URL from server and calls the
	 * method updateSubDomainList(List<Rating> result)
	 * 
	 * @param url
	 *            URL, which to receive from, all sub domains
	 */
	protected void receiveSubDomains(String url) {
		fP.verticalPanel.setVisible(true);

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<Rating>> callback = new AsyncCallback<List<Rating>>() {

			public void onFailure(Throwable caught) {
				Window.alert("An error occured while retrieving the url list.");
			}

			@Override
			public void onSuccess(List<Rating> subDomains) {
				// Calls method for widget update
				wUpd.updateSubDomainList((List<Rating>) subDomains);
			}
		};

		rateService.getSubDomains(url, callback);
	}
	
	/**
	 * Receives top URLs from server
	 * 
	 * @param urlCount
	 *            Counter for top list
	 * @param startDate
	 *            Start date for calculating top list
	 * @param endDate
	 *            End date for calculating top list
	 */
	public void receiveTopUrls(int urlCount, Date startDate, Date endDate) {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert("An error occured while retrieving the url list.");
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				receivedTopUrls = topUrls;
			}
		};

		rateService.getTopUrlsForPeriod(startDate, endDate, urlCount, callback);
	}
	
	/**
	 * Saves the last rating on client side and sends rating to server. If user
	 * already rated the URL, he will be asked for overwriting his rating.
	 * 
	 * @param url
	 *            Rated URL
	 * @param commment
	 *            Comment for rated URL (optionally)
	 * @param rating
	 *            The user rating for URL
	 */
	public void rateUrl(String url, String comment, int rating) {
		// Locally saved rating which will be used if server asks for permission
		// to overwrite user rating
		savedUrl = url;
		savedRating = rating;
		savedComment = comment;
		
		if (!loggedIn) {
			Window.alert("Please log in first");
			return;
		}

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				Window.alert("An error occured while sending your rating."
						+ "Please try it again.");
			}

			@Override
			public void onSuccess(Boolean result) {
				// If server sends false, the user will be asked for overwriting
				// his rating
				if (!result) {
					if (Window
							.confirm("Are you sure you want to replace your rating for this URL?")) {
						replaceRating = true;
						
						RateItServiceAsync rateService = (RateItServiceAsync) GWT
								.create(RateItService.class);
						AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("An error occured while sending your rating."
										+ "Please try it again.");								
							}

							@Override
							public void onSuccess(Boolean result) {
								savedUrl = null;
								savedRating = 0;
								savedComment = null;
								replaceRating = false;
								wUpd.clearUrlBox();
								wUpd.clearCommentBox();
								Window.alert("Thank your for your rating!");								
							}
							
						};

						rateService
						.rateUrl(savedUrl, savedComment, (float) savedRating, replaceRating, callback);
						return;
					}
					savedUrl = null;
					savedRating = 0;
					savedComment = null;
					replaceRating = false;
					return;
				}
				savedUrl = null;
				savedRating = 0;
				savedComment = null;
				replaceRating = false;
				wUpd.clearUrlBox();
				wUpd.clearCommentBox();
				Window.alert("Thank your for your rating!");
			}
		};

		rateService
				.rateUrl(url, comment, (float) rating, replaceRating, callback);
	}
	
	/**
	 * Shows users login status
	 */
	public void userAuthentication() {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				Window.alert("Could not get user information from server.");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert("Could not get user information from server.");
						}

						@Override
						public void onSuccess(String email) {
							fP.htmlNewHtml.setHTML("Logged in as " + email);
							loggedIn = true;
						}
					};

					rateService.getCurrentUserEmail(callback);
				} else {
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert("Could not get user information from server.");
						}

						@Override
						public void onSuccess(String url) {
							fP.htmlNewHtml.setHTML("Please log in " + "<a href=\""
									+ url + "\">here</a>");
							loggedIn = false;
						}
					};
					// TODO change url
					rateService
							.getLoginURL(
									"http://127.0.0.1:8888/RateIt.html?gwt.codesvr=127.0.0.1:9997",
									callback);
				}
			}
		};

		rateService.isUserLoggedIn(callback);
	}

	public void receiveUserRatings() {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<Rating>> callback = new AsyncCallback<List<Rating>>() {

			public void onFailure(Throwable caught) {
				Window.alert("Could not get user information from server.");
			}

			@Override
			public void onSuccess(List<Rating> result) {
				receivedUserRatings = result;				
			}
		};
		rateService.getAllUserRatedUrls(callback);
	}

	public void receiveTopDomains(int TOP_COUNT, Date oneYearBack, Date today) {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert("An error occured while retrieving the url list.");
			}

			@Override
			public void onSuccess(List<TopUrl> topDomains) {
				receivedTopDomains = topDomains;
			}
		};

		rateService.getTopHostsForPeriod(oneYearBack, today, TOP_COUNT, callback);
		
	}

}
