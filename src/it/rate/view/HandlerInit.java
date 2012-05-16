package it.rate.view;

import it.rate.client.Rating;
import it.rate.client.TopUrl;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;

public class HandlerInit {

	FrontPage fP;
	RPC rpc;
	WidgetUpdate wUpd;
	int TOP_COUNT;

	public List<TopUrl> receivedYearsTopUrls;
	public List<TopUrl> receivedMonthsTopUrls;
	public List<TopUrl> receivedTodaysTopUrls;
	public List<TopUrl> receivedYearsTopDomains;
	public List<TopUrl> receivedMonthsTopDomains;
	public List<TopUrl> receivedTodaysTopDomains;
	public List<Rating> receivedSubDomains;
	public List<Rating> receivedUserRatings;

	public HandlerInit(FrontPage frontPage, RPC rpc, WidgetUpdate widgetUpdate,
			final int TOP_COUNT) {
		this.fP = frontPage;
		this.rpc = rpc;
		this.wUpd = widgetUpdate;
		this.TOP_COUNT = TOP_COUNT;
	}

	/**
	 * Initializes handlers for widgets
	 */
	public void addHandlers() {

		/*
		 * URL textbox click handler
		 */
		fP.txtbxHallo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				wUpd.clearCommentBox();
				wUpd.clearUrlBox();
			}

		});

		/*
		 * Comment textbox click handler
		 */
		fP.txtrOptionalEnterYour.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				wUpd.clearCommentBox();
			}

		});

		/*
		 * Top URLs click handler. Shows rating.
		 */
		fP.listBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (receivedYearsTopUrls == null) {
					rpc.receiveTopUrls(TOP_COUNT, new TimePeriod().oneYearBack,
							new TimePeriod().today);
					receivedYearsTopUrls = rpc.receivedTopUrls;
				}
				Float rating = null;
				try {
					rating = receivedYearsTopUrls.get(
							fP.listBox.getSelectedIndex()).getAveradgeRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}

			}

		});

		fP.listBox_2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (receivedMonthsTopUrls == null) {
					rpc.receiveTopUrls(TOP_COUNT,
							new TimePeriod().oneMonthBack,
							new TimePeriod().today);
					receivedMonthsTopUrls = rpc.receivedTopUrls;
				}
				Float rating = null;
				try {
					rating = receivedMonthsTopUrls.get(
							fP.listBox_2.getSelectedIndex())
							.getAveradgeRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}
			}

		});

		fP.listBox_3.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (receivedTodaysTopUrls == null) {
					rpc.receiveTopUrls(TOP_COUNT, new TimePeriod().oneDayBack,
							new TimePeriod().today);
					receivedTodaysTopUrls = rpc.receivedTopUrls;
				}
				Float rating = null;
				try {
					rating = receivedTodaysTopUrls.get(
							fP.listBox_3.getSelectedIndex())
							.getAveradgeRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}
			}

		});

		/*
		 * Top URLs click handler
		 */
		fP.listBox_4.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				rpc.receiveSubDomains(fP.listBox_4.getItemText(fP.listBox_4
						.getItemCount()));
				receivedSubDomains = rpc.receivedSubDomains;
			}
		});

		fP.listBox_5.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				rpc.receiveSubDomains(fP.listBox_5.getItemText(fP.listBox_5
						.getItemCount()));
				receivedSubDomains = rpc.receivedSubDomains;
			}
		});

		fP.listBox_6.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				rpc.receiveSubDomains(fP.listBox_6.getItemText(fP.listBox_6
						.getItemCount()));
				receivedSubDomains = rpc.receivedSubDomains;
			}
		});

		/*
		 * Top URLs tap panel selection handler
		 */
		fP.tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {

			@SuppressWarnings("deprecation")
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				TimePeriod calendar = new TimePeriod();

				// Years top
				if (event.getSelectedItem() == 0) {
					if (receivedYearsTopUrls == null) {
						rpc.receiveTopUrls(TOP_COUNT, calendar.oneYearBack,
								calendar.today);
						receivedYearsTopUrls = rpc.receivedTopUrls;
					}
					wUpd.updateTopUrlsList(receivedYearsTopUrls, fP.listBox);
				}
				// Months top
				if (event.getSelectedItem() == 1) {
					if (receivedMonthsTopUrls == null) {
						rpc.receiveTopUrls(TOP_COUNT, calendar.oneMonthBack,
								calendar.today);
						receivedMonthsTopUrls = rpc.receivedTopUrls;
					}
					wUpd.updateTopUrlsList(receivedMonthsTopUrls, fP.listBox_2);
				}
				// Todays top
				if (event.getSelectedItem() == 2) {
					if (receivedTodaysTopUrls == null) {
						rpc.receiveTopUrls(TOP_COUNT, calendar.oneDayBack,
								calendar.today);
						receivedTodaysTopUrls = rpc.receivedTopUrls;
					}
					wUpd.updateTopUrlsList(receivedTodaysTopUrls, fP.listBox_3);
				}
			}

		});
		
		/*
		 * Top domains tap panel selection handler
		 */
		fP.tabPanel_1.addSelectionHandler(new SelectionHandler<Integer>() {

			@SuppressWarnings("deprecation")
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				TimePeriod calendar = new TimePeriod();

				// Years top
				if (event.getSelectedItem() == 0) {
					if (receivedYearsTopDomains == null) {
						rpc.receiveTopDomains(TOP_COUNT, calendar.oneYearBack,
								calendar.today);
						receivedYearsTopDomains = rpc.receivedTopDomains;
					}
					wUpd.updateDomainList(receivedYearsTopDomains, fP.listBox_4);
				}
				// Months top
				if (event.getSelectedItem() == 1) {
					if (receivedMonthsTopDomains == null) {
						rpc.receiveTopDomains(TOP_COUNT, calendar.oneMonthBack,
								calendar.today);
						receivedMonthsTopDomains = rpc.receivedTopDomains;
					}
					wUpd.updateDomainList(receivedMonthsTopDomains, fP.listBox_5);
				}
				// Todays top
				if (event.getSelectedItem() == 2) {
					if (receivedTodaysTopDomains == null) {
						rpc.receiveTopDomains(TOP_COUNT, calendar.oneDayBack,
								calendar.today);
						receivedTodaysTopDomains = rpc.receivedTopDomains;
					}
					wUpd.updateDomainList(receivedTodaysTopDomains, fP.listBox_6);
				}
			}

		});
		/*
		 * Rate button click handler
		 */
		fP.btnEnter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Get text from user input boxes
				if (!wUpd.checkInput()) {
					return;
				}
				String url = fP.txtbxHallo.getText();
				String comment = fP.txtrOptionalEnterYour.getText();
				// Get rating
				int rating = 0;
				if (fP.rdbtnStar.getValue())
					rating = 1;
				if (fP.rdbtnStars.getValue())
					rating = 2;
				if (fP.rdbtnStars_1.getValue())
					rating = 3;
				if (fP.rdbtnStars_2.getValue())
					rating = 4;
				if (fP.rdbtnStars_3.getValue())
					rating = 5;
				// Calls method for sending the rating to server
				rpc.rateUrl(url, comment, rating);
			}
		});

		/*
		 * Rated URL klick handler for enter button press
		 */
		fP.txtbxHallo.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					// Get text from user input boxes
					if (!wUpd.checkInput()) {
						return;
					}
					String url = fP.txtbxHallo.getText();
					String comment = fP.txtrOptionalEnterYour.getText();
					// Get rating
					int rating = 0;
					if (fP.rdbtnStar.getValue())
						rating = 1;
					if (fP.rdbtnStars.getValue())
						rating = 2;
					if (fP.rdbtnStars_1.getValue())
						rating = 3;
					if (fP.rdbtnStars_2.getValue())
						rating = 4;
					if (fP.rdbtnStars_3.getValue())
						rating = 5;
					// Calls method for sending the rating to server
					rpc.rateUrl(url, comment, rating);
				}

			}
		});
		
		fP.btnSeeOwnRatings.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				rpc.receiveUserRatings();
				receivedUserRatings = rpc.receivedUserRatings;
				wUpd.updateUserRatedUrls(receivedUserRatings);
			}
		});
		
		fP.listBox_7.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (receivedUserRatings == null) {
					rpc.receiveUserRatings();
					receivedUserRatings = rpc.receivedUserRatings;
				}
				Float rating = null;
				try {
					rating = receivedUserRatings.get(
							fP.listBox_7.getSelectedIndex())
							.getRating();
					wUpd.showUserRating(rating);
				} catch (Exception e) {
					wUpd.showUserRating(null);
				}
			}

		});
	}
}
