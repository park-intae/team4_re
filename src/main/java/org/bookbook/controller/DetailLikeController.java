package org.bookbook.controller;

import java.util.List;

import org.bookbook.domain.CommentsVO;
import org.bookbook.domain.UserVO;
import org.bookbook.mapper.DetailLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/api/book/detail/{bookid}/like")
public class DetailLikeController {
	@Autowired
	DetailLikeMapper mapper;
	
	@GetMapping("")
	public List<UserVO> getLikeUserIds(@PathVariable int bookid) {
		List<String> likeUserIds = mapper.getLikeIds(bookid);
		
		return mapper.getUsers(likeUserIds);
	}
	
	@GetMapping("/{userId}")
	public List<String> getBookIds(@PathVariable String userId) {
		return mapper.getLikeBookIdsByUserId(userId);
	}
	
	
}
