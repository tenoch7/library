/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.library.advice;

import library.dao.LibraryDao;
import library.dao.LibraryDaoException;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author juanm
 */
public class LoggingAdvice {
    
    LibraryDao dao;

    public LoggingAdvice(LibraryDao dao) {
        this.dao = dao;
    }
    
    public void createAuditEntry(JoinPoint jp) {
        Object[] args = jp.getArgs();
        String auditEntry = jp.getSignature().getName() + ": ";
        for (Object currentArg : args) {
            auditEntry += currentArg;
        }
        try {
            dao.writeAuditEntry(auditEntry);
        } catch (LibraryDaoException e) {
            System.err.println(
               "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }
}
