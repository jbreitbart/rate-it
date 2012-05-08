package it.rate.view;

import it.rate.client.RateItService;
import it.rate.client.RateItServiceAsync;
import it.rate.client.Rating;
import it.rate.client.TopUrl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HTML;

import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import com.google.gwt.i18n.client.HasDirection.Direction;

public class FrontPage {

	RootPanel rootPanel;
	VerticalPanel verticalPanel_2;
	HorizontalPanel horizontalPanel;
	SimplePanel simplePanel;
	Label lblRateIt;
	VerticalPanel verticalPanel;
	RadioButton rdbtnStar;
	RadioButton rdbtnStars;
	RadioButton rdbtnStars_1;
	RadioButton rdbtnStars_2;
	RadioButton rdbtnStars_3;
	SimplePanel simplePanel_13;
	VerticalPanel verticalPanel_1;
	TextBox txtbxHallo;
	Button btnEnter;
	HorizontalPanel horizontalPanel_1;
	VerticalPanel verticalPanel_3;
	Label lblNewLabel;
	TabPanel tabPanel;
	ListBox listBox;
	VerticalPanel verticalPanel_4;
	private ListBox listBox_1;
	private Label lblNewLabel_1;
	private HTML htmlNewHtml;
	private static final int TOP_COUNT = 10;
	private ListBox listBox_2;
	private ListBox listBox_3;
	private TextArea txtrOptionalEnterYour;

	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("deprecation")
	/**
	 * Site's visualization
	 */
	public void show() {
		// Associate the Main panel with the HTML host page.
		rootPanel = RootPanel.get("mainPane");
		rootPanel.setSize("800px", "800px");
		rootPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);

		verticalPanel_2 = new VerticalPanel();
		verticalPanel_2
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.add(verticalPanel_2, 10, 10);

		horizontalPanel = new HorizontalPanel();
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel_2.add(horizontalPanel);
		horizontalPanel.setWidth("771px");
		verticalPanel_2.setCellHorizontalAlignment(horizontalPanel,
				HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(20);

		simplePanel = new SimplePanel();
		horizontalPanel.add(simplePanel);
		horizontalPanel.setCellHorizontalAlignment(simplePanel,
				HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setCellVerticalAlignment(simplePanel,
				HasVerticalAlignment.ALIGN_MIDDLE);

		lblRateIt = new Label("Rate it!");
		simplePanel.setWidget(lblRateIt);
		lblRateIt.setSize("100%", "100%");
		lblRateIt.setWordWrap(false);
		lblRateIt.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblRateIt.setDirectionEstimator(true);
		lblRateIt.setStyleName("RateItLabel");

		verticalPanel = new VerticalPanel();
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel);
		horizontalPanel.setCellHorizontalAlignment(verticalPanel,
				HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setWidth("0");
		horizontalPanel.setCellVerticalAlignment(verticalPanel,
				HasVerticalAlignment.ALIGN_MIDDLE);

		rdbtnStar = new RadioButton("new name", "1 Star");
		verticalPanel.add(rdbtnStar);
		rdbtnStar.setDirectionEstimator(true);
		rdbtnStar.setValue(true);
		rdbtnStar.setWordWrap(false);

		rdbtnStars = new RadioButton("new name", "2 Stars");
		verticalPanel.add(rdbtnStars);

		rdbtnStars_1 = new RadioButton("new name", "3 Stars");
		verticalPanel.add(rdbtnStars_1);

		rdbtnStars_2 = new RadioButton("new name", "4 Stars");
		verticalPanel.add(rdbtnStars_2);

		rdbtnStars_3 = new RadioButton("new name", "5 Stars");
		rdbtnStars_3.setWordWrap(false);
		verticalPanel.add(rdbtnStars_3);
		rdbtnStars_3.setSize("", "");

		verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		horizontalPanel.setCellVerticalAlignment(verticalPanel_1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(verticalPanel_1,
				HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		btnEnter = new Button("Enter");
		horizontalPanel.add(btnEnter);
		horizontalPanel.setCellVerticalAlignment(btnEnter,
				HasVerticalAlignment.ALIGN_MIDDLE);
		btnEnter.setText("Rate");

		horizontalPanel_1 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_1);

		verticalPanel_3 = new VerticalPanel();
		horizontalPanel_1.add(verticalPanel_3);

		lblNewLabel = new Label("Top rated sites");
		verticalPanel_3.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel.setWordWrap(false);
		lblNewLabel.setStyleName("h1");

		tabPanel = new TabPanel();
		verticalPanel_3.add(tabPanel);

		listBox = new ListBox();
		tabPanel.add(listBox, "Year", false);
		listBox.setSize("5cm", "3cm");
		listBox.setMultipleSelect(true);
		listBox.setVisibleItemCount(10);
		listBox.setTitle("Double-klick for sub domains");

		listBox_2 = new ListBox();
		tabPanel.add(listBox_2, "Month", false);
		listBox_2.setSize("5cm", "3cm");
		listBox_2.setVisibleItemCount(5);
		listBox_2.setTitle("Double-klick for sub domains");

		listBox_3 = new ListBox();
		tabPanel.add(listBox_3, "Today", false);
		listBox_3.setSize("5cm", "3cm");
		listBox_3.setVisibleItemCount(5);
		listBox_3.setTitle("Double-klick for sub domains");

		verticalPanel_4 = new VerticalPanel();
		horizontalPanel_1.add(verticalPanel_4);
		verticalPanel_4.setVisible(false);

		lblNewLabel_1 = new Label("Sub Domains");
		verticalPanel_4.add(lblNewLabel_1);
		verticalPanel_4.setCellHorizontalAlignment(lblNewLabel_1, HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel_1.setWordWrap(false);

		listBox_1 = new ListBox();
		verticalPanel_4.add(listBox_1);
		verticalPanel_4.setCellHorizontalAlignment(listBox_1, HasHorizontalAlignment.ALIGN_CENTER);
		listBox_1.setVisibleItemCount(5);

		txtbxHallo = new TextBox();
		txtbxHallo.setStyleName("gwt-TextBox.passiv");
		txtbxHallo.setVisibleLength(50);
		verticalPanel_1.add(txtbxHallo);
		txtbxHallo.setSize("100%", "100%");
		verticalPanel_1.setCellHeight(txtbxHallo, "100%");
		verticalPanel_1.setCellWidth(txtbxHallo, "100%");
		txtbxHallo.setText("Enter URL");

		txtbxHallo.setFocus(true);

		txtrOptionalEnterYour = new TextArea();
		txtrOptionalEnterYour
				.setText("Optional: Enter your experience with that site!");
		verticalPanel_1.add(txtrOptionalEnterYour);
		txtrOptionalEnterYour.setWidth("100%");

		rootPanel.add(verticalPanel_2);
		
		htmlNewHtml = new HTML("", true);
		rootPanel.add(htmlNewHtml, 0, 0);
	}

	/**
	 * Adds handlers for widgets
	 */
	public void addHandlers() {

		/*
		 * URL textbox click handler
		 */
		txtbxHallo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				txtbxHallo.setText(null);
				txtrOptionalEnterYour.setText(null);
				txtbxHallo.setStylePrimaryName("gwt-ListBox");
			}

		});

		/*
		 * Comment textbox click handler
		 */
		txtbxHallo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				txtbxHallo.setText(null);
				txtrOptionalEnterYour.setText(null);
				txtbxHallo.setStylePrimaryName("gwt-ListBox");
			}

		});

		/*
		 * Top domains click handler
		 */
		listBox.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				// Show sub domain panel
				verticalPanel_4.setVisible(true);
				receiveSubDomains(listBox.getItemText(listBox.getItemCount()));
			}
		});
		
		listBox_2.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				// Show sub domain panel
				verticalPanel_4.setVisible(true);
				receiveSubDomains(listBox.getItemText(listBox.getItemCount()));
			}
		});
		
		listBox_3.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				// Show sub domain panel
				verticalPanel_4.setVisible(true);
				receiveSubDomains(listBox.getItemText(listBox.getItemCount()));
			}
		});

		/*
		 * Top domains tap panel selection handler
		 */
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Date startDate = new GregorianCalendar().getTime();
				Date endDate;
				// Years top
				if (event.getSelectedItem() == 0) {
					endDate = new GregorianCalendar(startDate.getYear()-1, startDate.getMonth(), startDate.getDay()).getTime();
					getTopUrls(TOP_COUNT, startDate, endDate);
				}
				// Months top
				if (event.getSelectedItem() == 1) {
					endDate = new GregorianCalendar(startDate.getYear(), startDate.getMonth()-1, startDate.getDay()).getTime();
					getTopUrls(TOP_COUNT, startDate, endDate);
				}
				// Todays top
				if (event.getSelectedItem() == 2) {
					endDate = new GregorianCalendar(startDate.getYear(), startDate.getMonth(), startDate.getDay()-1).getTime();
					getTopUrls(TOP_COUNT, startDate, endDate);
				}
			}

		});

		/*
		 * Rate button click handler
		 */
		btnEnter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				URL url = null;
				// Get text from user input boxes
				try{
					// Throws exception if URL is invalid
					url = new URL(txtbxHallo.getText());
				} catch (Exception e){
					txtbxHallo.setText(null);
					txtbxHallo.setStylePrimaryName("serverResponseLabelError");
					txtbxHallo.setText("Please enter a URL");
					return;
				}
				// Get host
				String host = url.getHost();
				String comment = txtrOptionalEnterYour.getText();
				// Get rating
				int rating = 0;
				if (rdbtnStar.getValue())
					rating = 1;
				if (rdbtnStars.getValue())
					rating = 2;
				if (rdbtnStars_1.getValue())
					rating = 3;
				if (rdbtnStars_2.getValue())
					rating = 4;
				if (rdbtnStars_3.getValue())
					rating = 5;
				// Calls method for sending the rating to server
				rateUrl(host, comment, rating);
			}
		});

		/*
		 * Rated URL klick handler for enter button press
		 */
		txtbxHallo.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					// Get text from user input boxes
					if (txtbxHallo.getText().isEmpty()) {
						Window.alert("Please enter a URL");
						return;
					}
					String url = txtbxHallo.getText();
					String comment = txtrOptionalEnterYour.getText();
					// Get rating
					int rating = 0;
					if (rdbtnStar.getValue())
						rating = 1;
					if (rdbtnStars.getValue())
						rating = 2;
					if (rdbtnStars_1.getValue())
						rating = 3;
					if (rdbtnStars_2.getValue())
						rating = 4;
					if (rdbtnStars_3.getValue())
						rating = 5;
					// Calls method for sending the rating to server
					rateUrl(url, comment, rating);
				}

			}
		});
	}

	/**
	 * Method which receives all sub domains for a URL from server and calls the
	 * method updateSubDomainList(List<Rating> result)
	 * 
	 * @param url
	 *            URL, which to receive from, all sub domains
	 */
	protected void receiveSubDomains(String url) {
		verticalPanel.setVisible(true);

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<Rating>> callback = new AsyncCallback<List<Rating>>() {

			public void onFailure(Throwable caught) {
				Window.alert("An error occured while retrieving the url list.");
			}

			@Override
			public void onSuccess(List<Rating> subDomains) {
				// Calls method for widget update
				updateSubDomainList((List<Rating>) subDomains);
			}
		};

		rateService.getSubDomains(url, callback);
	}

	/**
	 * Updates the sub domain list box widget
	 * 
	 * @param subDomains
	 *            List of all sub domains
	 */
	protected void updateSubDomainList(List<Rating> subDomains) {
		for (Rating r : subDomains) {
			listBox.addItem(r.getUrl());
		}
		listBox.setVisibleItemCount(subDomains.size());
		if (!listBox.isEnabled()) {
			listBox.setEnabled(true);
		}
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
	public void getTopUrls(int urlCount, Date startDate, Date endDate) {

		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<List<TopUrl>> callback = new AsyncCallback<List<TopUrl>>() {

			public void onFailure(Throwable caught) {
				Window.alert("An error occured while retrieving the url list.");
			}


			@Override
			public void onSuccess(List<TopUrl> topUrls) {
				updateTopUrlsList((List<TopUrl>) topUrls);
				
			}
		};

		rateService.getTopUrlsForPeriod(startDate, endDate, urlCount, callback);
	}

	/**
	 * Updates the top url list widget
	 * 
	 * @param topUrls
	 *            List of all top URLs
	 */
	protected void updateTopUrlsList(List<TopUrl> topUrls) {
		for (TopUrl r : topUrls) {
			listBox.addItem(r.getUrl());
		}
		listBox.setVisibleItemCount(topUrls.size());
		// TODO show url rating
	}

	/**
	 * Rates
	 */
	public void rateUrl(String url, String comment, int rating) {
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				Window.alert("An error occured while sending your rating."
						+ "Please try it again.");
			}

			@Override
			public void onSuccess(Boolean result) {
				Window.alert("Thank your for your rating!");				
			}
		};

		rateService.rateUrl(url, comment, (float) rating, callback);
	}
	
	
	/*
	 * 
	 */
	public void userAuthentication(){
		RateItServiceAsync rateService = (RateItServiceAsync) GWT
				.create(RateItService.class);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				Window.alert("Could not get user information from server.");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result){
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert("Could not get user information from server.");
						}

						@Override
						public void onSuccess(String email) {
							htmlNewHtml.setHTML("logged in as " + email);
						}
					};

					rateService.getCurrentUserEmail(callback);
				}
				else{
					RateItServiceAsync rateService = (RateItServiceAsync) GWT
							.create(RateItService.class);

					AsyncCallback<String> callback = new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							Window.alert("Could not get user information from server.");
						}

						@Override
						public void onSuccess(String url) {
							htmlNewHtml.setHTML("please log in " + "<a href=\"" + url + ">here</a>");
						}
					};

					rateService.getLoginURL("", callback);
				}
			}
		};

		rateService.isUserLoggedIn(callback);
	}

}
