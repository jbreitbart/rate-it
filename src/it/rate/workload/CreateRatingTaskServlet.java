package it.rate.workload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import it.rate.Constants;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class CreateRatingTaskServlet extends HttpServlet implements Constants{

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		try {

//			String url = req.getParameter("url");
//			String comment = req.getParameter("comment");
//			String rating = req.getParameter("rating");

			String url = WORKLOAD_URL;
			String comment = WORKLOAD_COMMENT;
			String rating = WORKLOAD_RATING;
			Queue queue = QueueFactory.getQueue(WORKLOAD_RATING_QUEUE);
			for (int i = 0; i < WORKLOAD_RATING_TASKS ; i++) {
				queue.add(TaskOptions.Builder.withUrl(WORKLOAD_RATING_URL_PATTERN)
						.param("id", Integer.toString(i)).param("url", url).param("comment", comment).param("rating", rating));
				strCallResult = (i+1) + " rating-job(s) put into queue";
				resp.getWriter().println(strCallResult);
			}
		} catch (Exception ex) {
			strCallResult = "Fail: " + ex.getMessage();
			resp.getWriter().println(strCallResult);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
