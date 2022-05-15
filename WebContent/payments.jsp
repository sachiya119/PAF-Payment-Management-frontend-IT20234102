<%@ page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
	<html>
		<head>
		<meta charset="ISO-8859-1">
		<title>Payment Management</title>
		
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery.min.js"></script>
		<script src="Components/payments.js"></script>
		
		</head>
	<body>
	
	<center>
	<h1>Payment Management</h1>
	</center>
	<div class="container">
		<div class="row">
			<div class="col">
				<form id="formPayment" name="formPayment" method="post" action="payments.jsp">
					Payment method:
					<input id="paymentMethod" name="paymentMethod" type="text"
					class="form-control form-control-sm">
					<br> Address:
					<input id="address" name="address" type="text"
					class="form-control form-control-sm">
					<br> NIC:
					<input id="nic" name="nic" type="text"
					class="form-control form-control-sm">
					<br> Amount:
					<input id="amount" name="amount" type="text"
					class="form-control form-control-sm">
					<br>
					<input id="btnSave" name="btnSave" type="button" value="Save"
					class="btn btn-primary btn-lg">
					<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
				</form>
				
			</div>
		</div>
	</div>
	<br>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
	<br>
		<div id="divPaymentGrid">
			 <%
				 Payment paymentObj = new Payment(); 
				 out.print(paymentObj.readPayment()); 
			 %>
		</div>
	</body>
	</html>