<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Page - Panthers Spotify</title>
    <link rel="stylesheet" href="${cp}/resources/css/userPage.css">
    <script src="${cp}/resources/js/userPage.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  </head>
  <div id ="userPage-main">
    <div class="suggestion-container-top" >
      <div class="user_image_box" style="width: 20%">
         <c:choose>
           <c:when test="${not empty selectedFriend.photoUrl}">
             <img width=100% height=width src="${cp}/resources/data${selectedFriend.photoUrl}"  class="img-rounded" alt="Generic placeholder thumbnail">
           </c:when>
           <c:otherwise>
             <img  width=100% height=width  src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg"  class="img-rounded" alt="Generic placeholder thumbnail">
           </c:otherwise>
         </c:choose>          
      </div>
      <div class="userInfoBoxes" style="width:70%">
        <div id ="userInfo" style="margin-top: 4%; margin-left:5%;">
          <h5> User Page </h5>
          <p style="font-size: 1.8em;">
            <c:out value="${selectedFriend.userName}"></c:out>
          </p>
          <div class="col-md-5" style="display:inline;">
            <c:if test="${selectedFriend.email != user.email}">
              <button class="w3-button w3-round-xxlarge formButton AddFriendButton" style="width:auto" >
                <span id="friendStatus" >
                  <c:choose>
                    <c:when test="${fn:contains(userFriendList,selectedFriend)}">
                      Delete Friend
                    </c:when>
                    <c:otherwise>
                      Add Friend
                    </c:otherwise>
                  </c:choose>
                </span>
                <span id="friendEmail" style="display:none;">
                  <c:out value="${selectedFriend.email}"></c:out>
                </span>
              </button>
            </c:if>
          </div>
        </div>
      </div>
    </div>
    
  
  </div>
 
  <div id="song-collection">
      <h2>Recently Play Songs</h2>
      <div class="table-responsive">
        <table class="table" id="song-table">
          <thead>
            <tr>
              <th id="th-play-button"></th>
              <!-- play/pause button -->
              <th id="th-song-title">
                <p> TITLE </p>
              </th>
              <th id="th-album">ALBUM</th>
              <th id="th-artist">ARTIST</th>
              <th id="th-date">
                <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
              </th>
              
            </tr>
          </thead>
          <tbody>
          
          </tbody>
        </table>
      </div>
    </div>
          
<!--    <div id="friend-playlist-collection">
      <h2>User's Playlist</h2>
      <div class="table-responsive">
        <table class="table" id="playlist-table">
          <thead>
            <tr>
                  
              <th id="th-play-button"></th>
              <th id="th-song-title">Name </th>
              <th id="th-album"># of Songs</th>
              <th id="th-artist"># of Followers</th>
              <th id="th-date">
                <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
              </th>       
            </tr>
          </thead>
          <tbody>
          
          </tbody>
        </table>
      </div>
    </div>-->
          
    <div id="playlist-collection">
      <h2>User's Playlist</h2>
      <div class="table-responsive">
        <table class="table" id="playlist-table">
          <thead>
            <tr>
              <th id="th-play-button"></th>
              <!-- play/pause button -->
              <th id="th-song-title">
                <p> Name </p>
              </th>
              <th id="th-album"># of Songs</th>
              <th id="th-artist"># of Followers</th>
              <th id="th-date">
                <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
              </th>
              
            </tr>
          </thead>
          <tbody>
          
          </tbody>
        </table>
      </div>
    </div>
    
