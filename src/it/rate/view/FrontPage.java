package it.rate.view;

import it.rate.client.RateItService;
import it.rate.client.RateItServiceAsync;
import it.rate.data.Rating;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.ListBox;

public class FrontPage{

	/**
	 * @wbp.parser.entryPoint
	 */
	public void show(){
		// Associate the Main panel with the HTML host page.
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("800", "600");
		
		VerticalPanel verticalPanel_2 = new VerticalPanel();
		rootPanel.add(verticalPanel_2);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel);
		horizontalPanel.setSpacing(20);
		
		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel.add(simplePanel);
		horizontalPanel.setCellHorizontalAlignment(simplePanel, HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setCellVerticalAlignment(simplePanel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		Label lblRateIt = new Label("Rate it!");
		simplePanel.setWidget(lblRateIt);
		lblRateIt.setSize("100%", "100%");
		lblRateIt.setWordWrap(false);
		lblRateIt.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblRateIt.setDirectionEstimator(true);
		lblRateIt.setStyleName("RateItLabel");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		horizontalPanel.setCellVerticalAlignment(verticalPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		RadioButton rdbtnStar = new RadioButton("new name", "1 Star");
		verticalPanel.add(rdbtnStar);
		rdbtnStar.setValue(true);
		rdbtnStar.setWordWrap(false);
		
		RadioButton rdbtnStars = new RadioButton("new name", "2 Stars");
		verticalPanel.add(rdbtnStars);
		
		RadioButton rdbtnStars_1 = new RadioButton("new name", "3 Stars");
		verticalPanel.add(rdbtnStars_1);
		
		RadioButton rdbtnStars_2 = new RadioButton("new name", "4 Stars");
		verticalPanel.add(rdbtnStars_2);
		
		RadioButton rdbtnStars_3 = new RadioButton("new name", "5 Stars");
		verticalPanel.add(rdbtnStars_3);
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		horizontalPanel.setCellVerticalAlignment(verticalPanel_1, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(verticalPanel_1, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		SimplePanel simplePanel_1 = new SimplePanel();
		verticalPanel_1.add(simplePanel_1);
		verticalPanel_1.setCellVerticalAlignment(simplePanel_1, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setCellWidth(simplePanel_1, "400");
		
		TextBox txtbxHallo = new TextBox();
		simplePanel_1.setWidget(txtbxHallo);
		txtbxHallo.setSize("100%", "100%");
		txtbxHallo.setText("Enter URL");
		
		SimplePanel simplePanel_2 = new SimplePanel();
		verticalPanel_1.add(simplePanel_2);
		verticalPanel_1.setCellVerticalAlignment(simplePanel_2, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setCellWidth(simplePanel_2, "400");
		
		TextArea txtrOptionalYourExperience = new TextArea();
		simplePanel_2.setWidget(txtrOptionalYourExperience);
		txtrOptionalYourExperience.setText("Optional: Enter your experience with that site");
		txtrOptionalYourExperience.setSize("100%", "100%");
		verticalPanel_1.setCellHorizontalAlignment(txtrOptionalYourExperience, HasHorizontalAlignment.ALIGN_CENTER);
		
		SimplePanel simplePanel_3 = new SimplePanel();
		horizontalPanel.add(simplePanel_3);
		horizontalPanel.setCellVerticalAlignment(simplePanel_3, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(simplePanel_3, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnEnter = new Button("Enter");
		btnEnter.setText("Rate");
		simplePanel_3.setWidget(btnEnter);
		btnEnter.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_1);
		
		VerticalPanel verticalPanel_3 = new VerticalPanel();
		horizontalPanel_1.add(verticalPanel_3);
		
		SimplePanel simplePanel_6 = new SimplePanel();
		verticalPanel_3.add(simplePanel_6);
		verticalPanel_3.setCellHorizontalAlignment(simplePanel_6, HasHorizontalAlignment.ALIGN_CENTER);
		
		Label lblNewLabel = new Label("Top rated sites");
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel.setWordWrap(false);
		lblNewLabel.setStyleName("h1");
		simplePanel_6.setWidget(lblNewLabel);
		lblNewLabel.setSize("100%", "100%");
		
		TabPanel tabPanel = new TabPanel();
		verticalPanel_3.add(tabPanel);
		
		SimplePanel simplePanel_4 = new SimplePanel();
		tabPanel.add(simplePanel_4, "Year", false);
		simplePanel_4.setSize("200px", "300px");
		
		ListBox listBox = new ListBox();
		listBox.setMultipleSelect(true);
		simplePanel_4.setWidget(listBox);
		listBox.setSize("100%", "100%");
		listBox.setVisibleItemCount(10);
		
		SimplePanel simplePanel_5 = new SimplePanel();
		tabPanel.add(simplePanel_5, "Month", false);
		simplePanel_5.setSize("200px", "300px");
		
		Tree tree = new Tree();
		tree.setAnimationEnabled(true);
		simplePanel_5.setWidget(tree);
		tree.setSize("100%", "100%");
		
		TreeItem trtmDomain = new TreeItem("Domain1");
		tree.addItem(trtmDomain);
		
		SimplePanel simplePanel_7 = new SimplePanel();
		tabPanel.add(simplePanel_7, "Today", false);
		simplePanel_7.setSize("5cm", "3cm");
		
		VerticalPanel verticalPanel_4 = new VerticalPanel();
		horizontalPanel_1.add(verticalPanel_4);
		
		txtbxHallo.setFocus(true);

		// TODO Move cursor focus to the input box.
		// Example listener
	    // Listen for mouse events on the Add button.
	    btnEnter.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  RateItServiceAsync rateService = (RateItServiceAsync) GWT.create(RateItService.class);

	    	  // (2) Create an asynchronous callback to handle the result.
	    	  //
	    	  AsyncCallback callback = new AsyncCallback() {
	    	    public void onSuccess(Object result) {
	    	      // do some UI stuff to show success
	    	    }

	    	    public void onFailure(Throwable caught) {
	    	      // do some UI stuff to show failure
	    	    }
	    	  };

	    	  // (3) Make the call. Control flow will continue immediately and later
	    	  // 'callback' will be invoked when the RPC completes.
	    	  //
	    	  rateService.rateUrl("testUser", "test.com", "testCom", (float)5.0, callback);

	      }
	    });
	}

}
