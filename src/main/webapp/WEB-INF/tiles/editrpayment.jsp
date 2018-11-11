<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/accounting/updaterpayment" modelAttribute="payment" >
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Date Due</b></td>
			<td><b>Payment Due</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="reference" readonly="true" /></td>
			<td><sf:input class="fancy" path="date_due" type="date" /></td>
			<td><sf:input class="fancy" path="payment_due" type="number" step=".01"/></td>
		</tr>
		<tr>
			<sf:errors path="reference" class="error" />
			<sf:errors path="date_due" class="error" />
			<sf:errors path="payment_due" class="error" />
		</tr>
		<tr>
			<td colspan="3">Comments:<br>
				<sf:textarea class="fancy-textarea" path="comments" rows="4" cols="75"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"><sf:button class="fancy-button" type="submit" ><b>Save Payment</b></sf:button></td>
			<td><button type="button" class="fancy-button" onclick="window.history.back()"><b>Cancel</b></button></td>
		</tr>
	</table>
	<sf:hidden path="id" />
	<sf:hidden path="payment_date"/>
	<sf:hidden path="payment"/>
	<sf:hidden path="penalties"/>
</sf:form>


