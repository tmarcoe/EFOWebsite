<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="vendor" method="post" action="/basic/addtransaction" modelAttribute="transaction">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Amount:</b><br> <sf:input id="total_due" class="fancy" path="amount" type="number" step=".01"
					onchange="eachPayment()" /></td>

			<td><b>Profile Name: </b> <sf:select class="fancy" path="name">
					<sf:options items="${profileList}" />
				</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="amount" class="error" /></td>
		</tr>
		<tr>
			
			<td class="centerHeading" colspan="2"><b>Credit Terms</b></td>

		</tr>
		<tr>
			<td><b>Down Payment:</b><br> <sf:input id="down_payment" class="fancy" path="down" type="number" step=".01"
					onchange="eachPayment()" /></td>
			<td><b>Interest:</b><br> <sf:input id="interest" class="fancy" path="interest" type="number" step=".01"
					onchange="eachPayment()" /></td>
		</tr>
		<tr>
			<sf:errors path="down" class="error" />
			<sf:errors path="interest" class="error" />
		</tr>
		<tr>
			<td><b>Number of Payments:</b><br> <sf:input id="num_payments" class="fancy" path="num_payments"
					type="number" step="1" onchange="eachPayment()" /></td>
			<td><b>Each Paymment</b><br> <sf:input id="each_payment" class="fancy" path="each_payment" type="number"
					step=".01" /></td>
		</tr>
		<tr>
			<sf:errors path="num_payments" class="error" />
			<sf:errors path="each_payment" class="error" />
		</tr>
		<tr>
			<td colspan="2"><b>Comments</b><br> <sf:textarea class="fancy-textarea" path="description" cols="50" rows="5" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Save</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</sf:button>
		</tr>
		<tr>
			<td><sf:hidden path="reference" /></td>
			<td><sf:hidden path="timestamp"/></td>
			<td><input type="hidden" id="total_tax" value="0.00" /></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript">
	function eachPayment() {
		var total_due = $("#total_due").val();
		var total_tax = $("#total_tax").val();
		var down_payment = $("#down_payment").val();
		var interest = $("#interest").val();
		var num_payments = $("#num_payments").val();

		if (total_due > 0 && num_payments > 0) {
			var ttl = parseFloat(total_due) + parseFloat(total_tax);
			$.getJSON(
					"/rest/calculatepayments?total=" + ttl + "&down="
							+ down_payment + "&interest=" + interest
							+ "&num_payments=" + num_payments, function(data) {
						$("#each_payment").val(data.each_payment);
					}).fail(
					function(jqXHR, textStatus, errorThrown) {
						alert("error " + textStatus + "\n" + "incoming Text "
								+ jqXHR.responseText);
					});
		}
	}
</script>
