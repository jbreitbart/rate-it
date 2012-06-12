package it.rate.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CronJobImpl extends RemoteServiceServlet
{

	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
	    TopsCalculator.calculateAllTops();
	  }
}
