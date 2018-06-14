/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
//        writeAuditEntry("Movie " + movie.getMovieName() + " CREATED.");
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
//        writeAuditEntry("Movie " + movieName + " REMOVED.");
        return removedMovie;
    }

    private void loadLibrary() throws LibraryDaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new LibraryDaoException(
                    "-_- Could not load library data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentTokens holds each of the parts of the currentLine after it has
        // been split on our DELIMITER
        // NOTE FOR APPRENTICES: In our case we use :: as our delimiter.  If
        // currentLine looks like this:
        // 1234::Joe::Smith::Java-September2013
        // then currentTokens will be a string array that looks like this:
        //
        // ___________________________________
        // |    |   |     |                  |
        // |1234|Joe|Smith|Java-September2013|
        // |    |   |     |                  |
        // -----------------------------------
        //  [0]  [1]  [2]         [3]
        String[] currentTokens;
        // Go through LIBRARY_FILE line by line, decoding each line into a 
        // Movie object.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // break up the line into tokens
            currentTokens = currentLine.split(DELIMITER);
            // Create a new Movie object and put it into the map of movies
            // NOTE FOR APPRENTICES: We are going to use the movie id
            // which is currentTokens[0] as the map key for our movie object.
            // We also have to pass the movie id into the Movie constructor
            Movie currentMovie = new Movie(currentTokens[0]);
            // Set the remaining vlaues on currentMovie manually
            currentMovie.setReleaseDate(LocalDate.parse(currentTokens[1], DateTimeFormatter.ISO_DATE));
            currentMovie.setmPAARating(currentTokens[2]);
            currentMovie.setDirectorName(currentTokens[3]);
            currentMovie.setStudio(currentTokens[4]);
            currentMovie.setUserRating(currentTokens[5]);

            // Put currentMovie into the map using movieID as the key
            movies.put(currentMovie.getMovieName(), currentMovie);
        }
        // close scanner
        scanner.close();
    }

    /**
     * Writes all movies in the roster out to a LIBRARY_FILE. See loadLibrary
     * for file format.
     *
     * @throws LibraryDaoException if an error occurs writing to the file
     */
    private void writeLibrary() throws LibraryDaoException {
        // NOTE FOR APPRENTICES: We are not handling the IOException - but
        // we are translating it to an application specific exception and 
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us.  It is the responsibility of the calling code to 
        // handle any errors that occur.
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new LibraryDaoException(
                    "Could not save movie data.", e);
        }

        // Write out the Movie objects to the roster file.
        // NOTE TO THE APPRENTICES: We could just grab the movie map,
        // get the Collection of Movies and iterate over them but we've
        // already created a method that gets a List of Movies so
        // we'll reuse it.
        List<Movie> movieList = this.getAllMovies();
        for (Movie currentMovie : movieList) {
            // write the Movie object to the file
            out.println(currentMovie.getMovieName() + DELIMITER
                    + currentMovie.getReleaseDate() + DELIMITER
                    + currentMovie.getMPAARating() + DELIMITER
                    + currentMovie.getDirectorName() + DELIMITER
                    + currentMovie.getStudio() + DELIMITER
                    + currentMovie.getUserRating() );
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
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