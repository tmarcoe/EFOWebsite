<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/efoweb?useSSL=false"
	user="root" password="3xc7vbkjlv99" />
	
<link type="text/css" rel="stylesheet" href="/css/forum.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />

<div class="forumHeader">
	Forum
</div>

<div class="scrollRegion" >
	<c:forEach var="item" items="${forumList}">
		<div class="forumRow fancy" >
			<div class="forumLeftcolumn">
				<div class="forumCard" style="padding-left: 1em;">
					<h2>${item.title}</h2>
					<h5><b>Created on: <fmt:formatDate value="${item.post_created}" /></b> By: ${item.name}</h5>
					${item.text}<br>
					<a href="/user/replytopost?reference=${item.reference}&parent=0&level=0">Reply</a>
					<c:if test="${whoAmI == item.author}">
						<a href="/user/editpost?reference=${item.reference}">Edit</a>
						<a href="/user/deletepost?reference=${item.reference}">Delete</a>
					</c:if>
				</div>
				<sql:query dataSource = "${ds}" var = "result">
         			SELECT * from forum_replies WHERE reference = ${item.reference} ORDER BY parent, post_created DESC;
      			</sql:query>
				<c:forEach var="row" items="${result.rows}">
					<c:set var="indent" value="${row.level}"/>
					<div style="padding-left: ${indent * 3}em">
						<b>Created on: <fmt:formatDate value="${row.post_created}"/></b> By: ${row.name}<br>
						${row.text}<br>
						<a href="/user/replytopost?reference=${row.reference}&parent=${row.id}&level=${row.level}">Reply</a>
						<c:if test="${whoAmI == row.author}">
							<a href="/user/editreply?id=${row.id}">Edit</a>
							<a href="/user/deletereply?id=${row.id}">Delete</a>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
</div>

