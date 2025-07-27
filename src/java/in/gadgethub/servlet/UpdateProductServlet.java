/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.ProductDAOImpl;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UpdateProductServlet extends HttpServlet {

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
         HttpSession session =request.getSession();
       String userName=(String)session.getAttribute("userName");      
       String password=(String)session.getAttribute("password");
       String usertype=(String)session.getAttribute("usertype");
       if(usertype==null || !usertype.equalsIgnoreCase("admin")){
           response.sendRedirect("login.jsp?message= Access Denied! Please Login as admin");
       }else if(userName==null||password==null){
         response.sendRedirect("login.jsp?message=Session Expired! Please Login again");
       }
       String prodId=request.getParameter("pid");
        String prodName=request.getParameter("name");
        String prodType=request.getParameter("type");
         String prodInfo=request.getParameter("info"); 
         Double prodPrice=Double.parseDouble(request.getParameter("price"));
         Integer prodQuantity=Integer.parseInt(request.getParameter("quantity"));
         ProductPojo product=new ProductPojo();
         product.setProdId(prodId);
         product.setProdName(prodName);
         product.setProdType(prodType);
         product.setProdInfo(prodInfo);
         product.setProdPrice(prodPrice);
         product.setProdQuantity(prodQuantity);
         ProductDAOImpl productimp=new ProductDAOImpl();
         String status=productimp.updateProductWithoutImage(prodId, product);
         RequestDispatcher rd=request.getRequestDispatcher("updateProduct.jsp?message="+status);
          request.setAttribute("product", product);
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
