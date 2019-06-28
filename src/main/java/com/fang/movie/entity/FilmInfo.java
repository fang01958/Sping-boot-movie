package com.fang.movie.entity;

public class FilmInfo {
    private Integer filmId;

    private String actorName;

    private Integer actorType;

    private String actorPic;

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName == null ? null : actorName.trim();
    }

    public Integer getActorType() {
        return actorType;
    }

    public void setActorType(Integer actorType) {
        this.actorType = actorType;
    }

    public String getActorPic() {
        return actorPic;
    }

    public void setActorPic(String actorPic) {
        this.actorPic = actorPic == null ? null : actorPic.trim();
    }
}