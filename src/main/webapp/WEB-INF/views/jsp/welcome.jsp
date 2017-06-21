<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>talk over JSON</title>

<core:url var="home" value="/" scope="request" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCSS" />
<spring:url value="/resources/core/js/bootstrap.min.js"
	var="bootstrapJS" />
<spring:url value="/resources/core/js/jquery.1.10.2.min.js"
	var="jqueryJS" />

<link href="${bootstrapCSS}" rel="stylesheet">
<script src="${jqueryJS}" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		notificationFun();
		$("#search-form").submit(function(event) {
			enableSearchButton(false);
			event.preventDefault();
			searchViaAjax();
		});
	});
		
	function searchViaAjax() {
		var search = {}
		search["username"] = $("#username").val();
		search["email"] = $("#email").val();
		displayRequest(search);
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${home}search/api/getSearchResult",
			data : JSON.stringify(search),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				displayResponse(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				displayResponse(e);
			},
			done : function(e) {
				console.log("DONE");
				enableSearchButton(true);
			}
		});

	}
		
	function enableSearchButton(flag) {
		$("#btn-search").prop("disabled", flag);
	}
		
	function displayRequest(search) {	
		var json = "<h4>Ajax Request</h4><pre>" + JSON.stringify(search, null, 4) + "</pre>";
		$('#sentJSONId').html(json);
	}
		
	function displayResponse(data) {
		var json = "<h4>Ajax Response</h4><pre>" + JSON.stringify(data, null, 4) + "</pre>";
		$('#receivedJSONId').html(json);
	}
	
	function notificationFun() {
		var json = "<h4>To Do</h4><pre>" + "Search for : <br/>(username1 and/or email1) <br/>(username2 and/or email2) <br/>(username3 and/or email3) " + "</pre>";
		$('#notificationId').html(json);
	}
		
	
</script>
</head>
<body>

	<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Data transfer in JSON format</a>
		</div>
	</div>
	</nav>

	<div class="container" style="min-height: 500px">
		<div class="starter-template">
			<h1>Search Form</h1>
			<br>
			<div id="sentJSONId"></div>
			<div id="receivedJSONId"></div>
			<div id="notificationId">
			
			</div>
			<form class="form-horizontal" id="search-form">
				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label">Username</label>
					<div class="col-sm-10">
						<input type=text class="form-control" id="username">
					</div>
				</div>
				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label">Email</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="email">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" id="bth-search"
							class="btn btn-primary btn-lg">Search</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="container">
		<footer>
		<p>
			&copy; Harshil Gupta
		</p>
		</footer>
	</div>
	
</body>
</html>
