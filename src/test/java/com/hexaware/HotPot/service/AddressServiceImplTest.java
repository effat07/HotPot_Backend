package com.hexaware.HotPot.service;

import com.hexaware.HotPot.dto.AddressDTO;
import com.hexaware.HotPot.entity.Address;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.repository.AddressRepository;
import com.hexaware.HotPot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock private AddressRepository addressRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private AddressServiceImpl addressService;

    private AddressDTO dto;
    private User user;
    private Address entity;

    @BeforeEach
    void setUp() {
        dto = new AddressDTO();
        dto.setAddressId(null);
        dto.setUserId(10L);
        dto.setLine1("221B Baker St");
        dto.setCity("London");
        dto.setState("LDN");
        dto.setPincode("123456");
        dto.setLandmark("Near Museum");

        user = new User();
        user.setUserId(10L);

        entity = new Address();
        entity.setAddressId(1L);
        entity.setUser(user);
        entity.setLine1(dto.getLine1());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPincode(dto.getPincode());
        entity.setLandmark(dto.getLandmark());
    }

    @Test
    void create_linksUserAndSaves() {
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(addressRepository.save(any(Address.class))).thenReturn(entity);

        Address saved = addressService.create(dto);

        assertNotNull(saved);
        assertEquals(1L, saved.getAddressId());
        assertEquals(10L, saved.getUser().getUserId());
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void create_throwsWhenUserMissing() {
        when(userRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> addressService.create(dto));
        verify(addressRepository, never()).save(any());
    }

    @Test
    void getById_found() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(entity));
        assertTrue(addressService.getById(1L).isPresent());
        verify(addressRepository).findById(1L);
    }

    @Test
    void update_appliesChanges_andSaves() {
        AddressDTO upd = new AddressDTO();
        upd.setAddressId(1L);
        upd.setCity("Paris");
        upd.setLandmark("Near Louvre");

        when(addressRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(addressRepository.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        Address updated = addressService.update(upd);

        assertEquals("Paris", updated.getCity());
        assertEquals("Near Louvre", updated.getLandmark());
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void update_relinksUserWhenUserIdChanges() {
        AddressDTO upd = new AddressDTO();
        upd.setAddressId(1L);
        upd.setUserId(99L);

        User newUser = new User(); newUser.setUserId(99L);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userRepository.findById(99L)).thenReturn(Optional.of(newUser));
        when(addressRepository.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        Address updated = addressService.update(upd);

        assertEquals(99L, updated.getUser().getUserId());
    }

    @Test
    void update_throwsWhenIdMissingOrAddressNotFound() {
        AddressDTO noId = new AddressDTO();
        assertThrows(IllegalArgumentException.class, () -> addressService.update(noId));

        AddressDTO bad = new AddressDTO(); bad.setAddressId(777L);
        when(addressRepository.findById(777L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> addressService.update(bad));
    }

    @Test
    void delete_success_returnsMessage() {
        when(addressRepository.existsById(1L)).thenReturn(true);
        String msg = addressService.delete(1L);
        assertEquals("Deleted Successfully", msg);
        verify(addressRepository).deleteById(1L);
    }

    @Test
    void delete_throwsWhenNotExists() {
        when(addressRepository.existsById(5L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> addressService.delete(5L));
        verify(addressRepository, never()).deleteById(anyLong());
    }

    @Test
    void getByUser_checksUserAndReturnsList() {
        when(userRepository.existsById(10L)).thenReturn(true);
        when(addressRepository.findByUserUserId(10L)).thenReturn(List.of(entity));

        List<Address> out = addressService.getByUser(10L);

        assertEquals(1, out.size());
        assertEquals(10L, out.get(0).getUser().getUserId());
    }

    @Test
    void getByUser_throwsWhenUserNotFound() {
        when(userRepository.existsById(123L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> addressService.getByUser(123L));
        verify(addressRepository, never()).findByUserUserId(anyLong());
    }
}