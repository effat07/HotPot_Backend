/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.AddressDTO;
import com.hexaware.HotPot.entity.Address;

public interface IAddressService {
  
    Address create(AddressDTO addressdto);
    Optional<Address> getById(Long addressId);
    Address update(AddressDTO addressdto);
    String delete(Long addressId);
    
  
    List<Address> getByUser(Long userId);
}
