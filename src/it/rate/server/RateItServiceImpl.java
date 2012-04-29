package it.rate.server;

import java.util.ArrayList;
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
		
		/*
		 * Testdata START
		 */
		List<Rating> subDomains = new ArrayList<Rating>();
		subDomains.add(new Rating("test", "test.com", "test comment", (float)5.0));
		subDomains.add(new Rating("test2", "test.com", "test2 comment", (float)5.0));
		/*
		 * Testdata END
		 */
		return subDomains;
	}

	@Override
	public List<Rating> getTopUrlsForPeriod(String startDate, String endDate) {
		/*
		 * Testdata START
		 */
		List<Rating> topUrls = new ArrayList<Rating>();
		topUrls.add(new Rating("test", "test.com", "test comment", (float)5.0));
		topUrls.add(new Rating("test2", "test.com", "test2 comment", (float)5.0));
		/*
		 * Testdata END
		 */
		return topUrls;
	}

	@Override
	public float getAverageRatingForPeriod(String url, String startDate,
			String endDate) {
		
		return (float)5.0;
	}

	@Override
	public List<Rating> getAllUserRatedUrls() {
		/*
		 * Testdata START
		 */
		List<Rating> allUrls = new ArrayList<Rating>();
		allUrls.add(new Rating("test", "test.com", "test comment", (float)5.0));
		allUrls.add(new Rating("test2", "test.com", "test2 comment", (float)5.0));
		/*
		 * Testdata END
		 */
		return allUrls;
		
	}

	@Override
	public float getUsersUrlRating(String url) {
		// TODO Auto-generated method stub
		return (float)5.0;
	}


}
