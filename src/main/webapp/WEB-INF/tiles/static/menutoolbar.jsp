<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sec:getPrincipal />
<script type="text/javascript">
function countItems(uName) {
	if (uName == "anonymousUser") {
		$("#scCount").text("(0)");
	}else{
		
		var cnt = 0;
		$.getJSON("/rest/shoppingcartcount?username=" + uName,
				function(data) {
						$("#scCount").text("(" + data.count + ")");
				}).fail(
					function(jqXHR, textStatus, errorThrown) {
						alert("error " + textStatus + "\n" + "incoming Text "
						+ jqXHR.responseText);
				});
	}
}

</script>
<div id="menuBar" class="nav-bar">
	<script type="text/javascript">countItems('${principal}')</script>
	
	<sec:isAuthenticated>
		<c:set var="scLink" value="/user/shoppingcart"/>
	</sec:isAuthenticated>
		
	<sec:isNotAuthenticated>
		<c:set var="scLink" value="#" />
	</sec:isNotAuthenticated>
	
	<span style="position: absolute; top: 0; right: 100px; color: white; font-size: 24px;"> 
		<img alt="cart" src="<c:url value="/images/shopping-cart-icon.png" />"><a id="scCount" href="${scLink}" style="text-decoration: none; color: white;">(0)</a></span>
	<div class="container">
		<ul class="nav">
			<li><button class="dropbtn" onclick="window.location.href = '/index/'">Home</button></li>
			<sec:isAuthenticated>
				<li><button class="dropbtn" onclick="window.location.href = '/logout'">Logout</button></li>
				<li><button class="dropbtn" onclick="window.location.href = '/user/edituserprofile'">Edit Profile</button></li>
				<li><button class="dropbtn" onclick="window.location.href = '/user/changepassword'">Change Password</button></li>
			</sec:isAuthenticated>
			<sec:isNotAuthenticated>
				<li><button class="dropbtn" onclick="window.location.href = '/login'">Login</button></li>
				<li><button class="dropbtn" onclick="window.location.href = '/index/createuserprofile'">Sign Up</button></li>
			</sec:isNotAuthenticated>
			<sec:hasRole role="ADMIN">
				<li><button class="dropbtn" onclick="window.location.href = '/settings'">To EFO</button></li>
			</sec:hasRole>
			<li><div class="dropdown">
					<button class="dropbtn">Blogs</button>
					<div class="dropdown-content">
						<a href="/index/viewblog">View Blogs</a>
						<sec:hasRole role="ADMIN">
							<a href="/admin/createblog">Create a Blog</a>
							<a href="/admin/manageblogs">Manage Blogs</a>
						</sec:hasRole>
					</div>
				</div></li>
			<li><div class="dropdown">
					<button class="dropbtn">Forums</button>
					<div class="dropdown-content">
						<a href="/index/viewPosts">View Posts</a>
						<sec:isAuthenticated>
							<a href="/user/createpost">Create a post</a>
						</sec:isAuthenticated>
					</div>
				</div></li>
			<li><div class="dropdown">
					<button class="dropbtn">Products</button>
					<div class="dropdown-content">
						<a href="/index/efo">Electronic Financial Officer</a> 
						<a href="/index/ftl">Flexible Transaction Language</a> 
						<a href="/index/ftlide">FTL IDE</a>
					</div>
				</div></li>
			<li><button class="dropbtn" onclick="window.location.href = '/index/documentation'">Documentation</button></li>
			<sec:isAuthenticated>
				<li><div class="dropdown">
						<button class="dropbtn" title="We want you to compete with us">EFO Marketplace</button>
						<div class="dropdown-content">
							<sec:isAuthenticated>
								<a href="/user/marketplaceregister">Add A Product</a>
							</sec:isAuthenticated>
							<sec:hasRole role="ADMIN">
								<a href="/admin/managempvendors">Vendor Reports</a>
							</sec:hasRole>
							<a href="/index/viewmarketplace">Explore the Marketplace</a>
						</div>
					</div></li>
			</sec:isAuthenticated>
			<sec:hasRole role="ADMIN">
				<li><div class="dropdown">
						<button class="dropbtn">EFO Product Managment</button>
						<div class="dropdown-content">
							<a href="/admin/newproduct">New Product</a> 
							<a href="/admin/manageproducts">Manage Products</a>
						</div>
					</div></li>
			</sec:hasRole>
			<li><button class="dropbtn" onclick="window.location.href = '/index/contactus'">Contact Us</button></li>
			<li><button class="dropbtn" onclick="window.location.href = '/index/aboutus'">About Us</button></li>
		</ul>
	</div>
</div>
