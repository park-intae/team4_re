package org.bookbook.controller;

import org.bookbook.domain.RatingVO;
import org.bookbook.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookbook/rating")
public class RatingController {
	@Autowired
	RatingMapper mapper;
	
	@PostMapping("/add")
	public ResponseEntity<String> addRating(@RequestBody RatingVO rating) {
	    int adjustedRating = rating.getRating() - 1;
	    rating.setRating(adjustedRating);
	    mapper.addRating(rating);
	    
	    String message = "별점이 등록되었습니다.";
	    return ResponseEntity.ok(message);
	}
	
//	@DeleteMapping("/delete")
//	public String deleteRating(RatingVO rating) {
//		mapper.deleteRating(rating);	
//		return "OK";
//	}
}
