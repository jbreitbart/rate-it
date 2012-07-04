package it.rate.workload;

import it.rate.Constants;
import it.rate.client.RateItService;
import it.rate.client.RateItServiceAsync;
import it.rate.client.TopUrl;
import it.rate.view.FrontPage;
import it.rate.view.WidgetUpdate;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientSideWorkload implements Constants {
	
	FrontPage fP;
	WidgetUpdate wUpd;
	private static volatile int counter = 0;
	public long startTime;
	public int max = -1;
	
	public ClientSideWorkload(FrontPage frontPage, WidgetUpdate widgetUpdate, int max) {
		this.fP = frontPage;
		this.wUpd = widgetUpdate;
		this.max = max;
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
				wUpd.enableTestButtons();
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				counter++;
				if(counter == 1){
					startTime = System.currentTimeMillis();
					fP.label.setVisible(true);
					fP.label.setText("Top Urls test begins");
				} else if(counter == max - 1){
					long elepsedTime = System.currentTimeMillis() - startTime;
					fP.label.setVisible(true);
					fP.label.setText("Test finished successfully. ElepsedTime: " + elepsedTime + "ms");
					wUpd.enableTestButtons();
				}
			}
		};

		rateService.getTopUrlsForDay(callback);
	}
	
	
	public void receiveTodaysTopHosts() {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				fP.label.setVisible(true);
				fP.label.setText("Error occured through test execution");
				wUpd.enableTestButtons();			
			}

			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				counter++;
				if(counter == 1){
					startTime = System.currentTimeMillis();
					fP.label.setVisible(true);
					fP.label.setText("Top Hosts test begins");
				} else if(counter == max - 1){
					long elepsedTime = System.currentTimeMillis() - startTime;
					fP.label.setVisible(true);
					fP.label.setText("Test finished successfully. ElepsedTime: " + elepsedTime + "ms");
					wUpd.enableTestButtons();
				}
			}
		};

		rateService.getTopHostsForDay(callback);
	}

	public void rate(String mail, String url, String comment, float rating) {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				fP.label.setVisible(true);
				fP.label.setText("Error occured through test execution");
				wUpd.enableTestButtons();				
			}

			@Override
			public void onSuccess(Integer result) {
				counter++;
				if(counter == 1){
					startTime = System.currentTimeMillis();
					fP.label.setVisible(true);
					fP.label.setText("Rating test begins");
				} else if(counter == max - 1){
					long elepsedTime = System.currentTimeMillis() - startTime;
					fP.label.setVisible(true);
					fP.label.setText("Test finished successfully. ElepsedTime: " + elepsedTime + "ms");
					wUpd.enableTestButtons();
				}
			}
		};

		rateService.ratingTest(mail, url, comment, rating, callback);
	}
}
