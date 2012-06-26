package it.rate.workload;

import it.rate.Constants;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public class DbHelper implements Constants {

	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private static Key key = null;

	public static void setStartTime(long value) {
		try {
			Entity time;
			if (key == null) {
				time = new Entity("TimeMeasurement");
				time.setProperty("startTime", value);
				key = datastore.put(time);
			} else {
				time = datastore.get(key);
				time.setProperty("startTime", value);
			}
		} catch (Exception e) {
			System.out.println("ERROR: setStartTime");
		}
	}

	public static long getStartTime() {
		try {
			return (Long) datastore.get(key).getProperty("startTime");
		} catch (EntityNotFoundException e) {
			System.out.println("ERROR: getStartTime");
		}
		return 0;
	}

	public static void setRunTime(long value) {
		try {
			Entity time = datastore.get(key);
			time.setProperty("runTime", value);
		} catch (Exception e) {
			System.out.println("ERROR: setRunTime");
		}
	}

	public static long getRunTime() {
		try {
			return (Long) datastore.get(key).getProperty("runTime");
		} catch (EntityNotFoundException e) {
			System.out.println("ERROR: getRunTime");
		}
		return 0;
	}

//	public static void initCounter(long workload) {
//		try {
//			Entity time;
//			if (key == null) {
//				time = new Entity("TimeMeasurement");
//				time.setProperty("counter", workload);
//				key = datastore.put(time);
//			} else {
//				time = datastore.get(key);
//				time.setProperty("counter", workload);
//			}
//		} catch (Exception e) {
//			System.out.println("ERROR: initCounter");
//		}
//	}
//
//	public static long getCounter() {
//		try {
//			Entity time = datastore.get(key);
//			return (Long) time.getProperty("counter");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("ERROR: getCounter");
//		}
//		return 0;
//	}
//
//	public static void decrementCounter() {
//		try {
//			Entity time = datastore.get(key);
//			time.setProperty("counter",
//					(Long) time.getProperty("counter") - 1);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("ERROR: decrementCounter");
//		}
//	}

	public static DatastoreService getDataStore() {
		return datastore;
	}
}
