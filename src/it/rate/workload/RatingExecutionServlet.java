package it.rate.workload;

import it.rate.Constants;
import it.rate.server.RateTask;
import it.rate.util.ErrorMessage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class RatingExecutionServlet extends HttpServlet implements Constants {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
			String mail = req.getParameter("mail");
			String url = req.getParameter("url");
			String comment = req.getParameter("comment");
//			String strRating = req.getParameter("rating");
			float rating = 1;
			RateTask.rateUrl(url, comment, rating, mail);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
