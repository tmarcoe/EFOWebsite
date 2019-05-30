<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<link type="text/css" href="/css/modal-popup.css" rel="stylesheet" />
<link type="text/css" href="/css/fancy-input.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder">

	<thead>
		<tr>
			<th>Heading</th>
			<th>Description</th>
			<th>Timestamp</th>
			<th colspan="2">&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.heading}</td>
			<td>${item.description}</td>
			<td><fmt:formatDate value="${item.timestamp}"/></td>
			<td><button type="button" onclick="window.location.href='/admin/editblog?id=${item.id}'">Edit</button></td>
			<td><button type="button" onclick="deleteBlog('${item.id}')">Delete</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="5"><button type="button" onclick="window.location.href = '/index/viewblog'">Back</button></td>
		</tr>
	</tfoot>
</table>
	<div id="askDelete" class="modal">
		<div class="modal-content small-modal fancy">
			<h2>Is it OK to delete this blog?</h2>
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<td><button class="fancy-button" type="button" onclick="del()">
							<b>Yes</b>
						</button></td>
					<td><button class="fancy-button" type="button" onclick="$('#askDelete').hide()">
							<b>No</b>
						</button></td>
					<td><input id="blogId" type="hidden"></td>
				</tr>
			</table>
		</div>
	</div>
<script type="text/javascript">
	function deleteBlog(id) {
		$("#blogId").val(id);
		$("#askDelete").show();
		
	}
	function del() {
		var id = $("#blogId").val();
		window.location.href = "/admin/deleteblog?id=" + id;
	}
</script>
