package org.bookbook.service;

import java.util.ArrayList;
import java.util.List;

import org.bookbook.domain.notification.Notification;
import org.bookbook.mapper.NotificationMapper;
import org.bookbook.sse.SseEmitters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceimpl implements NotificationService {

	@Autowired
	NotificationMapper notificationMapper;

	@Autowired
	SseEmitters sseEmitters;

	private List<Notification> notifications = new ArrayList<>();

	// 알림 추가
	public void addNotification(Notification notification) {
		notifications.add(notification);
		try {
			notificationMapper.insertNotification(notification);
		} catch (Exception e) {
			log.error("알림 메세지 저장중 오류 발생 : ", e);
		}
	}

	// 로그인 성공 알림을 전송하는 메소드
	public void sendLoginSuccessNotification(String username) {
		Notification notification = new Notification(username + "님이 로그인하셨습니다.", username);
		sseEmitters.sendNotificationToUser(username, notification);
	}

	@Override
	public void markNotificationAsRead(Long id) {
		notificationMapper.updateNotificationReadStatus(id, true);

	}

	public void sendFollowNotification(String followerId, String followingId) {
		String content = followerId + "님이 팔로우를 했습니다";
		Notification notification = new Notification(content, followingId);

		// 알림 객체 생성 및 저장
		addNotification(notification);

		sseEmitters.sendNotificationToUser(followingId, notification);
	}

	@Override
	public List<Notification> getNotificationsByUserId(String userId) {
		return notificationMapper.getNotificationsByUserId(userId);
	}
	
	public void deleteNotification(Long notificationId) {
	    notificationMapper.deleteNotification(notificationId);
	}
}