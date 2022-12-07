
<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="false"%>

  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
 <c:url value="/css/main.css" var="jstlCss" />

<meta charset="utf-8">
<title>Show Status of Job</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
 <%@ include file = "header.jsp" %>
 <%@ include file = "navigations.jsp" %>
 
	<div class="container">

		<div class="form-group">
			<label for="bankAccountNo">Account Number</label> <input
				type="number" class="form-control" id="bankAccountNo"
				placeholder="account number" required>
		</div>
		<div class="form-group">
			<label for="voucherDate">Date</label> <input type="date"
				class="form-control" id="voucherDate" required>
		</div>

		<!-- <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>
		<button data-toggle="modal" data-target="#myModal" onclick="myFunction()">GO::</a></button> -->
		<button data-toggle="modal" data-target="#myModal"
			onclick="myFunction(document.getElementById('bankAccountNo').value,document.getElementById('voucherDate').value)">Search</button>
	</div>
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			Modal content
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Modal Header</h4>
				</div>

				<div class="modal-body">
					<div class="columns download">
						<p>
							<a th:href="@{'download_file'}" class="btn"><i
								class="fa fa-download"></i></a>
						</p>

						<br>
					</div>

					<table class="table table-bordered table-striped">
						<tr>
							<th>Bank Account Number</th>
							<th>Voucher Date</th>
							<th>Process Flag</th>
							<th>Status</th>
							<th>Error Mesg</th>
						</tr>
						
						
						 <tbody>
                <c:forEach items="${brsd}" var="temp">
                    <tr>
                        <td>${temp.bank_account_no}</td>
                        <td>${temp.voucherdate}</td>
                        <td>${temp.process_flag}</td>
                        <td>${temp.status}</td>
                        <td>${temp.error_mesg}</td>
						<%-- <tr>
							<c:forEach var="user" items="${brsData}"> 
								<td>${user.bank_account_no}"</td>
								<td>${user.voucherdate}"</td>
								<td>${user.process_flag}"</td>
								<td>${user.status}"</td>
								<td>${user.error_mesg}"</td> --%>
						</tr>
						</c:forEach>
						</tbody>
					
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	<script>
/* myFunction() */
 
async function myFunction(bankAccountNo,voucherDate) {
	///joblistfromdb/{bankAccountNo}/{voucherDate} .$name_city;
	let response = await fetch("http://localhost:9595/brs/joblistfromdb/"+bankAccountNo+'/'+voucherDate);
	let res = await response.text();

	console.log(res);
	
 
  /* fetch("http://localhost:9595/brs/job-list-fromdb/"+{bankAccountNo})
  .then(response => response.text())
  .then(data => {
	  console.log(data)
	  //console.log(json.status)
	  alert(data)
	  alert("Hello! I am an alert box!"); 
  }) */

  
	/* fetch('http://localhost:9595/brs/job-list-fromdb/"'+bankAccountNo+'/'+voucherDate),{
	    method: 'GET',
	  headers: {
	    'Content-Type': 'application/json'
	  }
	}).then(response => response.text())
	.then(data => {
	        console.log(data);
 
	})
	.catch(err => {
	          console.log(err);
	}); */
  }

</script>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>