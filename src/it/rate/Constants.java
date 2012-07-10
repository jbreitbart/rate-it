package it.rate;

public interface Constants {

	/**
	 * Count for top lists length
	 */
	public static final int TOP_COUNT = 10;	
	
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	public static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	/**
	 * Message for replacing an existing rating
	 */
	public static final String REPLACE_WARINING = "Are you sure you want to replace your rating for this URL?";
	
	public static final String LOGIN_WARNING = "Please log in first";
	
	public static final String RATING_ACKNOWLEDGMENT = "Thank you for your rating";
	
	/**
	 * URL the user will be redirected after login/logout
	 */
	public static final String REDIRECTION_URL = "http://rateerate.appspot.com/"; //"http://127.0.0.1:8888/RateIt.html?gwt.codesvr=127.0.0.1:9997";

	public static final String COMMENTS_BOX_DEFAULT = "Optional: Enter your experience with that site!";
	
	public static final String URL_BOX_DEFAULT = "Please enter a URL";
	
	public static final String NO_SELECTION = "No item selected";
	
	public static final String RECALCULATE_TOPS_SUCCESS = "Top list recalculated with DB data";
	
	public static final String RECALCULATE_TOPS_FAILED = "Top list could not recalculated";
	
	public static final String CACHE_CLEAR_FAILED = "Clearing cache failed";
	
	public static final String CACHE_CLEAR_SUCCESS = "Cache successfully cleard";

	public static final String WORKLOAD_RATING = "1";
	
	public static final int WORKLOAD_RATING_TASKS = 10000;
	
	public static final int WORKLOAD_CALC_TOPS_TASKS = 1000;
	
	public static final int WORKLOAD_FETCH_TOPS_TASKS = 1000;
	
	public static final String WORKLOAD_RATING_URL_PATTERN = "/queue/ratingtasks";
	
	public static final String WORKLOAD_RATING_QUEUE = "rate-queue";
	
	public static final String WORKLOAD_SPLIT_JOBS_URL_PATTERN = "/queue/splitjobs";
	
	public static final String WORKLOAD_SPLIT_JOBS_QUEUE = "split-queue";
	
	public static final String WORKLOAD_CALC_TOPS_URL_PATTERN = "/queue/calctasks";
	
	public static final String WORKLOAD_CALC_TOPS_QUEUE = "calc-tops-queue";
	
	public static final String WORKLOAD_FETCH_TOPS_URL_PATTERN = "/queue/fetchtasks";
	
	public static final String WORKLOAD_FETCH_TOPS_QUEUE = "fetch-tops-queue";
	
	public static final int NUMBER_TOP_URLS_CALLS = 1000;
	
	public static final int NUMBER_TOP_HOSTS_CALLS = 1000;
	
	public static enum Period {DAY, MONTH, YEAR};
	
}
