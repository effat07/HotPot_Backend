package com.hexaware.HotPot.service;

import com.hexaware.HotPot.dto.PaymentDTO;
import com.hexaware.HotPot.entity.Order;
import com.hexaware.HotPot.entity.Payment;
import com.hexaware.HotPot.exception.OrderNotFoundException;
import com.hexaware.HotPot.exception.PaymentFailedException;
import com.hexaware.HotPot.repository.OrderRepository;
import com.hexaware.HotPot.repository.PaymentRepository;
import com.hexaware.HotPot.entity.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock private PaymentRepository paymentRepository;
    @Mock private OrderRepository orderRepository;

    @InjectMocks private PaymentServiceImpl paymentService;

    private PaymentDTO dto;
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setOrderId(10L);

        dto = new PaymentDTO();
        dto.setOrderId(10L);
        dto.setAmount(500.0);
        dto.setStatus(PaymentStatus.SUCCESS);
        dto.setCreatedAt(LocalDateTime.of(2025, 8, 15, 10, 0));
    }

    @Test
    void create_shouldSave_whenValid() {
        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        Payment saved = new Payment();
        saved.setPaymentId(1L);
        when(paymentRepository.save(any(Payment.class))).thenReturn(saved);

        Payment out = paymentService.create(dto);

        assertNotNull(out);
        assertEquals(1L, out.getPaymentId());

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(captor.capture());
        Payment toSave = captor.getValue();
        assertEquals(order, toSave.getOrder());
        assertEquals(500.0, toSave.getAmount());
        assertEquals(PaymentStatus.SUCCESS, toSave.getStatus());
    }

    @Test
    void create_shouldThrow_whenDtoNull() {
        assertThrows(PaymentFailedException.class, () -> paymentService.create(null));
    }

    @Test
    void create_shouldThrow_whenAmountInvalid() {
        dto.setAmount(0);
        assertThrows(PaymentFailedException.class, () -> paymentService.create(dto));
    }

    @Test
    void create_shouldThrow_whenOrderNotFound() {
        when(orderRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> paymentService.create(dto));
    }

    @Test
    void getById_shouldReturnOptional() {
        Payment p = new Payment(); p.setPaymentId(2L);
        when(paymentRepository.findById(2L)).thenReturn(Optional.of(p));

        Optional<Payment> out = paymentService.getById(2L);

        assertTrue(out.isPresent());
        assertEquals(2L, out.get().getPaymentId());
    }

    @Test
    void update_shouldModify_whenValid() {
        dto.setPaymentId(3L);
        Payment existing = new Payment();
        existing.setPaymentId(3L);
        existing.setOrder(order);
        when(paymentRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        Payment updated = paymentService.update(dto);

        assertEquals(500.0, updated.getAmount());
        assertEquals(PaymentStatus.SUCCESS, updated.getStatus());
    }

    @Test
    void update_shouldChangeOrder_whenDifferentOrderId() {
        dto.setPaymentId(4L);
        dto.setOrderId(20L);
        Order newOrder = new Order(); newOrder.setOrderId(20L);

        Payment existing = new Payment();
        existing.setPaymentId(4L);
        existing.setOrder(order);

        when(paymentRepository.findById(4L)).thenReturn(Optional.of(existing));
        when(orderRepository.findById(20L)).thenReturn(Optional.of(newOrder));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        Payment updated = paymentService.update(dto);

        assertEquals(newOrder, updated.getOrder());
    }

    @Test
    void update_shouldThrow_whenDtoNull() {
        assertThrows(PaymentFailedException.class, () -> paymentService.update(null));
    }

    @Test
    void update_shouldThrow_whenPaymentIdMissing() {
        assertThrows(PaymentFailedException.class, () -> paymentService.update(dto));
    }

    @Test
    void update_shouldThrow_whenPaymentNotFound() {
        dto.setPaymentId(99L);
        when(paymentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PaymentFailedException.class, () -> paymentService.update(dto));
    }

    @Test
    void delete_shouldRemove_whenExists() {
        when(paymentRepository.existsById(7L)).thenReturn(true);

        String msg = paymentService.delete(7L);

        assertEquals("Deleted successfully", msg);
        verify(paymentRepository).deleteById(7L);
    }

    @Test
    void delete_shouldThrow_whenIdNull() {
        assertThrows(PaymentFailedException.class, () -> paymentService.delete(null));
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        when(paymentRepository.existsById(8L)).thenReturn(false);
        assertThrows(PaymentFailedException.class, () -> paymentService.delete(8L));
    }

    @Test
    void getAllPayment_shouldReturnList() {
        when(paymentRepository.findAll()).thenReturn(List.of(new Payment(), new Payment()));
        assertEquals(2, paymentService.getAllPayment(new PaymentDTO()).size());
    }

    @Test
    void getByOrder_shouldReturnOptional() {
        Payment p = new Payment(); p.setPaymentId(11L);
        when(paymentRepository.findByOrderOrderId(10L)).thenReturn(Optional.of(p));

        Optional<Payment> out = paymentService.getByOrder(10L);

        assertTrue(out.isPresent());
        assertEquals(11L, out.get().getPaymentId());
    }

    @Test
    void getByUser_shouldReturnList() {
        when(paymentRepository.findByOrderUserUserId(1L)).thenReturn(List.of(new Payment()));
        assertEquals(1, paymentService.getByUser(1L).size());
    }

    @Test
    void getByCreatedBetween_shouldReturnList() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        when(paymentRepository.findByCreatedAtBetween(start, end)).thenReturn(List.of(new Payment()));

        assertEquals(1, paymentService.getByCreatedBetween(start, end).size());
    }
}
