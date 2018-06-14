/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.library;

import library.controller.LibraryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author juanm
 */
public class App {

    public static void main(String[] args) {
//        UserIO myIo = new UserIOConsoleImpl();
//        LibraryView myView = new LibraryView(myIo);
//        LibraryDao myDao = new LibraryDaoFileImpl();
//        LibraryController controller = new LibraryController(myDao, myView);
//        
//        controller.run();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        LibraryController controller = ctx.getBean("controller", LibraryController.class);
        controller.run();
    }
}
