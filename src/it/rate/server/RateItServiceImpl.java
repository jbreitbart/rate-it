package it.rate.server;

import it.rate.Rating;
import it.rate.client.RateItService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RateItServiceImpl extends RemoteServiceServlet implements
		RateItService {

	@Override
	public void rateUrl(Rating rating) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rating[] getSubDomains(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rating[] getTopUrlsForPeriod(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getAverageRatingForPeriod(String url, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Rating[] getAllUserRatedUrls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getUsersUrlRating(String url) {
		// TODO Auto-generated method stub
		return 0;
	}


}
