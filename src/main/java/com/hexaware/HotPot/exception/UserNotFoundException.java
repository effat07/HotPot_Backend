/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}