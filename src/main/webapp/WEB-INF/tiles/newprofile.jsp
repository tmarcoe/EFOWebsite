<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="vendor" method="post" action="/basic/addprofile" modelAttribute="profile">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Profile Name: </b><br>
				<sf:input class="fancy" path="name"/></td>
			<td><b>Script File: </b><br>
				<sf:input class="fancy" path="script" size="40"/></td>
			<td><b>Credit Terms: </b>
				<sf:checkbox class="fancy" path="terms"/></td>
		</tr>
		<tr>
			<sf:errors path="name" class="error" />
			<sf:errors path="script" class="error" />
		</tr>
		<tr>
			<td><b>Created On: </b><br>
				<sf:input class="fancy" type="date" path="created" /></td>
			<td><b>Active: </b>
				<sf:checkbox class="fancy" path="active" checked="true" /></td>
		</tr>
		<tr>
			<sf:errors path="created" class="error" />
		</tr>
		<tr>
			<td colspan="2"><b>Extra Variables: </b><br>
				Use the form &lt;variable name&gt; ',' &lt;variable type&gt;.<br>
				Variables are separated by a semicolon ';'.<br>
				<sf:textarea class="fancy-textarea" path="variables" rows="5" cols="60"/>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button>
		</tr>
	</table>
</sf:form>
<script type="text/javascript">
</script>
