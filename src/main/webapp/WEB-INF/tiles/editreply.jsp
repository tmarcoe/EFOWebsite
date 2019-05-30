<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="postblog" method="post" action="/user/updatereply" modelAttribute="forumReply">
	<table class="fancy-table tableshadow" style="background-color: #ffeee6;">
		<tr>
			<td><h1>Reply</h1></td>
		</tr>
		<tr>
			<td><sf:textarea class="fancy-textarea" path="text" rows="10" cols="53" />
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Submit</b>
				</sf:button></td>
		</tr>
	</table>
	<sf:hidden path="id" />
	<sf:hidden path="reference" />
	<sf:hidden path="post_created" />
	<sf:hidden path="post_read" />
	<sf:hidden path="parent" />
	<sf:hidden path="level" />
	<sf:hidden path="author" />
	<sf:hidden path="name"/>
</sf:form>
<script>
	
</script>
