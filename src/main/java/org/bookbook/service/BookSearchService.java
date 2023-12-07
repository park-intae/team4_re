package org.bookbook.service;

import java.util.List;

import org.bookbook.domain.BestVO;
import org.bookbook.domain.BookSearchVO;
import org.bookbook.domain.BookVO;
import org.bookbook.domain.CommentsVO;
import org.bookbook.domain.GenreVO;
import org.bookbook.domain.LikeVO;
import org.bookbook.domain.TopicVO;
import org.bookbook.model.Criteria;

public interface BookSearchService {
	public List<BookVO> getBookList(BookSearchVO bookSearch);

	public List<TopicVO> getTopicList(TopicVO topic);

	public List<GenreVO> getGenreList(GenreVO genre);

	public List<BookVO> getListPaging(Criteria cri);
	
	public List<BookVO> getBookListById(List<Long> bookIds);
	
	public BookVO getBookById(int bookIds);
	
	public List<BestVO> getBestBookList();
	
	public void insertBookId(String userId, int bookId);
		

	 /* °Ô½ÃÆÇ ÃÑ °¹¼ö */
    public int getTotal(String keywordParam);

    
    // ï¿½ï¿½ï¿½Æ¿ï¿½ ï¿½ß°ï¿½ ï¿½Îºï¿½
    void addLike(LikeVO like);
    List<LikeVO> getLikes(String userid);
    
 // ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Îºï¿½
    void deleteLike(LikeVO like);

}