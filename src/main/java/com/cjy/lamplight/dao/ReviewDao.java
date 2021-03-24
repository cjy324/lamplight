package com.cjy.lamplight.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cjy.lamplight.dto.Review;

@Mapper
public interface ReviewDao {

	List<Review> getForPrintReviews(@Param("relTypeCode") String relTypeCode);
	Review getReview(@Param("id")int id);
	void deleteReview(@Param("id")int id);
	void modifyReview(@Param("id")int id, @Param("body")String body);
	void addReview(Map<String, Object> param);
	Review getReviewByMemberId(@Param("memberId") int memberId);

}
