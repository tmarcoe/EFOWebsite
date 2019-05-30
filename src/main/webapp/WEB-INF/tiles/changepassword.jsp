<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/user/savepassword" modelAttribute="user">
	<table class="fancy-table tableshadow" >
		<tr>
			<th>Password</th>
			<th>Repeat Password</th>
		</tr>
		<tr>
			<td><sf:password id="password" name="password" path="password" class="control fancy" /></td>
			<td><input class="fancy" id="confirmpass" class="control fancy" name="confirmpass" type="password" /></td>
		</tr>
		<tr>
			<td><div id="pbar">
					<label id="pLabel"></label>
					<div id="pStrength"></div>
				</div>&nbsp;</td>
			<td><div id="matchpass"></div>&nbsp;</td>
		</tr>
		<tr>
			<td><button class="fancy-button" type="submit">
					<b>Save</b>
				</button></td>
		</tr>
	</table>
</sf:form>