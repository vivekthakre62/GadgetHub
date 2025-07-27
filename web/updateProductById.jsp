<%-- 
    Document   : updateProductById
    Created on : 7 Jan, 2025, 12:25:01 AM
    Author     : Vivek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html> 
<head> 
<title>Update Product</title> 
<meta charset="utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"> 
 
<link rel="stylesheet" href="mycss.css"> 
</head> 
<body style="background-color: #E6F9E6;"> 
 <% 
    String userName=(String)session.getAttribute("userName");      
       String password=(String)session.getAttribute("password");
       String usertype=(String)session.getAttribute("usertype");
       String search=request.getParameter("search");
        String type=request.getParameter("type");
       if(usertype==null || !usertype.equalsIgnoreCase("admin")){
           response.sendRedirect("login.jsp?message= Access Denied! Please Login as admin");
       }else if(userName==null||password==null){
         response.sendRedirect("login.jsp?message=Session Expired! Please Login again");
       }
 %> 
 
 <jsp:include page="header.jsp" /> 
 
 <% 
 String message=(String)request.getAttribute("message");   
 %> 
 <div class="container"> 
  <div class="row justify-content-center"> 
   <form action="./UpdateProductByIdServlet" method="post" 
    class="col-md-4 col-md-offset-4 myform"> 
    <div class="text-center"> 
     <h3 class="text-primary">Product Update Form</h3> 
     <% 
 if(message!=null){
     %> 
     <p class="text-danger"> 
      <%=message%> 
     </p> 
     <% 
  }
     %> 
    </div> 
    <div></div> 
    <div class="row mt-3"> 
     <div class="col-md-12 form-group"> 
      <label for="last_name">Product Id</label><input type="text" 
       placeholder="Enter Product Id" name="prodid" class="form-control" 
       id="last_name" required> 
     </div> 
    </div> 
    <div class="row mt-3 mb-3"> 
     <div class="col-md-6 text-center" style="margin-bottom: 2px;"> 
      <a href="./AdminViewServlet" class="btn btn-info">Cancel</a> 
     </div> 
     <div class="col-md-6 text-center"> 
      <button type="submit" class="btn btn-danger">Update 
       Product</button> 
     </div> 
    </div> 
   </form> 
  </div> 
 </div> 
 
 <%@ include file="footer.jsp"%> 
</body> 
</html>
