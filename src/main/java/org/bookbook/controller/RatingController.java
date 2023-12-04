package org.bookbook.controller;

import org.bookbook.domain.RatingVO;
import org.bookbook.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/best")
public class RatingController {
	@Autowired
	RatingMapper mapper;
	
	@PostMapping("/list")
	public ResponseEntity<String> addRating(@RequestParam int rating) {
	    int adjustedRating = rating - 1;
	    
	    RatingVO vo = new RatingVO();
	    vo.setRating(adjustedRating);
	    mapper.addRating(vo);
	    
	    String message = "별점이 등록되었습니다.";
	    return ResponseEntity.ok(message);
	}
	
	
	@PostMapping("/submitRating")
    public ResponseEntity<String> submitRating( RatingVO rating) {
      mapper.addRating(rating);

        return ResponseEntity.ok("Received rating: " + rating);
    }
}
