/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.controller;

import library.dao.LibraryDaoException;
import Library.dto.Movie;
import Library.ui.LibraryView;

import java.util.List;
import library.dao.LibraryDao;

/**
 *
 * @author juanm
 */
public class LibraryController {

    LibraryView view;
    LibraryDao dao;

    public LibraryController(LibraryDao dao, LibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        listMovies();
                        break;
                    case 2:
                        createMovie();
                        break;
                    case 3:
                        viewMovie();
                        break;
                    case 4:
                        removeMovie();
                        break;
                    case 5:
                        editMovie();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (LibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void createMovie() throws LibraryDaoException {
        view.displayCreateMovieBanner();
        Movie newMovie = view.getNewMovieInfo();
        
        dao.addMovie(newMovie.getMovieName(), newMovie);
        view.displayCreateSuccessBanner();
    }

    private void listMovies() throws LibraryDaoException {
        view.displayDisplayAllBanner();
        List<Movie> movieList = dao.getAllMovies();
        view.displayMovieList(movieList);
    }

    private void viewMovie() throws LibraryDaoException {
        view.displayDisplayMovieBanner();
        String movieName = view.getMovieNameChoice();
        Movie movie = dao.getMovie(movieName);
        view.displayMovie(movie);
    }

    private void removeMovie() throws LibraryDaoException {
        view.displayRemoveMovieBanner();
        String movieName = view.getMovieNameChoice();
        Movie movie = dao.getMovie(movieName);
        if (movie != null) {
            dao.removeMovie(movieName);
            view.displayRemoveSuccessBanner();
        } else {
            System.out.println("Movie not found.");
        }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
    
    private void editMovie() throws LibraryDaoException {
        view.displayEditMovieBanner();
        String movieName = view.getMovieNameChoice();
        Movie movie = dao.getMovie(movieName);
        if (movie != null) {
            view.changeMovieInfo(movie);
            dao.addMovie(movie.getMovieName(), movie);
            view.displayEditSuccessBanner();
            dao.writeAuditEntry("Movie " + movieName + " Edited." + movie.toString()); 
        } else {
            System.out.println("Sorry, movie not found.");
        }
    }
}
