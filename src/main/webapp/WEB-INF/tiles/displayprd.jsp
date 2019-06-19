<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/efoweb?useSSL=false"
	user="root" password="3xc7vbkjlv99" />
	
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<fmt:formatNumber var="prdId" pattern="0000000000" value="${product.product_reference}" />
<fmt:formatNumber var="price" type="currency" currencySymbol="" value="${product.product_price}" />

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

		<td><button class="fancy-button" onclick="window.location.href='/index/viewmarketplace'">
				<b>Back to Shopping</b>
			</button>
	</tr>
</table>
<div id="warning" class="modal">
	<div class="modal-content small-modal fancy">
		<table style="margin-left: auto; margin-right: auto;">
			<tr>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
	

</script>