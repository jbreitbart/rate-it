package it.rate.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.rate.Constants;
import it.rate.client.RateItService;
import it.rate.client.RateItServiceAsync;
import it.rate.client.Rating;
import it.rate.client.TopUrl;
import it.rate.util.ErrorMessage;

public class RPC implements Constants {

	FrontPage fP;
	WidgetUpdate wUpd;
	ServerDataCache dataCache;
	public String savedUrl;
	public int savedRating;
	public String savedComment;
	boolean replaceRating = false;

	public RPC(FrontPage frontPage, WidgetUpdate widgetUpdate,
			ServerDataCache dataCache) {
		this.fP = frontPage;
		this.wUpd = widgetUpdate;
		this.dataCache = dataCache;
	}

	/**
	 * Receives top URLs from server for today
	 * 
	 * @param urlCount
	 *            Counter for top list
	 * @param startDate
	 *            Start date for calculating top list
	 * @param endDate
	 *            End date for calculating top list
	 */
	public void receiveTodaysTopUrls() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				wUpd.updateTopUrlsList(topUrls, Period.DAY);
				dataCache.receivedTodaysTopUrls = topUrls;
			}
		};

		rateService.getTopUrlsForDay(callback);
	}

	/**
	 * Receives top URLs from server for last month
	 * 
	 * @param urlCount
	 *            Counter for top list
	 * @param startDate
	 *            Start date for calculating top list
	 * @param endDate
	 *            End date for calculating top list
	 */
	public void receiveMonthsTopUrls() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				wUpd.updateTopUrlsList(topUrls, Period.MONTH);
				dataCache.receivedMonthsTopUrls = topUrls;
			}
		};

		rateService.getTopUrlsForMonth(callback);
	}

	/**
	 * Receives top URLs from server for last year
	 * 
	 * @param urlCount
	 *            Counter for top list
	 * @param startDate
	 *            Start date for calculating top list
	 * @param endDate
	 *            End date for calculating top list
	 */
	public void receiveYearsTopUrls() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				wUpd.updateTopUrlsList(topUrls, Period.YEAR);
				dataCache.receivedYearsTopUrls = topUrls;
			}
		};

		rateService.getTopUrlsForYear(callback);
	}

	/**
	 * Receives top domains from server for today
	 * 
	 * @param domainCount
	 *            Counter for top list
	 * @param startDate
	 *            Start date for calculating top list
	 * @param endDate
	 *            End date for calculating top list
	 */
	public void receiveTodaysTopDomains() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<TopUrl> topDomains) {
				wUpd.updateTopDomainsList(topDomains, Period.DAY);
				dataCache.receivedTodaysTopDomains = topDomains;
			}
		};

		rateService.getTopHostsForDay(callback);
	}

	/**
	 * Receives top domains from server for last month
	 * 
	 * @param domainCount
	 *            Counter for top list
	 * @param startDate
	 *            Start date for calculating top list
	 * @param endDate
	 *            End date for calculating top list
	 */
	public void receiveMonthsTopDomains() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<TopUrl> topDomains) {
				wUpd.updateTopDomainsList(topDomains, Period.MONTH);
				dataCache.receivedMonthsTopDomains = topDomains;
			}
		};

		rateService.getTopHostsForMonth(callback);
	}

	/**
	 * Receives top domains from server for last year
	 * 
	 * @param domainCount
	 *            Counter for top list
	 * @param startDate
	 *            Start date for calculating top list
	 * @param endDate
	 *            End date for calculating top list
	 */
	public void receiveYearsTopDomains() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<TopUrl> topDomains) {
				wUpd.updateTopDomainsList(topDomains, Period.YEAR);
				dataCache.receivedYearsTopDomains = topDomains;
			}
		};

		rateService.getTopHostsForYear(callback);
	}

	/**
	 * Method which receives all sub domains for a URL from server and calls the
	 * method updateSubDomainList(List<Rating> result)
	 * 
	 * @param url
	 *            URL, which to receive from, all sub domains
	 */
	public void receiveSubDomains(String domain) {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<Rating>> callback = new AsyncCallback<List<Rating>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<Rating> subDomains) {

				// Calls method for widget update
				if (!subDomains.isEmpty() && subDomains != null) {
						wUpd.updateSubDomainList(subDomains);
				}
			}
		};

		rateService.getSubDomains(domain, callback);
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

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {

					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

						public void onFailure(Throwable caught) {
							Window.alert(SERVER_ERROR);
						}

						@Override
						public void onSuccess(Integer result) {
							// If server sends rating already exists, the user
							// will be asked
							// for overwriting his rating

							if (result == ErrorMessage.RATE_EXISTS) {
								if (Window.confirm(REPLACE_WARINING)) {
									replaceRating = true;

									RateItServiceAsync rateService = (RateItServiceAsync) GWT
											.create(RateItService.class);
									AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert(SERVER_ERROR);
										}

										@Override
										public void onSuccess(Integer result) {
											savedUrl = null;
											savedRating = 0;
											savedComment = null;
											replaceRating = false;
											wUpd.clearUrlBox();
											wUpd.clearCommentBox();
											Window.alert(RATING_ACKNOWLEDGMENT);
										}

									};

									rateService.rateUrl(savedUrl, savedComment,
											(float) savedRating, replaceRating,
											callback);
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
							Window.alert(RATING_ACKNOWLEDGMENT);
						}
					};

					rateService.rateUrl(savedUrl, savedComment,
							(float) savedRating, replaceRating, callback);

				} else {
					Window.alert(LOGIN_WARNING);
				}
			}
		};
		rateService.isUserLoggedIn(callback);
	}

	public void receiveUserRatings() {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<List<Rating>> callback = new AsyncCallback<List<Rating>>() {

						public void onFailure(Throwable caught) {
							Window.alert(SERVER_ERROR);
						}

						@Override
						public void onSuccess(List<Rating> result) {
							wUpd.showUserRatings(result);
							dataCache.receivedUserRatings = result;
						}
					};
					rateService.getAllUserRatedUrls(callback);
				} else {
					Window.alert(LOGIN_WARNING);
				}
			}
		};
		rateService.isUserLoggedIn(callback);
	}

	/**
	 * Shows users login status at page start up
	 */
	public void userAuthenticationAtStart() {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {

					checkAdmin();
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert(SERVER_ERROR);
						}

						public void onSuccess(String email) {
							wUpd.showUserLoggedIn(email);
						}
					};

					rateService.getCurrentUserEmail(callback);
				} else {
					wUpd.showUserNotLoggedIn();
				}
			}
		};
		rateService.isUserLoggedIn(callback);
	}

	/**
	 * Receive login url from user service
	 */
	public void userAuthentication() {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert(SERVER_ERROR);
						}

						@Override
						public void onSuccess(String url) {
							Window.Location.assign(url);
						}
					};

					rateService.getLogoutURL(REDIRECTION_URL, callback);

				} else {
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert(SERVER_ERROR);
						}

						@Override
						public void onSuccess(String url) {
							Window.Location.assign(url);
						}
					};

					rateService.getLoginURL(REDIRECTION_URL, callback);
				}
			}
		};
		rateService.isUserLoggedIn(callback);
	}

	public void clearServerCache() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(CACHE_CLEAR_FAILED);

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert(CACHE_CLEAR_SUCCESS);

			}
		};

		rateService.clearServerCache(callback);
	}

	public void checkAdmin() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer result) {
				if (result == 0) {
					fP.adminPanel.setVisible(true);
				} else if (result == -1) {
					fP.adminPanel.setVisible(false);
				}

			}

			@Override
			public void onFailure(Throwable caught) {
			}
		};
		rateService.isCurUserAdmin(callback);
	}

	public void recalculateTops() {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Void> callback = new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				Window.alert(RECALCULATE_TOPS_SUCCESS);

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(RECALCULATE_TOPS_FAILED);

			}
		};
		rateService.recalculateTops(callback);
	}

	public void init() {
		receiveTodaysTopDomains();
		receiveMonthsTopDomains();
		receiveYearsTopDomains();
		receiveTodaysTopUrls();
		receiveMonthsTopUrls();
		receiveYearsTopUrls();
	}

	public void clearDB() {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("DB could not be cleared");

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("DB cleared");
			}

		};
		rateService.clearDB(callback);
	}
}
