package it.rate.server;

import it.rate.data.RatingDB;
import it.rate.util.PMF;

import javax.jdo.PersistenceManager;


public class ClearDB {

	public static void clearDB() {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		javax.jdo.Query query = pm.newQuery(RatingDB.class);
		query.deletePersistentAll();
	}
}
