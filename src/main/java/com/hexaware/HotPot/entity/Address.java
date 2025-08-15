/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/


package com.hexaware.HotPot.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "addresses")
public class Address {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String line1;
    private String city;
    private String state;
    private String pincode;
    private String landmark;
    
    
    public Address() {
    	
    }


	public Address(Long addressId, User user, String line1, String city, String state, String pincode,
			String landmark) {
		super();
		this.addressId = addressId;
		this.user = user;
		this.line1 = line1;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.landmark = landmark;
	}


	public Long getAddressId() {
		return addressId;
	}


	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getLine1() {
		return line1;
	}


	public void setLine1(String line1) {
		this.line1 = line1;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getLandmark() {
		return landmark;
	}


	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}


	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", user=" + user + ", line1=" + line1 + ", city=" + city + ", state="
				+ state + ", pincode=" + pincode + ", landmark=" + landmark + "]";
	}
    
    
}
