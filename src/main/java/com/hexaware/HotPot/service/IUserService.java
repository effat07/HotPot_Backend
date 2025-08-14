/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.UserDTO;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.entity.enums.Role;

public interface IUserService {
  
    User create(UserDTO userdto);
    Optional<User> getById(Long userId);
    User update(UserDTO userdto);
    String delete(Long userId);
    List<User> getAllUser(UserDTO userdto);

    
    Optional<User> getByEmail(String email);
    Optional<User> getByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    List<User> getByRole(Role role);
}
