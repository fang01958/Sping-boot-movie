package com.fang.movie.mapper;

import com.fang.movie.entity.FilmSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FilmSchedule record);

    int insertSelective(FilmSchedule record);

    FilmSchedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FilmSchedule record);

    int updateByPrimaryKey(FilmSchedule record);

    List<FilmSchedule> lisrFilmSchedule(@Param("filmCode") String filmCode, @Param("cinemaCode") String cinemaCode);

}