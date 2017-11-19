<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
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
   <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    <script src="https://www.w3schools.com/lib/w3.js"></script> <!-- for include html in div tag -->
   
     <script src="${cp}/resources/js/main.js" /></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
    $(document).on("click", "#playbar-queue-button", function () {
    	$("#main-changing-content").load("jsp/queue.jsp");
    });
    </script>
    <%-- <link href="<c:url value="/WEB-INF/resources/css/custom.css" />" rel="stylesheet"> --%>
    <%-- <style><%@include file="/WEB-INF/resources/css/custom.css"%></style> --%>
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
          <!--Scrollable Main Page Section-->
          <div class="scrolled-main" id="style-1">
            <!--Top Tool Section-->
            <div class ="row" id="top-tool">
              <div id="top-tool-page-change">
                <button class="top-tool-page-button"><i class="fa fa-angle-left fa-2x" aria-hidden="true"></i></button>
                <button class="top-tool-page-button"><i class="fa fa-angle-right fa-2x" aria-hidden="true"></i></button>
              </div>
              <div class="col-sm-3 col-sm-offset-3" id="top-tool-search">
                <div class="input-group stylish-input-group"  id="top-tool-search">
                <form:form action="search" method="POST">
                	<div class="row" >
	                  <input type="text" class="w3-input" style="width: 80%; height: 75%;" placeholder="Search" name="search">
	                  <span class="input-group-addon">
	                  <button type="submit">
	                  <i class="fa fa-search" aria-hidden="true" ></i>
	                  </button>
	                  </span>
                  	</div>
                  </form:form>
                </div>
              </div>
              <div id="top-tool-upgrade-and-user">

                <div class="w3-dropdown-hover">
                  <button class="w3-button w3-black" id="top-tool-profile">
                  <img width=25px height=25px class="rounded-circle" alt="Generic placeholder thumbnail" id="dropdown-img"  src="http://orig05.deviantart.net/f239/f/2011/089/3/3/jack_skellington_facebook_icon_by_valashard-d3cu1bt.jpg">
                  <span class="user_name">${username}</span>
                  <i class="fa fa-angle-down" aria-hidden="true"></i>
                  </button>
                  <div class="w3-dropdown-content w3-bar-block w3-border user-dropdown-button">
                    <a href="javascript:displayAccount()" class="w3-bar-item w3-button" id="dropdown-item">Account</a>
                    <a href="#" class="w3-bar-item w3-button" id="dropdown-item">Upgrade Your Account</a>
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
        <div class="col-md-6 col-sm-6" id="playbar-control">
          <div class="row">
            <div class="col-md-3">
              <!-- offset -->
            </div>
            <div class="col-md-6" id = "playbar-center">
              <ul id="playbar-center-icons">
                <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Shuffle" id="playbar-shuffle-button"> <i class="material-icons">shuffle</i></button></li>
                <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Previous"  id="playbar-prev-button"><i class="material-icons">skip_previous</i></button></li>
                <li><button class="unstyle-buttons" data-toggle="tooltip-play" title="Play" id="playbar-play-button" onclick="playSong()"> <i class="material-icons"><span id="play-pause-button">play_circle_filled</span></i></button></li>
                <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Next"  id="playbar-next-button"><i class="material-icons">skip_next</i></button></li>
                <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Repeat"  id="playbar-repeat-button"><i class="material-icons"><span id="repeat-button-text">repeat</span></i></button></li>
              </ul>
            </div>
          </div>
          <div class="row">
            <div class="col-md-3">
              <!-- offset -->
              <div class = "playback-bar-current-time" style="float:right; margin-right: 12px;"> <span id="currentPos"> 0:00 </span></div>
            </div>
            <div class="col-md-6 playback-bar">
              <!-- <div class="progress" onseeked="testing(this.child);">
                <div class="progress-bar"  id="progress_bar_play" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:10%">
                  <span class="sr-only">70% Complete</span>
                </div>
                </div> -->
              <div id="slider">
                <div id="innerSlider" style="background-color:blue; width: 30px; left:30%;"></div>
              </div>
              <div class="audio-bar">
                <!-- use javascript to play the songs -->
                <audio id="myAudio" ontimeupdate="updateTime()">
                  <source src="" type="audio/mpeg">
                  Your browser does not support the audio element.
                </audio>
              </div>
            </div>
            <div class="col-md-3">
              <div class="playback-bar-finish-time" style="margin-left: 12px;"> <span id="duration">  0:00 </span> </div>
            </div>
          </div>
        </div>
        <div class="col-md-3 col-sm-3" id="playbar-right">
          <ul id="playbar-right-icons">
            <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Queue" id="playbar-queue-button"/> <i class="material-icons">add_to_queue</i></li>
            <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Mute"  id="playbar-mute-anchor-tag"/><i class="material-icons">volume_up</i></li>
            <li><input class="bar" type="range" id="rangeinput" value="50" min = "0" max = "100" onchange="console.log(this.value);"/></li>
          </ul>
        </div>
      </div>
    </footer>
  </body>
