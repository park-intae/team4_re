package org.bookbook.controller;

import java.security.Principal;
import java.util.List;

import org.bookbook.domain.CommentsVO;
import org.bookbook.mapper.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/api/book/detail/{bookid}/comment")
public class CommentsController {
	@Autowired
	CommentsMapper mapper;

	@GetMapping("")
	public List<CommentsVO> readComments(@PathVariable int bookid) {
		List<CommentsVO> comments = mapper.readAll(bookid);
		return comments;
	}

	@PostMapping(value="", consumes = "application/json;charset=UTF-8")
	public CommentsVO create(@RequestBody CommentsVO vo) {
		log.info(vo);
		mapper.create(vo);
		log.info("test1");
		int id = vo.getRatingid();
		log.info("------->>> "+id);
		return mapper.get(id);
	}
	
	
	@GetMapping("/{ratingid}")
	public CommentsVO readComment(
			@PathVariable int bookid, @PathVariable int ratingid) {
		return mapper.get(ratingid);
	}


	
	@PutMapping("/{ratingid}")
	public ResponseEntity<?> update(@PathVariable int ratingid, @RequestBody CommentsVO vo, Principal principal) {
	    try {
	        log.info("Updating comment with ID: " + ratingid);
	        log.info("Received comment data: " + vo);

	        // 현재 로그인한 사용자의 userid를 가져와서 CommentsVO 객체에 설정
	        String loggedInUserId = principal.getName();
	        vo.setUserid(loggedInUserId);

	        CommentsVO existingComment = mapper.get(ratingid);
	        if (existingComment == null || !existingComment.getUserid().equals(vo.getUserid())) {
	            log.warn("Unauthorized access attempt to comment with ID: " + ratingid);
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
	        }
	        mapper.update(vo);
	        return ResponseEntity.ok(mapper.get(vo.getRatingid()));
	    } catch (Exception e) {
	        log.error("Error updating comment with ID: " + ratingid, e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@DeleteMapping("/{ratingid}")
	public ResponseEntity<?> delete(@PathVariable int ratingid, Principal principal) {
	  try {
	    CommentsVO existingComment = mapper.get(ratingid);
	    if (existingComment == null || !existingComment.getUserid().equals(principal.getName())) {
	      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
	    }
	    mapper.delete(ratingid);
	    return ResponseEntity.ok().build();
	  } catch (Exception e) {
	    log.error("Error deleting comment", e);
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	  }
	}

}
