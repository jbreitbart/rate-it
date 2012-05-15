package it.rate;

import it.rate.client.RateItService;
import it.rate.client.RateItServiceAsync;
import it.rate.view.FrontPage;
import it.rate.view.HandlerInit;
import it.rate.view.RPC;
import it.rate.view.WidgetUpdate;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RateIt implements EntryPoint {
	
	private static final int TOP_COUNT = 10;

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final RateItServiceAsync rateItService = GWT
			.create(RateItService.class);
	
	@Override
	public void onModuleLoad() {
		FrontPage page = new FrontPage();
		WidgetUpdate wUpd = new WidgetUpdate(page);
		RPC rpc = new RPC(page, wUpd);
		HandlerInit init = new HandlerInit(page, rpc, wUpd, TOP_COUNT);
		page.show();
		init.addHandlers();
		rpc.userAuthentication();
	}

}
