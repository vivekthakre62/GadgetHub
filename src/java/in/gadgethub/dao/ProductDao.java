/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.ProductPojo;
import java.util.List;

/**
 *
 * @author Vivek
 */
public interface ProductDao {
   public String addProduct(ProductPojo product);
   public String updateProduct(ProductPojo prevProduct,ProductPojo updateProduct);
   public String updateProductPrice(String productId,double updatePrice);
   public List<ProductPojo>getAllProducts();
   public List<ProductPojo>getAllProductsByType(String type);
   public List<ProductPojo>searchAllProducts(String search);
   public ProductPojo getProductDetails(String prodid);
   public int getProductQuantity(String prodid);
   public String updateProductWithoutImage(String prevProductid,ProductPojo updateProduct);
   public double getProductPrice(String Prodid);
   public boolean sellNProduct(String prodid,int n);
   public List<String>getAllProductType();
   public byte[]getImage(String prodid);
   public String removeProduct(String prodid);
}
