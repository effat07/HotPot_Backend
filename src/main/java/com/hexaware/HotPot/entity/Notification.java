/*
 * Author: Effat Mujawar 
 * Date:02/08/2025
 **/

package com.hexaware.HotPot.entity;

import java.time.LocalDateTime;

import com.hexaware.HotPot.entity.enums.NotificationChannel;
import com.hexaware.HotPot.entity.enums.NotificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
	    @Id 
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long notificationId;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user; 

	    @ManyToOne
	    @JoinColumn(name = "order_id")
	    private Order order; 

	    @Enumerated(EnumType.STRING)
	    private NotificationChannel channel; // EMAIL

	    @Enumerated(EnumType.STRING)
	    private NotificationType type; // ORDER_PLACED, ORDER_UPDATED

	    private LocalDateTime sentAt;
}
