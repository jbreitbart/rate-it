package it.rate.view;

import it.rate.Constants;
import it.rate.client.Rating;
import it.rate.client.TopUrl;
import java.util.List;
import com.google.gwt.user.client.ui.ListBox;

public class WidgetUpdate implements Constants {

	FrontPage fP;
	ServerDataCache dataCache;
	ListBox urlYearBox;
	ListBox urlMonthBox;
	ListBox urlDayBox;
	ListBox domainYearBox;
	ListBox domainMonthBox;
	ListBox domainDayBox;

	public WidgetUpdate(FrontPage frontPage, ServerDataCache dataCache) {
		this.fP = frontPage;
		this.dataCache = dataCache;
	}

	public void showRating(Float rating) {
		fP.lblNewLabel_2.setVisible(true);
		fP.lblNewLabel_2.setText("Rating");
		if (rating == null) {
			fP.lblNewLabel_2.setText(NO_SELECTION);
			return;
		}
		fP.lblNewLabel_3.setVisible(true);
		fP.lblNewLabel_3.setText(rating.toString());
	}

	public void showRating(Float rating, String comment) {
		fP.verticalPanel_7.setVisible(true);
		fP.textBox.setVisible(false);
		if (rating == null) {
			fP.lblNewLabel_4.setText(NO_SELECTION);
			return;
		}
		if (!comment.equals(null) && !comment.isEmpty()) {
			fP.textBox.setVisible(true);
			fP.textBox.setText(comment);
		}
		fP.lblNewLabel_5.setText(rating.toString());
	}

	/**
	 * Updates the sub domain list box widget
	 * 
	 * @param subDomains
	 *            List of all sub domains
	 */
	public void updateSubDomainList(List<Rating> receivedSubDomains) {
		fP.listBox_1.clear();
		try {
			for (Rating r : receivedSubDomains) {
				fP.listBox_1.addItem(r.getUrl());
			}
			fP.listBox_1.setVisibleItemCount(receivedSubDomains.size());
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Updates the url top list widget
	 * 
	 * @param topUrls
	 *            List of all top URLs
	 */
	public void updateTopUrlsList(List<TopUrl> topUrls, Period period) {
		ListBox box = null;
		urlYearBox = fP.listBox;
		urlMonthBox = fP.listBox_2;
		urlDayBox = fP.listBox_3;
		switch (period) {
		case DAY:
			box = urlDayBox;
			break;
		case MONTH:
			box = urlMonthBox;
			break;
		case YEAR:
			box = urlYearBox;
			break;
		default:
			box = urlYearBox;
		}
		box.clear();
		try {
			for (TopUrl r : topUrls) {
				box.addItem(r.getUrl());
			}
			box.setVisibleItemCount(topUrls.size());
			if(period == Period.YEAR)
				fP.tabPanel.selectTab(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the domain top list widget
	 * 
	 * @param topUrls
	 *            List of all top URLs
	 */
	public void updateTopDomainsList(List<TopUrl> topDomains, Period period) {
		ListBox box = null;
		domainYearBox = fP.listBox_4;
		domainMonthBox = fP.listBox_5;
		domainDayBox = fP.listBox_6;
		switch (period) {
		case DAY:
			box = domainDayBox;
			break;
		case MONTH:
			box = domainMonthBox;
			break;
		case YEAR:
			box = domainYearBox;
			break;
		default:
			box = domainYearBox;
		}
		box.clear();
		try {
			for (TopUrl r : topDomains) {
				box.addItem(r.getUrl());
			}
			box.setVisibleItemCount(topDomains.size());
			fP.tabPanel_1.selectTab(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showUserRatings(List<Rating> list) {
		fP.btnShowOwnRatings.setVisible(false);
		fP.verticalPanel_8.setVisible(true);
		fP.listBox_7.clear();
		try {
			for (Rating r : list) {
				fP.listBox_7.addItem(r.getUrl());
			}
			fP.listBox_7.setVisibleItemCount(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearUrlBox() {
		String url = fP.txtbxHallo.getText();
		if (url.contains(" ") || !url.contains(".")) {
			fP.txtbxHallo.setText(null);
			fP.txtbxHallo.setStylePrimaryName("gwt-ListBox");
		}
	}

	public void clearCommentBox() {
		String comment = fP.txtrOptionalEnterYour.getText();
		if (comment.equals(COMMENTS_BOX_DEFAULT)) {
			fP.txtrOptionalEnterYour.setText(null);
		}
	}

	public boolean checkInput() {
		String url = fP.txtbxHallo.getText();
		if (url.contains(" ") || !url.contains(".") || url.equals(null)) {
			fP.txtbxHallo.setText(null);
			fP.txtbxHallo.setStylePrimaryName("serverResponseLabelError");
			fP.txtbxHallo.setText(URL_BOX_DEFAULT);
			return false;
		}
		return true;
	}
	
	public void disableTestButtons(){
		fP.btnTopUrlsTest.setEnabled(false);
		fP.btnRatingTest.setEnabled(false);
		fP.btnNewButton_1.setEnabled(false);
	}

	public void enableTestButtons(){
		fP.btnTopUrlsTest.setEnabled(true);
		fP.btnRatingTest.setEnabled(true);
		fP.btnNewButton_1.setEnabled(true);
	}
	
	public void showUserLoggedIn(String userId){
		fP.horizontalPanel_3.setVisible(true);
		fP.lblUserMail.setText("You are logged in as " + userId);
		fP.btnNewButton_2.setText("Logout");
	}
	
	public void showUserNotLoggedIn(){
		fP.horizontalPanel_3.setVisible(true);
		fP.lblUserMail.setText("You are not logged in");
		fP.btnNewButton_2.setText("Login");
	}

}
