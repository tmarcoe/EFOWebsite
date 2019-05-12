<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/efo?useSSL=false"
	user="donzalma_admin" password="In_heaven3" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/calendar.css" />

<sf:form method="post" action="/user/savepassword" commandName="user">
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Login and Security</a></li>
			<sec:hasRole role="PERSONNEL">
				<li><a href="#tabs-2">Personnel</a></li>
			</sec:hasRole>
			<sec:hasRole role="BASIC">
				<li><a href="#tabs-3">Basic Application</a></li>
			</sec:hasRole>
			<sec:hasRole role="ACCOUNTING">
				<li><a href="#tabs-4">Accounting</a></li>
			</sec:hasRole>
			<sec:hasRole role="EVENTS">
				<li><a href="#tabs-5">Event Calendar</a></li>
			</sec:hasRole>
			<sec:hasRole role="REPORTS">
				<li><a href="#tabs-6">Reports</a></li>
			</sec:hasRole>
			<sec:hasRole role="ADMIN">
				<li><a href="#tabs-7">Admin</a></li>
			</sec:hasRole>

		</ul>
		<div id="tabs-1">
			<table class="menuTable menuTableSpace">
				<tr>
					<sec:isAuthenticated>
						<td><a href="#" onclick="openPopup()"><img class="tile" alt="Change Password"
							src="<c:url value='/images/password.png'/>"></a></td>
						<td><a href="/logout"><img class="tile" alt="Login" src="<c:url value='/images/logout.png'/>"></a></td>
					</sec:isAuthenticated>
					<sec:isNotAuthenticated>
						<td><a href="/login"><img class="tile" alt="Login" src="<c:url value='/images/login.png'/>"></a></td>
					</sec:isNotAuthenticated>
				</tr>
			</table>
		</div>
		<sec:hasRole role="PERSONNEL">
			<div id="tabs-2">
				<table class="menuTable menuTableSpace">
					<tr>
						<td><a href="/personnel/customerlist"><img class="tile" alt="Customer"
								src="<c:url value='/images/customer.png'/>"></a></td>
						<td><a href="/personnel/vendorlist"><img class="tile" alt="Vendor"
								src="<c:url value='/images/vendor.png'/>"></a></td>
						<td><a href="/personnel/employeelist"><img class="tile" alt="Employees"
								src="<c:url value='/images/employees.png'/>"></a></td>
						<td><a href="/personnel/investorlist"><img class="tile" alt="Investor"
								src="<c:url value='/images/investor.png'/>"></a></td>
					</tr>
				</table>
			</div>
		</sec:hasRole>
		<sec:hasRole role="BASIC">
			<div id="tabs-3">
				<table class="menuTable menuTableSpace">
					<tr>
						<td><a href="/basic/profileslist"><img class="tile" alt="Profiles"
								src="<c:url value='/images/profile.png'/>"></a></td>
						<td><a href="/basic/newoverheadtransaction"><img class="tile" alt="New Overhead"
								src="<c:url value='/images/overhead.png'/>"></a></td>
						<td><a href="/basic/newretailexpensetransaction"><img class="tile" alt="New Retail Expense"
								src="<c:url value='/images/retailexpense.png'/>"></a></td>
						<td><a href="/basic/newcapitalexpensetransaction"><img class="tile" alt="New Capital Expense"
								src="<c:url value='/images/capitalexpense.png'/>"></a></td>
					</tr>
					<tr>
						<td><a href="#" onclick="inputDate('/basic/transactionslist')"><img class="tile" alt="Transactions"
								src="<c:url value='/images/transactions.png'/>"></a></td>
						<td><a href="/basic/newreceiveretailtransaction"><img class="tile" alt="New Retail"
								src="<c:url value='/images/retail.png'/>"></a></td>
						<td><a href="/basic/newloantransaction"><img class="tile" alt="New Loan"
								src="<c:url value='/images/loan.png'/>"></a></td>
						<td><a href="/basic/newinvestortransaction"><img class="tile" alt="New Investor"
								src="<c:url value='/images/stock.png'/>"></a></td>
					</tr>
				</table>
			</div>
		</sec:hasRole>
		<sec:hasRole role="ACCOUNTING">
			<div id="tabs-4">
				<table class="menuTable menuTableSpace">
					<tr>
						<td><a href="#" onclick="inputDate('/accounting/ledgerlist')"><img class="tile" alt="General Ledger"
								src="<c:url value='/images/general.png'/>"></a></td>
						<td><a href="/accounting/accountslist"><img class="tile" alt="Chart Of Accounts"
								src="<c:url value='/images/accounts.png'/>"></a></td>
					</tr>
				</table>
			</div>
		</sec:hasRole>
		<sec:hasRole role="EVENTS">
			<div id="tabs-5">
				<table class="calendarTitle">
					<tr>
						<td><button class="fancy-button" type="button" onclick="reverse()" style="font-size: 14px;">
								<b>&lt;&lt;</b>
							</button></td>
						<td><div id="month"></div></td>
						<td><button class="fancy-button" type="button" onclick="forward()" style="font-size: 14px;">
								<b>&gt;&gt;</b>
							</button></td>
					</tr>
				</table>
				<table class="calendarTable">
					<thead>
						<tr>
							<th>Sun</th>
							<th>Mon</th>
							<th>Tue</th>
							<th>Wed</th>
							<th>Thu</th>
							<th>Fri</th>
							<th>Sat</th>
						</tr>
					</thead>
					<c:forEach var="i" begin="1" end="42">
						<td><input id="d${i}" type="hidden" /> <input id="m${i}" type="hidden" /> <input id="y${i}" type="hidden" />
							<div id="div${i}" class="calendarContent" onclick="getEvents('#y${i}', '#m${i}', '#d${i}')"></div></td>
						<c:if test="${i % 7 == 0}">
							<tr></tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</sec:hasRole>
		<sec:hasRole role="REPORTS">
			<div id="tabs-6">
				<table class="menuTable menuTableSpace">
					<tr>
						<td><a href="/reports/malefemale"><img class="tile" alt="Demographics"
								src="<c:url value='/images/gender.png'/>"></a></td>
						<td><a href="#" onclick="inputDate('/accounting/printgeneralledger')"><img class="tile"
								alt="Print General Ledger" src="<c:url value='/images/print-ledger.png'/>"></a></td>
						<td><a href="/accounting/printaccounts"><img class="tile" alt="Print Chart Of Accounts"
								src="<c:url value='/images/print-accounts.png'/>"></a></td>
					<td><a href="#" onclick="inputDate('/reports/revenuereport')"><img class="tile"
								alt="Revenue Report" src="<c:url value='/images/revenues.png'/>"></a></td>
					
					</tr>
				</table>
			</div>
		</sec:hasRole>
		<sec:hasRole role="ADMIN">
			<div id="tabs-7">
				<table class="menuTable menuTableSpace">
					<tr>
						<td><a href="#" onclick="showWarning()"><img class="tile" alt="Print Chart Of Accounts"
								src="<c:url value='/images/shutdown.png'/>"></a></td>
					<td><a href="/admin/listroles"><img class="tile" alt="Roles"
							src="<c:url value='/images/security-roles.png'/>"></a></td>
					</tr>
				</table>
			</div>
		</sec:hasRole>
	</div>
	<div id="sPass" class="modal">
		<div class="modal-content small-modal fancy">
			<h2>Change your password</h2>
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<th>Password</th>
					<th>Repeat Password</th>
				</tr>
				<tr>
					<td><sf:password id="password" name="password" path="password" class="control fancy" /></td>
					<td><input class="fancy" id="confirmpass" class="control fancy" name="confirmpass" type="password" /></td>
				</tr>
				<tr>
					<td><div id="pbar">
							<label id="pLabel"></label>
							<div id="pStrength"></div>
						</div>&nbsp;</td>
					<td><div id="matchpass"></div>&nbsp;</td>
				</tr>
				<tr>
					<td><button class="fancy-button" type="submit">
							<b>Save</b>
						</button></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="getPeriod" class="modal">
		<div class="modal-content small-modal fancy">
			<h2>Enter the Period</h2>
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<th>Start Date</th>
					<th>End Date</th>
				</tr>
				<tr>
					<td><input class="fancy" id="stDate" type="date" /></td>
					<td><input class="fancy" id="endDate" type="date" /></td>
				</tr>
				<tr>
					<td><button class="fancy-button" type="button" onclick="setDate()">
							<b>View Period</b>
						</button></td>
					<td><button class="fancy-button" type="button" onclick="cancelLedger()">
							<b>Cancel</b>
						</button></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="showEvents" class="modal">
		<div id="eventList" class="modal-content small-modal fancy" onclick="closeShowEvents()" style="text-align: left;"></div>
	</div>
	<div id="warning" class="modal">
		<div class="modal-content small-modal fancy">
			<h3>This will shutdown the entire application!</h3>
			<h3>Are you sure you wish to continue?</h3>
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<td><button class="fancy-button" type="button" onclick="window.location.href='/admin/shutdown'" ><b>Shutdown</b></button></td>
					<td><button class="fancy-button" type="button" onclick="$('#warning').hide()" ><b>Cancel</b></button></td>
				</tr>
			</table>
		</div>
	</div>
	<input id="destination" type="hidden" />
	<input id="calMonth" type="hidden" value="${calMonth}" />
	<input id="calYear" type="hidden" value="${calYear}" />


</sf:form>
<script type="text/javascript">
	$(document).ready(function() {
		getCalendar();
	});

	function getCalendar() {
		var mnth = $("#calMonth").val();
		var yr = $("#calYear").val();
		$
				.getJSON(
						"/rest/getcalendar?month=" + mnth + "&year=" + yr,
						function(data) {
							$("#calMonth").val(data.calMonth);
							$("#calYear").val(data.calYear);
							populateCalendar(data.calendar, data.calMonth,
									data.calYear);
						}).fail(
						function(jqXHR, textStatus, errorThrown) {
							alert("error " + textStatus + "\n"
									+ "incoming Text " + jqXHR.responseText);
						});

	}

	function getEvents(yearInput, monthInput, dayInput) {
		var yr = $(yearInput).val();
		var mnth = $(monthInput).val();
		var day = $(dayInput).val();

		$
				.getJSON(
						"/rest/getevents?year=" + yr + "&month=" + mnth
								+ "&day=" + day, function(data) {
							if (data.length > 0) {
								eventPopup(data);
							}
						}).fail(
						function(jqXHR, textStatus, errorThrown) {
							alert("error " + textStatus + "\n"
									+ "incoming Text " + jqXHR.responseText);
						});
	}
	function eventPopup(data) {
		$("#showEvents").show()
		var content = "<ul>";

		for (i = 0; i < data.length; i++) {
			if (data[i].completed == false) {

				content = content + "<li><a href='" + data[i].link + "'>"
						+ data[i].name + "</a></li>";
			} else {
				content = content + "<li><del>" + data[i].name + "</del></li>";
			}
		}
		content = content + "<br><b>Click here to close</b></ul>";
		$("#eventList").html(content);
	}

	function closeShowEvents() {
		$("#showEvents").hide();
	}

	function populateCalendar(calArray, month, year) {
		for (i = 0; i < calArray.length; i++) {
			var cell = "#div" + (i + 1);
			var cellId = "div" + (i + 1);
			var yearStore = "#y" + (i + 1);
			var monthStore = "#m" + (i + 1);
			var dayStore = "#d" + (i + 1);
			var content = calArray[i].day;
			var yr = calArray[i].year;
			var mn = calArray[i].month;
			var dy = calArray[i].day;
			$(cell).removeClass("today thisMonth otherMonth");
			var thisMonth = false;
			if (calArray[i].isToday == true && calArray[i].month == month) {
				$(cell).addClass("today");
				thisMonth = true;
			} else if (calArray[i].month == month && calArray[i].year == year) {
				thisMonth = true;
				$(cell).addClass("thisMonth");
			} else {
				thisMonth = false;
				$(cell).addClass("otherMonth");
			}
			if (thisMonth == true && parseInt(calArray[i].num_events) > 0) {
				var evn = "";
				if (parseInt(calArray[i].num_events) == 1) {
					evn = "Event"
				} else {
					evn = "Events"
				}
				content = content + "<br><b>" + calArray[i].num_events + " "
						+ evn + "</b>";
			}
			$(cell).html(content);
			$(yearStore).val(yr);
			$(monthStore).val(mn);
			$(dayStore).val(dy);
		}
		displayMonthYear();
	}

	function displayMonthYear() {

		var m = $("#calMonth").val();
		var y = $("#calYear").val();
		var mnth = [ "January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November",
				"December" ];

		$("#month").text(mnth[m - 1] + ", " + y);
	}
	function disableButton(id) {
		document.getElementById(id).disabled = true;
	}
	function inputDate(dest) {
		$("#destination").val(dest);
		var modal = document.getElementById('getPeriod');
		modal.style.display = "block";
	}
	function cancelLedger() {
		var modal = document.getElementById('getPeriod');
		modal.style.display = "none";
	}
	function setDate() {
		var dest = $("#destination").val();
		var from = document.getElementById('stDate').value;
		var to = document.getElementById('endDate').value;
		if (from.length > 0 && to.length > 0) {
			window.location.href = dest + "/from/" + from + "/to/" + to;
		}
	}
	function openPopup() {
		var modal = document.getElementById('sPass');
		modal.style.display = "block"
	}

	function closePopup() {
		var modal = document.getElementById('sPass');
		modal.style.display = "none";
	}
	$(function() {
		$("#tabs").tabs({
			classes : {
				"ui-tabs" : "ui-corner-all"
			}
		});
	});
	function dummyFunction() {

	}

	function forward() {
		var m = parseInt($("#calMonth").val());
		var y = parseInt($("#calYear").val());

		m = m + 1;
		if (m == 13) {
			m = 1;
			y = y + 1;
		}
		$("#calMonth").val(m);
		$("#calYear").val(y);
		getCalendar();
	}

	function reverse() {
		var m = parseInt($("#calMonth").val());
		var y = parseInt($("#calYear").val());

		m = m - 1;
		if (m == 0) {
			m = 12;
			y = y - 1;
		}
		$("#calMonth").val(m);
		$("#calYear").val(y);
		getCalendar();
	}
	function showWarning() {
		$("#warning").show();
	}
</script>
<c:if test="${user.isTemp_pw() == true}">
	<script>
		openPopup()
	</script>
</c:if>

