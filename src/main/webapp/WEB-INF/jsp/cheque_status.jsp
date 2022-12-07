

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="false"%>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<!-- 	<c:url value="/css/main.css" var="jstlCss" />
 -->
 </head>
<body>

<%
response.sendRedirect("statusjob.jsp");
%>
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
							<th>Cheque Statusr</th>
							
						</tr>
						<tr>
							<%-- <c:forEach var="user" items="${brscheques}"> --%>
								<td>${user.cheque_status}"</td>
								
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	
		<script>
	async function myFunction(bankAccountNo,voucherDate) {
	
	let response = await fetch('http://localhost:9595/brs/chequestatus/40310005866/2022-08-08');
	let res = await response.json();

	console.log(res);
	}
	
	
	 $("#contactUs").click(function(){
         // any value you need
         $(document).scrollTop(600) 
         });
	</script>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>

</html>