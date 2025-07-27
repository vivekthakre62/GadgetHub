/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.UserDAOImpl;
import in.gadgethub.pojo.UserPojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     String userName=request.getParameter("userName");
     String password=request.getParameter("password");
     String usertype=request.getParameter("usertype");
     String status="Login denied! Invalid userid or password";
     if(usertype.equalsIgnoreCase("admin")){
         if(userName.equals("admin@gmail.com")&&password.equals("admin")){
             RequestDispatcher rd=request.getRequestDispatcher("./AdminViewServlet");
             HttpSession session =request.getSession();
             session.setAttribute("userName",userName);
             session.setAttribute("password",password);
             session.setAttribute("usertype", usertype);
             rd.forward(request, response);
         }else{
                          RequestDispatcher rd=request.getRequestDispatcher("login.jsp?message="+status);
                          rd.include(request, response);
         }
    }else if(usertype.equalsIgnoreCase("customer")){
        UserDAOImpl userDao=new UserDAOImpl();
        status=userDao.isValidCredentials(userName, password);
        if(status.equalsIgnoreCase("Login succesful!")){
            UserPojo user=userDao.getUserDetails(userName);
            HttpSession session=request.getSession();
            session.setAttribute("userdata", user);
            session.setAttribute("userName",userName);
            session.setAttribute("password",password);
            session.setAttribute("usertype", usertype);
             RequestDispatcher rd=request.getRequestDispatcher("./UserHomeServlet");
             rd.forward(request, response);
    }else{
                          RequestDispatcher rd=request.getRequestDispatcher("login.jsp?message="+status);
                          rd.include(request, response);
         }
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
