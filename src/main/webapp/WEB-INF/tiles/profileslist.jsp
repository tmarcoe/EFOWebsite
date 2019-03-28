<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder tableSpacing rjsixth rjseventh">
	<thead>
		<tr>
			<th>Name</th>
			<th>Script File</th>
			<th>Payment Terms?</th>
			<th>Capital Asset?</th>
			<th>Created On</th>
			<th>Active</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.name}</td>
			<td>${item.script}</td>
			<td>${item.terms}</td>
			<td>${item.depreciation}</td>
			<td><fmt:formatDate value="${item.entryDate}" /></td>
			<td>${item.active}</td>
			<td><button type="button" onclick="window.location.href='/basic/editprofile'">Edit</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="6"><button type="button" onclick="window.location.href='/basic/newprofile'">New Profile</button></td>
			<td><button type="button" onclick="window.location.href='/#tabs-3'">Back</button></td>
		</tr>
	</tfoot>
</table>
