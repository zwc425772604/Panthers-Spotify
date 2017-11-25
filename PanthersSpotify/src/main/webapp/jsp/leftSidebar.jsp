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
<script>
  $(document).ready(function(){
      //hide all other containers in <div id= 'middle-content'> beside except overview_container
      $("#overview_container").siblings().hide();
     
    });
</script>
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
        <a class="nav-link color-nav" href="javascript:displayLeftNavbarContent('albums')">Albums</a>
      </li>
      <li class="nav-item">
        <a class="nav-link color-nav" href="javascript:displayLeftNavbarContent('artists')">Artists</a>
      </li>
      <li class="nav-item" id="extra-padding">
        <a class="nav-link color-nav" href="javascript:displayLeftNavbarContent('local_files')">Local Files</a>
      </li>
    </ul>
  </li>
  <li class= "nav-item">
    <button  class="unstyle-buttons btn btn-info btn-lg " id="new_playlist_button" style="width: auto;" >
    <i class="fa fa-plus" aria-hidden="true" style="margin-right: 10%;"></i>New Playlist</button>
  </li>
  <div id="dialog" title="Create Playlist" style="display:none; color:black;">
    <!-- create playlist -->
    <form:form action="createPlaylist" method="POST" enctype="multipart/form-data" class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
      <div class="w3-row w3-section">
        <div class="w3-col" style="width:50px"><span style="font-size: 14px">Name</span></div>
        <div class="w3-rest">
          <input class="w3-input w3-border" name="playlist_name" type="text" id="new_playlist_name" placeholder="Playlist name">
        </div>
      </div>
      <div class="w3-row w3-section">
        <div class="w3-col" style="width:50px"><span style="font-size: 14px">Image</span></div>
        <div class="w3-rest">
          <input class="w3-input w3-border" type="file" name="file">
        </div>
      </div>
      <!--<div class="w3-row w3-section">
        <div class="w3-rest">
          <img src ="https://pbs.twimg.com/profile_images/1642161716/music_logo.png" class="img-responsive" width="200px" height="200px">
        </div>
        </div>-->
      <div class="w3-row w3-section">
        <div class="w3-col" style="width:30px"><span style="font-size: 1.2em">Description</span></div>
        <div class="w3-col" style="width:30px; float:right; margin-right: 30px;"><span style="font-size: 1.2em">1/100</span></div>
      </div>
      <div class="w3-row w3-section">
        <textarea class="w3-input w3-border" rows="4" cols="50" name="playlist_description" placeholder="Give your playlist a catchy description">
        </textarea>
      </div>
      <div class="w3-row w3-section">
        <div class="w3-third w3-container">
          <button onclick="event.preventDefault(); $('#dialog').dialog('close');" class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
        </div>
        <div class="w3-third w3-container">
        </div>
        <div class="w3-third w3-container">
          <button type="submit" class="w3-button w3-block w3-section w3-blue w3-ripple">Create</button>
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
<script>
  w3.includeHTML();
  function displayContainer(name)
  {
    var n = "#" + name;
    $(n).siblings().hide();
    $(n).show();
  }
</script>