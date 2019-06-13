<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script src="https://cdn.snipcart.com/scripts/2.0/snipcart.js"
	data-api-key="MDc1NzE4OTYtODU2Mi00NWJiLTg0YzctNzZiMGE5ODUyY2JkNjM1ODgwMTIzNDIwNTMxODQz" id="snipcart"></script>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link href="https://cdn.snipcart.com/themes/2.0/base/snipcart.min.css" rel="stylesheet" type="text/css" />

<fmt:formatNumber var="prdId" pattern="0000000000" value="${product.product_reference}"/>
<fmt:formatNumber var="price" type="currency" currencySymbol="" value="${product.product_price}"/>

<table class="fancy-table tableshadow rjsecond" style="width: 50%;">
	<tr>
		<td><img alt="Logo" src="<c:url value='${logoPath}${product.marketPlaceVendors.logo}'/>" width="70px"></td>

	</tr>
	<tr>
		<td><b>Product Name:</b> ${product.product_name}</td>
		<td><b>Created by:</b> ${product.marketPlaceVendors.company_name}</td>
	</tr>
	<tr>
		<td><b>Version: </b> ${product.version}</td>
		<td><b>Submitted On:</b> <fmt:formatDate value="${product.introduced_on}" />
	</tr>
	<tr>
		<td>${product.product_description}</td>
	</tr>
	<tr>
		<td><b>Price </b></td>
		<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${product.product_price}" /></b></td>
	</tr>
	<tr>
		<td><button class="snipcart-add-item fancy-button" 
						data-item-id="${prdId}" 
						data-item-name="${product.product_name}"
						data-item-price="${price}" 
						data-item-max-quantity="1"
						data-item-url="/user/displayprd/${prdId}"
						data-item-description="${product.product_description}">
						<b>Add To Cart</b>
					</button></td>
		<td><button class="fancy-button" onclick="window.location.href='/index/viewmarketplace'"><b>Back to Shopping</b></button>
	</tr>
</table>
