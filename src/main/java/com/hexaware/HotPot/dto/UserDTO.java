/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;


import com.hexaware.HotPot.entity.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {
	 private Long userId; 

	    @NotBlank(message = "Username is mandatory")
	    private String userName;

	    @NotBlank(message = "Email is mandatory")
	    @Email(message = "Invalid email format")
	    private String email;

	    @NotBlank(message = "Password is mandatory")
	    @Size(min = 8, message = "Password must be at least 8 characters long")
	    private String password;

	    @NotBlank(message = "Phone number is mandatory")
	    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	    private String phone;

	    @NotNull(message = "Role is required")
	    private Role role; // CUSTOMER, RESTAURANT, ADMIN

	    private boolean active;
	    
	    public UserDTO() {
	    	
	    }

		public UserDTO(Long userId, @NotBlank(message = "Username is mandatory") String userName,
				@NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") String email,
				@NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password must be at least 8 characters long") String password,
				@NotBlank(message = "Phone number is mandatory") @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits") String phone,
				@NotNull(message = "Role is required") Role role, boolean active) {
			super();
			this.userId = userId;
			this.userName = userName;
			this.email = email;
			this.password = password;
			this.phone = phone;
			this.role = role;
			this.active = active;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		@Override
		public String toString() {
			return "UserDTO [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password="
					+ password + ", phone=" + phone + ", role=" + role + ", active=" + active + "]";
		}
	    
	    
}
