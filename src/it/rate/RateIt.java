package it.rate;

import it.rate.view.FrontPage;
import it.rate.view.HandlerInit;
import it.rate.view.RPC;
import it.rate.view.ServerDataCache;
import it.rate.view.WidgetUpdate;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RateIt implements EntryPoint, Constants{

	@Override
	public void onModuleLoad() {
		FrontPage page = new FrontPage();
		ServerDataCache dataCache = new ServerDataCache();
		WidgetUpdate wUpd = new WidgetUpdate(page, dataCache);
		RPC rpc = new RPC(page, wUpd, dataCache);
		HandlerInit init = new HandlerInit(page, rpc, wUpd, dataCache);
		
		page.show();
		init.addHandlers();
		rpc.userAuthenticationAtStart();
		rpc.init();
	}

}
