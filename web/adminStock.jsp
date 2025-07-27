<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ page import="in.gadgethub.dao.impl.*,in.gadgethub.dao.*,in.gadgethub.pojo.*,javax.servlet.ServletOutputStream,java.io.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Product Stocks</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<link rel="stylesheet" href="mycss.css">
</head>
<body style="background-color: #E6F9E6;">

 <jsp:include page="header.jsp" />

 <div class="text-center text-primary h3 m-3">Stock Products</div>
 <div class="container-fluid">
  <div class="table-responsive ">
   <table class="table table-hover table-sm">
    <thead>
     <tr>
      <th>Image</th>
      <th>ProductId</th>
      <th>Name</th>
      <th>Type</th>
      <th>Price</th>
      <th>Sold Qty</th>
      <th>Stock Qty</th>
      <th colspan="2" class="text-center">Actions</th>
     </tr>
    </thead>
    <tbody >



     <%     
         List<ProductPojo>products=(List<ProductPojo>)request.getAttribute("products");
         for(ProductPojo product:products){
     %>

     <tr>
      <td><img src="./ShowImageServlet?pid=<%=product.getProdId() %>"
       style="width: 50px; height: 50px;"></td>
      <td><a
      href="./UpdateProductByIdServlet?prodid=<%=product.getProdId()%>"><%=product.getProdId() %></a></td>
      <%
      String prodName=product.getProdName();
      prodName=prodName.substring(0,Math.min(prodName.length(), 25));
      %>
      <td><%=prodName %></td>
      <td><%=product.getProdType().toUpperCase() %></td>
      <td><%=product.getProdPrice() %></td>
      <td><%=new OrderDAOImpl().getSoldQuantity(product.getProdId()) %></td>
      <td><%=product.getProdQuantity() %></td>
      <td>
       <form method="post">
        <button type="submit"
         formaction="./UpdateProductByIdServlet?prodid=<%=product.getProdId() %>"
         class="btn btn-primary">Update</button>
       </form>
      </td>
      <td>
       <form method="post">
        <button type="submit"
         formaction="./RemoveProductServlet?prodid=<%=product.getProdId() %>"
         class="btn btn-danger">Remove</button>
       </form>
      </td>

     </tr>

     <%
     }
     %>
     <%
     if(products.isEmpty()) {
     %>
     <tr style="background-color: grey; color: white;">
      <td colspan="7" style="text-align: center;">No Items
       Available</td>

     </tr>
     <%
     }
     %>
    </tbody>
   </table>
  </div>
 </div>

 <%@ include file="footer.jsp"%>
</body>
</html>