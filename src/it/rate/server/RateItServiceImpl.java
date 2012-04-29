package it.rate.server;

import java.util.List;

import it.rate.client.RateItService;
import it.rate.data.Rating;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RateItServiceImpl extends RemoteServiceServlet implements
		RateItService {

	@Override
	public void rateUrl(String user, String url, String comment, float rating) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Rating> getSubDomains(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rating> getTopUrlsForPeriod(String startDate, String endDate) {
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
	public List<Rating> getAllUserRatedUrls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getUsersUrlRating(String url) {
		// TODO Auto-generated method stub
		return 0;
	}


}
