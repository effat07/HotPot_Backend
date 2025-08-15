/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressDTO {

    private Long addressId; 
    
    @jakarta.validation.constraints.NotNull
    private Long userId;

    @NotBlank(message = "Address line is mandatory")
    @Size(min = 3, max = 150, message = "Address line must be between 3 and 150 characters")
    private String line1;

    @NotBlank(message = "City is mandatory")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @NotBlank(message = "State is mandatory")
    @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters")
    private String state;

    @NotBlank(message = "Pincode is mandatory")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be exactly 6 digits")
    private String pincode;

    @Size(max = 100, message = "Landmark cannot exceed 100 characters")
    private String landmark; 
    
    public AddressDTO() {
    	
    }

	public AddressDTO(Long addressId, @NotNull Long userId,
			@NotBlank(message = "Address line is mandatory") @Size(min = 3, max = 150, message = "Address line must be between 3 and 150 characters") String line1,
			@NotBlank(message = "City is mandatory") @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters") String city,
			@NotBlank(message = "State is mandatory") @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters") String state,
			@NotBlank(message = "Pincode is mandatory") @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be exactly 6 digits") String pincode,
			@Size(max = 100, message = "Landmark cannot exceed 100 characters") String landmark) {
		super();
		this.addressId = addressId;
		this.userId = userId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
		return "AddressDTO [addressId=" + addressId + ", userId=" + userId + ", line1=" + line1 + ", city=" + city
				+ ", state=" + state + ", pincode=" + pincode + ", landmark=" + landmark + "]";
	}
    
}
