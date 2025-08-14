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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}
