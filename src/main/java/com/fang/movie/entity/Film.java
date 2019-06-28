package com.fang.movie.entity;

public class Film {
    private Integer id;

    private String filmName;

    private String filmCode;

    private String filmPic;

    private String filmType;

    private String filmTime;

    private String filmShowTime;

    private String plot;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName == null ? null : filmName.trim();
    }

    public String getFilmCode() {
        return filmCode;
    }

    public void setFilmCode(String filmCode) {
        this.filmCode = filmCode == null ? null : filmCode.trim();
    }

    public String getFilmPic() {
        return filmPic;
    }

    public void setFilmPic(String filmPic) {
        this.filmPic = filmPic == null ? null : filmPic.trim();
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType == null ? null : filmType.trim();
    }

    public String getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(String filmTime) {
        this.filmTime = filmTime == null ? null : filmTime.trim();
    }

    public String getFilmShowTime() {
        return filmShowTime;
    }

    public void setFilmShowTime(String filmShowTime) {
        this.filmShowTime = filmShowTime == null ? null : filmShowTime.trim();
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot == null ? null : plot.trim();
    }
}