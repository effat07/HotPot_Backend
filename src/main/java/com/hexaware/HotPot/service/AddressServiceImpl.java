/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hexaware.HotPot.dto.AddressDTO;
import com.hexaware.HotPot.entity.Address;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.repository.AddressRepository;
import com.hexaware.HotPot.repository.UserRepository;

@Service
public class AddressServiceImpl implements IAddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Address create(AddressDTO dto) {
        log.info("Creating new Address for userId {}", dto.getUserId());
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found: {}", dto.getUserId());
                    return new UserNotFoundException("User not found: " + dto.getUserId());
                });

        Address a = new Address();
        a.setUser(user);
        copyDtoToEntity(dto, a);

        Address saved = addressRepository.save(a);
        log.debug("Address created with ID {}", saved.getAddressId());
        return saved;
    }

    @Override
    public Optional<Address> getById(Long addressId) {
        log.debug("Fetching Address by ID {}", addressId);
        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isEmpty()) log.error("Address not found with ID {}", addressId);
        return address;
    }

    @Override
    public Address update(AddressDTO dto) {
        log.info("Updating Address with ID {}", dto.getAddressId());
        if (dto.getAddressId() == null) {
            throw new IllegalArgumentException("Address ID is required for update");
        }

        Address existing = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> {
                    log.error("Address not found: {}", dto.getAddressId());
                    return new RuntimeException("Address not found with id: " + dto.getAddressId());
                });

        if (dto.getUserId() != null &&
            (existing.getUser() == null || !existing.getUser().getUserId().equals(dto.getUserId()))) {
            var user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> {
                        log.error("User not found: {}", dto.getUserId());
                        return new UserNotFoundException("User not found: " + dto.getUserId());
                    });
            existing.setUser(user);
        }

        copyDtoToEntity(dto, existing);
        Address updated = addressRepository.save(existing);
        log.debug("Address updated with ID {}", updated.getAddressId());
        return updated;
    }

    @Override
    public String delete(Long addressId) {
        log.info("Deleting Address with ID {}", addressId);
        if (!addressRepository.existsById(addressId)) {
            log.error("Address not found with ID {}", addressId);
            throw new RuntimeException("Address not found with id: " + addressId);
        }
        addressRepository.deleteById(addressId);
        log.debug("Address {} deleted", addressId);
        return "Deleted Successfully";
    }

    @Override
    public List<Address> getByUser(Long userId) {
        log.debug("Fetching Addresses for userId {}", userId);
        if (!userRepository.existsById(userId)) {
            log.error("User not found with ID {}", userId);
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return addressRepository.findByUserUserId(userId);
    }

    private void copyDtoToEntity(AddressDTO dto, Address entity) {
        entity.setLine1(dto.getLine1());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPincode(dto.getPincode());
        entity.setLandmark(dto.getLandmark());
    }
}
