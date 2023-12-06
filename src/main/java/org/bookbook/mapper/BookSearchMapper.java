package org.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.bookbook.domain.BestVO;
import org.bookbook.domain.BookSearchVO;
import org.bookbook.domain.BookVO;
import org.bookbook.domain.GenreVO;
import org.bookbook.domain.LikeVO;
import org.bookbook.domain.TopicVO;
import org.bookbook.model.Criteria;

@Mapper
public interface BookSearchMapper {

	public List<BookVO> read(BookSearchVO bookSearch);

	public List<TopicVO> getTopic(TopicVO topic);

	public List<GenreVO> getGenre(GenreVO genre);

	public List<BookVO> getListPaging(Criteria cri);
	
    public List<BookVO> selectBooksByIds(List<Long> bookIds);
    
    public BookVO selectBookById(int bookId);
    
    public List<BestVO> selectBestBook();
    
    public void insertHistory(String user_id, int book_id);

	/* 게시판 총 갯수 */
	public int getTotal(String keywordparam);
	
	// 수정부분
	void addLike(LikeVO like);
	List<LikeVO> getLikes(String userid);
	
	//삭제 
	void deleteLike(LikeVO like);
}
