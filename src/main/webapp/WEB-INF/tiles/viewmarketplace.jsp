<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<c:forEach var="item" items="${mpList}">
	<table class="fancy-table tableshadow rjsecond" style="width: 50%;" >
		<tr>
			<td><img alt="Logo" src="<c:url value='${logoPath}${item.marketPlaceVendors.logo}'/>" width="70px"></td>

		</tr>
		<tr>
			<td><b>Product Name:</b> ${item.product_name}</td>
			<td><b>Created by:</b> ${item.marketPlaceVendors.company_name}</td>
		</tr>
		<tr>
			<td><b>Version: </b> ${item.version}</td>
			<td><b>Submitted On:</b> <fmt:formatDate value="${item.introduced_on}"/>
		</tr>
		<tr>
			<td>${item.product_description}</td>
		</tr>
		<tr>
			<td><b>Price </b></td>
			<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${item.product_price}" /></b></td>
		</tr>
	</table>
	<br>
</c:forEach>

