/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/
package com.hexaware.HotPot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.hexaware.HotPot.dto.PaymentDTO;
import com.hexaware.HotPot.entity.Payment;

public interface IPaymentService {
   
    Payment create(PaymentDTO paymentddto);
    Optional<Payment> getById(Long paymentId);
    Payment update(PaymentDTO paymentddto);
    String delete(Long paymentId);
    List<Payment> getAllPayment(PaymentDTO paymentddto);
   
    Optional<Payment> getByOrder(Long orderId);
    List<Payment> getByUser(Long userId);
    List<Payment> getByCreatedBetween(LocalDateTime start, LocalDateTime end);
}
