package com.example.gms.model;

public class Batch {

	// Attributes
	int bid;
	String name;
	String time;
	
	// Constructors
	public Batch() {
	}

	public Batch(int bid, String name, String time) {
		this.bid = bid;
		this.name = name;
		this.time = time;
	}

	// Getters and Setters
	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	// To-String Method
	@Override
	public String toString() {
		return "Batch [bid=" + bid + ", name=" + name + ", time=" + time + "]";
	}
	
}
