package com.fang.movie.service;
import com.fang.movie.entity.*;
import com.fang.movie.exception.MyException;
import com.fang.movie.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private FilmInfoMapper filmInfoMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private CinemaMapper cinemaMapper;

    @Autowired
    private FilmScheduleMapper filmScheduleMapper;

    public List<Film> listFilms(){
        return filmMapper.listFilms();
    }

    public Film queryFilm(int filmId){
        return filmMapper.selectByPrimaryKey(filmId);
    }

    public Map<String, List<FilmInfo>> quertFilmInfo(int filmId){
        HashMap<String, List<FilmInfo>> map = new HashMap<>();
        map.put("directors",filmInfoMapper.queryFilmInfo(filmId, 0));
        map.put("actors",filmInfoMapper.queryFilmInfo(filmId, 1));
        return map;
    }

    public List<Area> listAreas(){
        return areaMapper.listAreas();
    }

    public List<Cinema> listCinemas(int filmId, String areaCode){
        Film film = filmMapper.selectByPrimaryKey(filmId);
        if (null == film){
            throw new MyException("电影不存在");
        }
       return cinemaMapper.listFilmCinema(areaCode,film.getFilmCode());
    }

    public Cinema queryCinema(String cinemaCode){
        return cinemaMapper.queryCienma(cinemaCode);
    }

    public List<FilmSchedule> listFilmSchedule(int filmId,String cinemaCode){
        Film film = filmMapper.selectByPrimaryKey(filmId);
        return filmScheduleMapper.lisrFilmSchedule(film.getFilmCode(),cinemaCode);
    }

    public List<Film> listCinemaFilms(String cinemaCode){
        List<Film> films = filmMapper.listFilmByCinema(cinemaCode);
//        for (Film film : films) {
//            System.out.println(film.toString());
//        }
        return films;
    }

    public FilmSchedule queryScene(int sceneId){
        return filmScheduleMapper.selectByPrimaryKey(sceneId);
    }

    public Cinema getCinema(int cinemaID){
        return cinemaMapper.selectByPrimaryKey(cinemaID);
    }
}
