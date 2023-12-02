package org.bookbook.mapper;

import java.util.List;

import org.bookbook.domain.BestVO;
import org.bookbook.domain.Criteria;

public interface BestMapper {
	public int getTotalCount(Criteria cri);
	public int getTotalCount2(Criteria cri);
	public int getTotalCount3(Criteria cri);
	public int getTotalCount4(Criteria cri);
	public int getTotalCount5(Criteria cri);
	public int getTotalCount6(Criteria cri);

	// 페이지 목록 추출
	public List<BestVO> getList(Criteria cri);
	public List<BestVO> getList2(Criteria cri);
	public List<BestVO> getList3(Criteria cri);
	public List<BestVO> getList4(Criteria cri);
	public List<BestVO> getList5(Criteria cri);
	public List<BestVO> getList6(Criteria cri);

//	public int getTotal(Criteria cri);
//	public int getTotal2(Criteria cri);
//	public int getTotal3(Criteria cri);
//	public int getTotal4(Criteria cri);
//	public int getTotal5(Criteria cri);
//	public int getTotal6(Criteria cri);

	public BestVO get(int Column1);
	
	public List<BestVO> getListWithPaging(Criteria cri);
	public List<BestVO> getListWithPaging2(Criteria cri);
	public List<BestVO> getListWithPaging3(Criteria cri);
	public List<BestVO> getListWithPaging4(Criteria cri);
	public List<BestVO> getListWithPaging5(Criteria cri);
	public List<BestVO> getListWithPaging6(Criteria cri);
	

}