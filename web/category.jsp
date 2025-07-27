<%@page import="in.gadgethub.dao.impl.ProductDAOImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="in.gadgethub.dao.impl.ProductDAOImpl,java.util.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Categories of the Products</title>
                  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
        integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
                
	</head>
	<body>
		<li class="nav-item dropdown" style="position: relative">
			<a
				class="nav-link dropdown-toggle"
				href="#"
				id="dropdownMenuLink"
				role="button"
				data-bs-toggle="dropdown"
				aria-expanded="false"
			>
				Category
			</a>
			<ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
				<%
                                    ProductDAOImpl productDao=new ProductDAOImpl();
                                    List<String>productTypes=productDao.getAllProductType();
                                    for(String type:productTypes){
                                         String str=type.substring(0,1).toUpperCase()+type.substring(1);
                                    
                                  %>
                                  <li>
                                      <a href="LandingServlet?type=<%=type%>" class="dropdown-item"><%=str%></a> </li>
                                 
                                  <%
                                      }
                                      %>
			</ul>
		</li>
	</body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</html>
