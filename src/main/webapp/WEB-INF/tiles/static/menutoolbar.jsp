<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<div class="nav-bar">
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
				<li><button class="dropbtn" onclick="window.location.href = '/settings'">Settings</button></li>
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
						<a href="/index/efo">Electronic Financial Officer</a> <a href="/index/ftl">Flexible Transaction Language</a> <a
							href="/index/ftlide">FTL IDE</a>
					</div>
				</div></li>
			<li><button class="dropbtn" onclick="window.location.href = '/index/documentation'">Documentation</button></li>
			<sec:isAuthenticated>
				<li><div class="dropdown">
						<button class="dropbtn">EFO Marketplace</button>
						<div class="dropdown-content">
							<a href="/user/marketplaceregister">Register To Sell</a> 
							<a href="#">Add a Product</a> 
							<a href="#">Shop</a>
						</div>
					</div></li>
			</sec:isAuthenticated>
			<li></li>
			<li><button class="dropbtn" onclick="window.location.href = '/index/contactus'">Contact Us</button></li>
			<li><button class="dropbtn" onclick="window.location.href = '/index/aboutus'">About Us</button></li>
		</ul>
	</div>
</div>