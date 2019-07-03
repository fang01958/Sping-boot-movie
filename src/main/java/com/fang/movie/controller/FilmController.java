package com.fang.movie.controller;
import com.fang.movie.dto.ResponseDTO;
import com.fang.movie.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilmController extends BaseController {

    @Autowired
    private FilmService filmService;

    @RequestMapping(value = "/film/listFilms",method = RequestMethod.GET)
    public ResponseDTO listFilms(){
        return ResponseDTO.success(filmService.listFilms());
    }

    @RequestMapping(value = "/film/{filmId}",method = RequestMethod.GET)
    public ResponseDTO queryFilmInfo(@PathVariable int filmId){
        return ResponseDTO.success(filmService.queryFilm(filmId));
    }

    @RequestMapping(value = "/film/actor/{filmId}",method = RequestMethod.GET)
    public ResponseDTO queryFilmActor(@PathVariable int filmId){
        return ResponseDTO.success(filmService.quertFilmInfo(filmId));
    }

    @RequestMapping(value = "/film/listAreas",method = RequestMethod.GET)
    public ResponseDTO listAreas(){
        return ResponseDTO.success(filmService.listAreas());
    }

    @RequestMapping(value = "/film/listFilmCinema/{filmId}/{areaCode}",method = RequestMethod.GET)
    public ResponseDTO listFilmCinema(@PathVariable int filmId, @PathVariable String areaCode){
        return ResponseDTO.success(filmService.listCinemas(filmId,areaCode));
    }

    @RequestMapping(value = "/cinema/queryCinema/{cinemaCode}",method = RequestMethod.GET)
    public ResponseDTO queryCinema( @PathVariable String cinemaCode){
        return ResponseDTO.success(filmService.queryCinema(cinemaCode));
    }

    @RequestMapping(value = "/film/filmSchedule/{filmId}/{cinemaCode}",method = RequestMethod.GET)
    public ResponseDTO filmSchedule(@PathVariable int filmId, @PathVariable String cinemaCode){
        return ResponseDTO.success(filmService.listFilmSchedule(filmId,cinemaCode));
    }

    @RequestMapping(value = "/cinema/cinemaFilms/{cinemaCode}",method = RequestMethod.GET)
    public ResponseDTO cinemaFilms(@PathVariable String cinemaCode){
        return ResponseDTO.success(filmService.listCinemaFilms(cinemaCode));
    }

    @RequestMapping(value = "/film/querySence/{sceneId}",method = RequestMethod.GET)
    public ResponseDTO querySence(@PathVariable int sceneId){
        return ResponseDTO.success(filmService.queryScene(sceneId));
    }

    @RequestMapping(value = "/film/listSelectedSeats/{sceneId}",method = RequestMethod.GET)
    public ResponseDTO listSelectedSeats(@PathVariable int sceneId){
        return ResponseDTO.success(filmService.listSelectedSeats(sceneId));
    }


}
