package org.bookbook.controller;

import org.bookbook.sse.SseEmitters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SseController {

	@Autowired
	private SseEmitters sseEmitters;

	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> connect(Authentication authentication) {

		if (authentication == null || !authentication.isAuthenticated()) {
			// 인증되지 않은 사용자의 경우, 연결을 설정하지 않음
		//	log.info("인증되지 않은 사용자의 SSE 연결 시도");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String username = authentication.getName();
		SseEmitter emitter = new SseEmitter(60 * 10000L); // 10분
		sseEmitters.addEmitter(username, emitter);

		emitter.onCompletion(() -> sseEmitters.removeEmitter(username));
		emitter.onTimeout(() -> sseEmitters.removeEmitter(username));
		emitter.onError((e) -> sseEmitters.removeEmitter(username));

		return ResponseEntity.ok(emitter);
	}
}