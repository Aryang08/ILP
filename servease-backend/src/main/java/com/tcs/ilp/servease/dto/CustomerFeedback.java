package com.tcs.ilp.servease.dto;

public class CustomerFeedback {
	private String customerName;
	private String serviceCenterName;
	private int rating;
	private String comment;
	private String date;
	
	public CustomerFeedback(String customerName, String serviceCenterName, int rating, String comment, String date) {
		super();
		this.customerName = customerName;
		this.serviceCenterName = serviceCenterName;
		this.rating = rating;
		this.comment = comment;
		this.date = date;
	}
	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getServiceCenterName() {
		return serviceCenterName;
	}

	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
}
