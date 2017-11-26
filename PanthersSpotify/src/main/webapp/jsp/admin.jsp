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
<link rel="stylesheet" href="${cp}/resources/css/admin.css">
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
<body>
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
							title="Add User" id="add-user-button" onclick="addUser()">
							<i class="material-icons">add</i>
						</button></li>
					<li><button class="unstyle-buttons"
							data-toggle="tooltip-queue" title="Edit User Information"
							id="edit-user-button">
							<i class="material-icons">mode_edit</i>
						</button></li>
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
									<input class="w3-input w3-border" name="artist-name"
										type="text" placeholder="Artist's stage name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Artist Email </span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" type="email"
										name="artist-email" placeholder="Artist Email Address">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Password</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="artist-password"
										type="password" placeholder="Password">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Artist First Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="artist-first-name"
										type="text" placeholder="First name">
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-col" style="width: 50px">
									<span style="font-size: 0.8em">Artist Last Name</span>
								</div>
								<div class="w3-rest">
									<input class="w3-input w3-border" name="artist-last-name"
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
											name="artist-biography" placeholder="Artist Biography"></textarea>
									</div>
								</div>
								<div class="w3-row w3-section">
									<div class="w3-col" style="width: 50px">
										<span style="font-size: 0.8em">Date of Birth</span>
									</div>
									<div class="w3-rest">
										<input class="w3-input w3-border" type="date"
											placeholder="Date of Birth (yyyy-mm-dd)" name="artist-dob"
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
					<li><button class="unstyle-buttons"
							data-toggle="tooltip-queue" title="Edit Song Information"
							id="edit_song_button">
							<i class="material-icons">mode_edit</i>
						</button></li>
				</ul>
			</li>
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
			</li>
			<li>
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
			</li>
			<li>
				<p class="color-nav-header">Royalty Payment:</p>
				<ul class="left_sizebar">
					<li><button class="unstyle-buttons" data-toggle="tooltip-mute"
							title="Make Payment" id="playbar-prev-button">
							<i class="material-icons">add</i>
						</button></li>
				</ul>
			</li>
		</ul>
		</li>
		</ul>
	</nav>
	<!--Main Page-->
	<main class="main-page"> <!--Scrollable Main Page Section-->
	<div class="scrolled-main" id="style-1">
		<!--Top Tool Section-->
		<div class="row" id="top-tool">
			<div id="top-tool-page-change">
				<button class="top-tool-page-button">
					<i class="fa fa-angle-left fa-2x" aria-hidden="true"></i>
				</button>
				<button class="top-tool-page-button">
					<i class="fa fa-angle-right fa-2x" aria-hidden="true"></i>
				</button>
			</div>
			<div class="col-sm-3 col-sm-offset-3" id="top-tool-search">
				<div class="input-group stylish-input-group" id="top-tool-search">
					<input type="text" class="form-control" placeholder="Search">
					<span class="input-group-addon">
						<button type="submit">
							<i class="fa fa-search" aria-hidden="true"></i>
						</button>
					</span>
				</div>
			</div>
			<div id="top-tool-upgrade-and-user">
				<a href=""> Admin</a>
				<div class="w3-dropdown-hover">
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
				</div>
			</div>
		</div>
		<!--Banner Image-->
		<div id="main-changing-content"></div>
		<!-- closed tag for scrolled-main -->
	</div>
	</main>
</body>