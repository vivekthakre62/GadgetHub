/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.OrderDAOImpl;
import in.gadgethub.dao.impl.UserDAOImpl;
import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vivek
 */
public class UnshippedItemServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String password = (String) session.getAttribute("password");
        String userType = (String) session.getAttribute("usertype");
        if (userType == null || password == null || userName == null ) {
            response.sendRedirect("login.jsp?message=Access denied! Please login first");
            return;
        }
        else if(!userType.equalsIgnoreCase("admin")){
            response.sendRedirect("login.jsp?message=Access denied! Please login as admin");
            return;
        }
        List<OrderPojo>orders=new OrderDAOImpl().getAllOrders();
        request.setAttribute("orders", orders);
        Map<String,String>user_id=new HashMap<>();        
        Map<String,String>user_address=new HashMap<>();
        UserDAOImpl user=new UserDAOImpl();

        for(OrderPojo order:orders){
            String orderId=order.getOrderId();
            String productid=order.getProdId();
            user_id.put( productid,orderId);
            String useradd=user.getUserAddr(orderId);
            user_address.put(orderId, useradd);
            }
      request.setAttribute("user_id",user_id);      
      request.setAttribute("user_address",user_address);
               RequestDispatcher rd=request.getRequestDispatcher("unShippedItems.jsp");
rd.forward(request, response);
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
