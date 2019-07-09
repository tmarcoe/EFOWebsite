<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<div class="scrollArea">
	<table class="tableview tableshadow tableborder spread-out rjthird">
		<tr>
			<th>Name</th>
			<th>Company Name</th>
			<th>Sales Total</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach var="item" items="${vendorList}">
			<tr>
				<td>${item.name}</td>
				<td>${item.company_name}</td>
				<td><fmt:formatNumber type="currency" currencyCode="USD" value="${item.sales_total}" /></td>
				<td><c:choose>
						<c:when test="${item.sales_total > 0 && item.marketPlaceProducts.size() > 0}">
							<button type="button" onclick="window.location.href='/admin/individualreport?id=${item.reference}'">Print
								Report</button>
						</c:when>
						<c:otherwise>
							<button type="button" disabled>Print Report</button>
						</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>

	</table>
</div>