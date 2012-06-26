package it.rate.workload;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import it.rate.Constants;
import it.rate.data.RatingDB;
import it.rate.util.PMF;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class CreateRatingTaskServlet extends HttpServlet implements Constants {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		try {
			// String url = req.getParameter("url");
			// String comment = req.getParameter("comment");
			// String rating = req.getParameter("rating");

			// String url = WORKLOAD_URL;
			// String comment = WORKLOAD_COMMENT;
			
			String strWorkloadCount = req.getParameter("count");
			int workloadCount = -1;
			try{
				workloadCount = Integer.valueOf(strWorkloadCount);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}

			// Delete all entries in DB first
			PersistenceManager pm = PMF.getInstance().getPersistenceManager();			
			javax.jdo.Query query = pm.newQuery(RatingDB.class);
		    query.deletePersistentAll();
		    
			String rating = WORKLOAD_RATING;
			Queue queue = QueueFactory.getQueue(WORKLOAD_RATING_QUEUE);
			for (int i = 0; i < workloadCount; i++) {
				queue.add(TaskOptions.Builder
						.withUrl(WORKLOAD_RATING_URL_PATTERN)
						.param("id", Integer.toString(i))
						.param("mail", RandomString.nextEmail())
						.param("url", RandomString.nextUrl())
						.param("comment", RandomString.nextComment())
						.param("rating", rating)
						.param("count", Integer.toString(workloadCount)));
				strCallResult = (i + 1) + " rating-job(s) put into queue";
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
