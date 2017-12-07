<%-- set content type, language, and include the tag library for spring forms --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cp"
	value="${pageContext.request.servletContext.contextPath}"
	scope="request" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Admin - Panthers Spotify</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--CSS ref-->
<link rel="stylesheet" href="${cp}/resources/css/bootstrap.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<!--js ref-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="
	<c:url value="/resources/js/bootstrap.min.js" />
	"></script>
<script src="https://www.w3schools.com/lib/w3.js"></script>
<!-- for include html in div tag -->
.
<!--  <script src="${cp}/resources/js/main.js"></script> -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="${cp}/resources/css/custom.css">
<link rel="stylesheet" href="${cp}/resources/css/admin.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="${cp}/resources/css/leftSidebar.css">
<script>
      $(document).ready(function(){
        //hide all other containers in <div id= 'middle-content'> beside except overview_container
        //$("#overview_container").siblings().hide();
        //$("#main-changing-content").load("browse.html");
        $("#main-changing-content").load("jsp/dashboard.jsp");
        $("#add_new_song_button").click(function(){
          $('#new_song_dialog').dialog({
           height: 600,
           width: 550,
           modal: true,
           resizable: false,
           dialogClass: 'no-close'
        	});
          });
      });
      function myFunction() {
       var x = document.getElementById("Demo");
       if (x.className.indexOf("w3-show") == -1) {
           x.className += " w3-show";
       } else {
           x.className = x.className.replace(" w3-show", "");
       }
      }

      function addUser(){
       $("#main-changing-content").load("jsp/adminAddUser.jsp");
      }
    </script>
</head>
<body style="margin-top:-1em;">
	<nav class="sidebar" id="left-sidebar">
		<ul class="nav flex-column">
			<!--First Section: browse and radio-->
			<li class="nav-item" id="extra-padding"><a
				class="nav-link color-nav"
				href="javascript:displayPage('dashboard')">Dashboard</a></li>
			<!--Actions taken by the admin-->
			<li>
				<p class="color-nav-header">User:</p>
				<ul class="left_sizebar">
					<li><button class="unstyle-buttons" data-toggle="tooltip-mute"
							title="Add User" id="add-new-user-button">
							<i class="material-icons">add</i>
						</button></li>
						<div id="new-user-dialog" title="Add User To Database" style="display: none;">
						<!-- create playlist -->
						<form:form method="POST" action="addUserToDatabase" class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Username</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="username"
										type="text" placeholder="Username">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Email</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" type="email"
										name="email" placeholder="Email Address">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Password</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="password"
										type="password" placeholder="Password">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">First Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="firstName"
										type="text" placeholder="First name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Middle Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="middleName"
										type="text" placeholder="Middle name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Last Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="lastName"
										type="text" placeholder="Last name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Gender</span>
								</div>
								<div class="w3-rest">
									<div style="display: inline";>
										<input class="w3-input w3-border w3-radio" type="radio"
											name="gender" value="male" checked> <label>Male</label>
										<input class="w3-input w3-border w3-radio" type="radio"
											name="gender" value="female"> <label>Female</label>
									</div>
								</div>
								<div class="w3-row w3-section">
									<div class="w3-col" style="width: 50px">
										<span style="font-size: 0.8em">Date of Birth</span>
									</div>
									<div class="w3-rest">
										<input class="w3-input w3-border" type="date"
											placeholder="Date of Birth (yyyy-mm-dd)" name="DOB"
											required>
									</div>
								</div>
								<div class="w3-row w3-section">
									<div class="w3-col" style="width: 50px">
										<span style="font-size: 0.8em">User Type</span>
									</div>
									<div class="w3-rest">
										<select class="w3-select w3-input" name="userType">
						                    <option value="" disabled selected>Choose user type</option>
						                    <option value="Basic">Basic</option>	
						                    <option value="Premium">Premium</option>	                    
						                </select>
									</div>
								</div>
								<div class="w3-row w3-section">
									<div class="w3-third w3-container">
										<button
											onclick="event.preventDefault(); $('#new-user-dialog').dialog('close');"
											class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
									</div>
									<div class="w3-third w3-container"></div>
									<div class="w3-third w3-container">
										<button type="submit"
											class="w3-button w3-block w3-section w3-blue w3-ripple">Create</button>
									</div>
								</div>
						</form:form>
					</div>
				</ul>


			</li>
		
			<li>
				<p class="color-nav-header">Playlist:</p>
				<ul class="left_sizebar">
					<li><button class="unstyle-buttons" data-toggle="tooltip-mute"
							title="Add Artist" id="add-new-playlist-button">
							<i class="material-icons">add</i>
						</button></li>
						<div id="new-playlist-dialog" title="Create Playlist" style="display:none; background: #2f2f2f">
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
					          <button id="dialog-button-cancel" onclick="event.preventDefault(); $('#new-playlist-dialog').dialog('close');" class="w3-button w3-block w3-section w3-ripple">Cancel</button>
					        </div>

					        <div class="w3-third w3-container" >
					          <button id="dialog-button" type="submit" class="w3-button w3-block w3-section w3-green w3-ripple">Create</button>
					        </div>
					      </div>
					    </form:form>
					  </div>
					<!-- end of dialog popup box -->
				</ul>
			</li>
			<li>
				<p class="color-nav-header">Artist:</p>
				<ul class="left_sizebar">
					<li><button class="unstyle-buttons" data-toggle="tooltip-mute"
							title="Add Artist" id="add-new-artist-button">
							<i class="material-icons">add</i>
						</button></li>
					<div id="new-artist-dialog" title="Add Artist To Database"
						style="display: none;">
						<!-- create playlist -->
						<form:form method="POST" action="addArtistToDatabase"
							class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Artist Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="artistName"
										type="text" placeholder="Artist's stage name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Artist Email </span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" type="email"
										name="artistEmail" placeholder="Artist Email Address">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Password</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="artistPassword"
										type="password" placeholder="Password">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Artist First Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="artistFirstName"
										type="text" placeholder="First name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Artist Middle Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="artistMiddleName"
										type="text" placeholder="Last name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Artist Last Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="artistLastName"
										type="text" placeholder="Last name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Gender</span>
								</div>
								<div class="w3-rest">
									<div style="display: inline";>
										<input class="w3-input w3-border w3-radio" type="radio"
											name="gender" value="male" checked> <label>Male</label>
										<input class="w3-input w3-border w3-radio" type="radio"
											name="gender" value="female"> <label>Female</label>
									</div>
								</div>
								<div class="w3-row w3-section">
									<div class="w3-col" style="width: 50px">
										<span style="font-size: 0.8em">Artist Biography</span>
									</div>
									<div class="w3-rest">
										<textarea class="form-control w3-border" rows="5"
											name="artistBiography" placeholder="Artist Biography"></textarea>
									</div>
								</div>
								<div class="w3-row w3-section">
									<div class="w3-col" style="width: 50px">
										<span style="font-size: 0.8em">Date of Birth</span>
									</div>
									<div class="w3-rest">
										<input class="w3-input w3-border" type="date"
											placeholder="Date of Birth (yyyy-mm-dd)" name="artistDOB"
											required>
									</div>
								</div>
								<div class="w3-row w3-section">
									<div class="w3-third w3-container">
										<button
											onclick="event.preventDefault(); $('#new-artist-dialog').dialog('close');"
											class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
									</div>
									<div class="w3-third w3-container"></div>
									<div class="w3-third w3-container">
										<button type="submit"
											class="w3-button w3-block w3-section w3-blue w3-ripple">Create</button>
									</div>
								</div>
						</form:form>
					</div>
					<!-- end of dialog popup box -->
				</ul>
			</li>
			<li>
				<p class="color-nav-header">Song:</p>
				<ul class="left_sizebar">
					<li><button class="unstyle-buttons" data-toggle="tooltip-mute"
							title="Add Song" id="add_new_song_button">
							<i class="material-icons">add</i>
						</button></li>
					<div id="new_song_dialog" title="Add Song To Database"
						style="display: none;">
						<!-- create playlist -->
						<form:form method="POST" action="addSongToDatabase"
							enctype="multipart/form-data"
							class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Song Title</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="song_title" type="text"
										id="new_song_name" placeholder="Song Title">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Song Time</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" type="song_time"
										name="song_time" placeholder="Song Title">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Release Day</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="release_day"
										type="text" placeholder="Release date">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Genre</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="song_genre" type="text"
										placeholder="Song Genre">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Song Type</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="song_type" type="text"
										placeholder="Song Type(mp4, ogg, etc)">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Album ID</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="song_album_id"
										type="text" placeholder="Album ID">
								</div>
							</div>
							<div class="w3-row w3-section">
								<!-- <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Song URL</span></div>
                    <div class="w3-rest">
                      <input class="w3-input w3-border" name="song_url" type="text"  placeholder="Song Url">
                    </div>  -->
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Song File</span>
								</div>
								<div>
									<input type="file" id="songFile" name="song_url">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-third w3-container">
									<button
										onclick="event.preventDefault(); $('#new_song_dialog').dialog('close');"
										class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
								</div>
								<div class="w3-third w3-container"></div>
								<div class="w3-third w3-container">
									<button type="submit"
										class="w3-button w3-block w3-section w3-blue w3-ripple">Create</button>
								</div>
							</div>
						</form:form>
					</div>
					<!-- end of dialog popup box -->
					<!--  <li><button class="unstyle-buttons"
							data-toggle="tooltip-queue" title="Edit Song Information"
							id="edit_song_button">
							<i class="material-icons">mode_edit</i>
						</button></li>-->
				</ul>
			</li>
			<!--  
			<li>
				<p class="color-nav-header">Album:</p>
				<ul class="left_sizebar">
					<li><button class="unstyle-buttons" data-toggle="tooltip-mute"
							title="Add Album" id="playbar-prev-button">
							<i class="material-icons">add</i>
						</button></li>
					<li><button class="unstyle-buttons"
							data-toggle="tooltip-queue" title="Edit Album Information"
							id="playbar-shuffle-button">
							<i class="material-icons">mode_edit</i>
						</button></li>
				</ul>
			</li> -->
			<!--<li>
				<p class="color-nav-header">Daily Recommended Songs:</p>
				<ul class="left_sizebar">
					<li><button class="unstyle-buttons" data-toggle="tooltip-mute"
							title="Add Daily Recommended Songs" id="playbar-prev-button">
							<i class="material-icons">add</i>
						</button></li>
					<li><button class="unstyle-buttons"
							data-toggle="tooltip-queue" title="Edit Daily Recommended Songs"
							id="playbar-shuffle-button">
							<i class="material-icons">mode_edit</i>
						</button></li>
					<li><button class="unstyle-buttons" data-toggle="tooltip-play"
							title="Delete Daily Recommended Songs" id="playbar-play-button"
							onclick="playSong()">
							<i class="material-icons">delete_forever</i>
						</button></li>
				</ul>
			</li>-->
			<!--  
			<li>
				<p class="color-nav-header">Royalty Payment:</p>
				<ul class="left_sizebar">
					<li><button class="unstyle-buttons" data-toggle="tooltip-mute"
							title="Make Payment" id="playbar-prev-button">
							<i class="material-icons">add</i>
						</button></li>
				</ul>
			</li>-->
		</ul>
		</li>
		</ul>
	</nav>
	<!--Main Page-->
	<main class="main-page"> <!--Scrollable Main Page Section-->
	<div class="scrolled-main" id="style-1">
		<!--Top Tool Section-->
		<div id="top-tool">
			<div>
				<a href="home" style="height: 2px; margin-left: 2%; margin-top:5%; width:10%;">Log Out</a>
				<!--<div class="w3-dropdown-hover">
					<button class="w3-button w3-black" id="top-tool-profile">
						<img width=25px height=25px class="rounded-circle"
							alt="Generic placeholder thumbnail" id="dropdown-img"
							src="http://orig05.deviantart.net/f239/f/2011/089/3/3/jack_skellington_facebook_icon_by_valashard-d3cu1bt.jpg">
						<span class="user_name">Username</span> <i
							class="fa fa-angle-down" aria-hidden="true"></i>
					</button>
					<div
						class="w3-dropdown-content w3-bar-block w3-border user-dropdown-button">
						<a href="#" class="w3-bar-item w3-button" id="dropdown-item">Session</a>
						<a href="#" class="w3-bar-item w3-button" id="dropdown-item">Account</a>
						<a href="#" class="w3-bar-item w3-button" id="dropdown-item">Upgrade
							Your Account</a> <a href="#" class="w3-bar-item w3-button"
							id="dropdown-item">Settings</a> <a href="home"
							class="w3-bar-item w3-button" id="dropdown-item">Log Out</a>
					</div>
				</div> -->
			</div>
		</div>
		<!--Banner Image-->
		<div id="main-changing-content"></div>
		<!-- closed tag for scrolled-main -->
	</div>
	</main>
</body>
