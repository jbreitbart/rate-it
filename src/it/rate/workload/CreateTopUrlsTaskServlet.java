package it.rate.workload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import it.rate.Constants;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class CreateTopUrlsTaskServlet extends HttpServlet implements Constants{

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		try {
			Queue queue = QueueFactory.getQueue(WORKLOAD_URL_QUEUE);
			for (int i = 0; i < WORKLOAD_URL_TASKS ; i++) {
				queue.add(TaskOptions.Builder.withUrl(WORKLOAD_URL_URL_PATTERN)
						.param("id", "i"));
				strCallResult = (i+1) + " top-urls-job(s) put into queue";
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
