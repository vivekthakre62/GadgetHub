
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.CartDAOImpl;
import in.gadgethub.dao.impl.ProductDAOImpl;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class UserHomeServlet extends HttpServlet {


    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =request.getSession();
       String userName=(String)session.getAttribute("userName");      
       String password=(String)session.getAttribute("password");
       String usertype=(String)session.getAttribute("usertype");
       String search=request.getParameter("search");
        String type=request.getParameter("type");
       if(usertype==null || !usertype.equalsIgnoreCase("customer")){
           response.sendRedirect("login.jsp?message= Access Denied! Please Login as customer");
       }else if(userName==null||password==null){
         response.sendRedirect("login.jsp?message=Session Expired! Please Login again");
       }
                  ProductDAOImpl productDao=new ProductDAOImpl();
                    String message="All Products";
                     List<ProductPojo> products=new ArrayList<>();
                       if(search!=null){
            products=productDao.searchAllProducts(search);
            message="Showing result for '"+search+"'";
        }
        else if(type!=null){
            products=productDao.searchAllProducts(type);
            message="Showing result for '"+type+"'";
        }
        else{
            products=productDao.getAllProducts();
        }
        if(products.isEmpty()){
              products=productDao.getAllProducts();
              message="No items found for "+(search!=null?search:type)+"'";
        }
        CartDAOImpl cartDao=new CartDAOImpl();
         Map<String, Integer> map = new HashMap<>();

        for (ProductPojo product : products) {
            int qty = cartDao.getCartItemCount(userName, product.getProdId());
            map.put(product.getProdId(), qty);

        }
        RequestDispatcher rd=request.getRequestDispatcher("userHome.jsp");
        request.setAttribute("userName", userName);
        request.setAttribute("message", message);
        request.setAttribute("products", products);
        request.setAttribute("map", map);
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
