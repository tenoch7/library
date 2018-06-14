/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.dto;

import java.time.LocalDate;

/**
 *
 * @author juanm
 */
public class Movie {

    private LocalDate releaseDate;
    private String mPAARating;
    private String movieName;
    private String directorName;
    private String studio;
    private String userRating;

    public Movie(String movieName) {
        this.movieName = movieName;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMPAARating() {
        return mPAARating;
    }

    public void setmPAARating(String mPAARating) {
        this.mPAARating = mPAARating;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    @Override
    public String toString() {
        return " Name: " + movieName + " |Release date: " + releaseDate + " |mPAARating: "
                + mPAARating + " |Director's name: " + directorName + " |Studio: " + studio + " |User Rating: " + userRating;
    }
}
