package it.rate.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import it.rate.client.RateItService;
import it.rate.client.Rating;
import it.rate.data.RatingDB;
import it.rate.util.PMF;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RateItServiceImpl extends RemoteServiceServlet implements
		RateItService {

	@Override
	public void rateUrl(String userEmail, String url, String comment,
			float rating) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Query query = pm.newQuery(RatingDB.class,
				"(userEmail == userParam ) && ( url == urlParam)");

		RatingDB temp = new RatingDB(userEmail, url, comment, rating);
		try {
			List<RatingDB> result = (List<RatingDB>) query.execute(userEmail,
					url);
			// if DB doesn't contain a rating-object then
			// add this Rating-object to DataStore

			if (result.isEmpty()) {
				try {
					pm.makePersistent(temp);
				} finally {
					pm.close();
				}
			}

		} finally {
			query.closeAll();
		}
	}

	@Override
	public List<Rating> getSubDomains(String url) {

		/*
		 * Testdata START
		 */
		// List<Rating> subDomains = new ArrayList<Rating>();
		// subDomains.add(new RatingDB("test", "test.com", "test comment",
		// (float) 5.0));
		// subDomains.add(new RatingDB("test2", "test.com", "test2 comment",
		// (float) 5.0));
		/*
		 * Testdata END
		 */
		return null;
	}

	@Override
	public List<Rating> getTopUrlsForPeriod(Date startDate, Date endDate,
			int countOfUrls) {

		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> result = null;
		List<Rating> topUrls = new ArrayList();
		int i = 0;

		Query query = pm.newQuery(RatingDB.class);
		query.setOrdering("rating desc");

		try {
			// get all ratings of url
			result = (List<Rating>) query.execute();

		} finally {
			query.closeAll();
		}

		for (Rating r : result) {
			// select entries from certain period (>= startDate && <= endDate)
			if ((r.getDate().compareTo(startDate) == 0)
					|| (r.getDate().compareTo(endDate) == 0)
					|| ((r.getDate().compareTo(startDate) > 0) && (r.getDate()
							.compareTo(endDate) < 0))) {
				if (i < countOfUrls) {
					topUrls.add(r);
					i++;
				} else {
					break;
				}
			}

		}

		// for (Rating re : topUrls)
		// {
		// System.out.println(re.getRating());
		// }
		return topUrls;
	}

	@Override
	public float getAverageRatingForPeriod(String url, Date startDate,
			Date endDate) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> result = null;

		float advRating = 0;

		Query query = pm.newQuery(RatingDB.class, "url == urlParam");

		try {
			// get all ratings of url
			result = (List<Rating>) query.execute(url);

		} finally {
			query.closeAll();
		}

		for (Rating r : result) {
			// select entries from certain period (>= startDate && <= endDate)
			if ((r.getDate().compareTo(startDate) == 0)
					|| (r.getDate().compareTo(endDate) == 0)
					|| ((r.getDate().compareTo(startDate) > 0) && (r.getDate()
							.compareTo(endDate) < 0))) {
				advRating = advRating + r.getRating();
			}
		}

		if (result.size() != 0) {
			advRating = advRating / result.size();
		}

		return advRating;
	}

	@Override
	public List<Rating> getAllUserRatedUrls() {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<Rating> allUrls = null;

		Query query = pm.newQuery(RatingDB.class);

		try {
			// get all rated urls of all users
			allUrls = (List<Rating>) query.execute();

		} finally {
			query.closeAll();
		}

		// for (Rating r :allUrls)
		// {
		// System.out.println("user : " + r.getUser() + " url : " + r.getUrl() +
		// " rating : " + r.getRating() );
		// }

		return allUrls;

	}

	@Override
	public float getUsersUrlRating(String user, String url) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		List<RatingDB> userRatedUrl = null;
		float userRating = 0;

		Query query = pm.newQuery(RatingDB.class, "(userEmail == userParam"
				+ ") && (url == urlParam)");

		try {
			// get user rating of url
			userRatedUrl = (List<RatingDB>) query.execute(user, url);

			if ((!userRatedUrl.isEmpty()) && (userRatedUrl.size() == 1)) {
				userRating = userRatedUrl.get(0).getRating();
			}

		} finally {
			query.closeAll();
		}
		return userRating;
	}

	@Override
	public String getLoginURL(String destinationURL) {
		System.out.println(UserServiceFactory.getUserService().createLoginURL(destinationURL));
		return UserServiceFactory.getUserService().createLoginURL(
				destinationURL);
	}

	@Override
	public String getLogoutURL(String destinationURL) {

		return UserServiceFactory.getUserService().createLogoutURL(
				destinationURL);
	}

	@Override
	public boolean isUserLoggedIn() {
		return UserServiceFactory.getUserService().isUserLoggedIn();
	}

	@Override
	public String getCurrentUserEmail() {

		return UserServiceFactory.getUserService().getCurrentUser().getEmail();
	}

}
