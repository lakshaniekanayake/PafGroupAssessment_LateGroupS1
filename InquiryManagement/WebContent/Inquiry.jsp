<%@page import="com.Inquiry"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inquiry Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/Inquiry.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" />
</head>
<body>
<h1 class="text-white text-center mt-5">Inquiry Management</h1>
	<div class="container ">
	<div class="row">
			<div class="mx-auto float-left col-10" style="width:30%;" >
				<form id="formItem" name="formItem" class="mt-5" method="post" action="items.jsp">
				
				<div class="input-group mb-3">				
  					<div class="input-group-prepend"> 					
    					<span class="input-group-text" id="basic-addon1">
    						<i class="fa fa-user" aria-hidden="true"></i>
    					</span>
  					</div>			
  					<input id="CustomerName" name="CustomerName" type="text" class="form-control" placeholder="Enter Name">
				</div>
				
				<div class="input-group mb-3">				
  					<div class="input-group-prepend"> 					
    					<span class="input-group-text" id="basic-addon1">
    						<i class="fa fa-location-arrow" aria-hidden="true"></i>
    					</span>
  					</div>			
  					<textarea id="Address" name="Address"  class="form-control" placeholder="Enter Address"></textarea>
				</div>
				
				<div class="input-group mb-3">				
  					<div class="input-group-prepend"> 					
    					<span class="input-group-text" id="basic-addon1">
    						<i class="fa fa-calendar" aria-hidden="true"></i>
    					</span>
  					</div>			
  					<input id="Date" name="Date" type="text" class="form-control"  placeholder="Enter Date">
				</div>
				
				<div class="input-group mb-3">				
  					<div class="input-group-prepend"> 					
    					<span class="input-group-text" id="basic-addon1">
							<i class="fa fa-commenting" aria-hidden="true"></i>
						</span>
  					</div>			
  					<textarea id="Reason" name="Reason"  class="form-control" placeholder="Enter Reason"></textarea>
				</div>
				
					
					 <div class="text-center mt-2"> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary" style="width:30%; height:50px;"> 
					<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">	
					</div>	
				</form>
				<div id="alertSuccess" class="alert alert-success mt-5"></div>
				<div id="alertError" class="alert alert-danger mt-5"></div>
			</div>
				<div id="divItemsGrid" class="col-12 mb-4  mt-5 text-white text-center container d-flex justify-content-center">
					<%
						Inquiry InquiryObj = new Inquiry();
						out.print(InquiryObj.readInquiry());
					%>
				</div>
			</div>	
		</div>
		
	
	
</body>

<style>
	body{
		/* background-image: radial-gradient( circle 610px at 5.2% 51.6%,  rgba(5,8,114,1) 0%, rgba(7,3,53,1) 97.5% ); */

		/* background-image: linear-gradient( 90.9deg,  rgba(3,195,195,1) 0.3%, rgba(37,84,112,1) 87.8% ); */
		/* 
		background: #0f0c29;  
		background: -webkit-linear-gradient(to right, #24243e, #302b63, #0f0c29);  
		background: linear-gradient(to right, #24243e, #302b63, #0f0c29);  */

		background: #000428;  /* fallback for old browsers */
		background: -webkit-linear-gradient(to right, #004e92, #000428);  /* Chrome 10-25, Safari 5.1-6 */
		background: linear-gradient(to right, #004e92, #000428); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
	}
		
</style>
	
</html>