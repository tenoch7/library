/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.ui;

import Library.dto.Movie;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author juanm
 */
public class LibraryView {

    private UserIO io;
//    UserIO io = new UserIOConsoleImpl(); changed for dependency injection

    public LibraryView(UserIO io) {
        this.io = io;
    }

    LocalDate ld;

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. List Movie IDs");
        io.print("2. Create Movie");
        io.print("3. View a Movie");
        io.print("4. Remove a Movie");
        io.print("5. Edit Movie");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public Movie getNewMovieInfo() {

        String movieName = io.readString("Please enter Movie name");
        Movie currentMovie = new Movie(movieName);

        boolean askAgain = true;
        do {
        String releaseDate = io.readString("Please enter release date (yyyy-MM-dd)");
        if (!releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            io.print("Incorrect entry");
        } else if (releaseDate.trim().isEmpty()){
            io.print("Blank entry");
        } else if (releaseDate.matches("\\d{4}-\\d{2}-\\d{2}"))  {  
            ld = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE);
            currentMovie.setReleaseDate(ld);
            askAgain = false;
            io.print("Correct entry!");
        
        } else {
            io.print("Incorrect entry");
        }
        } while(askAgain == true);
        

        String mPAARating = io.readString("Please enter MPAA rating");
        if (mPAARating.trim().isEmpty()) {
            io.print("Blank entry (null)");
        } else {
            currentMovie.setmPAARating(mPAARating);
        }

        String directorName = io.readString("Please enter director name");
        if (directorName.trim().isEmpty()) {
            io.print("Blank entry (null)");
        } else {
            currentMovie.setDirectorName(directorName);
        }

        String studio = io.readString("Please enter the name of the studio");
        if (studio.trim().isEmpty()) {
            io.print("Blank entry (null)");
        } else {
            currentMovie.setStudio(studio);
        }

        String userRating = io.readString("Please enter user rating or note");
        if (userRating.trim().isEmpty()) {
            io.print("Blank entry (null)");
        } else {
            currentMovie.setUserRating(userRating);
        }

        return currentMovie;
    }

    public void displayCreateMovieBanner() {
        io.print("=== Create Movie ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString(
                "Movie successfully created.  Please hit enter to continue");
    }

    public void displayMovieList(List<Movie> movieList) {
        for (Movie currentMovie : movieList) {
            io.print(currentMovie.getMovieName() /*+ ": \n Released: "
                    + currentMovie.getReleaseDate() + "\n MPAA: "
                    + currentMovie.getMPAARating() + "\n Director: "
                    + currentMovie.getDirectorName()*/);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All Movies ===");
    }

    public void displayDisplayMovieBanner() {
        io.print("=== Display Movie ===");
    }

    public String getMovieNameChoice() {
        return io.readString("Please enter the Movie ID.");
    }

    public void displayMovie(Movie movie) {
        if (movie != null) {
            io.print(("  Name: ") + movie.getMovieName()
                    + ("\n  Release date: ") + movie.getReleaseDate()
                    + ("\n  MPAA rating: ") + movie.getMPAARating()
                    + ("\n  Director: ") + movie.getDirectorName()
                    + ("\n  Studio: ") + movie.getStudio()
                    + ("\n  User rating: ") + movie.getUserRating());
        } else {
            io.print("Movie doesn't exist");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveMovieBanner() {
        io.print("=== Remove Movie ===");
    }

    public void displayRemoveSuccessBanner() {
        io.readString("Movie successfully removed. Please hit enter to continue.");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public Movie changeMovieInfo(Movie movie) {

        io.print("Current release date: " + movie.getReleaseDate());
        
        boolean askAgain = true;
        do {
        String releaseDate = io.readString("Please enter NEW release date (yyyy-MM-dd)");
        if (!releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            io.print("Incorrect entry");
        } else if (releaseDate.trim().isEmpty()){
            io.print("Field NOT changed (blank entry)");
            askAgain = false;
        } else if (releaseDate.matches("\\d{4}-\\d{2}-\\d{2}"))  {  
            ld = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE);
            movie.setReleaseDate(ld);
            askAgain = false;
            io.print("Correct entry!");
        
        } else {
            io.print("Incorrect entry");
        }
//        if (releaseDate.trim().isEmpty()) {
//            io.print("Field NOT changed (blank entry)");
//        } else {
//            ld = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE);
//            movie.setReleaseDate(ld);
//        }
        } while (askAgain == true);

        io.print("Current MPAA rating: " + movie.getMPAARating());
        String mPAARating = io.readString("Please enter MPAA rating");
        if (mPAARating.trim().isEmpty()) {
            io.print("Field NOT changed (blank entry)");
        } else {
            movie.setmPAARating(mPAARating);
        }

        io.print("Current director name: " + movie.getDirectorName());
        String directorName = io.readString("Please enter director name");
        if (directorName.trim().isEmpty()) {
            io.print("Field NOT changed (blank entry))");
        } else {
            movie.setDirectorName(directorName);
        }

        io.print("Current studio: " + movie.getStudio());
        String studio = io.readString("Please enter the name of the studio");
        if (studio.trim().isEmpty()) {
            io.print("Field NOT changed (blank entry)");
        } else {
            movie.setStudio(studio);
        }

        io.print("Current user rating: " + movie.getUserRating());
        String userRating = io.readString("Please enter user rating or note");
        if (userRating.trim().isEmpty()) {
            io.print("Field NOT changed (blank entry)");
        } else {
            movie.setUserRating(userRating);
        }

        return movie;
    }

    public void displayEditMovieBanner() {
        io.print("=== Edit Movie ===");
    }

    public void displayEditSuccessBanner() {
        io.print("=== Movie Successfully edited ===");
    }
    
//    public List<Movie> filterByDate(Movie movie) {
//        
//    }
    
    public void displayFilterMovieBanner() {
        io.print("=== Filter Movies by date ===");
    }

}
