<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<sf:form id="mpProduct" method="post" action="/user/addregistry" modelAttribute="marketPlaceProduct" enctype="multipart/form-data">
<table class="fancy-table tableshadow">
		<c:if test="${marketPlaceProduct.firstTime == true }">
			<tr>
				<td colspan="3"><sf:input class="fancy" path="company_name" placeholder="Company Name" size="74"/></td>
			</tr>
		</c:if>
		<tr>
			<td colspan="3"><sf:input class="fancy" path="product_name" placeholder="Product Name" />
				<sf:input class="fancy" path="product_price" type="number" step=".01" placeholder="Asking Price" />
				<sf:input class="fancy" path="version" placeholder="Version (xx.xx.xx)" />
		</tr>
		<tr>
			<td><sf:errors class="error" path="product_name" /></td>
			<td><sf:errors class="error" path="product_price" /></td>
			<td><sf:errors class="error" path="version" /></td>
		</tr>
		<tr>
			<td><b>File</b><br>
				<sf:input class="fancy" type="file" path="file" /></td>
			<c:if test="${marketPlaceProduct.firstTime == true }">
				<td><b>Company Logo</b><br>
					<sf:input class="fancy" type="file" path="company_logo"/></td>
			</c:if>
		</tr>
		<tr>
			<td><sf:errors class="error" path="file" /></td>
		</tr>
		<tr>
			<td colspan="3"><b>Briefly Describe Your Product</b><br> <sf:textarea class="fancy-textarea"
					path="product_description" rows="10" cols="65" /></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="product_description"/>
		</tr>
		<tr>
			<td colspan="3"><b>Enter Product Keywords (Separate by space)</b><br> <sf:textarea class="fancy-textarea"
					path="keywords" rows="10" cols="65" /></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="keywords"/>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Register</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button"
					onclick="window.location.href='/user/cancelregistry?reference=${marketPlaceProduct.reference}'">
					<b>Cancel</b>
				</sf:button></td>
		</tr>
	</table>
	<sf:hidden path="product_reference" />
	<sf:hidden path="reference" />
	<sf:hidden path="product_tax" />
	<sf:hidden path="introduced_on" />
	<sf:hidden id="fTime" path="firstTime" />
	<div id="mpAgreement" class="modal">
		<div class="modal-content medium-modal fancy">
			<h1>Buy continuing, you accept the terms of this agreement.</h1>
			<h3>Any violation and the software will be removed and the person barred</h3>
			<h3>from any sales in the furture. Furthermore, any moneys collected will go</h3>
			<h3>back to the buyer.</h3>
			<ol>
				<li><b>No illegal or malicious software is allowed</b><br> No software with the intent to do harm will be
					tolerated.<br> The seller also agrees to support his/her product to the best<br> of their ability.<br></li>
				<li><b>The seller shall make no false claim</b><br> If the seller makes a claim about the software, he/she will be
					willing to<br> make sure the software can back up that claim. If not,<br> the seller is obligated to make
					any necessary modifications.<br></li>
				<li><b>The seller shall make no claim of superiority</b><br> The software simply fulfills a function. It
					is not better or worse<br> than any other software in the EFO Marketplace. Descriptions are limited<br>
					functional only. They are not making any claims that they are better,<br> nor shall they slander 
					any other seller.<br></li>
				<li><b>EFO reserves the right to remove any software that doesn't meet EFO's standards</b><br> We maintain a
					high standard in our company. You might not be an employee<br> but you are still representing our company by
					posting your software.<br> Make sure that all software your post is thoroughly tested. There will always<br>
					be some use case that you didn't think of. Just make sure it's kept to a minimum<br> and if or when the software
					breaks, be willing to fix it.<br></li>
			</ol>
			<table>
				<tr>
					<td><button class="fancy-button" type="button" onclick="$('#mpAgreement').hide()">
							<b>I Agree</b>
						</button></td>
					<td><button class="fancy-button" type="button"
							onclick="window.location.href='/user/cancelregistry?reference=${marketPlaceProduct.reference}'">
							<b>I Disagree</b>
						</button></td>
				</tr>
			</table>
		</div>
	</div>
</sf:form>
<script>
	function checkFirstTime() {
		var f = $('#fTime').val();
		if (f == "true") {
			$('#mpAgreement').show();
		}
	}
</script>
<script>
	checkFirstTime();
</script>

