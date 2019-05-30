<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="postforum" method="post" action="/user/updatepost" modelAttribute="forum" >
	<table class="fancy-table tableshadow" style="background-color: #ffeee6;" >
		<tr><td colspan="2"><h1>Forum</h1></td></tr>
		<tr>
			<td colspan="2"><b>Post Title</b><br>
				<sf:input class="fancy" path="title" size="60" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Post Text</b><br>
				<sf:textarea class="fancy-textarea" path="text" rows="6" cols="53"/>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Post</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()" ><b>Cancel</b></sf:button>
		</tr>
	</table>
	<sf:hidden path="reference"/>
	<sf:hidden path="post_read"/>
	<sf:hidden path="author"/>
	<sf:hidden path="subject"/>
	<sf:hidden path="post_created"/>
	<sf:hidden path="name"/>
	
</sf:form>

<script>
</script>
