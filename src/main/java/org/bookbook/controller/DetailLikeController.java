package org.bookbook.controller;

import java.util.List;

import org.bookbook.domain.BookVO;
import org.bookbook.domain.LikeUserVO;
import org.bookbook.domain.UserVO;
import org.bookbook.mapper.DetailLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/api/book/detail/{bookid}/like")
public class DetailLikeController {
    @Autowired
    DetailLikeMapper mapper;
    
    @GetMapping("")
    public List<LikeUserVO> getLikeUserIds(@PathVariable int bookid) {
        List<String> likeUserIds = mapper.getLikeIds(bookid);
                
        List<LikeUserVO> users = mapper.getUsers(likeUserIds);
    
        return users;

    }
    
    @GetMapping("/{userId}")
    public List<BookVO> getBookIds(@PathVariable String userId) {

        try {
            List<String> bookIds = mapper.getLikeBookIdsByUserId(userId);

            List<BookVO> books = mapper.getBooks(bookIds);

            return books;
        } catch (Exception e) {
            log.error("Error occurred while fetching bookIds or books: " + e.getMessage());
            return null;
        }
    }

}
