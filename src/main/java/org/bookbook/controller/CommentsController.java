package org.bookbook.controller;

import java.util.List;

import org.bookbook.domain.CommentsVO;
import org.bookbook.mapper.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
	public CommentsVO update(
			@PathVariable int ratingid,
			@RequestBody CommentsVO vo) {
		System.out.println("==> " + vo);
		mapper.update(vo);
		return mapper.get(vo.getRatingid());
	}

	@DeleteMapping("/{ratingid}")
	public String delete(@PathVariable int ratingid) {
		System.out.println("delete ==>" + ratingid);
		mapper.delete(ratingid);
		return "OK";
	}

}
