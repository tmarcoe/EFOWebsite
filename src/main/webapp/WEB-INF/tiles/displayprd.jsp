<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/efoweb?useSSL=false" user="root"
	password="3xc7vbkjlv99" />

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<fmt:formatNumber var="prdId" pattern="0000000000" value="${product.product_reference}" />
<fmt:formatNumber var="price" type="currency" currencySymbol="" value="${product.product_price}" />

<sf:form id="shpCartForm" method="post" action="/user/processorder" modelAttribute="shoppingCart">
	<button class="fancy-button" type="button" onclick="$('#shoppingCart').show()"
		style="position: absolute; top: 10px; left: 30px">
		<b>Show Cart</b>
	</button>
	<table class="fancy-table tableshadow rjsecond" style="width: 50%;">
		<tr>
			<td><img alt="Logo" src="<c:url value='${logoPath}${product.marketPlaceVendors.logo}'/>" width="70px"></td>

		</tr>
		<tr>
			<td><b>Product Name:</b> ${product.product_name}</td>
			<td><b>Created by:</b> ${product.marketPlaceVendors.company_name}</td>
		</tr>
		<tr>
			<td><b>Version: </b> ${product.version}</td>
			<td><b>Submitted On:</b> <fmt:formatDate value="${product.introduced_on}" />
		</tr>
		<tr>
			<td>${product.product_description}</td>
		</tr>
		<tr>
			<td><b>Price </b></td>
			<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${product.product_price}" /></b></td>
		</tr>
		<tr>
			<td><button class="fancy-button" type="button" onclick="addToCart()">
					<b>Add To Cart</b>
				</button>
			<td><button class="fancy-button" type="button" onclick="window.location.href='/index/viewmarketplace'">
					<b>Back to Shopping</b>
				</button>
		</tr>
	</table>
	<div id="shoppingCart" class="modal">
		<sql:query var="result" dataSource="${ds}">
			SELECT * FROM shopping_cart_items WHERE reference = ${shoppingCart.reference};
		</sql:query>
		<div class="modal-content medium-modal fancy" style="border-style: solid; border-width: 2px; border-color: black;">
			<table class="tableview tableheading rjthird rjfourth rjfifth rjsixth"
				style="margin-left: auto; margin-right: auto; width: 100%">
				<caption>Shopping Cart</caption>
				<tr>
					<td colspan="7"><h3>Invoice #: ${shoppingCart.reference}</h3></td>
				</tr>
				<tr>
					<th>Product Name</th>
					<th>Qty</th>
					<th>Original Price</th>
					<th>Discount</th>
					<th>Price</th>
					<th>Tax</th>
					<th>&nbsp;</th>
				</tr>
				<c:set var="totalPrice" value="0" />
				<c:set var="totalTax" value="0" />
				<c:forEach var="item" items="${result.rows}">
					<tr>
						<td>${item.product_name}</td>
						<td>${item.qty}</td>
						<td><fmt:formatNumber type="currency" currencyCode="USD" value="${item.product_price * item.qty}" /></td>
						<td><fmt:formatNumber type="currency" currencyCode="USD" value="${item.product_discount}" /></td>
						<td><fmt:formatNumber type="currency" currencyCode="USD"
								value="${(item.product_price * item.qty) - item.product_discount}" /></td>
						<td><fmt:formatNumber type="currency" currencyCode="USD" value="${item.tax}" /></td>
						<td><button type="button" onclick="removeItem('${item.product_id}')" >Delete Item</button></td>
					</tr>
					<c:set var="totalPrice" value="${totalPrice + ((item.product_price * item.qty) - item.product_discount)}" />
					<c:set var="totalTax" value="${totalTax +  item.tax}" />
				</c:forEach>
				<tfoot class="tablefooter">
					<tr>
						<td colspan="3">&nbsp;</td>
						<td><b>Total Price -----></b></td>
						<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${totalPrice}" /></b></td>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
						<td><b>Total Tax --------></b></td>
						<td>&nbsp;</td>
						<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${totalTax}" /></b></td>
						<td>&nbsp;</td>
						
					</tr>
					<tr>
						<td colspan="7">&nbsp;</td>
					</tr>
				</tfoot>
			</table>
			<table>
				<tr>
					<td><button class="fancy-button" type="button" onclick="window.location.href='#'">
							<b>Check Out</b>
						</button></td>
					<td><button class="fancy-button" type="button" onclick="$('#shoppingCart').hide()">
							<b>Close Cart</b>
						</button></td>
				</tr>
			</table>

		</div>
	</div>
	<div id="scError" class="modal">
		<div class="modal-content small-modal fancy" style="border-style: solid; border-width: 2px; border-color: red;">
			<h2>You Already have this in your cart</h2>
			<button class="fancy-button" type="button" onclick="$('#scError').hide()">
				<b>OK</b>
			</button>
		</div>
	</div>
	<sf:hidden id="shpCrtId" path="reference" />
	<sf:hidden path="user_id" />
	<sf:hidden path="time_ordered" />
	<sf:hidden path="time_processed" />
	<sf:hidden path="time_delivered" />
	<sf:hidden path="payment_gateway" />
	<sf:hidden path="result" />
	<sf:hidden path="trans_result" />
	<input id="prd" type="hidden" value="${prdId}" />
</sf:form>
<script type="text/javascript">
	function addToCart() {
		var scId = $("#shpCrtId").val();
		var pId = $("#prd").val();

		$.getJSON(
				"/rest/addshoppingcartitem?cartID=" + scId + "&prdId=" + pId
						+ "&qty=1",
				function(data) {
					if (data.result === "ERROR") {
						$("#scError").show();
					} else {
						$("#menuBar").load(location.href + " #menuBar>*", "");
						$("#shoppingCart").load(
								location.href + " #shoppingCart>*", "");
						$("#shoppingCart").show();
					}
				}).fail(
				function(jqXHR, textStatus, errorThrown) {
					alert("error " + textStatus + "\n" + "incoming Text "
							+ jqXHR.responseText);
				});
	}
	
	function removeItem(pId) {
		var scId = $("#shpCrtId").val();
		
		$.getJSON(
				"/rest/deleteshoppingcartitem?cartID=" + scId + "&prdId=" + pId,
				function(data) {
					$("#menuBar").load(location.href + " #menuBar>*", "");
					$("#shoppingCart").load(location.href + " #shoppingCart>*", "");
					$("#shoppingCart").show();

				}).fail(
				function(jqXHR, textStatus, errorThrown) {
					alert("error " + textStatus + "\n" + "incoming Text "
							+ jqXHR.responseText);
				});
}
</script>