<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="prd" method="post" action="/admin/addproduct" modelAttribute="product"  enctype="multipart/form-data" >
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="2"><sf:input class="fancy" path="product_name" placeholder="Product Name" size="55"/></td>
		</tr>
		<tr>
			<td><b>EFO</b><sf:input class="fancy" path="product_id" placeholder="Unique Product ID"/> </td>
			<td><sf:input class="fancy" type="number" step=".01" path="product_price" placeholder="Price" /></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="product_name" /></td>
			<td><sf:errors class="error" path="product_price" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" type="number" step=".01"  path="product_tax" placeholder="Tax" /></td>
			<td><sf:input class="fancy" type="number" step=".01" path="product_discount" placeholder="Discount" /></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="product_tax" /></td>
			<td><sf:errors class="error" path="product_discount" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" type="file" path="product_file"/></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="product_file" /></td>
		</tr>
		<tr>
			<td colspan="2"><sf:textarea class="fancy-textarea" path="product_description" placeholder="Description" rows="5" cols="50"/></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="product_description" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Add Product</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button>
		</tr>
	</table>

</sf:form>