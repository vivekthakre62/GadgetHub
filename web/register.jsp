<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
        integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<body>
     <jsp:include page="header.jsp"/>
<div class="container" >
    <div class="row align-item-center justify-content-center m-5">
        <form action="#" class="col-md-6 mt-4" style="border-radius:10px; box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;">
            <h3 class="text-center" style="color: salmon;">Register Form</h3>
            <div class="row">
                <div class="col-sm-6 form-ground mt-1">
                    <p>Name</p>
                    <input type="text" class="form-control" required>
                </div>
                <div class="col-sm-6 form-ground">
                    <p>Email</p>
                    <input type="email" class="form-control" required>
                </div>
            </div>
            <p class="mt-2">Address</p>
            <textarea name="" id="" rows="3" class="form-control " 
                ></textarea>
                <div class="row">
                    <div class="col-sm-6 form-ground mt-1">
                        <p>Mobile</p>
                        <input type="text" class="form-control" required>
                    </div>
                    <div class="col-sm-6 form-ground">
                        <p>Pincode</p>
                        <input type="email" class="form-control" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 form-ground mt-1">
                        <p>Password</p>
                        <input type="text" class="form-control" required>
                    </div>
                    <div class="col-sm-6 form-ground">
                        <p>Confirm Password</p>
                        <input type="email" class="form-control" required>
                    </div>
                </div>
                    <div class="row m-2 justify-content-center">
                    <div class="col-md-5 form-group">
                 <button type="button" class="btn" style="background-color: red;" >Reset</button>
                    </div>
                    <div class="col-md-5 justify-content-center form-group mt-1 ">
                        <button type="button" class="btn bg-primary ms-4" >send</button>
                    </div>
                    </div>
                
        </form>
    </div>
</div>
     <jsp:include page="footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>

</html>