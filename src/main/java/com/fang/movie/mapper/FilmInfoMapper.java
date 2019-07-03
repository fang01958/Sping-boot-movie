package com.fang.movie.mapper;
import com.fang.movie.entity.FilmInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface FilmInfoMapper {
    int insert(FilmInfo record);

    int insertSelective(FilmInfo record);

    List<FilmInfo> queryFilmInfo(@Param("filmId") int filmId, @Param("actorType") int actorType);



}