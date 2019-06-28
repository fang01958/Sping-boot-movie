package com.fang.movie.mapper;

import com.fang.movie.entity.Cinema;
import com.fang.movie.entity.Film;
import com.fang.movie.entity.FilmSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CinemaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cinema record);

    int insertSelective(Cinema record);

    Cinema selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cinema record);

    int updateByPrimaryKey(Cinema record);

    List<Cinema> listFilmCinema(@Param("areaCode") String areaCode, @Param("filmCode") String filmCode);

    Cinema queryCienma(String cinemaCode);



}