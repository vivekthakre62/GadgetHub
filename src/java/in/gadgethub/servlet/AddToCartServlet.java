/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.CartDAOImpl;
import in.gadgethub.dao.impl.DemandDAOImpl;
import in.gadgethub.dao.impl.ProductDAOImpl;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AddToCartServlet extends HttpServlet {

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
        PrintWriter pw = response.getWriter();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String password = (String) session.getAttribute("password");
        String userType = (String) session.getAttribute("usertype");
        if (userType == null || password == null || userName == null || !userType.equalsIgnoreCase("customer")) {
            response.sendRedirect("login.jsp?message=Access denied ! Please login first");
            return;
        }
        CartDAOImpl cartDao = new CartDAOImpl();
//        Map<String, Integer> map = new HashMap<>();
//        for (ProductPojo product : products) {
//            int qty = cartDao.getCartItemCount(userName, product.getProdId());
//            map.put(product.getProdId(), qty);
//        }
        String userId = userName;
        String prodId = request.getParameter("pid");
        int pQty = Integer.parseInt(request.getParameter("pqty"));
        String action = request.getParameter("action");

        ProductDAOImpl productDao = new ProductDAOImpl();
        ProductPojo productPojo = productDao.getProductDetails(prodId);
        int avQty = productDao.getProductQuantity(prodId);
        int cartQty = cartDao.getCartItemCount(userId, prodId);
        pQty += cartQty;
        if (action != null) {
            cartDao.updateProductToCart(new CartPojo(userId, prodId, pQty));
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailServlet");
            rd.forward(request, response);
        } else if (pQty == cartQty) {
            String status = cartDao.removeProductFromCart(userId, prodId);
            List<ProductPojo> products = productDao.getAllProducts();
            Map<String, Integer> map = new HashMap<>();
            for (ProductPojo product : products) {
                int qty = cartDao.getCartItemCount(userName, product.getProdId());
                map.put(product.getProdId(), qty);
            }
            request.setAttribute("products", products);
            request.setAttribute("map", map);
            RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");
            request.setAttribute("message", status);
            rd.forward(request, response);

        } else if (avQty < pQty) {
            String status = null;
            if (avQty == 0) {
                status = "Product out of stock!";
            } else {
                cartDao.updateProductToCart(new CartPojo(userId, prodId, avQty));
                status = "Only " + avQty + " no of " + productPojo.getProdName() + " are available, so we are adding only " + avQty + " products in your cart";
            }
            DemandDAOImpl demand = new DemandDAOImpl();
            DemandPojo demandPojo = new DemandPojo(userId, prodId, pQty - avQty);
            boolean flag = demand.addProduct(demandPojo);
            if (flag) {
                status += "<br/>Later we will mail you when " + productPojo.getProdName() + " will be available";
            }
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailServlet");
         
            request.setAttribute("message", status);
            rd.forward(request, response);
        } else {
            String status = cartDao.updateProductToCart(new CartPojo(userId, prodId, pQty));
            List<ProductPojo> products = productDao.getAllProducts();
            Map<String, Integer> map = new HashMap<>();
            for (ProductPojo product : products) {
                int qty = cartDao.getCartItemCount(userName, product.getProdId());
                map.put(product.getProdId(), qty);
            }
            request.setAttribute("products", products);
            request.setAttribute("map", map);
            RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");
          
            request.setAttribute("message", status);
            rd.forward(request, response);
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
