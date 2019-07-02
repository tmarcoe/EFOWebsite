<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="overhead" method="post" action="/index/adduserprofile" modelAttribute="user" enctype="multipart/form-data">
	<table class="fancy-table tableshadow cjfirst" >
		<tr>
			<td colspan="3"><sf:select class="fancy" path="customer.salutation">
				<sf:option value="Mr.">Mr.</sf:option>
				<sf:option value="Mrs.">Mrs.</sf:option>
				<sf:option value="Ms.">Ms.</sf:option>
				<sf:option value="Miss.">Miss.</sf:option>
				<sf:option value="Dr.">Dr.</sf:option>
				</sf:select>
				<sf:input class="fancy" path="customer.firstname" placeholder="First Name"/>
				<sf:input class="fancy" path="customer.lastname" placeholder="Last Name"/></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="username" placeholder="Email"/></td>
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
			<td><b>Password</b><br>
				<sf:password id="password" class="fancy" path="password" autocomplete="false"
					showPassword="true" placeholder="Password" /></td>
			<td><b>Comfirm Password</b><br>
				<input class="fancy" id="confirmpass" class="control" name="confirmpass"
				type="password" placeholder="Confirm Password" /></td>
		</tr>
		<tr>
			<td><div id="pbar">
					<label id="pLabel"></label>
					<div id="pStrength"></div>
				</div>&nbsp;</td>
			<td><div id="matchpass"></div>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="Submit" ><b>Create Account</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()" ><b>Cancel</b></sf:button></td>
		</tr>
	</table>
</sf:form>
<script>
</script>