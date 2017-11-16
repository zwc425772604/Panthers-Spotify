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
<div class="right-col-top">
            <h2 id="right-col-topic">Friend activity</h2>
            <div class="line"></div>
          </div>
          <c:if test="${not empty userFriendList }">
          	<c:forEach var="friend" items="${userFriendList}">
	          <div class="right-col-section row">
	            <div><img src="http://weclipart.com/gimg/0A3C841B9FA4F2C6/13099629981030824019profile.svg.hi.png" class="rounded-circle right-col-image" alt="Generic placeholder thumbnail"></div>
	            <div class="right-col-desp">
	              <div class="right-col-friends-name">${friend.uname}</div>
	              <div class="right-col-song-name">PLACEHOLDER</div>
	              <div class="right-col-singer">PLACEHOLDER</div>
	            </div>
	          </div>
	        </c:forEach>
          </c:if>
          <button onclick="" class="w3-button right-col-button">FIND FRIENDS</button>