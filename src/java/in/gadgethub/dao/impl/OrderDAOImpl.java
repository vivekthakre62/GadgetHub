/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.OrderDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import in.gadgethub.util.DBUtil;
import in.gadgethub.util.IDUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vivek
 */
public class OrderDAOImpl implements OrderDao{

    @Override
    public boolean addOrder(OrderPojo order) {
        boolean status=false;
         PreparedStatement ps=null;
       Connection conn=DBUtil.provideConnection();
       try{
           ps=conn.prepareStatement("insert into orders values(?,?,?,?,?)");
           ps.setString(1,order.getOrderId());
           ps.setString(2,order.getProdId());          
           ps.setInt(3,order.getQuantity());
           ps.setDouble(4,order.getAmount());
           ps.setInt(5,0);
           int count=ps.executeUpdate();
           if(count>0){
               status=true;
           }
       }catch(SQLException ex){
            System.out.println("Error in addOrder()"+ex);
            ex.printStackTrace();
        }
       
         DBUtil.closeStatement(ps);
         return status;
        
    }
        
    

    @Override
    public boolean addTransaction(TransactionPojo transaction) {
             boolean status=false;
         PreparedStatement ps=null;
       Connection conn=DBUtil.provideConnection();
       try{
           ps=conn.prepareStatement("insert into transactions values(?,?,?,?)");
           ps.setString(1,transaction.getTransactionId());
                   ps.setString(2,transaction.getUserEmail());
                   java.util.Date d1=transaction.getTransDate();
                   java.sql.Date d2=new java.sql.Date(d1.getTime());
                   ps.setDate(3,d2);
                   ps.setDouble(4,transaction.getAmount());
//           ps.setInt(5,0);
                   int count=ps.executeUpdate();
           if(count>0){
               status=true;
           }
       }catch(SQLException ex){
            System.out.println("Error in addTransaction()"+ex);
            ex.printStackTrace();
        }
       
         DBUtil.closeStatement(ps);
         return status;
    }

    @Override
    public List<OrderPojo> getAllOrders() {
     List<OrderPojo> orderList = new ArrayList<>();
     Statement st=null;
       Connection conn=DBUtil.provideConnection();
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("select * from orders");
            while (rs.next()){
                OrderPojo order=new OrderPojo();
                order.setOrderId(rs.getString("orderid"));
                order.setProdId(rs.getString("prodid"));
                order.setQuantity(rs.getInt("quantity"));
                order.setShipped(rs.getInt("shipped"));
                order.setAmount(rs.getDouble("amount"));
                orderList.add(order);
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllOrder()"+ex);
            ex.printStackTrace();
        }
       
         DBUtil.closeStatement(st);
         return orderList;
    }

    @Override
    public String shipNow(String orderId, String prodId) {
      String status="Failed";
      Connection conn=DBUtil.provideConnection();
      PreparedStatement ps=null;
      try{
          ps=conn.prepareStatement("update orders set shipped==1 where orderid=? and prodid=?");
          ps.setString(1, orderId);
          ps.setString(2, prodId);
          int x=ps.executeUpdate();
          if(x>0){
              status="Updated";
          }
          
      }catch(SQLException ex){
            System.out.println("Error in getAllOrder()"+ex);
            ex.printStackTrace();
        }
       
         DBUtil.closeStatement(ps);
         return status;
    }
     public String paymentSuccess(String username, double paidAmount) {
      String status="Orderss Placement Failed!";
        CartDAOImpl cartDao=new CartDAOImpl();
        List <CartPojo>cartList=cartDao.getAllCartItems(username);
        if(cartList.isEmpty()){
            status="ordder Placed failed!";
        }
        String transactionId=IDUtil.generateTransId();
        TransactionPojo trPojo=new TransactionPojo();
        trPojo.setTransactionId(transactionId);
        trPojo.setUserEmail(username);        
        trPojo.setAmount(paidAmount);
        trPojo.setTransDate(new java.util.Date());
        boolean result=addTransaction(trPojo);
         if(result==false){
            status="order Placed Failed!";
        }
        boolean ordered=true;
        ProductDAOImpl productDAO=new ProductDAOImpl();
        for(CartPojo cartPojo:cartList){
            double amount=productDAO.getProductPrice(cartPojo.getProdId())*cartPojo.getQuantity();
            OrderPojo order=new OrderPojo();
            order.setOrderId(transactionId);
            order.setProdId(cartPojo.getProdId());
            order.setQuantity(cartPojo.getQuantity());
            order.setAmount(amount);
            order.setShipped(0);
            ordered=addOrder(order);
              
            if(!ordered){
                break;
            }
          
            ordered=cartDao.removeAProduct(cartPojo.getUseremail(),cartPojo.getProdId());
            if(!ordered){
                break;
            }
            ordered=productDAO.sellNProduct(cartPojo.getProdId(), cartPojo.getQuantity());
            if(!ordered){
                break;
            }
            
        }
        if(ordered){
            status="Order Placed Succesfully!";
          
        }else{
            System.out.println("Transaction failed:"+transactionId);
                    
        }
        return status;
    }
     public static void main(String[] args) {
         System.out.println("zhiii");
        ProductDAOImpl pd=new ProductDAOImpl();
//        double price=pd.getProductPrice("P202501084226");
//         System.out.println(price);
         System.out.println(pd.getProductQuantity("P202501084226"));
    }
      public List<OrderDetailsPojo> getAllOrderDetails(String userEmailId) {
      List<OrderDetailsPojo>orderList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("Select p.pid as prodid,o.orderid as orderid,o.shipped as shipped,p.image as image,p.pname as pname,o.quantity as qty,o.amount as amount,t.transtime as time FROM orders o,product p,transactions t where o.orderid=t.transid and o.prodid=p.pid and t.useremail=? ");
            ps.setString(1,userEmailId);
            rs=ps.executeQuery();
            while(rs.next()){
                OrderDetailsPojo orderDetails=new OrderDetailsPojo();
                orderDetails.setOrderId(rs.getString("orderid"));
                orderDetails.setProdImage(rs.getAsciiStream("image"));
                orderDetails.setProdId(rs.getString("prodid"));
                orderDetails.setProdName(rs.getString("pname"));
                orderDetails.setQuantity(rs.getInt("qty"));
                orderDetails.setAmount(rs.getDouble("amount"));
                orderDetails.setTime(rs.getTimestamp("time"));
                orderDetails.setShipped(rs.getInt("shipped"));
                orderList.add(orderDetails);
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllOrderDetails"+ex);
            ex.printStackTrace();
        } 
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return orderList;
    }

    @Override
    public int getSoldQuantity(String prodId) {
         int count=0;
      Connection conn=DBUtil.provideConnection();
      PreparedStatement ps=null;
      ResultSet rs=null;
      try{
          ps=conn.prepareStatement("select sum(quantity) as quant from orders where prodid=?");
          ps.setString(1, prodId);
          rs=ps.executeQuery();
if(rs.next()){
    count=rs.getInt(1);
}
      }catch(SQLException ex){
            System.out.println("Error in getAllOrder()"+ex);
            ex.printStackTrace();
        }
       
         DBUtil.closeStatement(ps);
         DBUtil.closeResultSet(rs);
         return count;
    }
     
         
    
}

