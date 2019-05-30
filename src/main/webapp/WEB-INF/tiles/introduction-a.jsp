<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<sf:form method="post" action="/user/savepassword" modelAttribute="user">
	<div class="centerTitle">
		<h2>So, What is EFO?</h2>
		<h3>EFO (Electonic Financial Officer) is a;</h3>
		<ul style="list-style: none;">
			<li>browser based</li>
			<li>highly flexible</li>
			<li>double entry accounting application</li>
		</ul>
		<h4>It is so flexible that it is actually powering this website</h4>
		<button class="mainPage right-button" type="button" onclick="window.location.href='/index/introduction-b'">NEXT>></button>
	</div>
	<div id="sPass" class="modal">
		<div class="modal-content small-modal fancy">
			<h2>Change your password</h2>
			<table style="margin-left: auto; margin-right: auto;">
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
		</div>
	</div>
</sf:form>
<script>
	function openPopup() {
		var modal = document.getElementById('sPass');
		modal.style.display = "block"
	}

	function closePopup() {
		var modal = document.getElementById('sPass');
		modal.style.display = "none";
	}
</script>
<c:if test="${user.isTemp_pw() == true}">
	<script>
		openPopup()
	</script>
</c:if>
