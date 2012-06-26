package it.rate.workload;

import it.rate.Constants;
import it.rate.server.RateTask;
import it.rate.util.ErrorMessage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class RatingExecutionServlet extends HttpServlet implements Constants {

	public static volatile int counter = 0;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String strCallResult = "";
		resp.setContentType("text/plain");

		try {

			String strId = req.getParameter("id");
			String strCount = req.getParameter("count");
			int id = -1;
			int count = -1;
			try {
				id = Integer.valueOf(strId);
				count = Integer.valueOf(strCount);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			String mail = req.getParameter("mail");
			String url = req.getParameter("url");
			String comment = req.getParameter("comment");
			String strRating = req.getParameter("rating");
			float rating = 1;

			try {
				rating = Integer.valueOf(strRating);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			rateUrl(id, url, comment, rating, mail, count);

		} catch (Exception ex) {
			strCallResult = "FAIL: Rating not placed";
			System.out.println(strCallResult);
		}
	}

	public void rateUrl(int id, String url, String comment, float rating,
			String mail, int count) throws Exception {
		switch (RateTask.rateUrl(url, comment, rating, mail)) {
		case ErrorMessage.DB_ERROR:
			System.out.println("DB_ERROR");
			break;
		case ErrorMessage.RATE_SUCCESS:
			// System.out.println("SUCCESS");
			break;
		}
		if (id == 0) {
			DbHelper.setStartTime(System.currentTimeMillis());
		} else if (id == count - 1) {
			DbHelper.setRunTime(System.currentTimeMillis()
					- DbHelper.getStartTime());
		}
		System.out.println(id);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
