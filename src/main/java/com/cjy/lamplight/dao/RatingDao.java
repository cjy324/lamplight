package com.cjy.lamplight.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cjy.lamplight.dto.Rating;

@Mapper
public interface RatingDao {

	Rating getRating(@Param("id")int id);
	void deleteRating(@Param("id")int id);
	void modifyRating(@Param("id")int id, @Param("body")String body);
	void addRating(Map<String, Object> param);

}
