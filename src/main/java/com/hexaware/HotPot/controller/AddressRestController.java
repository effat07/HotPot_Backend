/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HotPot.dto.AddressDTO;
import com.hexaware.HotPot.entity.Address;
import com.hexaware.HotPot.service.IAddressService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/addresses")
public class AddressRestController {

    @Autowired
    private IAddressService addressService;

    @PostMapping
    public Address create(@Valid @RequestBody AddressDTO dto) {
        return addressService.create(dto);
    }

    @GetMapping("/{id}")
    public Optional<Address> getById(@PathVariable Long id) {
        return addressService.getById(id);
    }

    @PutMapping
    public Address update(@Valid @RequestBody AddressDTO dto) {
        return addressService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }

    @GetMapping("/by-user/{userId}")
    public List<Address> getByUser(@PathVariable Long userId) {
        return addressService.getByUser(userId);
    }
}