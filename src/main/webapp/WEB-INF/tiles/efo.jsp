<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet" href="/css/w3.css">
<link rel="stylesheet" href="/css/products.css">
<link rel="stylesheet" href="/css/fancy-input.css">
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<fmt:formatNumber var="prdId" pattern="0000000000" value="${product.product_reference}" />
<fmt:formatNumber var="price" type="currency" currencySymbol="" value="${product.product_price}" />

<h2 class="w3-center">EFO</h2>
<h4 class="w3-center">EFO is a browser based, double entry accounting system.</h4>
<h4 class="w3-center">This is just the basic system. We have add-ons that enhance this product.</h4>

<div class="w3-content w3-section" style="max-width: 800px">
	<img class="mySlides" src="/images/slideshow/fig14.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/fig15.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/fig19.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/fig30.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/fig31.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/fig32.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/fig4.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/fig6.png" style="width: 100%">
</div>

<div class="w3-center">
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
</div>

<div class="price">
	<table style="align: center; right-margin: 0; left-margin: 0;">
		<tr>
			<td><b>Price</b></td>
			<td>....................................................................................................................................</td>
			<td><strike>$500.00</strike></td>
		</tr>
		<tr>
			<td colspan="2"><button class="fancy-button" onclick="window.location.href='/user/displayefoprd?prdId=EFO1'"><b>Order Product</b></button></td>
			<td><b>$100.00</b></td>
		</tr>
	</table>
</div>
<script>
	var myIndex = 0;
	carousel();

	function carousel() {
		var i;
		var x = document.getElementsByClassName("mySlides");
		var dots = document.getElementsByClassName("demo");

		for (i = 0; i < x.length; i++) {
			x[i].style.display = "none";
		}
		myIndex++;
		if (myIndex > x.length) {
			myIndex = 1
		}
		for (i = 0; i < dots.length; i++) {
			dots[i].className = dots[i].className.replace(" w3-red-color", "");
		}
		x[myIndex - 1].style.display = "block";
		dots[myIndex - 1].className += " w3-red-color";
		setTimeout(carousel, 2000); // Change image every 2 seconds
	}
	
</script>
