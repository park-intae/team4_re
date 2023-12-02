package org.bookbook.service;

import java.util.List;

import org.bookbook.domain.BestVO;
import org.bookbook.domain.Criteria;
import org.bookbook.mapper.BestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BestServiceImpl implements BestService {

	@Autowired
	private BestMapper mapper;

	@Override
	public int getTotalCount(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	@Override
	public int getTotalCount2(Criteria cri) {
		return mapper.getTotalCount2(cri);
	}
	@Override
	public int getTotalCount3(Criteria cri) {
		return mapper.getTotalCount3(cri);
	}
	@Override
	public int getTotalCount4(Criteria cri) {
		return mapper.getTotalCount4(cri);
	}
	@Override
	public int getTotalCount5(Criteria cri) {
		return mapper.getTotalCount5(cri);
	}
	@Override
	public int getTotalCount6(Criteria cri) {
		return mapper.getTotalCount6(cri);
	}

	@Override
	public List<BestVO> getList(Criteria cri) {
		List<BestVO> list = mapper.getList(cri);
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<BestVO> getList2(Criteria cri) {
		List<BestVO> list = mapper.getList2(cri);
		return mapper.getListWithPaging2(cri);
	}
	@Override
	public List<BestVO> getList3(Criteria cri) {
		List<BestVO> list = mapper.getList3(cri);
		return mapper.getListWithPaging3(cri);
	}
	@Override
	public List<BestVO> getList4(Criteria cri) {
		List<BestVO> list = mapper.getList4(cri);
		return mapper.getListWithPaging4(cri);
	}
	@Override
	public List<BestVO> getList5(Criteria cri) {
		List<BestVO> list = mapper.getList5(cri);
		return mapper.getListWithPaging5(cri);
	}
	@Override
	public List<BestVO> getList6(Criteria cri) {
		List<BestVO> list = mapper.getList6(cri);
		return mapper.getListWithPaging6(cri);
	}


	@Override
	public BestVO get(int column1) {
		BestVO best = mapper.get(column1);
		return best;
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	@Override
	public int getTotal2(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount2(cri);
	}
	@Override
	public int getTotal3(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount3(cri);
	}
	@Override
	public int getTotal4(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount4(cri);
	}
	@Override
	public int getTotal5(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount5(cri);
	}
	@Override
	public int getTotal6(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount6(cri);
	}

}