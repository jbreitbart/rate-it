package it.rate.workload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class CreateRatingTaskServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		try {
			// Extract out the To, Subject and Body of the Email to be sent
			String strId = req.getParameter("id");

			// Do validations here. Only basic ones i.e. cannot be null/empty

			if (strId == null)
				throw new Exception("Id field cannot be empty.");
			if (strId.length() == 0)
				throw new Exception("Email Id field cannot be empty.");
			
			// Queue queue = QueueFactory.getDefaultQueue();
			Queue queue = QueueFactory.getQueue("rate-queue");
			queue.add(TaskOptions.Builder.withUrl("/ratingsaver").param(
					"id", strId));
			strCallResult = "Rating successfully saved";
			resp.getWriter().println(strCallResult);
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
