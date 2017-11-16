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
	              <div class="right-col-friends-name">
	              	${friend.uname}	              	
	              	<span style="display:none;" class="friends-email">${friend.email}</span>
	              	
	              </div>
	              <div class="right-col-song-name">PLACEHOLDER</div>
	              <div class="right-col-singer">PLACEHOLDER</div>
	              
	              
	            </div>
	          </div>
	        </c:forEach>
          </c:if>
          <button id="addFriendButton" class="w3-button right-col-button">FIND FRIENDS</button>
          
          <div id="addFriendDialog" title="Add Friend" style="display:none;">
                          
              <form:form action="addFriend" method="POST" enctype="multipart/form-data" class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
                <div class="w3-row w3-section">
                  <div class="w3-col" style="width:70px"><span style="font-size: 12px">Username</span></div>
                  <div class="w3-rest">
                    <input class="w3-input w3-border" name="addFriendUsername" type="text" id="addFriendUsername" placeholder="Username of your friend">
                  </div>
                </div>            
                <div class="w3-row w3-section">
                  <div class="w3-third w3-container">
                    <button onclick="event.preventDefault(); $('#addFriendDialog').dialog('close');" class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
                  </div>
                  <div class="w3-third w3-container">
                  </div>
                  <div class="w3-third w3-container">
                    <button type="submit" class="w3-button w3-block w3-section w3-blue w3-ripple">Add</button>
                  </div>
                </div>
	            </form:form>
	           </div>