<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONException"%>
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
	
	   	<script src="${cp}/resources/js/artist.js" /></script>
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
          <!--Scrollable Main Page Section-->
          <div class="scrolled-main" id="style-1">
            <!--Top Tool Section-->
            <div class ="row" id="top-tool">
              <div class="col-sm-3 col-sm-offset-3" id="top-tool-search">
                <div class="input-group stylish-input-group"  id="top-tool-search">
                  <div class="row" >
                    <input type="text" class="w3-input" style="width: 70%; border-bottom-left-radius: 50px; border-top-left-radius:50px;" placeholder="   Search" name="search" id="search">
                    <span class="input-group-addon" style="border-bottom-right-radius: 50px; border-top-right-radius:50px;background:white;border: none;">
                    <button type="submit" id="search-button" style="border:none;background:white;">
                    <i class="fa fa-search" aria-hidden="true" style="background:white;"></i>
                    </button>
                    </span>
                  </div>
                </div>
              </div>
              <!--  <p id="private-session">Private Session:</p>
              <label class="switch" id="psSelected"  style="margin-top:0.8em;">
              	<input type="checkbox" >
                  <span class="checkmark"></span>
              </label>-->
              <div id="top-tool-upgrade-and-user">
                <div class="w3-dropdown-hover">
                  <button class="w3-button w3-black" id="top-tool-profile" style="border-radius:0;">
                  <c:choose>
		            <c:when test="${not empty user.photoUrl}">
		              <img id="dropdown-img" style="border-radius: 5px;" width=25px height=25px src="${cp}/resources/data${user.photoUrl}"  class="img-rounded" alt="Generic placeholder thumbnail">
		            </c:when>
		            <c:otherwise>
		              <img id="dropdown-img" style="border-radius: 5px;" width=25px height=25px src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg"  class="img-rounded" alt="Generic placeholder thumbnail">
		            </c:otherwise>
		          </c:choose>   
                  <span class="user_name">${user.userName}</span>
                  <i class="fa fa-angle-down" aria-hidden="true"></i>
                  </button>
                  <div class="w3-dropdown-content w3-bar-block w3-border user-dropdown-button">
                    <a href="javascript:startPrivateSession()" class="w3-bar-item w3-button private-button" id="dropdown-item">Private Session
                       <c:choose>
                         <c:when test="${user.privateSession == true}">
                         	<i class="fa fa-check" aria-hidden="true"></i>
                         </c:when>
                       </c:choose>
                       </a>
                    <a href="javascript:displayUserAccount()" class="w3-bar-item w3-button" id="dropdown-item">Account Setting</a>
                    <a href="javascript:displayAccount()" class="w3-bar-item w3-button" id="dropdown-item">Change Password </a>
                    <c:choose>
                      <c:when test="${user.userType == 0}">
                        <a href="javascript:displayUpgradeForm()" class="w3-bar-item w3-button" id="dropdown-item">Upgrade Your Account</a>
                      </c:when>
                      <c:when test="${user.userType == 1}">
                        <a href="javascript:displayPayment()" class="w3-bar-item w3-button" id="dropdown-item">View Payment</a>
                        <div id="paymentDialog" title="View Payment" style="display:none;">
						 	<form id="paymentForm">
						 	  <div style="width: 100%; text-algin:center; color: white;">
						 	    <div id="payment-holdername"></div>
						 	    <div  id="payment-ccn"></div> 		
						 	    <div  id="payment-expDate"></div> 
						 	    <div  id="payment-paymentInfo"></div>
						 	  </div>		  
							  <div class="w3-row w3-section">
							    <div class="w3-third w3-container">
							      <button onclick="event.preventDefault(); $('#paymentDialog').dialog('close');" class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
							    </div>							  						   
							  </div>
							 </form>
						</div>
                        <a href="javascript:displayDowngradeForm()" class="w3-bar-item w3-button" id="dropdown-item">Downgrade Your Account</a>                     
						<div id="downgradeDialog" title="Downgrade" style="display:none;">
						 	<form id="downgradeForm">
						 	  <div style="margin-left:25%; text-algin:center; color: white;">Are you sure you want to downgrade?</div> 				  
							  <div class="w3-row w3-section" style="margin-left:25%;">
							    <div class="w3-third w3-container">
							      <button onclick="event.preventDefault(); $('#downgradeDialog').dialog('close');" class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
							    </div>							  
							    <div class="w3-third w3-container">
							      <input id="downgradeButton" type="submit" class="w3-button w3-block w3-section w3-blue w3-ripple" value="Yes"></button>
							    </div>
							  </div>
							 </form>
						</div>
                      </c:when>
                    </c:choose>
                    <a href="javascript:displaySetting()" class="w3-bar-item w3-button" id="dropdown-item">Setting</a>
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
            <c:otherwise>
              <script> premUserAdv() </script>
            </c:otherwise>
          </c:choose>
        </main>
        <!--Right Most Column-->
        <div id="right-col">
          <div id="right-col-friend">
            <%@ include file = "rightSidebar.jsp" %>
          </div>
          <div id="openTicketArea" style="text-algin:center;">
            <div class="line" style="margin-left:0.5vw;"></div>
            <button id="openTicketButton" class="w3-button" style="margin-left: 2.5vw;">Contact Support</button>
            
            <div id="open_ticket_dialog" title="Submit a support ticket" style="display:none;">
            <!-- create playlist -->
            <form:form  method = "POST" action="supportTicket" enctype="multipart/form-data" class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">                
              <div class="w3-row w3-section">
                <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Anything you want to tell us:</span></div>
                <div class="w3-rest">
                  <textarea cols="70" rows="5" name="sendInfo" placeholder="What do you want to tell us?"></textarea>              
                </div>
              </div>                                                  
              <div class="w3-row w3-section">
                <div class="w3-third w3-container">
                  <button onclick="event.preventDefault(); $('#open_ticket_dialog').dialog('close');" class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
                </div>
                <div class="w3-third w3-container">
                </div>
                <div class="w3-third w3-container">
                  <button type="submit" class="w3-button w3-block w3-section w3-blue w3-ripple">Send</button>
                </div>
              </div>
	            </form:form>
	          </div>
	            
        </div>
        </div>
      </div>
    </div>
    <!--Footer Section-->
    <footer class="fixed-bottom" style="height:13.5vh;">		
      <div class="row">
        <div class="col-md-3 col-sm-3" id = "playbar-artist-info">
          <div class="media">
            <div class="media-left" style="margin: 0.65em;">
              <img src="https://i.pinimg.com/736x/8d/e4/20/8de42050e671b93b1d6bad2f2764ba89--calm-graphic-design-sun-graphic.jpg" class="media-object" style="width:60px">
            </div>
            <div class="media-body" style="margin-top: 0.2em; margin-left: 1em;">
              <h4 class="media-heading"> <a href="#" id="playbar-song-href"> <span id="playbar-song-name">   Song name     </span> </a></h4>
              <p id="playbar-artist-name"> 
                  
<!--                  <a href="#" id ="playbar-artist-href"/> <span id="playbar-artist-name"> Artist name </span>-->
               
              </p>
            </div>
          </div>
        </div>
        <div class="col-md-	 col-sm-6">
            <div id="jquery_jplayer_1" class="jp-jplayer"></div>
		<div id="jp_container_1" class="jp-audio" role="application" aria-label="media player" style="border-style: none;">
                    <div class="jp-type-single" >
			<div class="jp-gui jp-interface" style="background-color: #4E4E4E;"> 
                            <div class="row">
				<div class="" style="margin-left: 35%; margin-top:0.65em; ">
                                    <ul id="playbar-center-icons">
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Shuffle" id="playbar-shuffle-button" > <i class="material-icons" id="shuffle-icon">shuffle</i></button></li>
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Fast Backward"  id="playbar-backward-button"> <i class="material-icons">fast_rewind</i></button></li>
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Previous"  id="playbar-prev-button"><i class="material-icons">skip_previous</i></button></li>
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-play" title="Play" id="playbar-play-button"> <i class="material-icons"><span class="play-pause-button">play_circle_filled</span></i></button></li>
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Next"  id="playbar-next-button"><i class="material-icons">skip_next</i></button></li>
                                       <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Fast Forward"  id="playbar-forward-button"><i class="material-icons">fast_forward</i></button></li>
                                       <li><button class="unstyle-buttons" role="button" tabindex="0" data-toggle="tooltip-mute" title="Repeat"  id="playbar-repeat-button" ><i class="material-icons" id="repeat-icon">repeat</i></button></li>
                                    </ul>
                                </div>
                            </div>   
				       		
			    <div class="jp-progress" style="width:100%; margin-top: 5%; margin-left: 1.75em;">
				<div class="jp-seek-bar">
                                    <div class="jp-play-bar"></div>
				</div>
			    </div>
                            <div class="jp-time-holder" style="width:100%; margin-top: 5%; margin-left: 1.75em;">
				<div class="jp-current-time" role="timer" aria-label="time" style="color: white; font-size:0.75em;">&nbsp;</div>
                                <div class="jp-duration" role="timer" aria-label="duration" style="color: white; font-size:0.75em;">&nbsp;</div>				
                            </div>
			</div>	
                    </div>
		</div>
        </div>
        		
            <div class="col-md-3 col-sm-3" id="playbar-right">
              <ul id="playbar-right-icons" style="margin-top: 6%;">
                  <li>
                    
                  </li>
             <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Song Lyric" id="song-lyric-button"> <i class="material-icons">description</i></button></li>        
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
