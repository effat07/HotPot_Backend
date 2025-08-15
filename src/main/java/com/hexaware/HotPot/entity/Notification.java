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
	    
	    public Notification() {
	    	
	    }

		public Notification(Long notificationId, User user, Order order, NotificationChannel channel,
				NotificationType type, LocalDateTime sentAt) {
			super();
			this.notificationId = notificationId;
			this.user = user;
			this.order = order;
			this.channel = channel;
			this.type = type;
			this.sentAt = sentAt;
		}

		public Long getNotificationId() {
			return notificationId;
		}

		public void setNotificationId(Long notificationId) {
			this.notificationId = notificationId;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		public NotificationChannel getChannel() {
			return channel;
		}

		public void setChannel(NotificationChannel channel) {
			this.channel = channel;
		}

		public NotificationType getType() {
			return type;
		}

		public void setType(NotificationType type) {
			this.type = type;
		}

		public LocalDateTime getSentAt() {
			return sentAt;
		}

		public void setSentAt(LocalDateTime sentAt) {
			this.sentAt = sentAt;
		}

		@Override
		public String toString() {
			return "Notification [notificationId=" + notificationId + ", user=" + user + ", order=" + order
					+ ", channel=" + channel + ", type=" + type + ", sentAt=" + sentAt + "]";
		}
	    
}
