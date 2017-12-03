<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<script src="${cp}/resources/js/leftSidebar.js"></script>
<link rel="stylesheet" href="${cp}/resources/css/leftSidebar.css">
<!DOCTYPE html>
<ul class="nav flex-column" >
  <!--First Section: browse and radio-->
  <li class="nav-item" id="extra-padding">
    <a class="nav-link color-nav" href="javascript:displayLeftNavbarContent('browse')">Browse</a>
  </li>
  <!--Your Library Section-->
  <li>
    <p class="color-nav-header">YOUR LIBRARY</p>
    <ul class="nav nav-stacked flex-column">
      <li class="nav-item">
        <a class="nav-link color-nav" href="javascript:displayLeftNavbarContent('recently_played')">Recently Played</a>
      </li>
      <li class="nav-item">
        <a class="nav-link color-nav" href="javascript:displayLeftNavbarContent('songs')">Songs</a>
      </li>
      <li class="nav-item">
        <a class="nav-link color-nav" href="javascript:displayLeftNavbarContent('albums')">All Albums</a>
      </li>
      <li class="nav-item" id="extra-padding">
        <a class="nav-link color-nav" href="javascript:displayLeftNavbarContent('artists')">All Artists</a>
      </li>
    </ul>
  </li>
  <li class= "nav-item">
    <button  class="unstyle-buttons btn btn-info btn-lg " id="new_playlist_button" style="width: auto;" >
    <i class="fa fa-plus" aria-hidden="true" style="margin-right: 10%;"></i>New Playlist</button>
  </li>
  <div id="dialog" title="Create Playlist" style="display:none; background: #2f2f2f">
    <!-- create playlist -->
    <form:form action="createPlaylist" method="POST" enctype="multipart/form-data">
      <div class="w3-row w3-section">
        <div style="width:50px"><span id="dialog-font">Name</span></div>
        <div >
          <input class="w3-input w3-border" name="playlist_name" type="text" id="new_playlist_name" placeholder="Playlist name">
        </div>
      </div>
      <div class="w3-row">
      	<div class="w3-col m5 l3" id="dialog-image-box">
	      <div >
	        <div><span id="dialog-font">Image</span></div>
	        <div>
	          <img id="dialog-file-image" src="http://xn--80adh8aedqi8b8f.xn--p1ai/uploads/images/d/a/v/david_guetta_ft_nicki_minaj_afrojack_hey_mama.jpg">
	          <input class="w3-input" type="file" name="file" id="dialog-file-box">
	        </div>
	      </div>
	    </div>
	    <div class="w3-col m5 l8">
	      <!--<div class="w3-row w3-section">
	        <div class="w3-rest">
	          <img src ="http://xn--80adh8aedqi8b8f.xn--p1ai/uploads/images/d/a/v/david_guetta_ft_nicki_minaj_afrojack_hey_mama.jpg" class="img-responsive" width="200px" height="200px">
	        </div>
	        </div>-->
	      <div class="w3-row w3-section" id="dialog-textarea-header">
	        <div class="w3-col" style="width:30px"><span id="dialog-font">Description</span></div>
	        <div class="w3-col" style="width:30px; float:right; margin-right: 30px;"><span id="dialog-font">1/100</span></div>
	      </div>
	      <div class="w3-row w3-section">
	        <textarea id="dialog-textarea" class="w3-input w3-border" rows="8" cols="30" name="playlist_description" placeholder="Give your playlist a catchy description"></textarea>
	      </div>
	    </div>
      </div>
      <div class="w3-row " id="dialog-buttons">
        <div class="w3-third w3-container">
          <button id="dialog-button-cancel" onclick="event.preventDefault(); $('#dialog').dialog('close');" class="w3-button w3-block w3-section w3-ripple">Cancel</button>
        </div>
        
        <div class="w3-third w3-container" >
          <button id="dialog-button" type="submit" class="w3-button w3-block w3-section w3-green w3-ripple">Create</button>
        </div>
      </div>
    </form:form>
  </div>
  <!-- end of dialog popup box -->
  <!-- Playlist Section-->
  <li>
    <p class="color-nav-header">PLAYLISTS</p>
    <!--  
      <ul class="nav nav-stacked flex-column">
        <li class="nav-item">
          <a class="nav-link color-nav" href="#">Discover Weekly</a>
        </li>
      </ul>
      -->
    <c:if test="${not empty user_playlist}">
      <ul class="nav nav-stacked flex-column" >
        <c:forEach var="playlist" items="${user_playlist}">
          <li class="nav-item playlist-item">
            <a class="nav-link color-nav">${playlist.pname}</a>
            <span style="display:none;" class="playlist_id">${playlist.pid}</span>
          </li>
        </c:forEach>
      </ul>
    </c:if>
  </li>
</ul>