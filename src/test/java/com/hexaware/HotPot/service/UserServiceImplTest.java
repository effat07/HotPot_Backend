package com.hexaware.HotPot.service;

import com.hexaware.HotPot.dto.UserDTO;
import com.hexaware.HotPot.entity.User;
import com.hexaware.HotPot.entity.enums.Role;
import com.hexaware.HotPot.exception.UserNotFoundException;
import com.hexaware.HotPot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private UserServiceImpl userService;

    private UserDTO dto;

    @BeforeEach
    void setUp() {
        dto = new UserDTO();
        dto.setEmail("alice@example.com");
        dto.setUserName("alice");
        dto.setPassword("Secret#123");
        dto.setPhone("9999999999");
        dto.setRole(Role.CUSTOMER);
        dto.setActive(true);
    }

    @Test
    void create_shouldSave_whenValidAndUnique() {
        when(userRepository.existsByEmail("alice@example.com")).thenReturn(false);
        when(userRepository.existsByUserName("alice")).thenReturn(false);
        User saved = new User(); saved.setUserId(1L);
        when(userRepository.save(any(User.class))).thenReturn(saved);

        User out = userService.create(dto);

        assertNotNull(out);
        assertEquals(1L, out.getUserId());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User toSave = captor.getValue();
        assertEquals("alice@example.com", toSave.getEmail());
        assertEquals("alice", toSave.getUserName());
        assertEquals("Secret#123", toSave.getPassword());
        assertTrue(toSave.isActive());
    }

    @Test
    void create_shouldThrow_whenEmailMissing() {
        dto.setEmail("  ");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userService.create(dto));
        assertTrue(ex.getMessage().toLowerCase().contains("email"));
    }

    @Test
    void getById_shouldReturnOptional() {
        User u = new User(); u.setUserId(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.of(u));

        Optional<User> out = userService.getById(5L);

        assertTrue(out.isPresent());
        assertEquals(5L, out.get().getUserId());
    }

    @Test
    void update_shouldPersistChanges_whenUnique() {
        dto.setUserId(2L);
        User existing = new User();
        existing.setUserId(2L);
        existing.setEmail("old@example.com");
        existing.setUserName("oldname");
        when(userRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(userRepository.existsByEmail("alice@example.com")).thenReturn(false);
        when(userRepository.existsByUserName("alice")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User updated = userService.update(dto);

        assertEquals("alice@example.com", updated.getEmail());
        assertEquals("alice", updated.getUserName());
        assertEquals("9999999999", updated.getPhone());
        assertEquals(Role.CUSTOMER, updated.getRole());
        assertTrue(updated.isActive());
    }

    @Test
    void update_shouldThrow_whenUserNotFound() {
        dto.setUserId(99L);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.update(dto));
    }

    @Test
    void delete_shouldRemoveAndReturnMessage() {
        User existing = new User(); existing.setUserId(7L);
        when(userRepository.findById(7L)).thenReturn(Optional.of(existing));

        String msg = userService.delete(7L);

        assertEquals("Deleted Successfully", msg);
        verify(userRepository).delete(existing);
    }

    @Test
    void getAll_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        assertEquals(2, userService.getAllUser(new UserDTO()).size());
    }

    @Test
    void getByEmail_shouldTrimAndDelegate() {
        User u = new User(); u.setUserId(3L);
        when(userRepository.findByEmail("bob@example.com")).thenReturn(Optional.of(u));

        assertTrue(userService.getByEmail("  bob@example.com ").isPresent());
        verify(userRepository).findByEmail("bob@example.com");
    }

    @Test
    void getByRole_shouldThrowOnNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.getByRole(null));
    }
}