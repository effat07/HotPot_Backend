/*
 * Author: Effat Mujawar 
 * Date:10/08/2025
 **/
package com.hexaware.HotPot.dto;

import java.time.LocalDateTime;

import com.hexaware.HotPot.entity.enums.NotificationChannel;
import com.hexaware.HotPot.entity.enums.NotificationType;

import jakarta.validation.constraints.NotNull;

public class NotificationDTO {

    private Long notificationId;
    
    @jakarta.validation.constraints.NotNull
    private Long userId;
    
    @jakarta.validation.constraints.NotNull
    private Long orderId;

    @NotNull(message = "Notification channel is required")
    private NotificationChannel channel; // EMAIL

    @NotNull(message = "Notification type is required")
    private NotificationType type; // ORDER_PLACED, ORDER_UPDATED

    @NotNull(message = "Sent date/time is required")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime sentAt;
    
    public NotificationDTO() {
    	
    }

	public NotificationDTO(Long notificationId, @NotNull Long userId, @NotNull Long orderId,
			@NotNull(message = "Notification channel is required") NotificationChannel channel,
			@NotNull(message = "Notification type is required") NotificationType type,
			@NotNull(message = "Sent date/time is required") LocalDateTime sentAt) {
		super();
		this.notificationId = notificationId;
		this.userId = userId;
		this.orderId = orderId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
		return "NotificationDTO [notificationId=" + notificationId + ", userId=" + userId + ", orderId=" + orderId
				+ ", channel=" + channel + ", type=" + type + ", sentAt=" + sentAt + "]";
	}
    
}
