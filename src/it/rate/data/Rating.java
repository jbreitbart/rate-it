package it.rate.data;

import java.util.List;

/**
 * Class which can be used for one certain URL rating from a user (comments can't have more than one object)
 * or as a URL summary (comments can have more than one object).
 * The same goes for rating. Can be used for an average rating (URL summary) or a certain rating (from one user)
 * @author Waldo
 */
public class Rating {
	
	private String url;
	private List<String> comments;
	private float rating;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public void addComment(String comment){
		comments.add(comment);
	}
	
}