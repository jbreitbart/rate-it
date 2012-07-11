package it.rate.view;

import it.rate.Constants;
import it.rate.workload.ClientSideWorkload;
import it.rate.workload.RandomString;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

public class HandlerInit implements Constants {

	FrontPage fP;
	RPC rpc;
	WidgetUpdate wUpd;
	ServerDataCache dataCache;

	public HandlerInit(FrontPage frontPage, RPC rpc, WidgetUpdate widgetUpdate,
			ServerDataCache dataCache) {
		this.fP = frontPage;
		this.rpc = rpc;
		this.wUpd = widgetUpdate;
		this.dataCache = dataCache;
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
				fP.verticalPanel_4.setVisible(false);
				Float rating = null;
				try {
					rating = dataCache.receivedYearsTopUrls.get(
							fP.listBox.getSelectedIndex()).getAverageRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}

			}

		});

		fP.listBox_2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fP.verticalPanel_4.setVisible(false);
				Float rating = null;
				try {
					rating = dataCache.receivedMonthsTopUrls.get(
							fP.listBox_2.getSelectedIndex()).getAverageRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}
			}

		});

		fP.listBox_3.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fP.verticalPanel_4.setVisible(false);
				Float rating = null;
				try {
					rating = dataCache.receivedTodaysTopUrls.get(
							fP.listBox_3.getSelectedIndex()).getAverageRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}
			}

		});

		/*
		 * Top domains click handler. Shows rating.
		 */
		fP.listBox_4.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Float rating = null;
				try {
					rating = dataCache.receivedYearsTopDomains.get(
							fP.listBox_4.getSelectedIndex()).getAverageRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}

			}

		});

		fP.listBox_5.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Float rating = null;
				try {
					rating = dataCache.receivedMonthsTopDomains.get(
							fP.listBox_5.getSelectedIndex()).getAverageRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}
			}

		});

		fP.listBox_6.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Float rating = null;
				try {
					rating = dataCache.receivedTodaysTopDomains.get(
							fP.listBox_6.getSelectedIndex()).getAverageRating();
					wUpd.showRating(rating);
				} catch (Exception e) {
					wUpd.showRating(null);
				}
			}

		});

		/*
		 * Top domains double-click handler. Shows subdomains.
		 */
		fP.listBox_4.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				try {
					rpc.receiveSubDomains(fP.listBox_4
							.getItemText(fP.listBox_4.getSelectedIndex()));
					fP.verticalPanel_4.setVisible(true);
				} catch (Exception e) {
					wUpd.updateSubDomainList(null);
				}
			}
		});

		fP.listBox_5.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				try {
					rpc.receiveSubDomains(fP.listBox_5
							.getItemText(fP.listBox_5.getSelectedIndex()));
					fP.verticalPanel_4.setVisible(true);
				} catch (Exception e) {
					wUpd.updateSubDomainList(null);
				}
			}
		});

		fP.listBox_6.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				try {
					rpc.receiveSubDomains(fP.listBox_6
							.getItemText(fP.listBox_6.getSelectedIndex()));
					fP.verticalPanel_4.setVisible(true);
				} catch (Exception e) {
					wUpd.updateSubDomainList(null);
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
		 * Rated URL click handler for enter button press
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

		fP.listBox_7.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Float rating = null;
				String comment = null;
				try {
					rating = dataCache.receivedUserRatings.get(
							fP.listBox_7.getSelectedIndex()).getRating();
					comment = dataCache.receivedUserRatings.get(
							fP.listBox_7.getSelectedIndex()).getComment();
					wUpd.showRating(rating, comment);
				} catch (Exception e) {
					wUpd.showRating(null, null);
				}
			}

		});

		/*
		 * Refresh buttons click handler
		 */
		fP.btnNewButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				rpc.receiveUserRatings();
			}

		});

		fP.btnRefresh_1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fP.verticalPanel_4.setVisible(false);
				rpc.receiveTodaysTopUrls();
				rpc.receiveMonthsTopUrls();
				rpc.receiveYearsTopUrls();
			}

		});

		fP.btnRefresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fP.verticalPanel_4.setVisible(false);
				rpc.receiveTodaysTopDomains();
				rpc.receiveMonthsTopDomains();
				rpc.receiveYearsTopDomains();
			}

		});

		/*
		 * Admin Panel buttons click handler
		 */
		fP.clearCacheBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				rpc.clearServerCache();
			}
		});

		fP.recalculateTopsBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				rpc.recalculateTops();

			}
		});

		fP.btnRunWorkloadTest.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (fP.verticalPanel_9.isVisible()) {
					fP.verticalPanel_9.setVisible(false);
				}
				if (!fP.verticalPanel_9.isVisible()) {
					fP.verticalPanel_9.setVisible(true);
				}
			}

		});
		
		fP.btnClearDb.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				rpc.clearDB();
				
			}
		});

		/*
		 * Workload tests buttons click handler
		 */

		fP.btnTopUrlsTest.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				wUpd.disableTestButtons();
				int count = 0;
				try {
					count = Integer.valueOf(fP.integerBox.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				ClientSideWorkload work = new ClientSideWorkload(fP, wUpd,
						count);
				for (int i = 0; i < count; i++) {
					work.receiveTodaysTopUrls();
				}
			}
		});

		fP.btnNewButton_1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				wUpd.disableTestButtons();
				int count = 0;
				try {
					count = Integer.valueOf(fP.integerBox.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				ClientSideWorkload work = new ClientSideWorkload(fP, wUpd,
						count);
				for (int i = 0; i < count; i++) {
					work.receiveTodaysTopHosts();
				}
			}
		});

		fP.btnRatingTest.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				wUpd.disableTestButtons();
				int count = 0;
				try {
					count = Integer.valueOf(fP.integerBox.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				ClientSideWorkload work = new ClientSideWorkload(fP, wUpd,
						count);
				for (int i = 0; i < count; i++) {
					work.rate(RandomString.nextEmail(), RandomString.nextUrl(),
							RandomString.nextComment(), 2);
				}
			}
		});

		fP.btnClearDb.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				rpc.clearDB();
			}

		});
		
		fP.btnShowOwnRatings.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				rpc.receiveUserRatings();				
			}
		});
		
		fP.btnNewButton_2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				rpc.userAuthentication();
			}
		});
	}
}
