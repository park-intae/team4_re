package org.bookbook.service;

import java.util.List;

import org.bookbook.domain.BookSearchVO;
import org.bookbook.domain.BookVO;
import org.bookbook.domain.GenreVO;
import org.bookbook.domain.LikeVO;
import org.bookbook.domain.TopicVO;
import org.bookbook.model.Criteria;

public interface BookSearchService {
	public List<BookVO> getBookList(BookSearchVO bookSearch);

	public List<TopicVO> getTopicList(TopicVO topic);

	public List<GenreVO> getGenreList(GenreVO genre);

	public List<BookVO> getListPaging(Criteria cri);
	
	 /* 게시판 총 갯수 */
    public int getTotal();
    
    // 좋아요 추가 부분
    void addLike(LikeVO like);
    List<LikeVO> getLikes(String userid);

}