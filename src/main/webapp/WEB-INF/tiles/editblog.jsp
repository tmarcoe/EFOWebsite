<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="postblog" method="post" action="/admin/updateblog" modelAttribute="blog" >
	<table class="fancy-table tableshadow" style="background-color: #ffeee6;" >
		<tr><td colspan="2"><h1>Blog</h1></td></tr>
		<tr>
			<td colspan="2"><b>Blog Title</b><br>
				<sf:input class="fancy" path="heading" size="60" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Blog Description</b><br>
				<sf:input class="fancy" path="description" size="60" /></td>
		</tr>
		<tr>	
			<td><b>Blog Date</b><br>
				<sf:input class="fancy" id="ts" path="timestamp" /></td>
			<td><b>Image File</b><br>
				<sf:input class="fancy" path="image_file" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Blog Text</b><br>
				<sf:textarea class="fancy-textarea" path="blog_text" rows="6" cols="53"/>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Submit</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.location.href='/index/manageblogs'" ><b>Cancel</b></sf:button>
		</tr>
	</table>
	<sf:hidden path="id"/>
</sf:form>
<script>
$( function() {
    var date = $( "#ts" ).datepicker({
    	dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        clickInput: true
    	});
    	
  } );

</script>
