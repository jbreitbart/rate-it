package it.rate.workload;

import it.rate.Constants;
import it.rate.server.RateTask;
import it.rate.util.ErrorMessage;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class RatingExecutionServlet extends HttpServlet implements Constants {

	private static final Logger _logger = Logger
			.getLogger(RatingExecutionServlet.class.getName());
	HttpServletResponse response;
	private static int counter = 1;
	private static double startTime = 0.0;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String strCallResult = "";
		resp.setContentType("text/plain");

		try {

			String strId = req.getParameter("id");
			String url = req.getParameter("url");
			String comment = req.getParameter("comment");
			String strRating = req.getParameter("rating");
			float rating = 1;

			try {
				rating = Integer.valueOf(strRating);
			} catch (Exception e) {
				e.printStackTrace();
			}

			rateUrl(url, comment, rating);

		} catch (Exception ex) {
			strCallResult = "FAIL: Rating placed";
			System.out.println(strCallResult);
		}
	}

	public void rateUrl(String url, String comment, float rating)
			throws Exception {
		switch (RateTask.rateUrl(url, comment, rating, true)) {
		case ErrorMessage.URL_NOT_CORRECT:
			System.out.println("url not correct");
			break;
		case ErrorMessage.USER_NOT_LOGGED_IN:
			System.out.println("user not logged in");
			break;
		case ErrorMessage.RATE_EXISTS:
			System.out.println("rate exists");
			break;
		case ErrorMessage.RATE_SUCCESS:
			System.out.println("SUCCESS");
			break;
		}

		if (counter == 1) {
			startTime = System.currentTimeMillis();
		}
		if (counter % WORKLOAD_RATING_INFO_RATIO == 0) {
			System.out.println(
					counter + " ratings finished in "
							+ (System.currentTimeMillis() - startTime) + "ms");
		}
		counter++;

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
