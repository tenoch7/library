package library.dao;

import Library.dto.Movie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author juanm
 */
public class LibraryDaoFileImpl implements LibraryDao {

    public static final String LIBRARY_FILE = "library.txt";
    public static final String DELIMITER = "::";
    private Map<String, Movie> movies = new HashMap<>();
//    public LibraryAuditDao auditDao;
//    public LibraryDao dao;

    public LibraryDaoFileImpl(/*LibraryAuditDao auditDao, LibraryDao dao*/) {
//        this.auditDao = auditDao;
//        this.dao = dao;
    }
    
    

    @Override
    public Movie addMovie(String movieName, Movie movie) throws LibraryDaoException {
        Movie newMovie = movies.put(movieName, movie);
        writeLibrary();
        return newMovie;
    }

    @Override
    public List<Movie> getAllMovies() throws LibraryDaoException {
        loadLibrary();
        return new ArrayList<Movie>(movies.values());
    }

    @Override
    public Movie getMovie(String movieName) throws LibraryDaoException {
        loadLibrary();
        return movies.get(movieName);
    }

    @Override
    public Movie removeMovie(String movieName) throws LibraryDaoException {
        Movie removedMovie = movies.remove(movieName);
        writeLibrary();
        return removedMovie;
    }

    private void loadLibrary() throws LibraryDaoException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new LibraryDaoException(
                    "-_- Could not load library data into memory.", e);
        }

        String currentLine;
        String[] currentTokens;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();   
            currentTokens = currentLine.split(DELIMITER);
            Movie currentMovie = new Movie(currentTokens[0]);
            currentMovie.setReleaseDate(LocalDate.parse(currentTokens[1], DateTimeFormatter.ISO_DATE));
            currentMovie.setmPAARating(currentTokens[2]);
            currentMovie.setDirectorName(currentTokens[3]);
            currentMovie.setStudio(currentTokens[4]);
            currentMovie.setUserRating(currentTokens[5]);

            
            movies.put(currentMovie.getMovieName(), currentMovie);
        }
        
        scanner.close();
    }

    /**
     * Writes all movies in the roster out to a LIBRARY_FILE. See loadLibrary
     * for file format.
     *
     * @throws LibraryDaoException if an error occurs writing to the file
     */
    private void writeLibrary() throws LibraryDaoException {
        
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new LibraryDaoException(
                    "Could not save movie data.", e);
        }

        
        List<Movie> movieList = this.getAllMovies();
        for (Movie currentMovie : movieList) {
        
            out.println(currentMovie.getMovieName() + DELIMITER
                    + currentMovie.getReleaseDate() + DELIMITER
                    + currentMovie.getMPAARating() + DELIMITER
                    + currentMovie.getDirectorName() + DELIMITER
                    + currentMovie.getStudio() + DELIMITER
                    + currentMovie.getUserRating() );   
            out.flush();
        }
        out.close();
    }

    @Override
    public List<Movie> getMovieReleasedSince(LocalDate releasedSince) {
        return movies.values()
                .stream()
                .filter(m -> m.getReleaseDate().isAfter(releasedSince))
                .collect(Collectors.toList());
                
    }
    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws LibraryDaoException {
        PrintWriter out;
       
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new LibraryDaoException("Could not persist audit information.", e);
        }
 
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }

}
