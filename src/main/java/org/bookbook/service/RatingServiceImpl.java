package org.bookbook.service;

import org.bookbook.domain.RatingVO;
import org.bookbook.mapper.RatingMapper;

public class RatingServiceImpl implements RatingService {

    private final RatingMapper ratingMapper;

    public RatingServiceImpl(RatingMapper ratingMapper) {
        this.ratingMapper = ratingMapper;
    }

    @Override
    public void setRating(int column1) {
        double ratingAvg = ratingMapper.getRating(column1); // RatingMapper의 getRating 메서드 호출하여 평균 별점 가져오기

        RatingVO rv = new RatingVO();
        rv.setColumn1(column1);
        rv.setRating((int) ratingAvg);

        ratingMapper.addRating(rv); // RatingMapper의 addRating 메서드를 호출하여 RatingVO 객체 저장
    }
}

