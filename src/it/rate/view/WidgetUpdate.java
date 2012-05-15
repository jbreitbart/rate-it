package it.rate.view;

import it.rate.client.Rating;
import it.rate.client.TopUrl;

import java.util.List;

import com.google.gwt.user.client.ui.ListBox;

public class WidgetUpdate {

	FrontPage fP;

	public WidgetUpdate(FrontPage frontPage) {
		this.fP = frontPage;
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
		if (comment.equals("Optional: Enter your experience with that site!")) {
			fP.txtrOptionalEnterYour.setText(null);
		}
	}

	public boolean checkInput() {
		String url = fP.txtbxHallo.getText();
		if (url.contains(" ") || !url.contains(".") || url.equals(null)) {
			fP.txtbxHallo.setText(null);
			fP.txtbxHallo.setStylePrimaryName("serverResponseLabelError");
			fP.txtbxHallo.setText("Please enter a URL");
			return false;
		}
		return true;
	}

	public void showRating(Float rating) {
		fP.lblNewLabel_2.setVisible(true);
		fP.lblNewLabel_2.setText("Rating");
		if (rating == null) {
			fP.lblNewLabel_2.setText("No item selected");
			return;
		}
		fP.lblNewLabel_3.setVisible(true);
		fP.lblNewLabel_3.setText(rating.toString());
	}

	/**
	 * Updates the sub domain list box widget
	 * 
	 * @param subDomains
	 *            List of all sub domains
	 */
	public void updateSubDomainList(List<Rating> subDomains) {
		try {
			for (Rating r : subDomains) {
				fP.listBox_1.addItem(r.getUrl());
			}
			fP.verticalPanel_4.setVisible(true);
			fP.listBox_1.setVisibleItemCount(subDomains.size());
			if (!fP.listBox_1.isEnabled()) {
				fP.listBox_1.setEnabled(true);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Updates the top url list widget
	 * 
	 * @param topUrls
	 *            List of all top URLs
	 */
	public void updateTopUrlsList(List<TopUrl> topUrls, ListBox box) {
		try {
			for (TopUrl r : topUrls) {
				box.addItem(r.getUrl());
			}
			box.setVisibleItemCount(topUrls.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUserRatedUrls(List<Rating> userRatings) {
		fP.listBox_7.setVisible(true);
		try {
			for (Rating r : userRatings) {
				fP.listBox_7.addItem(r.getUrl());
			}
			fP.listBox_7.setVisibleItemCount(userRatings.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showUserRating(Float rating) {
		fP.lblNewLabel_4.setVisible(true);
		fP.lblNewLabel_4.setText("Rating");
		if (rating == null) {
			fP.lblNewLabel_4.setText("No item selected");
			return;
		}
		fP.lblNewLabel_5.setVisible(true);
		fP.lblNewLabel_5.setText(rating.toString());

	}

	public void updateDomainList(List<TopUrl> topDomains,
			ListBox box) {
		try {
			for (TopUrl r : topDomains) {
				box.addItem(r.getUrl());
			}
			box.setVisibleItemCount(topDomains.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
