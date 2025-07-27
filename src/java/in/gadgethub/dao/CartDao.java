/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.CartPojo;
import java.util.List;

/**
 *
 * @author Vivek
 */
public interface CartDao {
    public String addProductToCart(CartPojo cart);
    public String updateProductToCart(CartPojo cart);
    public List<CartPojo>getAllCartItems(String userId);
    public int getCartItemCount(String userId , String itemId);
    public String removeProductFromCart(String userId , String prodId);
    public Boolean removeAProduct(String userId , String prodId);
}
