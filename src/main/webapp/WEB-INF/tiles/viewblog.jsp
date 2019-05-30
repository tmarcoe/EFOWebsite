<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/blog.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />


<div class="blogHeader">
	<h2>EFO Blog</h2>
</div>
<div class="scrollRegion">
	<c:forEach var="item" items="${blogList}">
		<div class="blogRow fancy">
			<div class="blogLeftcolumn">
				<div class="blogCard">
					<h2>${item.heading}</h2>
					<h5>${item.description},
						<fmt:formatDate value="${item.timestamp}" />
					</h5>
					
					<div class="blogImg" style="height: 200px;">
						<img alt="Blog Image" src="<c:url value='${fileLoc.concat(item.image_file)}' />" width="200" />
					</div>
					<p>${item.blog_text}</p>
				</div>
			</div>
		</div>
	</c:forEach>
	<br> <br> <br> <br> <br> <br>
</div>

<div class="blogFooter">
	<table class="bigTitle">
		<tr>
			<td>Follow us on</td>
			<td><a href="https://web.facebook.com/Electronic-Financial-Officer-2231817403563729/?modal=admin_todo_tour"><img
					alt="Facebook" src="<c:url value='/images/f_logo_RGB-Blue_58.png' />" width="40" /></a></td>
			<td><a href="https://twitter.com/MarcoeTimothy"><img alt="Twitter"
					src="<c:url value='/images/Twitter_Logo_Blue.png '/>" width="70" /></a></td>
		</tr>
	</table>
</div>
<script>
</script>