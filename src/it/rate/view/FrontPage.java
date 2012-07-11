package it.rate.view;

import it.rate.Constants;

import com.google.gwt.dom.client.Style.Position;
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
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.DockPanel;

public class FrontPage implements Constants {

	public RootPanel rootPanel;
	public VerticalPanel verticalPanel_2;
	public HorizontalPanel horizontalPanel;
	public SimplePanel simplePanel;
	public Label lblRateIt;
	public VerticalPanel verticalPanel;
	public RadioButton rdbtnStar;
	public RadioButton rdbtnStars;
	public RadioButton rdbtnStars_1;
	public RadioButton rdbtnStars_2;
	public RadioButton rdbtnStars_3;
	public SimplePanel simplePanel_13;
	public VerticalPanel verticalPanel_1;
	public TextBox txtbxHallo;
	public Button btnEnter;
	public HorizontalPanel horizontalPanel_1;
	public VerticalPanel verticalPanel_3;
	public Label lblNewLabel;
	public TabPanel tabPanel;
	public ListBox listBox;
	public VerticalPanel verticalPanel_4;
	public ListBox listBox_1;
	public Label lblNewLabel_1;
	public ListBox listBox_2;
	public ListBox listBox_3;
	public Label lblNewLabel_2;
	public Label lblNewLabel_3;
	public TextArea txtrOptionalEnterYour;
	public VerticalPanel verticalPanel_6;
	public Label lblTopRatedDomains;
	public TabPanel tabPanel_1;
	public ListBox listBox_4;
	public ListBox listBox_5;
	public ListBox listBox_6;
	public VerticalPanel verticalPanel_5;
	public ListBox listBox_7;
	public HorizontalPanel horizontalPanel_2;
	public Label lblNewLabel_4;
	public VerticalPanel verticalPanel_7;
	public Label lblNewLabel_5;
	public VerticalPanel verticalPanel_8;
	public Button btnRefresh;
	public Button btnRefresh_1;
	public Button btnNewButton;
	public TextBox textBox;
	public Label lblNewLabel_6;
	public VerticalPanel adminPanel;
	public Label adminLabel;
	public Button clearCacheBtn;
	public Button recalculateTopsBtn;
	public Button btnRunWorkloadTest;
	public Label label;
	public VerticalPanel verticalPanel_9;
	public Label lblNewLabel_7;
	public Button btnTopUrlsTest;
	public Button btnNewButton_1;
	public Button btnRatingTest;
	public IntegerBox integerBox;
	public Button btnClearDb;
	public Button btnShowOwnRatings;
	public Button btnNewButton_2;
	public HorizontalPanel horizontalPanel_3;
	public Label lblUserMail;

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
		rootPanel.add(verticalPanel_2, 10, 10);
		verticalPanel_2.setSize("800px", "");
		
		horizontalPanel_3 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("100%");
		horizontalPanel_3.setVisible(false);
		
		lblUserMail = new Label("");
		horizontalPanel_3.add(lblUserMail);
		
		btnNewButton_2 = new Button("New button");
		horizontalPanel_3.add(btnNewButton_2);
		horizontalPanel_3.setCellHorizontalAlignment(btnNewButton_2, HasHorizontalAlignment.ALIGN_RIGHT);
		btnNewButton_2.setText("Login");
		verticalPanel_2.setCellVerticalAlignment(btnNewButton_2, HasVerticalAlignment.ALIGN_MIDDLE);
		btnNewButton_2.setSize("147px", "28px");

		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setBorderWidth(0);
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel_2.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
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
		horizontalPanel_1.setSpacing(20);
		verticalPanel_2.add(horizontalPanel_1);

		verticalPanel_3 = new VerticalPanel();
		horizontalPanel_1.add(verticalPanel_3);

		lblNewLabel = new Label("Top rated URLs");
		verticalPanel_3.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel.setWordWrap(false);
		lblNewLabel.setStyleName("h1");

		tabPanel = new TabPanel();
		verticalPanel_3.add(tabPanel);

		listBox = new ListBox();
		listBox.setMultipleSelect(true);
		tabPanel.add(listBox, "Year", false);
		listBox.setVisible(true);
		listBox.setSize("5cm", "3cm");

		listBox_2 = new ListBox();
		listBox_2.setMultipleSelect(true);
		tabPanel.add(listBox_2, "Month", false);
		listBox_2.setSize("5cm", "3cm");

		listBox_3 = new ListBox();
		listBox_3.setMultipleSelect(true);
		tabPanel.add(listBox_3, "Today", false);
		listBox_3.setSize("5cm", "3cm");
		
		btnRefresh_1 = new Button("Refresh");
		verticalPanel_3.add(btnRefresh_1);
		verticalPanel_3.setCellHorizontalAlignment(btnRefresh_1, HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel_6 = new VerticalPanel();
		horizontalPanel_1.add(verticalPanel_6);

		lblTopRatedDomains = new Label("Top rated domains");
		lblTopRatedDomains
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_6.add(lblTopRatedDomains);

		tabPanel_1 = new TabPanel();
		verticalPanel_6.add(tabPanel_1);

		listBox_4 = new ListBox();
		listBox_4.setMultipleSelect(true);
		tabPanel_1.add(listBox_4, "Year", false);
		listBox_4.setSize("5cm", "3cm");
		listBox_4.setVisibleItemCount(5);
		listBox_4.setTitle("Double-klick for sub domains");

		listBox_5 = new ListBox();
		listBox_5.setMultipleSelect(true);
		tabPanel_1.add(listBox_5, "Month", false);
		listBox_5.setSize("5cm", "3cm");
		listBox_5.setVisibleItemCount(5);
		listBox_5.setTitle("Double-klick for sub domains");

		listBox_6 = new ListBox();
		listBox_6.setMultipleSelect(true);
		tabPanel_1.add(listBox_6, "Today", false);
		listBox_6.setSize("5cm", "3cm");
		listBox_6.setVisibleItemCount(5);
		listBox_6.setTitle("Double-klick for sub domains");
		
		btnRefresh = new Button("Refresh");
		verticalPanel_6.add(btnRefresh);
		verticalPanel_6.setCellHorizontalAlignment(btnRefresh, HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel_4 = new VerticalPanel();
		horizontalPanel_1.add(verticalPanel_4);
		verticalPanel_4.setVisible(false);

		lblNewLabel_1 = new Label("Sub Domains");
		verticalPanel_4.add(lblNewLabel_1);
		verticalPanel_4.setCellHorizontalAlignment(lblNewLabel_1,
				HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel_1.setWordWrap(false);

		listBox_1 = new ListBox();
		listBox_1.setMultipleSelect(true);
		verticalPanel_4.add(listBox_1);
		verticalPanel_4.setCellHorizontalAlignment(listBox_1,
				HasHorizontalAlignment.ALIGN_CENTER);

		verticalPanel_5 = new VerticalPanel();
		verticalPanel_5
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.add(verticalPanel_5);
		horizontalPanel_1.setCellHorizontalAlignment(verticalPanel_5,
				HasHorizontalAlignment.ALIGN_CENTER);

		lblNewLabel_2 = new Label("Rating");
		lblNewLabel_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblNewLabel_2.setVisible(false);
		verticalPanel_5.add(lblNewLabel_2);

		lblNewLabel_3 = new Label("");
		lblNewLabel_3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblNewLabel_3.setVisible(false);
		verticalPanel_5.add(lblNewLabel_3);

		txtbxHallo = new TextBox();
		txtbxHallo.setStyleName("gwt-TextBox.passiv");
		txtbxHallo.setVisibleLength(50);
		verticalPanel_1.add(txtbxHallo);
		txtbxHallo.setSize("100%", "100%");
		verticalPanel_1.setCellHeight(txtbxHallo, "100%");
		verticalPanel_1.setCellWidth(txtbxHallo, "100%");
		txtbxHallo.setText("Enter URL");
		txtbxHallo.setTitle("Enter a URL you want to rate");

		txtbxHallo.setFocus(true);

		txtrOptionalEnterYour = new TextArea();
		txtrOptionalEnterYour.setDirectionEstimator(false);
		txtrOptionalEnterYour
				.setText("Optional: Enter your experience with that site!");
		verticalPanel_1.add(txtrOptionalEnterYour);
		txtrOptionalEnterYour.setWidth("100%");
		txtrOptionalEnterYour.setTitle("Enter comment for URL");
		
		adminPanel = new VerticalPanel();
		adminPanel.setBorderWidth(0);
		adminPanel.setVisible(false);
		adminPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(adminPanel);
		horizontalPanel.setCellVerticalAlignment(adminPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(adminPanel, HasHorizontalAlignment.ALIGN_CENTER);
		adminPanel.setSize("131px", "116px");
		
		adminLabel = new Label("Admin Panel");
		adminLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		adminPanel.add(adminLabel);
		
		btnClearDb = new Button("Clear DB");
		adminPanel.add(btnClearDb);
		btnClearDb.setWidth("112px");
		
		clearCacheBtn = new Button("clear cache");
		adminPanel.add(clearCacheBtn);
		clearCacheBtn.setSize("112px", "30");
		
		recalculateTopsBtn = new Button("recalculate Tops");
		adminPanel.add(recalculateTopsBtn);
		
		btnRunWorkloadTest = new Button("Run workload test");
		btnRunWorkloadTest.setText("Workload tests");
		adminPanel.add(btnRunWorkloadTest);
		btnRunWorkloadTest.setSize("112px", "30px");
		
		verticalPanel_9 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_9);
		horizontalPanel.setCellHorizontalAlignment(verticalPanel_9, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_9.setVisible(false);
		
		lblNewLabel_7 = new Label("Workload Tests");
		lblNewLabel_7.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_9.add(lblNewLabel_7);
		
		btnTopUrlsTest = new Button("Top URLs Test");
		verticalPanel_9.add(btnTopUrlsTest);
		btnTopUrlsTest.setWidth("112px");
		
		btnNewButton_1 = new Button("New button");
		btnNewButton_1.setText("Top Hosts Test");
		verticalPanel_9.add(btnNewButton_1);
		btnNewButton_1.setWidth("112px");
		
		btnRatingTest = new Button("Rating Test");
		verticalPanel_9.add(btnRatingTest);
		btnRatingTest.setWidth("112px");
		
		integerBox = new IntegerBox();
		verticalPanel_9.add(integerBox);
		integerBox.setWidth("112px");
		
		label = new Label("");
		verticalPanel_9.add(label);
		label.setVisible(false);

		rootPanel.add(verticalPanel_2);
		
		horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setSpacing(20);
		verticalPanel_2.add(horizontalPanel_2);
		
		btnShowOwnRatings = new Button("Show own ratings");
		horizontalPanel_2.add(btnShowOwnRatings);
		
		verticalPanel_8 = new VerticalPanel();
		verticalPanel_8.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_2.add(verticalPanel_8);
		verticalPanel_8.setVisible(false);
		
		lblNewLabel_6 = new Label("Your ratings");
		verticalPanel_8.add(lblNewLabel_6);
		
		listBox_7 = new ListBox();
		listBox_7.setMultipleSelect(true);
		verticalPanel_8.add(listBox_7);
		verticalPanel_8.setCellHorizontalAlignment(listBox_7, HasHorizontalAlignment.ALIGN_CENTER);
		listBox_7.setVisibleItemCount(5);
		
		btnNewButton = new Button("New button");
		btnNewButton.setText("Refresh");
		verticalPanel_8.add(btnNewButton);
		
		verticalPanel_7 = new VerticalPanel();
		horizontalPanel_2.add(verticalPanel_7);
		verticalPanel_7.setVisible(false);
		
		lblNewLabel_4 = new Label("Rating");
		verticalPanel_7.add(lblNewLabel_4);
		
		lblNewLabel_5 = new Label("");
		verticalPanel_7.add(lblNewLabel_5);
		
		textBox = new TextBox();
		verticalPanel_7.add(textBox);
		textBox.setVisible(false);
		textBox.setReadOnly(true);
	}
}
