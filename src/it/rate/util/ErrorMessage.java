package it.rate.util;

/**
 * The interface contains return messages of rate - method
 * 
 * @author Vladimir
 * 
 */
public interface ErrorMessage {

	public final int RATE_SUCCESS 		= 0;
	public final int RATE_EXISTS 		= 1;
	public final int URL_NOT_CORRECT 	= 2;
	public final int USER_NOT_LOGGED_IN = 4;
	public final int DB_ERROR 			= 5;

}
