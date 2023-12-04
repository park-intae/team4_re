package org.bookbook.controller;

import org.bookbook.domain.RatingVO;
import org.bookbook.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/api/book/detail/{bookid}")
public class RatingController {
    @Autowired
    RatingMapper mapper;
    
    @GetMapping
    public RatingVO getReply(@RequestParam int bookid) {
        log.info("---->>> Rating : " + bookid);
        return mapper.get(bookid);
    }
}

