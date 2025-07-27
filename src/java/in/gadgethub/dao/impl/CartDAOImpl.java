/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDao;
import in.gadgethub.dao.DemandDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vivek
 */
public class CartDAOImpl implements CartDao{

    @Override
    public String addProductToCart(CartPojo cart) {
        String status="Failed to add to cart!";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("select * from USERCART where prodid=? and useremail=?");
            ps1.setString(1,cart.getProdId());
            ps1.setString(2, cart.getUseremail());
            rs=ps1.executeQuery();
            if(rs.next()){
                ProductDAOImpl prod=new ProductDAOImpl();
               int stockQnty = prod.getProductQuantity(cart.getProdId());
                int newQty=cart.getQuantity()+rs.getInt("quantity");
                if(stockQnty<newQty){
                     cart.setQuantity(stockQnty);
                    updateProductToCart(cart);
                    status = "Only "+stockQnty+" no of item are available in our stock so we are adding "+stockQnty+" in your cart";
                    DemandPojo demandPojo = new DemandPojo();
                    demandPojo.setProdid(cart.getProdId());
                    demandPojo.setUseremail(cart.getUseremail());
                    demandPojo.setQuantity(newQty-stockQnty);
                    DemandDAOImpl demandDAO = new DemandDAOImpl();
                    boolean result = demandDAO.addProduct(demandPojo);
                    if(result==true){
                        status+="We will mail you when "+(newQty-stockQnty)+" no of items will be available";
                    }
                }
                else{
                    cart.setQuantity(newQty);
                    status = updateProductToCart(cart);
                }
            }
        }
        catch(SQLException ex){
            status="updation failed due to exception!";
            System.out.println("Error in cartDAOImpl()!");
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
        return status;
    }

    @Override
    public String updateProductToCart(CartPojo cart) {
         String status="Failed to add to cart!";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("select * from USERCART where prodid=? and useremail=?");
            ps1.setString(1,cart.getProdId());
            ps1.setString(2, cart.getUseremail());
            rs=ps1.executeQuery();
            if(rs.next()){
                        int quantity=cart.getQuantity();
            if(quantity>0){
                 ps2=conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                 ps2.setInt(1, cart.getQuantity());
                 ps2.setString(2, cart.getUseremail());
                ps2.setString(3, cart.getProdId());
                int ans=ps2.executeUpdate();
                if(ans>0){
                    status="product successfully updated in the cart";
                }
            }
            else if(quantity==0){
                ps2=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
                ps2.setString(1, cart.getUseremail());
                ps2.setString(2, cart.getProdId());
                int ans=ps2.executeUpdate();
                if(ans>0){
                    status="product successfully updated in the cart";
                }else{
                    status="Could'nt update the product!";
                }
            }
            }
            else{
                    ps2=conn.prepareStatement("insert into usercart values(?,?,?)");
                ps2.setString(1, cart.getUseremail());
                ps2.setString(2, cart.getProdId());
                ps2.setInt(3, cart.getQuantity());
                int ans=ps2.executeUpdate();
                if(ans>0){
                    status="product succesfully added into the cart";
                }
                else{
                   status="could'nt add the product";
                 }
            }
        }catch(SQLException ex){
            status="updation failed due to exception!";
            System.out.println("Error in cartDAOImpl()!");
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps2); 
        DBUtil.closeStatement(ps1);
        return status;
    }

    @Override
    public List<CartPojo> getAllCartItems(String userId){
        List<CartPojo> items= new ArrayList<>();
         Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        ResultSet rs=null;
        try{
                       ps1=conn.prepareStatement("select * from USERCART where useremail=?");
                         ps1.setString(1,userId);
                         rs = ps1.executeQuery();
                         while(rs.next()){
                             CartPojo cartPojo = new CartPojo();
                            cartPojo.setUseremail(rs.getString("useremail"));
                            cartPojo.setQuantity(rs.getInt("quantity"));
                            cartPojo.setProdId(rs.getString("prodId"));
                            items.add(cartPojo);
                         }
        }catch(SQLException ex){
          
            System.out.println("Error in getAllCartItems()! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1); 
        return items;
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        if(userId==null || itemId==null){
        return 0;   
       }
        int count=0;
          Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        ResultSet rs=null;
        try{
          ps1=conn.prepareStatement("select quantity from USERCART where useremail=? and prodid=?");
                         ps1.setString(1,userId);
                         ps1.setString(2, itemId);
                         rs=ps1.executeQuery();
                         if(rs.next()){
                            count=rs.getInt(1);
                         }
                         
        }catch(SQLException ex){
          
            System.out.println("Error in getAllCartItems()! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1); 
        return count;
        
    }

    @Override
    public String removeProductFromCart(String userId, String prodId) {
      String status="Failed to remove to cart!";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;        
        PreparedStatement ps2=null;
        ResultSet rs=null;
         try{
            ps1=conn.prepareStatement("select * from USERCART where prodid=? and useremail=?");
            ps1.setString(1,prodId);
            ps1.setString(2, userId);
            rs=ps1.executeQuery();
           if(rs.next()){
               int productQty = rs.getInt("quantity");
               productQty-=1;
               if(productQty>0){
                   ps2 = conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                   ps2.setInt(1,productQty);
                   ps2.setString(2, userId);
                   ps2.setString(3,prodId);
                   int n = ps2.executeUpdate();
                   if(n>0){
                       status="Product successfully removed from the cart!";
                   }
               }
                   else {
                   ps2 = conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
                   ps2.setString(1, userId);
                   ps2.setString(2,prodId);
                   int n = ps2.executeUpdate();
                   if(n>0){
                       status="Product successfully removed from the cart!";
               }
           }
         }
    }
        catch(SQLException ex){
            System.out.println("Error in getAllCartItems()! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1); 
        return status;
    }
    @Override
    public Boolean removeAProduct(String userId, String prodId) {
       Boolean status=false;
       Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       try{
           ps=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
           ps.setString(1, userId);
           ps.setString(2, prodId);
           int x=ps.executeUpdate();
           if(x>0){
               status=true;             
           }
       }  catch(SQLException ex){
            System.out.println("Error in getAllCartItems()! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps); 
        return status;
    }


}
