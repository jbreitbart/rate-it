package it.rate.workload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import it.rate.Constants;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class CreateCalculateTopsTaskServlet extends HttpServlet implements Constants{

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		
		String strWorkloadCount = req.getParameter("count");
		int workloadCount = -1;
		try{
			workloadCount = Integer.valueOf(strWorkloadCount);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		try {
			Queue queue = QueueFactory.getQueue(WORKLOAD_CALC_TOPS_QUEUE);
			for (int i = 0; i < workloadCount ; i++) {
				queue.add(TaskOptions.Builder.withUrl(WORKLOAD_CALC_TOPS_URL_PATTERN)
						.param("id", "i").param("count", Integer.toString(workloadCount)));
				strCallResult = (i+1) + " calc-tops-job(s) put into queue";
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