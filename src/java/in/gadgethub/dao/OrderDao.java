/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import java.util.List;

/**
 *
 * @author Vivek
 */
public interface OrderDao {
    boolean addOrder(OrderPojo order);
    boolean addTransaction(TransactionPojo transaction);
    List<OrderPojo> getAllOrders();
    String shipNow(String orderId,String prodId);  
    String paymentSuccess(String username, double paidAmount);
    List<OrderDetailsPojo> getAllOrderDetails(String userEmailId) ;
    int getSoldQuantity(String prodId);
}
