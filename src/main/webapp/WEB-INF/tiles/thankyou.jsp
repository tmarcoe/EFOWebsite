<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />

<table class="tableview tableshadow tableheading rjthird rjfourth rjfifth rjsixth" style="margin-left: auto; margin-right: auto; width: 50%">
	<caption>Shopping Cart</caption>
	<tr>
		<td colspan="6"><h3>Invoice #: ${shoppingCart.reference}</h3></td>
	</tr>
	<tr>
		<th>Product Name</th>
		<th>Qty</th>
		<th>Original Price</th>
		<th>Discount</th>
		<th>Price</th>
		<th>Tax</th>
	</tr>
	<c:set var="totalPrice" value="0" />
	<c:set var="totalTax" value="0" />
	<c:forEach var="item" items="${shoppingCart.shoppingCartItems}">
		<tr>
			<td>${item.product_name}</td>
			<td>${item.qty}</td>
			<td><fmt:formatNumber type="currency" currencyCode="USD" value="${item.product_price * item.qty}" /></td>
			<td><fmt:formatNumber type="currency" currencyCode="USD" value="${item.product_discount}" /></td>
			<td><fmt:formatNumber type="currency" currencyCode="USD"
					value="${(item.product_price * item.qty) - item.product_discount}" /></td>
			<td><fmt:formatNumber type="currency" currencyCode="USD" value="${item.product_tax}" /></td>
		</tr>
		<c:set var="totalPrice" value="${totalPrice + ((item.product_price * item.qty) - item.product_discount)}" />
		<c:set var="totalTax" value="${totalTax +  item.product_tax}" />
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="3">&nbsp;</td>
			<td><b>Total Price -----></b></td>
			<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${totalPrice}" /></b></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
			<td><b>Total Tax --------></b></td>
			<td>&nbsp;</td>
			<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${totalTax}" /></b></td>
		</tr>
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="6"><button class="fancy-button" type="button" onclick="window.location.href='/index/introduction-a'"><b>OK</b></button></td>
			
		</tr>
	</tfoot>
</table>
	<div class="centerTitle" style="padding-top: 1.5em;">
		<h2>Thank you for your purchase</h2>
		<h3>You will receive a recipt by email.</h3>
	</div>

