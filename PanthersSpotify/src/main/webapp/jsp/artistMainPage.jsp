<%-- set content type, language, and include the tag library for spring forms --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Artist - Panthers Spotify</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!--CSS ref-->
  <link rel="stylesheet" href="${cp}/resources/css/bootstrap.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <!--js ref-->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://www.w3schools.com/lib/w3.js"></script> <!-- for include html in div tag -->.
  <!--  <script src="${cp}/resources/js/main.js"></script> -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
   <script src="${cp}/resources/js/artistMainPage.js"></script>
  <link rel="stylesheet" href="${cp}/resources/css/admin.css">
  <script>
    $(document).ready(function(){
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
  </script>
</head>
<body>
  <nav class="sidebar" id="left-sidebar">
    <ul class="nav flex-column" >
      <li>
        <p class="color-nav-header">Song:</p>
        <ul class="left_sizebar">
          <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Add Song"  id="add_new_song_button"><i class="material-icons">add</i></button></li>
          <div id="new_song_dialog" title="Add Song To Database" style="display:none;">
            <!-- create playlist -->
            <form:form  method = "POST" action="submitSongForUploading" enctype="multipart/form-data" class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
              <div class="w3-row w3-section">
                <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Song Title</span></div>
                <div class="w3-rest">
                  <input class="w3-input w3-border" name="song_title" type="text" id="new_song_name" placeholder="Song Title">
                </div>
              </div>
              <div class="w3-row w3-section">
                <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Song Time</span></div>
                <div class="w3-rest">
                  <input class="w3-input w3-border" type="song_time" name="song_time"  placeholder="Song Title">
                </div>
              </div>
              <div class="w3-row w3-section">
                <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Release Day</span></div>
                <div class="w3-rest">
                  <input class="w3-input w3-border" name="release_day" type="date"  placeholder="Release date">
                </div>
              </div>
              <div class="w3-row w3-section">
                <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Genre or Mood</span></div>
                <div class="w3-rest">
                  <select class="w3-select w3-input" name="song_genre">
                    <option value="" disabled selected>Choose the song genre</option>
                    <option value="Latin">Latin</option>
                    <option value="Hip-Hop">Hip-Hop</option>
                    <option value="Mood">Mood</option>
                    <option value="Pop">Pop</option>
                    <option value="Country">Country</option>
                    <option value="Workout">Workout</option>
                    <option value="R&amp;B">R &amp;B</option>
                    <option value="Rock">Rock</option>
                    <option value="Jazz">Jazz</option>
                    <option value="Reggae">Reggae</option>
                    <option value="K-Pop">K-Pop</option>
                  </select>
                </div>
              </div>
              <div class="w3-row w3-section">
                <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Song Type</span></div>
                <div class="w3-rest">
                  <input class="w3-input w3-border" name="song_type" type="text"  placeholder="Song Type(mp4, ogg, etc)">
                </div>
              </div>
              <div class="w3-row w3-section">
                <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Album ID</span></div>
                <div class="w3-rest">
                  <input class="w3-input w3-border" name="song_album_id" type="text"  placeholder="Album ID">
                </div>
              </div>
              <div class="w3-row w3-section">
                <!-- <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Song URL</span></div>
                  <div class="w3-rest">
                    <input class="w3-input w3-border" name="song_url" type="text"  placeholder="Song Url">
                  </div>  -->
                <div class="w3-col" style="width:50px"><span style="font-size: 0.8em">Song File</span></div>
                <div>
                  <input class="w3-input" type="file" id="songFile" name="file">
                </div>
              </div>
              <div class="w3-row w3-section">
                <div class="w3-third w3-container">
                  <button onclick="event.preventDefault(); $('#new_song_dialog').dialog('close');" class="w3-button w3-block w3-section w3-blue w3-ripple">Cancel</button>
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
          <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Edit Song Information" id="edit_song_button"> <i class="material-icons">mode_edit</i></button></li>
        </ul>
      </li>
    </ul>
    </li>
    </ul>
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
            <input type="text" class="form-control"  placeholder="Search" >
            <span class="input-group-addon">
            <button type="submit">
            <i class="fa fa-search" aria-hidden="true"></i>
            </button>
            </span>
          </div>
        </div>
        <div id="top-tool-upgrade-and-user">
          <a href= ""> Artist</a>
          <div class="w3-dropdown-hover">
            <button class="w3-button w3-black" id="top-tool-profile">
            <img width=25px height=25px class="rounded-circle" alt="Generic placeholder thumbnail" id="dropdown-img"  src="http://orig05.deviantart.net/f239/f/2011/089/3/3/jack_skellington_facebook_icon_by_valashard-d3cu1bt.jpg">
            <span class="user_name">${username}</span>
            <i class="fa fa-angle-down" aria-hidden="true"></i>
            </button>
            <div class="w3-dropdown-content w3-bar-block w3-border user-dropdown-button">
              <a href="#" class="w3-bar-item w3-button" id="dropdown-item">Account</a>
              <a href="#" class="w3-bar-item w3-button" id="dropdown-item">Settings</a>
              <a href="home" class="w3-bar-item w3-button" id="dropdown-item">Log Out</a>
            </div>
          </div>
        </div>
      </div>
      <!--Banner Image-->
      <div id = "main-changing-content">
        <nav class="navbar navbar-expand-md bg-dark navbar-dark"
          id="bootstrap-overrides-navbar">
          <ul class="navbar-nav mr-auto tab" id="navbar-ul">
            <li class="nav-item">
              <a class="nav-link tablinks" href="javascript:displayContent('approvedSongsTableDiv')">APPROVED SONGS </a>
            </li>
            <li class="nav-item">
              <a class="nav-link tablinks" href="javascript:displayContent('pendingSongsTableDiv')">PENDING SONGS </a>
            </li>
          
            <li class="nav-item">
              <a class="nav-link tablinks" href="javascript:displayContent('royaltyTableDiv')">Royalty</a>
            </li>
          </ul>
        </nav>
        <div id="approvedSongsTableDiv" class="w3-container info-table">
         <h1><span id="num-of-approved-songs"></span> songs approved</h1>
              <table class="w3-table-all w3-hoverable" id = "artist-approved-songs-table">
			    <thead>
			      <tr class="w3-light-grey">
			        <th>Song ID</th>
			        <th>Song Title</th>
			        <th>Artist</th>
			        <th>Genre</th>
			        <th>Status</th>
			        <%-- <th>Created Date</th> --%>
			        <th></th>
			      </tr>
			    </thead>
			    <tbody>
			    </tbody>
			  </table>
        </div>
        <div id="pendingSongsTableDiv" class="w3-container info-table" style="display:none">
         <h1><span id="num-of-pending-songs"></span> songs need to be approved</h1>
              <table class="w3-table-all w3-hoverable" id = "artist-pending-songs-table">
			    <thead>
			      <tr class="w3-light-grey">
			        <th>Song ID</th>
			        <th>Song Title</th>
			        <th>Artist</th>
			        <th>Genre</th>
			        <th>Status</th>
			        <%-- <th>Created Date</th> --%>
			        <th></th>
			      </tr>
			    </thead>
			    <tbody>
			    </tbody>
			  </table>
        </div>
      
        <div id="royaltyTableDiv" class="w3-container info-table" style="display:none; color:white;">
          <h1> Royalty </h1>
          <h3> Your Royalty is $<span id ="artistRoyaltyText"></span></h3>
        </div>
      </div>
      <script>
        function displayContent(table) {
            var i;
            var x = document.getElementsByClassName("info-table");
            for (i = 0; i < x.length; i++) {
               x[i].style.display = "none";
            }
            document.getElementById(table).style.display = "block";
        }
      </script>
      <!-- closed tag for scrolled-main -->
    </div>
  </main>
</body>