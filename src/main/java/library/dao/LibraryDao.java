package library.dao;

import Library.dto.Movie;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author juanm
 */
public interface LibraryDao {

	Movie addMovie(String movieName, Movie movie) throws LibraryDaoException;

	List<Movie> getAllMovies() throws LibraryDaoException;
	  
	Movie getMovie(String movieName) throws LibraryDaoException;
	    
	Movie removeMovie(String movieName) throws LibraryDaoException;
            
	public List<Movie> getMovieReleasedSince(LocalDate releasedSince);
            
	public void writeAuditEntry(String entry) throws LibraryDaoException;
            
}
