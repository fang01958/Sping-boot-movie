package com.fang.movie.mapper;

import com.fang.movie.entity.Film;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Film record);

    int insertSelective(Film record);

    Film selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Film record);

    int updateByPrimaryKeyWithBLOBs(Film record);

    int updateByPrimaryKey(Film record);

    List<Film> listFilms();

    List<Film> listFilmByCinema(@Param("cinemaCode")String cinemaCode);

}