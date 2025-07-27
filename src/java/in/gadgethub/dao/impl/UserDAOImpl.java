/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.UserPojo;
import in.gadgethub.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Vivek
 */
public class UserDAOImpl implements UserDao{
    public boolean isRegistered(String emailId){
      PreparedStatement ps=null;
      ResultSet rs=null;
      Connection conn=DBUtil.provideConnection();
      boolean flag=false;
      try{
          ps=conn.prepareStatement("select 1 from users where useremail=?");
          ps.setString(1,emailId);
          rs=ps.executeQuery();
          if(rs.next()){
             flag=true;
          }
          
      }catch(SQLException se){
         System.out.println("Error in isRegistered()"+se);
         se.printStackTrace();
      }
      DBUtil.closeResultSet(rs);
      DBUtil.closeStatement(ps);
      return flag;
    }

    @Override
    public String registerUser(UserPojo user) {
       String status="Register failed!";
        boolean isRegistered=isRegistered(user.getUseremail());
       if(isRegistered){
           status="user is already registered! please try again!";
           return status;
       }
               PreparedStatement ps=null;
               Connection conn=DBUtil.provideConnection();
               try{
      ps=conn.prepareStatement("insert into users values(?,?,?,?,?,?)");
      ps.setString(0,user.getUseremail());
            ps.setString(1,user.getUsername());
      ps.setString(2,user.getMobile());
      ps.setString(4,user.getAddress());
            ps.setInt(3,user.getPincode());
            ps.setString(5,user.getPassword());
            int count=ps.executeUpdate();
            if(count==1){
                status="Registered Succesful!";
                //code to send email
            }
               }
               catch(SQLException ex){
                   System.out.println("Error in registerUser()"+ex);
                   ex.printStackTrace();
               }
               DBUtil.closeStatement(ps);
               return status;
       }
    

    @Override
    public String isValidCredentials(String emailId, String password) {
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        String status="Login Denied!. Invalid password and username";
        try{
            ps=conn.prepareStatement("select 1 from users where useremail=? and password=?");
            ps.setString(1 , emailId);
            ps.setString(2 , password);
            rs=ps.executeQuery();
            if(rs.next()){
                status="Login succesful!";
            }
    }
        catch(SQLException ex){
            System.out.println("Error in isvalidateCredentials()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         DBUtil.closeResultSet(rs);
               return status;
    }

    @Override
    public UserPojo getUserDetails(String emailId) {
       Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       ResultSet rs=null;
       UserPojo user=null;
       try{
           ps=conn.prepareStatement("select * from users where useremail=?");
           ps.setString(1, emailId);
           rs=ps.executeQuery();
           if(rs.next()){
          user=new UserPojo();
          user.setUseremail(rs.getString("useremail"));      
          user.setUsername(rs.getString("username"));
          user.setMobile(rs.getString("mobile"));
          user.setAddress(rs.getString("address"));
          user.setPincode(rs.getInt("pincode"));
          user.setPassword(rs.getString("password"));

           }
           
       }
        catch(SQLException ex){
            System.out.println("Error in getUserDetails()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         DBUtil.closeResultSet(rs);
         return user;
    }

    @Override
    public String getUserFirstName(String emailId) {
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        String name=null;
        try{
            ps=conn.prepareStatement("select username from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                name=rs.getString(1);
                String[]firstname=name.split(" ");
                name=firstname[0];
            }
        }catch(SQLException ex){
            System.out.println("Error in getUserDetails()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         DBUtil.closeResultSet(rs);
         return name;
    }

    @Override
    public String getUserAddr(String emailId) {
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        String address=null;
        try{
            ps=conn.prepareStatement("select address from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                address=rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in getUserDetails()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         DBUtil.closeResultSet(rs);
         return address;
    }

    @Override
    public List<String> getAllUserId() {
         Connection conn=DBUtil.provideConnection();
         List<String>users=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select useremail from users");
            rs=ps.executeQuery();
            while(rs.next()){
                String user=rs.getString(1);
                users.add(user);
            }
        }catch(SQLException ex){
            System.out.println("Error in getUserDetails()"+ex);
            ex.printStackTrace();
        }
         DBUtil.closeStatement(ps);
         DBUtil.closeResultSet(rs);
         return users;
    }
}
