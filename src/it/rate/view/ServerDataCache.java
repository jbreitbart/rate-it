package it.rate.view;

import it.rate.client.Rating;
import it.rate.client.TopUrl;

import java.util.List;

public class ServerDataCache {

	public List<TopUrl> receivedYearsTopUrls = null;
	public List<TopUrl> receivedMonthsTopUrls = null;
	public List<TopUrl> receivedTodaysTopUrls = null;
	public List<TopUrl> receivedYearsTopDomains = null;
	public List<TopUrl> receivedMonthsTopDomains = null;
	public List<TopUrl> receivedTodaysTopDomains = null;
	public List<Rating> receivedUserRatings = null;
}
