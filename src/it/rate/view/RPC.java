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
import it.rate.util.ErrorMessage;

public class RPC implements Constants {

	FrontPage fP;
	WidgetUpdate wUpd;
	ServerDataCache dataCache;
	public String savedUrl;
	public int savedRating;
	public String savedComment;
	boolean replaceRating = false;
	boolean loggedIn = false;
	public String currentCalledDomain;
	public TimePeriod timePeriod;

	public RPC(FrontPage frontPage, WidgetUpdate widgetUpdate,
			ServerDataCache dataCache) {
		this.fP = frontPage;
		this.wUpd = widgetUpdate;
		this.dataCache = dataCache;
		this.timePeriod = new TimePeriod();
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
		timePeriod.refresh();
		Date startDate = timePeriod.oneDayBack;
		Date endDate = timePeriod.today;

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

		rateService
				.getTopUrlsForPeriod(startDate, endDate, TOP_COUNT, callback);
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
		timePeriod.refresh();
		Date startDate = timePeriod.oneMonthBack;
		Date endDate = timePeriod.today;

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				wUpd.updateTopUrlsList(topUrls, Period.MONTH);
				dataCache.receivedTodaysTopUrls = topUrls;
			}
		};

		rateService
				.getTopUrlsForPeriod(startDate, endDate, TOP_COUNT, callback);
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
		timePeriod.refresh();
		Date startDate = timePeriod.oneYearBack;
		Date endDate = timePeriod.today;

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				wUpd.updateTopUrlsList(topUrls, Period.YEAR);
				dataCache.receivedTodaysTopUrls = topUrls;
			}
		};

		rateService
				.getTopUrlsForPeriod(startDate, endDate, TOP_COUNT, callback);
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
		timePeriod.refresh();
		Date startDate = timePeriod.oneDayBack;
		Date endDate = timePeriod.today;

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

				// TODO: Remove comment, when receiving sub domains
				// issue is resolved
//				try {
//					for (TopUrl domain : topDomains) {
//						
//						receiveSubDomains(domain.getUrl());
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		};

		rateService.getTopHostsForPeriod(startDate, endDate, TOP_COUNT,
				callback);
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
		timePeriod.refresh();
		Date startDate = timePeriod.oneMonthBack;
		Date endDate = timePeriod.today;

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

				// TODO: Remove comment, when receiving sub domains
				// issue is resolved
//				try {
//					for (TopUrl domain : topDomains) {
//						
//						receiveSubDomains(domain.getUrl());
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		};

		rateService.getTopHostsForPeriod(startDate, endDate, TOP_COUNT,
				callback);
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
		timePeriod.refresh();
		Date startDate = timePeriod.oneYearBack;
		Date endDate = timePeriod.today;

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

				// TODO: Remove comment, when receiving sub domains
				// issue is resolved
//				try {
//					for (TopUrl domain : topDomains) {
//						
//						receiveSubDomains(domain.getUrl());
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		};

		rateService.getTopHostsForPeriod(startDate, endDate, TOP_COUNT,
				callback);
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
				String hostDomain = subDomains.get(0).getHost();
				if (!subDomains.isEmpty() && subDomains != null) {
					if (!dataCache.subDomainsToDomainMap
							.containsKey(hostDomain)) {
						dataCache.subDomainsToDomainMap.put(hostDomain,
								subDomains);
					}
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

		if (!loggedIn) {
			Window.alert(LOGIN_WARNING);
			return;
		}

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(Integer result) {
				// If server sends rating already exists, the user will be asked
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
								(float) savedRating, replaceRating, callback);
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

		rateService.rateUrl(url, comment, (float) rating, replaceRating,
				callback);
	}

	public void receiveUserRatings() {
		if (!loggedIn) {
			Window.alert(LOGIN_WARNING);
			return;
		}
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<Rating>> callback = new AsyncCallback<List<Rating>>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<Rating> result) {
				wUpd.updateUserRatedUrls(result);
				dataCache.receivedUserRatings = result;
			}
		};
		rateService.getAllUserRatedUrls(callback);
	}

	/**
	 * Shows users login status
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
					loggedIn = true;
					receiveUserRatings();

					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert(SERVER_ERROR);
						}

						public void onSuccess(String email) {
							fP.htmlNewHtml.setHTML("Logged in as " + email);

							RateItServiceAsync rateService = (RateItServiceAsync) GWT
									.create(RateItService.class);

							AsyncCallback<String> callback = new AsyncCallback<String>() {

								public void onFailure(Throwable caught) {
									Window.alert(SERVER_ERROR);
								}

								@Override
								public void onSuccess(String url) {
									fP.htmlNewHtml_1.setVisible(true);
									fP.htmlNewHtml_1.setHTML("<a href=\"" + url
											+ "\">Logout</a>");
								}
							};
							rateService.getLogoutURL(REDIRECTION_URL, callback);
						}
					};

					rateService.getCurrentUserEmail(callback);
				} else {
					loggedIn = false;
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert(SERVER_ERROR);
						}

						@Override
						public void onSuccess(String url) {
							fP.htmlNewHtml.setHTML("Please log in "
									+ "<a href=\"" + url + "\">here</a>");
						}
					};
					rateService.getLoginURL(REDIRECTION_URL, callback);
				}
			}
		};
		rateService.isUserLoggedIn(callback);
	}

	/**
	 * User logout
	 */
	public void logout() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<String> callback = new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(String url) {
				fP.verticalPanel_7.setVisible(false);
				dataCache.receivedUserRatings = null;

				fP.htmlNewHtml.setHTML("Please log in " + "<a href=\"" + url
						+ "\">here</a>");
				fP.htmlNewHtml_1.setVisible(false);
				loggedIn = false;
			}
		};

		rateService.getLogoutURL(REDIRECTION_URL, callback);
	}

	public void init() {
		receiveTodaysTopDomains();
		receiveMonthsTopDomains();
		receiveYearsTopDomains();
		receiveTodaysTopUrls();
		receiveMonthsTopUrls();
		receiveYearsTopUrls();
		userAuthentication();
	}
}
