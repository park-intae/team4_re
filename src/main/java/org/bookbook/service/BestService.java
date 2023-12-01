package org.bookbook.service;

import java.util.List;

import org.bookbook.domain.BestVO;
import org.bookbook.domain.Criteria;

public interface BestService {
	public List<BestVO> getList(Criteria cri);
	public List<BestVO> getList2(Criteria cri);
	public List<BestVO> getList3(Criteria cri);
	public List<BestVO> getList4(Criteria cri);
	public List<BestVO> getList5(Criteria cri);
	public List<BestVO> getList6(Criteria cri);
	public int getTotalCount(Criteria cri);
	public int getTotalCount2(Criteria cri);
	public int getTotalCount3(Criteria cri);
	public int getTotalCount4(Criteria cri);
	public int getTotalCount5(Criteria cri);
	public int getTotalCount6(Criteria cri);
	public BestVO get(int column1);
	public int getTotal(Criteria cri);
	public int getTotal2(Criteria cri);
	public int getTotal3(Criteria cri);
	public int getTotal4(Criteria cri);
	public int getTotal5(Criteria cri);
	public int getTotal6(Criteria cri);
	
}