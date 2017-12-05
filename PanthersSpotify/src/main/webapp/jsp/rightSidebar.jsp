<%--
  Document   : browse
  Created on : Oct 19, 2017, 8:55:35 PM
  Author     : Weichao ZHao
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<script src="${cp}/resources/js/rightSidebar.js" /></script>
<div class="right-col-top">
  <h2 id="right-col-topic">Friend activity</h2>
  <div class="line"></div>
</div>
<div id = "userFriendsList">
	
</div>

<button id="addFriendButton" class="w3-button right-col-button">FIND FRIENDS</button>
<div id="addFriendDialog" title="Add Friend" style="display:none;">
 	<form id="findFriendForm">
	  <div class="w3-row w3-section">
	    <div class="w3-col" style="width:70px"><span style="font-size: 12px">Username</span></div>
	    <div class="w3-rest">
	      <input class="w3-input w3-border" name="addFriendUserEmail" type="email" id="addFriendUserEmail" placeholder="Email of your friend">
	    </div>
	  </div>
	  <div class="w3-row w3-section">
	    <div class="w3-third w3-container">
	      <button onclick="event.preventDefault(); $('#addFriendDialog').dialog('close');" class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
	    </div>
	    <div class="w3-third w3-container">
	    	<p id="findFriendMessage"></p>
	    </div>
	    <div class="w3-third w3-container">
	      <input id="findFriend" type="submit" class="w3-button w3-block w3-section w3-blue w3-ripple">Find</button>
	    </div>
	  </div>
	 </form>
</div>