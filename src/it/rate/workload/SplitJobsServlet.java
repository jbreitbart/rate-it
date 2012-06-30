package it.rate.workload;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import it.rate.Constants;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class SplitJobsServlet extends HttpServlet implements Constants{

	private static final Logger _logger = Logger.getLogger(CreateRatingTaskServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		try {
			
			String strJobs = req.getParameter("jobs");
			int jobs = -1;
			try{
				jobs = Integer.valueOf(strJobs);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			Queue queue = QueueFactory.getQueue(WORKLOAD_RATING_QUEUE);
			for (int i = 0; i < jobs; i++) {
				queue.add(TaskOptions.Builder
						.withUrl(WORKLOAD_RATING_URL_PATTERN)
						.param("mail", RandomString.nextEmail())
						.param("url", RandomString.nextUrl())
						.param("comment", RandomString.nextComment()));
			}
			resp.getWriter().println("all jobs were added to queue");
		} catch (Exception ex) {
			_logger.throwing(SplitJobsServlet.class.toString(), "StartingExecutionServlet", new Exception());
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}