<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONException"%>
<%
	JSONObject queue = (JSONObject)session.getAttribute("queueJSON");
	JSONObject preSong = new JSONObject();
	JSONObject nowSong = new JSONObject();
	JSONArray nextSong = new JSONArray();
	if(queue.length()!=0){
		preSong = queue.getJSONObject("previous");
		nowSong = queue.getJSONObject("nowPlay");
		nextSong = queue.getJSONArray("nextUp");
	}
	boolean hasPre = true;
	if(preSong==null || preSong.length()==0){
		hasPre = false;
	}
	boolean hasNowPlay = true;
	if(nowSong==null || nowSong.length()==0){
		hasNowPlay = false;
	}
	boolean hasNextUp = true;
	if(nextSong==null || nextSong.length()==0){
		hasNextUp = false;
	}
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Panthers Spotify</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--CSS ref-->
    <link rel="stylesheet" href="${cp}/resources/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <!--js ref-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="
    <c:url value="/resources/js/bootstrap.min.js" />
    "></script>
    <script src="https://www.w3schools.com/lib/w3.js"></script> <!-- for include html in div tag -->
    <script src="${cp}/resources/js/main.js" /></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<link href="${cp}/resources/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css" />
		
	  <script type="text/javascript" src="${cp}/resources/js/jquery.min.js"></script>
	   <script type="text/javascript" src="${cp}/resources/js/jquery.jplayer.js"></script>
	  <script type="text/javascript" src="${cp}/resources/js/jquery.jplayer.min.js"></script>
	   <script type="text/javascript" src="${cp}/resources/js/jplayer.playlist.js"></script>
	
	   	
		<script src="${cp}/resources/js/playbar.js" /></script>
		<script src="${cp}/resources/js/jquery.session.js" /></script>
    <link rel="stylesheet" href="${cp}/resources/css/custom.css">
  </head>
  <body>
    <div class="container-fluid">
      <div class="row">
        <nav class="sidebar" id="left-sidebar">
          <%@ include file = "leftSidebar.jsp" %>
        </nav>
        <!--Main Page-->
        <main class="main-page">
        <h3 style="color:white; display: none;"><%= hasNowPlay %></h3>
          <!--Scrollable Main Page Section-->
          <div class="scrolled-main" id="style-1">
            <!--Top Tool Section-->
            <div class ="row" id="top-tool">
              <div class="col-sm-3 col-sm-offset-3" id="top-tool-search">
                <div class="input-group stylish-input-group"  id="top-tool-search">
                  <div class="row" >
                    <input type="text" class="w3-input" style="width: 75%;" placeholder="Search" name="search" id="search">
                    <span class="input-group-addon">
                    <button type="submit" onclick="search()">
                    <i class="fa fa-search" aria-hidden="true" ></i>
                    </button>
                    </span>
                  </div>
                </div>
              </div>
              <p id="private-session">Private Session:</p>
              <label class="switch">
              <input type="checkbox" checked>
              <span class="slider round"></span>
              </label>
              <div id="top-tool-upgrade-and-user">
                <div class="w3-dropdown-hover">
                  <button class="w3-button w3-black" id="top-tool-profile">
                  <img width=25px height=25px class="rounded-circle" alt="Generic placeholder thumbnail" id="dropdown-img"  src="http://orig05.deviantart.net/f239/f/2011/089/3/3/jack_skellington_facebook_icon_by_valashard-d3cu1bt.jpg">
                  <span class="user_name">${username}</span>
                  <i class="fa fa-angle-down" aria-hidden="true"></i>
                  </button>
                  <div class="w3-dropdown-content w3-bar-block w3-border user-dropdown-button">
                    <a href="javascript:displayUserAccount()" class="w3-bar-item w3-button" id="dropdown-item">Account Setting</a>
                    <a href="javascript:displayAccount()" class="w3-bar-item w3-button" id="dropdown-item">Change Password </a>
                    <a href="javascript:displayUpgradeForm()" class="w3-bar-item w3-button" id="dropdown-item">Upgrade Your Account</a>
                    <a href="home" class="w3-bar-item w3-button" id="dropdown-item">Log Out</a>
                  </div>
                </div>
              </div>
            </div>
            <!--Banner Image-->
            <div id = "main-changing-content">

            </div>
            <!-- closed tag for scrolled-main -->
          </div>
          <c:choose>
            <c:when test="${user.userType == 0}">
              <div id="advertisement">
                <button id="ad-close"><i class="fa fa-times-circle" aria-hidden="true" style="margin-right: 10%;"></i></button>
                <img height="75em" src="http://dsim.in/blog/wp-content/uploads/2017/01/samsung2.jpg">
              </div>
            </c:when>
          </c:choose>
        </main>
        <!--Right Most Column-->
        <div id="right-col">
          <%@ include file = "rightSidebar.jsp" %>
        </div>
      </div>
    </div>
    <!--Footer Section-->
    <footer class="fixed-bottom">		
      <div class="row">
        <div class="col-md-3 col-sm-3" id = "playbar-artist-info">
          <div class="media">
            <div class="media-left">
              <img src="https://i.pinimg.com/736x/8d/e4/20/8de42050e671b93b1d6bad2f2764ba89--calm-graphic-design-sun-graphic.jpg" class="media-object" style="width:60px">
            </div>
            <div class="media-body">
              <h4 class="media-heading"> <a href="#" id="playbar-song-href"> <span id="playbar-song-name">   Song name     </span> </a></h4>
              <p> <a href="#" id ="playbar-artist-href"/> <span id="playbar-artist-name"> Artist name </span>
                <a href="#">
                <span class="glyphicon glyphicon-search"></span>
                </a>
                <a href="#" data-toggle ="tooltip-save-library" title= "Save to Your Library" id="playbar-save-anchor-tag"><i class="fa fa-plus-square"></i></a>
              </p>
            </div>
          </div>
        </div>
        <div class="col-md-6 col-sm-6">
            <div id="jquery_jplayer_1" class="jp-jplayer"></div>
		<div id="jp_container_1" class="jp-audio" role="application" aria-label="media player" style="border-style: none;">
                    <div class="jp-type-single" >
			<div class="jp-gui jp-interface" style="background-color: #4E4E4E;"> 
                            <div class="row">
				<div class="" style="margin-left: 35%; ">
                                    <ul id="playbar-center-icons">
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Shuffle" id="playbar-shuffle-button" > <i class="material-icons">shuffle</i></button></li>
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Previous"  id="playbar-prev-button"><i class="material-icons">skip_previous</i></button></li>
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-play" title="Play" id="playbar-play-button"> <i class="material-icons"><span class="play-pause-button">play_circle_filled</span></i></button></li>
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Next"  id="playbar-next-button"><i class="material-icons">skip_next</i></button></li>
                                       <li><button class="unstyle-buttons" role="button" tabindex="0" data-toggle="tooltip-mute" title="Repeat"  id="playbar-repeat-button" ><i class="material-icons"><span id="repeat-button-text">repeat</span></i></button></li>
                                    </ul>
                                </div>
                            </div>   
				       		
			    <div class="jp-progress" style="width:100%; margin-top: 5%;">
				<div class="jp-seek-bar">
                                    <div class="jp-play-bar"></div>
				</div>
			    </div>
                            <div class="jp-time-holder" style="width:100%; margin-top: 5%;">
				<div class="jp-current-time" role="timer" aria-label="time" style="color: black; font-size:1.2em;">&nbsp;</div>
                                <div class="jp-duration" role="timer" aria-label="duration" style="color: black; font-size:1.2em;">&nbsp;</div>				
                            </div>
			</div>	
                    </div>
		</div>
        </div>
        		
            <div class="col-md-3 col-sm-3" id="playbar-right">
              <ul id="playbar-right-icons" style="margin-top: 6%;">
                  <li>
                    
                  </li>
            <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Queue" id="playbar-queue-button"> <i class="material-icons">add_to_queue</i></button></li>
<!--            <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Mute"  id="playbar-mute-anchor-tag" onclick="volumeMute(this)"><i id="volume-up" class="material-icons">volume_up</i></li>-->
           

          </ul>
                <div class="jp-volume-controls" style="margin-left: -40%; margin-bottom: -5%;">
                        <button class="jp-mute" role="button" tabindex="0">mute</button>
			<button class="jp-volume-max" role="button" tabindex="0" style="margin-left: 80px;">max volume</button>
			<div class="jp-volume-bar" style="width:120px;">
                            <div class="jp-volume-bar-value"></div>
			</div>
		    </div>
        </div>
      </div> 
    </footer>
  </body>
