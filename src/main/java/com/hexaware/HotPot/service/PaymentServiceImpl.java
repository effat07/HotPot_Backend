/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HotPot.dto.PaymentDTO;
import com.hexaware.HotPot.entity.Payment;
import com.hexaware.HotPot.exception.OrderNotFoundException;
import com.hexaware.HotPot.exception.PaymentFailedException;
import com.hexaware.HotPot.repository.OrderRepository;
import com.hexaware.HotPot.repository.PaymentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment create(PaymentDTO dto) {
        log.debug("Creating payment");

        if (dto == null) {
            log.error("Payment payload missing");
            throw new PaymentFailedException("Payment payload is required");
        }
        if (dto.getAmount() <= 0) {
            log.error("Invalid amount");
            throw new PaymentFailedException("Amount must be positive");
        }

        var order = orderRepository.findById(dto.getOrderId())
            .orElseThrow(() -> {
                log.error("Order not found: {}", dto.getOrderId());
                return new OrderNotFoundException("Order not found: " + dto.getOrderId());
            });

        Payment p = new Payment();
        p.setOrder(order);
        p.setAmount(dto.getAmount());
        p.setStatus(dto.getStatus());
        p.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());

        Payment saved = paymentRepository.save(p);
        log.info("Payment created: {}", saved.getPaymentId());
        return saved;
    }

    @Override
    public Optional<Payment> getById(Long paymentId) {
        log.debug("Fetching payment by ID: {}", paymentId);
        return paymentRepository.findById(paymentId);
    }

    @Override
    public Payment update(PaymentDTO dto) {
        log.debug("Updating payment");

        if (dto == null) {
            log.error("Payment payload missing");
            throw new PaymentFailedException("Payment payload is required");
        }
        if (dto.getPaymentId() == null) {
            log.error("Payment ID missing");
            throw new PaymentFailedException("paymentId is required for update");
        }

        Payment existing = paymentRepository.findById(dto.getPaymentId())
            .orElseThrow(() -> {
                log.error("Payment not found: {}", dto.getPaymentId());
                return new PaymentFailedException("Payment not found: " + dto.getPaymentId());
            });

        if (dto.getOrderId() != null &&
            (existing.getOrder() == null || !existing.getOrder().getOrderId().equals(dto.getOrderId()))) {
            var order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> {
                    log.error("Order not found: {}", dto.getOrderId());
                    return new OrderNotFoundException("Order not found: " + dto.getOrderId());
                });
            existing.setOrder(order);
        }

        if (dto.getAmount() > 0) existing.setAmount(dto.getAmount());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getCreatedAt() != null) existing.setCreatedAt(dto.getCreatedAt());

        Payment updated = paymentRepository.save(existing);
        log.info("Payment updated: {}", updated.getPaymentId());
        return updated;
    }

    @Override
    public String delete(Long paymentId) {
        log.debug("Deleting payment: {}", paymentId);

        if (paymentId == null) {
            log.error("Payment ID missing");
            throw new PaymentFailedException("paymentId is required");
        }
        if (!paymentRepository.existsById(paymentId)) {
            log.error("Payment not found: {}", paymentId);
            throw new PaymentFailedException("Payment not found: " + paymentId);
        }

        paymentRepository.deleteById(paymentId);
        log.info("Payment deleted: {}", paymentId);
        return "Deleted successfully";
    }

    @Override
    public List<Payment> getAllPayment(PaymentDTO ignored) {
        log.debug("Fetching all payments");
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getByOrder(Long orderId) {
        log.debug("Fetching payment for order: {}", orderId);
        return paymentRepository.findByOrderOrderId(orderId);
    }

    @Override
    public List<Payment> getByUser(Long userId) {
        log.debug("Fetching payments for user: {}", userId);
        return paymentRepository.findByOrderUserUserId(userId);
    }

    @Override
    public List<Payment> getByCreatedBetween(LocalDateTime start, LocalDateTime end) {
        log.debug("Fetching payments between {} and {}", start, end);
        return paymentRepository.findByCreatedAtBetween(start, end);
    }
}
