<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder rjthird rjfourth rjfifth">
	<thead>
		<tr>
			<th>Product Id</th>
			<th>Product Name</th>
			<th>Product Price</th>
			<th>Product Tax</th>
			<th>Discount</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.product_id}</td>
			<td>${item.product_name}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.product_price}" /></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.product_tax}" /></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.product_discount}" /></td>
			<td><button type="button" onclick="window.location.href='/admin/scproductedit?id=${item.product_id}'">Edit</button></td>
			<td><button type="button" onclick="window.location.href='/admin/scproductdelete?id=${item.product_id}'">Delete</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
	<tr>
		<td colspan="6">&nbsp;</td>
		<td><button type="button" onclick="window.location.href='/index/introduction-a'">OK</button></td>
	</tr>
	</tfoot>
</table>