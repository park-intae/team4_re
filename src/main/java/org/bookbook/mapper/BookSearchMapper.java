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

	/* °Ô½ÃÆÇ ÃÑ °¹¼ö */
	public int getTotal();
	
	// ¼öÁ¤ºÎºÐ
	void addLike(LikeVO like);
	List<LikeVO> getLikes(String userid);
}
