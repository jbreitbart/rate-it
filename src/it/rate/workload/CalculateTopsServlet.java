package it.rate.workload;

import it.rate.Constants;
import it.rate.server.TopsCalculator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CalculateTopsServlet extends HttpServlet implements Constants {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String strCallResult = "";
		resp.setContentType("text/plain");
		
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

		try {

			TopsCalculator.calculateAllTops();			

			if (id == 0) {
				DbHelper.setStartTime(System.currentTimeMillis());
			} else if (id == count - 1) {
				DbHelper.setRunTime(System.currentTimeMillis()
						- DbHelper.getStartTime());
			}

		} catch (Exception ex) {
			strCallResult = "FAIL: Calculating top lists";
			System.out.println(strCallResult);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}

