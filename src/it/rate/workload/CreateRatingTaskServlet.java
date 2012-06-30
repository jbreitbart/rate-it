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
public class CreateRatingTaskServlet extends HttpServlet implements Constants {
	
	private static final Logger _logger = Logger.getLogger(CreateRatingTaskServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		try {
			String strJobs = req.getParameter("jobs");
			int jobs = -1;
			try{
				jobs = Integer.valueOf(strJobs);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			String strParts = req.getParameter("parts");
			int parts = -2;
			try{
				parts = Integer.valueOf(strParts);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			
			if(jobs%parts != 0){
				resp.getWriter().println("This combination of jobs and parts does not meet the requirements (jobs%parts == 0)");
				resp.getWriter().println("Test aborted");
				return;
			}
			// Delete all entries in DB first
//			PersistenceManager pm = PMF.getInstance().getPersistenceManager();			
//			javax.jdo.Query query = pm.newQuery(RatingDB.class);
//		    query.deletePersistentAll();

			_logger.warning("test is starting");
			int jobsPerPart = jobs / parts;
			Queue queue = QueueFactory.getQueue(WORKLOAD_SPLIT_JOBS_QUEUE);
			for (int i = 0; i < parts; i++) {
				queue.add(TaskOptions.Builder
						.withUrl(WORKLOAD_SPLIT_JOBS_URL_PATTERN)
						.param("jobs", Integer.toString(jobsPerPart)));
			}
			resp.getWriter().println(jobs + " jobs were split to " + parts + " queues");
			resp.getWriter().println("Every queue has to execute " + jobsPerPart + " jobs");
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
