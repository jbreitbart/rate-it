package it.rate.view;

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
	public static final String REDIRECTION_URL = "http://rateerate.appspot.com/";	

	public static final String COMMENTS_BOX_DEFAULT = "Optional: Enter your experience with that site!";
	
	public static final String URL_BOX_DEFAULT = "Please enter a URL";
	
	public static final String NO_SELECTION = "No item selected";
	
	public static enum Period {DAY, MONTH, YEAR};
	
}
