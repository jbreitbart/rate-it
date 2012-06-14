package it.rate.workload;

import it.rate.Constants;
import it.rate.server.TopsCalculator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class FetchTopHostsServlet extends HttpServlet implements Constants {

	private static int counter = 1;
	private static double startTime = 0.0;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String strCallResult = "";
		resp.setContentType("text/plain");

		try {

			TopsCalculator.getTopHostsForDay(TOP_COUNT);
			TopsCalculator.getTopHostsForMonth(TOP_COUNT);
			TopsCalculator.getTopHostsForYear(TOP_COUNT);
			
			if (counter == 1) {
				startTime = System.currentTimeMillis();
			}
			if (counter % WORKLOAD_HOST_INFO_RATIO == 0) {
				System.out.println(
						counter + " fetches of hosts top lists finished in "
								+ (System.currentTimeMillis() - startTime) + "ms");
			}
			counter++;

		} catch (Exception ex) {
			strCallResult = "FAIL: Fetching hosts top lists";
			System.out.println(strCallResult);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}

