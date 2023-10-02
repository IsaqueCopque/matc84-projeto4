<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://xmlns.jcp.org/jsf/html"
	xmlns:f="http://xmlns.jcp.org/jsf/core"
	xmlns:pt="http://xmlns.jcp.org/jsf/passthrough"
	xmlns:jsf="http://xmlns.jcp.org/jsf"
	xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
	xmlns:p="http://primefaces.org/ui">
	
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
	<h1>Teste</h1>
   	<div class="ui-fluid">
	    <div class="field">
	        <p:outputLabel for="firstname" value="Firstname" />
	        <p:inputText id="firstname" />
	    </div>
	    <div class="field">
	        <p:outputLabel for="lastname" value="Lastname" />
	        <p:inputText id="lastname" />
	    </div>
	</div>
</body>


</html>