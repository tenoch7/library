/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

/**
 *
 * @author juanm
 */
public class LibraryDaoException extends Exception{

    public LibraryDaoException(String message) {
        super(message);
    }

    public LibraryDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
