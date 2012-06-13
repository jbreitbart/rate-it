package it.rate.workload;

import it.rate.Constants;
import it.rate.Constants.Period;
import it.rate.client.RateItService;
import it.rate.client.RateItServiceAsync;
import it.rate.client.TopUrl;
import it.rate.view.FrontPage;
import it.rate.view.ServerDataCache;
import it.rate.view.WidgetUpdate;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientSideWorkload implements Constants {
	
	FrontPage fP;
	public static int todayUrlsCount = 1;
	
	public ClientSideWorkload(FrontPage frontPage) {
		this.fP = frontPage;
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
				fP.label.setVisible(true);
				fP.label.setText("Error occured through test execution");
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				todayUrlsCount++;
				if(todayUrlsCount == 1){
					fP.label.setVisible(true);
					fP.label.setText("Test begins");
				}
				if(todayUrlsCount == NUMBER_TOP_URLS_CALLS){
					fP.label.setVisible(true);
					fP.label.setText("Test finished successfully");
				}
			}
		};

		rateService.getTopUrlsForDay(TOP_COUNT, callback);
	}
//
//	/**
//	 * Receives top URLs from server for last month
//	 * 
//	 * @param urlCount
//	 *            Counter for top list
//	 * @param startDate
//	 *            Start date for calculating top list
//	 * @param endDate
//	 *            End date for calculating top list
//	 */
//	public void receiveMonthsTopUrls() {
//
//		RateItServiceAsync rateService = (RateItServiceAsync) GWT
//				.create(RateItService.class);
//
//		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {
//
//			public void onFailure(Throwable caught) {
//				Window.alert(SERVER_ERROR);
//			}
//
//			@Override
//			public void onSuccess(List<TopUrl> topUrls) {
//				wUpd.updateTopUrlsList(topUrls, Period.MONTH);
//				dataCache.receivedMonthsTopUrls = topUrls;
//			}
//		};
//
//		rateService.getTopUrlsForMonth(TOP_COUNT, callback);
//	}
//
//	/**
//	 * Receives top URLs from server for last year
//	 * 
//	 * @param urlCount
//	 *            Counter for top list
//	 * @param startDate
//	 *            Start date for calculating top list
//	 * @param endDate
//	 *            End date for calculating top list
//	 */
//	public void receiveYearsTopUrls() {
//
//		RateItServiceAsync rateService = (RateItServiceAsync) GWT
//				.create(RateItService.class);
//
//		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {
//
//			public void onFailure(Throwable caught) {
//				Window.alert(SERVER_ERROR);
//			}
//
//			@Override
//			public void onSuccess(List<TopUrl> topUrls) {
//				wUpd.updateTopUrlsList(topUrls, Period.YEAR);
//				dataCache.receivedYearsTopUrls = topUrls;
//			}
//		};
//
//		rateService.getTopUrlsForYear(TOP_COUNT, callback);
//	}
}
