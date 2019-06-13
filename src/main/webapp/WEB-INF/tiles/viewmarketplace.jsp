<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">

<div class="searchArea">
	<i class="fa fa-search searchIcon"></i> <input id="srch" type="search" class="fancy searchBox" placeholder="Search...">
	<button class="fancy-button" onclick="search()">
		<b>Search</b>
	</button>
</div>
<c:forEach var="item" items="${mpList}">
	<fmt:formatNumber var="prdId" pattern="0000000000" value="${item.product_reference}"/>
	<table class="fancy-table tableshadow rjsecond" style="width: 50%;" onclick="window.location.href='/user/displayprd/${prdId}'">
		<tr>
			<td><img alt="Logo" src="<c:url value='${logoPath}${item.marketPlaceVendors.logo}'/>" width="70px"></td>

		</tr>
		<tr>
			<td><b>Product Name:</b> ${item.product_name}</td>
			<td><b>Created by:</b> ${item.marketPlaceVendors.company_name}</td>
		</tr>
		<tr>
			<td><b>Version: </b> ${item.version}</td>
			<td><b>Submitted On:</b> <fmt:formatDate value="${item.introduced_on}" />
		</tr>
		<tr>
			<td>${item.product_description}</td>
		</tr>
		<tr>
			<td><b>Price </b></td>
			<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${item.product_price}" /></b></td>
		</tr>
		<c:choose>
			<c:when test="${userId == item.marketPlaceVendors.user_id}">
				<tr>
					<td><a href="/user/editmpproduct?product_reference=${item.product_reference}">Edit</a></td>
					<td><a href="/user/deletempproduct?product_reference=${item.product_reference}">Delete</a></td>
				</tr>
			</c:when>
			<c:otherwise>
				<sec:hasRole role="ADMIN">
					<tr>
						<td><a href="/user/editmpproduct?product_reference=${item.product_reference}">Edit</a></td>
						<td><a href="/user/deletempproduct?product_reference=${item.product_reference}">Delete</a></td>
					</tr>
				</sec:hasRole>
			</c:otherwise>
		</c:choose>
	</table>
	<br>
</c:forEach>
<script>
	function search() {
		window.location.href = "/index/marketplacesearch?search="
				+ $('#srch').val();
	}
</script>
