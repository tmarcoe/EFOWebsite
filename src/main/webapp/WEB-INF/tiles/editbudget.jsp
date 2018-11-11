<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/accounting/updatebudget" modelAttribute="budget">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="3"><b>Budget Title:</b><br><sf:input path="title" class="fancy" size="65"/></td>
		</tr>
		<tr>
			<td><sf:errors path="title" class="error"/></td>
		</tr>
		<tr>
			<td><b>Department:</b><br><sf:input path="department" class="fancy" readonly="true"/></td>
			<td><b>Total Budget:</b><br><sf:input id="total" path="total" class="fancy" /></td>
		</tr>
		<tr>
			<td><sf:errors path="department" class="error" /></td>
			<td><sf:errors path="total" class="error" /></td>
		</tr>
		<tr>
			<td><b>Beginning Period:</b><br><sf:input path="begin" type="date" class="fancy" /></td>
			<td><b>Ending Period:</b><br><sf:input path="end" type="date" class="fancy" /></td>
		</tr>
		<tr>
			<td><sf:errors path="begin" class="error"/></td>
			<td><sf:errors path="end" class="error"/></td>
		</tr>
		<tr>
			<td><sf:button type="submit" class="fancy-button" ><b>Save</b></sf:button></td>
			<td><sf:button type="button" class="fancy-button" onclick="window.history.back()"><b>Cancel</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="sumChildren()"><b>Total Cost</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden id="reference" path="reference"/>
	<sf:hidden path="author"/>
	<sf:hidden path="creation"/>
	<sf:hidden path="submitted"/>
	<sf:hidden path="rejected"/>
	<sf:hidden path="reason"/>
	<sf:hidden path="approved"/>
</sf:form>
<script type="text/javascript" >
function sumChildren() {
	var ref = $("#reference").val();
	
		$.getJSON(
				"/rest/sumchildren?reference=" + ref + "&parent=ROOT", function(data) {
					if (data.sum > 0.0 ) {
						$("#total").val(data.sum);
					}else{
						alert("No Children to Sum");
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
						alert("error " + textStatus + "\n" + "incoming Text "
									   + jqXHR.responseText);
				});
}

</script>