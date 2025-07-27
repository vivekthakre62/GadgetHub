
package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.util.DBUtil;
import in.gadgethub.util.IDUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vivek
 */
public class ProductDAOImpl implements ProductDao{

    @Override
    public String addProduct(ProductPojo product) {
       String status="Product registration failed!";
       if(product.getProdId()==null){
           product.setProdId(IDUtil.generateProdId());
       }
       PreparedStatement ps=null;
       Connection conn=DBUtil.provideConnection();
       try{
           ps=conn.prepareStatement("insert into product values(?,?,?,?,?,?,?,?)");
           ps.setString(1,product.getProdId());
                   ps.setString(2,product.getProdName());
           ps.setString(3,product.getProdType());
           ps.setString(4,product.getProdInfo());
           ps.setDouble(5,product.getProdPrice());
           ps.setInt(6,product.getProdQuantity());
           ps.setBlob(7,product.getImage());
           ps.setString(8,"Y");
           int count=ps.executeUpdate();
           if(count==1){
               status="product registered succesfully";
           }
       }catch(SQLException ex){
            System.out.println("Error in addProduct()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         return status;
    }

    @Override
    public String updateProduct(ProductPojo prevProduct, ProductPojo updateProduct) {
     String update="Update failed!";
     if(!prevProduct.getProdId().equals(updateProduct)){
         update="ID is not matched, Update failed!";
         return update;
       }
      PreparedStatement ps=null;
       Connection conn=DBUtil.provideConnection();
       ResultSet rs=null;
       try{
           ps=conn.prepareStatement("update product set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,image=? where pid=?"); 
                ps.setString(1, updateProduct.getProdName());              
               ps.setString(2, updateProduct.getProdType());
               ps.setString(3, updateProduct.getProdInfo());
               ps.setDouble(4, updateProduct.getProdPrice());
               ps.setInt(5, updateProduct.getProdQuantity());        
               ps.setBlob(6, updateProduct.getImage());
               ps.setString(7,updateProduct.getProdId());
               int count=ps.executeUpdate();
               if(count==1){
                  update="Update Succesful";
               }
           
     }
       catch(SQLException ex){
            System.out.println("Error in updateProduct()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         
         return update;
    }
    

    @Override
    public String updateProductPrice(String productId, double updatePrice) {
     String update="Update failed!";
      PreparedStatement ps=null;
       Connection conn=DBUtil.provideConnection();
       
       try{
           ps=conn.prepareStatement("update product set pprice=? where pid=?"); 
            
               ps.setDouble(1,updatePrice);
               ps.setString(2,productId);
               int count=ps.executeUpdate();
               if(count==1){
                  update="Update Succesful";
               }
           
     }
       catch(SQLException ex){
            System.out.println("Error in UpdateProductPrice()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         return update;
        
    }
    

    @Override
    public List<ProductPojo> getAllProducts() {
      List<ProductPojo>plist=new ArrayList<>();
      Connection conn=DBUtil.provideConnection();
      Statement st=null;
     ResultSet rs=null;
      try{
          st=conn.createStatement();
         rs=st.executeQuery("select * from product where available='Y'");
         while(rs.next()){
             ProductPojo product=new ProductPojo();
             product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                   product.setProdType(rs.getString("ptype"));
                      product.setProdInfo(rs.getString("pinfo"));
                         product.setProdPrice(rs.getDouble("pprice"));
                            product.setProdQuantity(rs.getInt("pquantity"));
                               product.setImage(rs.getBinaryStream("image"));
                               plist.add(product);
         }
      }
      catch(SQLException ex){
            System.out.println("Error in getAllProducts()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(st);
         DBUtil.closeResultSet(rs);
         return plist;
    }

    @Override
    public List<ProductPojo> getAllProductsByType(String typestString) {
        Connection conn=DBUtil.provideConnection();
        List<ProductPojo>plist=new ArrayList<>();
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
        ps=conn.prepareStatement("select * from product where lower(ptype) like ? and available='Y'");
        ps.setString(1,"%"+typestString.toLowerCase()+"%");
        rs=ps.executeQuery();
        while(rs.next()){
             ProductPojo product=new ProductPojo();
             product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                   product.setProdType(rs.getString("ptype"));
                      product.setProdInfo(rs.getString("pinfo"));
                         product.setProdPrice(rs.getDouble("pprice"));
                            product.setProdQuantity(rs.getInt("pquantity"));
                               product.setImage(rs.getBinaryStream("image"));
                               plist.add(product);
                               System.out.println("Everything is ok");
        }
        }catch(SQLException ex){
            System.out.println("Error in getAllProducts()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeResultSet(rs);
         DBUtil.closeStatement(ps);
         return plist;
    }
    public static void main(String[]args){
        ProductDAOImpl pd=new ProductDAOImpl();
//        pd.getAllProductsByType("VIVO");
        pd.getAllProductsByType("mobile");
    }

    @Override
    public List<ProductPojo> searchAllProducts(String search) {
         Connection conn=DBUtil.provideConnection();
        List<ProductPojo>plist=new ArrayList<>();
//        search=search.toUpperCase();
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
        ps=conn.prepareStatement("select * from product where lower(ptype) like ? or lower(pname) like ? or lower(pid) like ? and available='Y'");
//   ps=conn.prepareStatement("select * from product where like ? and available='A'");
//        ps.setString(1,"%"+search.toLowerCase()+"%");
        ps.setString(1,"%"+search.toLowerCase()+"%");  
        ps.setString(2,"%"+search.toLowerCase()+"%");
        ps.setString(3,"%"+search.toLowerCase()+"%");
        rs=ps.executeQuery();
        while(rs.next()){
             ProductPojo product=new ProductPojo();
             product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                   product.setProdType(rs.getString("ptype"));
                      product.setProdInfo(rs.getString("pinfo"));
                         product.setProdPrice(rs.getDouble("pprice"));
                            product.setProdQuantity(rs.getInt("pquantity"));
                               product.setImage(rs.getBinaryStream("image"));
                               plist.add(product);
        }
        }catch(SQLException ex){
            System.out.println("Error in getAllProducts()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeResultSet(rs);
         DBUtil.closeStatement(ps);
         return plist;
    }

    @Override
    public ProductPojo getProductDetails(String prodid) {
     Connection conn=DBUtil.provideConnection();
     ProductPojo product=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
        ps=conn.prepareStatement("select * from product where pid=?");
        ps.setString(1,prodid);  
         rs=ps.executeQuery();
        if(rs.next()){
          product=new ProductPojo();
          product.setProdId(rs.getString("pid"));
          product.setProdName(rs.getString("pname"));
          product.setProdType(rs.getString("ptype"));
          product.setProdInfo(rs.getString("pinfo"));
          product.setProdPrice(rs.getDouble("pprice"));
          product.setProdQuantity(rs.getInt("pquantity"));
          product.setImage(rs.getBinaryStream("image"));
        }
        }catch(SQLException ex){
            System.out.println("Error in getProductDetails()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeResultSet(rs);
         DBUtil.closeStatement(ps);
         return product;
    }

    @Override
    public int getProductQuantity(String prodid) {
        int quantity=0;
      PreparedStatement ps=null;
      ResultSet rs=null;
       Connection conn=DBUtil.provideConnection();
       
       try{
           ps=conn.prepareStatement("select pquantity from product where pid=?"); 
         
            ps.setString(1,prodid);
            rs=ps.executeQuery();
            if(rs.next()){
            quantity=rs.getInt(1);
            }
          
     }
       catch(SQLException ex){
            System.out.println("Error in getProductQuantity()"+ex);
            ex.printStackTrace();
        }
       DBUtil.closeResultSet(rs);
         DBUtil.closeStatement(ps);
         return quantity;
        
    }

    @Override
    public String updateProductWithoutImage(String prevProductid, ProductPojo updateProduct) {
        String update="Update failed!";
        int prevQunatity=0;
     if(!prevProductid.equals(updateProduct.getProdId())){
         update="ID is not matched, Update failed!";
         return update;
       }
      PreparedStatement ps=null;
       Connection conn=DBUtil.provideConnection();

       try{
           ps=conn.prepareStatement("update product set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=? where pid=?"); 
             prevQunatity=getProductQuantity(prevProductid);
                ps.setString(1, updateProduct.getProdName());              
               ps.setString(2, updateProduct.getProdType());
               ps.setString(3, updateProduct.getProdInfo());
               ps.setDouble(4, updateProduct.getProdPrice());
               ps.setInt(5, updateProduct.getProdQuantity());        
               ps.setString(6,updateProduct.getProdId());
               int count=ps.executeUpdate();
               if(count==1 && prevQunatity<updateProduct.getProdQuantity()){
                  update="Update Succesful and Mail send Succesfully";
                  //mail code
               }else if(count==1){
               update="update Succesfully";
               }
           
     }
       catch(SQLException ex){
            System.out.println("Error in updateProductWithoutImage()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         return update;
    }

    @Override
    public double getProductPrice(String Prodid) {
        double price=0.0;
      PreparedStatement ps=null;
      ResultSet rs=null;
       Connection conn=DBUtil.provideConnection();
       try{
           ps=conn.prepareStatement("select pprice from product where pid=?");
            ps.setString(1,Prodid);
            rs=ps.executeQuery();
            if(rs.next()){
            price=rs.getInt(1);
            }
          
     }
       catch(SQLException ex){
            System.out.println("Error in getProductPrice()"+ex);
            ex.printStackTrace();
        }
       DBUtil.closeResultSet(rs);
         DBUtil.closeStatement(ps);
         return price;
    }

    @Override
    public boolean sellNProduct(String prodid, int n) {
        boolean res=false;
        PreparedStatement ps=null;
        Connection conn=DBUtil.provideConnection();
 try{
     ps=conn.prepareStatement("update pquantity=(pquantity-?) where pid=?");
     ps.setInt(1, n);
     ps.setString(2, prodid);
    int count=ps.executeUpdate();
    if(count==1){
        res=true;
    }
    
 } catch(SQLException ex){
            System.out.println("Error in sellNProduct()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         return res;
      }

    @Override
    public List<String> getAllProductType() {
        List<String>plist=new ArrayList<>();
      Connection conn=DBUtil.provideConnection();
      Statement st=null;
     ResultSet rs=null;
      try{
          st=conn.createStatement();
         rs=st.executeQuery("select distinct ptype from product");
         while(rs.next()){

                              plist.add(rs.getString(1));
         }
      }
      catch(SQLException ex){
            System.out.println("Error in getAllProductType()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(st);
         DBUtil.closeResultSet(rs);
         return plist;
    }

    @Override
    public byte[] getImage(String prodid) {
     byte[]arr=null;
     Connection conn=DBUtil.provideConnection();
     PreparedStatement ps=null;
     ResultSet rs=null;
     try{
         ps=conn.prepareStatement("select image from product where pid=?");
         ps.setString(1, prodid);
         rs=ps.executeQuery();
         if(rs.next()){
             arr=rs.getBytes(1);
         }
    
     }
     catch(SQLException ex){
            System.out.println("Error in getimage()"+ex);
            ex.printStackTrace();
        }
     DBUtil.closeStatement(ps);
         DBUtil.closeResultSet(rs);
         return arr;
             }

    @Override
    public String removeProduct(String prodid) {
      String status="Product Not Removed";
      Connection conn=DBUtil.provideConnection();
      PreparedStatement ps1=null;
      PreparedStatement ps2=null;
      ResultSet rs=null;
      try{
          ps1=conn.prepareStatement("update product set available='N' where pid=? and available='Y'");
          ps1.setString(1, prodid);
          int k=ps1.executeUpdate();
          if(k>0){
              status="Product Removed Succesfully!";
              ps2=conn.prepareStatement("Delete from usercart where pid=?");
              ps2.setString(1, prodid);
              k=ps2.executeUpdate();
          }
          
      }
       catch(SQLException ex){
            System.out.println("Error in removeProduct()"+ex);
            ex.printStackTrace();
        }
     DBUtil.closeStatement(ps1);   
     DBUtil.closeStatement(ps2);

         DBUtil.closeResultSet(rs);
      return status;
    }
    
}
