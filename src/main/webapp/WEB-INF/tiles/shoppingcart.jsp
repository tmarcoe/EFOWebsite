<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<script src="https://js.braintreegateway.com/v2/braintree.js"></script>
<script type="text/javascript" src="/script/demo.js"></script>
<script type="text/javascript" src="/script/jquery.lettering-0.6.1.min.js"></script>
<script type="text/javascript" src="/script/response.js"></script>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/efoweb?useSSL=false" user="root"
	password="3xc7vbkjlv99" />

<sf:form id="shpCartForm" method="post" action="/user/processorder" modelAttribute="shoppingCart">

<div id="shoppingCart" >
	<sql:query var="result" dataSource="${ds}">
		SELECT * FROM shopping_cart_items WHERE reference = ${shoppingCart.reference};
	</sql:query>

	<table class="fancy tableview tableshadow tableheading ljfirst rjthird rjfourth rjfifth rjsixth rjseventh"
		style="margin-left: auto; margin-right: auto; width: 50%">
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
				<td><fmt:formatNumber type="currency" currencyCode="USD" value="${item.product_tax}" /></td>
				<td><button type="button" onclick="removeItem('${item.product_id}')">Delete Item</button></td>
			</tr>
			<c:set var="totalPrice" value="${totalPrice + ((item.product_price * item.qty) - item.product_discount)}" />
			<c:set var="totalTax" value="${totalTax +  item.product_tax}" />
			<input id="ttl" type="hidden" value="${totalPrice}" />
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td colspan="3">&nbsp;</td>
				<td><b>Total Price -----></b></td>
				<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${totalPrice}" /></b></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
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
			<tr>
				<td><button class="fancy-button" type="button" onclick="checkOut()">
						<b>Check Out</b>
					</button></td>
				<td colspan="5">&nbsp;</td>
				<td><button class="fancy-button" type="button" onclick="window.history.back()">
						<b>Close Cart</b>
					</button></td>

			</tr>
		</tfoot>
	</table>
	</div>
	<div id="payment" class="modal" style="padding-top: 1.5em;">
		<div class="bt-drop-in-wrapper modal-content medium-modal fancy">
			<div id="bt-dropin"></div>
			<sf:button id="sbutton" class="fancy-button" type="submit" hidden="true">
				<b>Submit</b>
			</sf:button>
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
	<input id="cToken" type="hidden" value="${clientToken}" />
	<input id="pr" type="hidden" value="${principal}" />
</sf:form>
<script type="text/javascript">
function removeItem(pId) {
	
	var scId = $("#shpCrtId").val();

	$.getJSON("/rest/deleteshoppingcartitem?cartID=" + scId
							+ "&prdId=" + pId,
					function(data) {
						$("#wait").show();
						var uName = $("#pr").val();
						countItems(uName);
						$("#shoppingCart").load( location.href + " #shoppingCart>*", "");
					}).fail(
					function(jqXHR, textStatus, errorThrown) {
						alert("error " + textStatus + "\n"
								+ "incoming Text " + jqXHR.responseText);
					});
}

	function checkOut() {
		var ttl = $("#ttl").val();
		if (ttl > 0) {
			$("#payment").show();

			var checkout = new Demo({
				formID : 'shpCartForm'
			});
			var input = document.getElementById("cToken");
			var client_token = input.value;

			braintree.setup(client_token, "dropin", {
				container : "bt-dropin",
				onReady : function(event) {
					document.getElementById("sbutton").hidden = false;
				}
			});
		} else {
			$("#shpCartForm").submit();
		}
	}
</script>