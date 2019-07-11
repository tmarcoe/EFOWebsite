<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<sf:form id="mpProduct" method="post" action="/user/updatempproduct" modelAttribute="marketPlaceProduct">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="3"><sf:input class="fancy" path="product_name" placeholder="Product Name" /> <sf:input
					class="fancy" path="product_price" type="number" step=".01" placeholder="Asking Price" /> <sf:input class="fancy"
					path="version" placeholder="Version (xx.xx.xx)" />
		</tr>
		<tr>
			<td><sf:errors class="error" path="product_name" /></td>
			<td><sf:errors class="error" path="product_price" /></td>
			<td><sf:errors class="error" path="version" /></td>
		</tr>
		<tr>
			<td colspan="3"><b>Briefly Describe Your Product</b><br> <sf:textarea class="fancy-textarea"
					path="product_description" rows="10" cols="65" /></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="product_description" />
		</tr>
		<tr>
			<td colspan="3"><b>Enter Product Keywords (Separate by space)</b><br> <sf:textarea class="fancy-textarea"
					path="keywords" rows="10" cols="65" /></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="keywords" />
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Update</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</sf:button></td>
		</tr>
	</table>
	<sf:hidden path="product_reference" />
	<sf:hidden path="reference" />
	<sf:hidden path="product_tax" value="0"/>
	<sf:hidden path="total_sales" value="0"/>
	<sf:hidden path="total_commission" value="0"/>
	<sf:hidden path="introduced_on" />
</sf:form>
