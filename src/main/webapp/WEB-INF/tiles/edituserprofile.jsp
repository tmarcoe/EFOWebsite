<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<script>
	function notCustomer() {
		$("#notCust").show();
	}
</script>
<div id="notCust" class="modal">
	<div class="modal-content small-modal fancy">
		<h1>This user is not in the customer table.</h1>
		<button class="fancy" type="button" onclick="window.history.back()">
			<b>Cancel</b>
		</button>
	</div>
</div>

<c:if test="${empty user.customer}">
	<script>
		notCustomer();
	</script>
</c:if>
<sf:form id="editpf" method="post" action="/user/updateuserprofile" modelAttribute="user">
	<table class="fancy-table tableshadow cjfirst">
		<tr>
			<td colspan="3"><sf:select class="fancy" path="customer.salutation">
					<sf:option value="Mr.">Mr.</sf:option>
					<sf:option value="Mrs.">Mrs.</sf:option>
					<sf:option value="Ms.">Ms.</sf:option>
					<sf:option value="Miss.">Miss.</sf:option>
					<sf:option value="Dr.">Dr.</sf:option>
				</sf:select> 
				<sf:input class="fancy" path="customer.firstname" placeholder="First Name" /> 
				<sf:input class="fancy" path="customer.lastname" placeholder="Last Name" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="username" placeholder="Email" /></td>
			<td><sf:select class="fancy" path="customer.maleFemale">
					<sf:option value="M">Male</sf:option>
					<sf:option value="F">Female</sf:option>
				</sf:select></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2"><h3>Billing Address</h3></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="common.address1" placeholder="Address Line 1" /></td>
			<td><sf:input class="fancy" path="common.address2" placeholder="Address Line 2" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="common.city" placeholder="City" /></td>
			<td><sf:input class="fancy" path="common.region" placeholder="Region or State" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="common.postalCode" placeholder="Postal Code" /></td>
			<td><sf:input class="fancy" path="common.country" placeholder="3 character Country Code" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Update Account</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</sf:button></td>
		</tr>

	</table>
	<sf:hidden path="user_id" />
	<sf:hidden path="password" />
	<sf:hidden path="enabled" />
	<sf:hidden path="temp_pw" />
	<sf:hidden path="common.user_id" />
	<sf:hidden path="customer.user_id" />
	<sf:hidden path="customer.since" />
	<sf:hidden id="selectedRoles" path="roleString" />
</sf:form>

