/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.listener;

import in.gadgethub.util.DBUtil;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Vivek
 */
public class DBConnectionListener implements ServletContextListener {

 private static Connection conn;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      ServletContext ctxt=sce.getServletContext();
      String dburl=ctxt.getInitParameter("url");
       String username=ctxt.getInitParameter("username");
        String password=ctxt.getInitParameter("password");
       DBUtil.openConnection(dburl, username, password);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
           DBUtil.closeConnection();
    }

}
