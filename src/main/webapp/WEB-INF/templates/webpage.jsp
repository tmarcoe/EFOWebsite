<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost/donzalma_peachys?useSSL=false"
	user="root" password="3xc7vbkjlv99" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
<link rel="icon" href="<c:url value='/images/favicon.png' />">
<link type="text/css" href="/css/webpage.css" rel="stylesheet" />
<link type="text/css" href="/css/jquery-ui.css" rel="stylesheet" />

<script type="text/javascript" src="/script/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/script/jquery-ui.js"></script>
<script type="text/javascript" src="/script/jquery.autocomplete.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script src="https://cdn.snipcart.com/scripts/2.0/snipcart.js" data-api-key="MDc1NzE4OTYtODU2Mi00NWJiLTg0YzctNzZiMGE5ODUyY2JkNjM1ODgwMTIzNDIwNTMxODQz" id="snipcart"></script>
<link href="https://cdn.snipcart.com/themes/2.0/base/snipcart.min.css" rel="stylesheet" type="text/css" />

<tiles:insertAttribute name="includes"></tiles:insertAttribute>

</head>

<body>
	<div class="content">
		<div class="toolbar">
			<tiles:insertAttribute name="toolbar"></tiles:insertAttribute>
		</div>
		<div class="pageBody">
			<tiles:insertAttribute name="content"></tiles:insertAttribute>
		</div>
		<tiles:insertAttribute name="footer" />
	</div>

	<script>
		function shutdown() {
			if (confirm("This will shutdown the entire system. Do you wish to proceed?") == true) {
				window.location.href = "/admin/shutdown";
			}
		}
	</script>
</body>
</html>