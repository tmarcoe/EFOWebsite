<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script src="https://cdn.snipcart.com/scripts/2.0/snipcart.js"
	data-api-key="MDc1NzE4OTYtODU2Mi00NWJiLTg0YzctNzZiMGE5ODUyY2JkNjM1ODgwMTIzNDIwNTMxODQz" id="snipcart"></script>

<link rel="stylesheet" href="/css/w3.css">
<link rel="stylesheet" href="/css/products.css">
<link rel="stylesheet" href="/css/fancy-input.css">
<link href="https://cdn.snipcart.com/themes/2.0/base/snipcart.min.css" rel="stylesheet" type="text/css" />
<style>
.mySlides {
	display: none;
}
</style>


<h2 class="w3-center">FTL</h2>
<h4 class="w3-center">FTL is the language that powers EFO. It is also available as</h4>
<h4 class="w3-center">a standalone product.</h4>

<div class="w3-content w3-section" style="max-width: 750px">
	<img class="mySlides" src="/images/slideshow/ftl-fig1.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/ftl-fig2.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/ftl-fig3.png" style="width: 100%"> 
	<img class="mySlides" src="/images/slideshow/ftl-fig4.png" style="width: 100%">
</div>

<div class="w3-center">
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
	<div class="w3-div demo">&#8226;</div>
</div>

<div class="price">
	<table style="align: center;">
		<tr>
			<td><b>Price</b></td>
			<td>....................................................................................................................................</td>
			<td><b>$0.00</b></td>
			<sec:isAuthenticated>
			<tr>
				<td colspan="2"><button class="snipcart-add-item fancy-button" 
						data-item-id="EFO2"
						data-item-name="FTL" 
						data-item-price="0.00" 
						data-item-max-quantity="1"
						data-item-url="/index/ftl" 
						data-item-description="Flexible Transaction Language">
						<b>Add To Cart</b>
					</button></td>
			</tr>
			</sec:isAuthenticated>
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
