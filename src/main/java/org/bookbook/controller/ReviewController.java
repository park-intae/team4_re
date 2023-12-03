package org.bookbook.controller;

import org.bookbook.domain.ReviewVO;
import org.bookbook.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bestp")
public class ReviewController {
	@Autowired
	private ReviewMapper mapper;

	@GetMapping("/list")
	public ReviewVO read(
		@PathVariable int ratingId, @PathVariable int column1) {
		return mapper.get(column1); 
	}
	
	@PostMapping("/get")
	public ReviewVO create(@RequestBody ReviewVO vo) {
		mapper.create(vo);
		return mapper.get(vo.getColumn1());
	}
}
