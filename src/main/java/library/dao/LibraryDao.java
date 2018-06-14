/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import Library.dto.Movie;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author juanm
 */
public interface LibraryDao {
    /**
	     * Adds the given Movie to the roster and associates it with the given 
	     * movie id. If there is already a movie associated with the given 
	     * movie id it will return that movie object, otherwise it will 
	     * return null.
	     * 
	     * @param movieName id with which movie is to be associated
	     * @param movie movie to be added to the roster
	     * @return the Movie object previously associated with the given  
	     * movie id if it exists, null otherwise
	     */
	    Movie addMovie(String movieName, Movie movie)
             throws LibraryDaoException;
	    
	    /**
	     * Returns a String array containing the movie ids of all 
	     * movies in the roster.
	     * 
	     * @return String array containing the ids of all the movies 
	     * in the roster
	     */
	    List<Movie> getAllMovies()
             throws LibraryDaoException;
	    
	    /**
	     * Returns the movie object associated with the given movie id.
	     * Returns null if no such movie exists
	     * 
	     * @param movieName ID of the movie to retrieve
	     * @return the Movie object associated with the given movie id,  
	     * null if no such movie exists
	     */
	    Movie getMovie(String movieName)
             throws LibraryDaoException;
	    
	    /**
	     * Removes from the roster the movie associated with the given id. 
	     * Returns the movie object that is being removed or null if 
	     * there is no movie associated with the given id
	     * 
	     * @param movieName id of movie to be removed
	     * @return Movie object that was removed or null if no movie 
	     * was associated with the given movie id
	     */
	    Movie removeMovie(String movieName)
             throws LibraryDaoException;
            
            public List<Movie> getMovieReleasedSince(LocalDate releasedSince);
            
            public void writeAuditEntry(String entry) throws LibraryDaoException;
            
}
