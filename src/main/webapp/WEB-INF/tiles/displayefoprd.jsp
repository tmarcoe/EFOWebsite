<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<sec:getPrincipal />

<script src="https://js.braintreegateway.com/v2/braintree.js"></script>
<script type="text/javascript" src="/script/demo.js"></script>
<script type="text/javascript" src="/script/jquery.lettering-0.6.1.min.js"></script>
<script type="text/javascript" src="/script/response.js"></script>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/efoweb?useSSL=false" user="root"
	password="3xc7vbkjlv99" />

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<fmt:formatNumber var="price" type="currency" currencySymbol="" value="${product.product_price}" />

<sf:form id="shpCartForm" method="post" action="/user/processorder" modelAttribute="shoppingCart">
	<button class="fancy-button" type="button" onclick="$('#shoppingCart').show()"
		style="position: absolute; top: 10px; left: 30px">
		<b>Show Cart</b>
	</button>
	<table class="fancy-table tableshadow rjsecond" style="width: 50%;">
		<c:set var="prc" value="${product.product_price - product.product_discount}"/>
		<tr>
			<td><b>Product Name:</b> ${product.product_name}</td>
		<tr>
			<td>${product.product_description}</td>
		</tr>
		<tr>
			<td><b>Price </b></td>
			<td><b><fmt:formatNumber type="currency" currencyCode="USD" value="${prc}" /></b></td>
		</tr>
		<tr>
			<td><button class="fancy-button" type="button" onclick="addToCart()">
					<b>Add To Cart</b>
				</button>
			<td><button class="fancy-button" type="button" onclick="window.location.href='/index/introduction-a'">
					<b>Back</b>
				</button>
		</tr>
	</table>
	<div id="payment" class="modal" style="padding-top: 1.5em;">
		<div class="bt-drop-in-wrapper modal-content medium-modal fancy">
			<div id="bt-dropin"></div>
			<sf:button id="sbutton" class="fancy-button" type="submit" hidden="true">
				<b>Submit</b>
			</sf:button>
		</div>
	</div>

	<div id="shoppingCart" class="modal">
		<div id="wait" class="modal" style="margin: 0 auto;">
			<img class="spinner" alt="wait" src="<c:url value="/images/spinner.gif" />" width="100px">
		</div>
		<sql:query var="result" dataSource="${ds}">
			SELECT * FROM shopping_cart_items WHERE reference = ${shoppingCart.reference};
		</sql:query>
		<div class="modal-content medium-large-modal fancy" style="border-style: solid; border-width: 2px; border-color: black;">
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
					<td><button class="fancy-button" type="button" onclick="checkOut()">
							<b>Check Out</b>
						</button></td>
					<td><button class="fancy-button" type="button" onclick="$('#shoppingCart').hide()">
							<b>close Cart</b>
						</button></td>
				</tr>
			</table>
			<script>
				$("#wait").hide()
			</script>
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
	<input id="cToken" type="hidden" value="${clientToken}" />
	<input id="pr" type="hidden" value="${principal}" />
	
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
						$("#wait").show();
						var uName = $("#pr").val();
						countItems(uName);
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

		$
				.getJSON(
						"/rest/deleteshoppingcartitem?cartID=" + scId
								+ "&prdId=" + pId,
						function(data) {
							$("#wait").show();
							var uName = $("#pr").val();
							countItems(uName);
							$("#shoppingCart").load(
									location.href + " #shoppingCart>*", "");
							$("#shoppingCart").show();

						}).fail(
						function(jqXHR, textStatus, errorThrown) {
							alert("error " + textStatus + "\n"
									+ "incoming Text " + jqXHR.responseText);
						});
	}

	function checkOut() {
		var ttl = $("#ttl").val();
		if (ttl > 0) {
			$("#shoppingCart").hide();
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