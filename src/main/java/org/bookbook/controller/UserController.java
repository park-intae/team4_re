package org.bookbook.controller;

import java.security.Principal;
import java.util.List;

import org.bookbook.domain.CommentsVO;
import org.bookbook.domain.UserVO;
import org.bookbook.domain.notification.Notification;
import org.bookbook.mapper.CommentsMapper;
import org.bookbook.service.FollowerService;
import org.bookbook.service.NotificationService;
import org.bookbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/api")
@Log4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowerService followerService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	CommentsMapper commentsMapper;

	@GetMapping("/usersWithFollowStatus")
	@ResponseBody
	public ResponseEntity<List<UserVO>> getAllUsersWithFollowStatus(Principal principal) {
		try {
			String currentUserId = principal.getName(); // 현재 로그인한 사용자의 ID를 얻음
			List<UserVO> users = userService.getAllUsersWithFollowStatus(currentUserId);
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} //
	}

	@PostMapping("/toggleFollow")
	public ResponseEntity<Boolean> toggleFollow(Principal principal, @RequestParam String followingId) {
		try {
			String currentUserId = principal.getName(); // 현재 로그인한 사용자의 ID
			boolean isFollowing = followerService.toggleFollow(currentUserId, followingId);
			return ResponseEntity.ok(isFollowing);
		} catch (Exception e) {
			log.error("팔로우 상태를 전환하는 중 오류가 발생", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 사용자에게 알람 데이터 조회 메소드 추가
	@GetMapping("/notifications")
	@ResponseBody
	public ResponseEntity<List<Notification>> getUserNotifications(Principal principal) {
		String userId = principal.getName();
		List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
		return ResponseEntity.ok(notifications);
	}

	// 알림 삭제 API
	@PostMapping("/deleteNotification/{notificationId}")
	public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
		try {
			notificationService.deleteNotification(notificationId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("알림 삭제 실패", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/user/comments")
	public ResponseEntity<List<CommentsVO>> getUserComments(@RequestParam String userId) {
		try {
			List<CommentsVO> comments = commentsMapper.readCommentsByUserId(userId);
			return ResponseEntity.ok(comments);
		} catch (Exception e) {
			log.error("사용자 댓글 불러오기 실패 " + userId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
