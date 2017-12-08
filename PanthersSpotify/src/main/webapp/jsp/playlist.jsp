<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONException"%>

<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Playlist - Panthers Spotify</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-dark-grey.css">
    <link rel="stylesheet" href="${cp}/resources/css/playlist.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="${cp}/resources/js/playbar.js"></script>
    <script src="${cp}/resources/js/playlist.js"></script>
   
  </head>
  <body>
    <!--  Container for DISCOVER -->
    <div id="playlist-top-background">
    <div class="playlist-top" id = "release-container">    
      <div class="suggestion-container-top">
        <div class="playlist_image_box" style="width: 20%">    
          <c:choose>
            <c:when test="${not empty selectedPlaylist.photoUrl}">
            <img width=100% height=width src="${cp}/resources/data${selectedPlaylist.photoUrl}"  class="img-rounded" alt="Generic placeholder thumbnail">
            </c:when>
            <c:otherwise>
            <img width=100% height=width src="http://res.cloudinary.com/dn1agy1ea/image/upload/v1495644755/empty-album-cover_wvtnrn.png"  class="img-rounded" alt="Generic placeholder thumbnail">
            </c:otherwise>
          </c:choose>
        </div>
        <div class="playlist-details-box">
          <div id ="playlist-info">
            <h5 id="playlist-page-title"> PLAYLIST </h5>
            <p id="playlist-name">
              <c:out value="${selectedPlaylist.pname}"></c:out>
            </p>
            <p id="playlistID" style="display:none;">
              <c:out value="${selectedPlaylist.pid}"></c:out>
            </p>
            <h5 id="playlist-des">
              <c:out value="${selectedPlaylist.des}"></c:out>
            </h5>
            <h5 id="playlist-create">
              Created by: 
              <a href="#">
                <c:out value="${selectedPlaylist.powner.userName}"></c:out>
              </a>
              <span id="num_song">
                <c:out value="${selectedPlaylistNumSongs}"></c:out>
                song 
              </span>
              , <span id="total_length"> 4 min 26 sec </span>
            </h5>
            <div class="row">
              <div id="playlist-play-top" style="display:inline;">
                <button id="play-playlist-button" class="w3-button w3-round-xxlarge btn formButton">  <span class="playingStatus">PLAY</span></button>
                <c:if test="${selectedPlaylist.powner != user}">
                  <button class="w3-button w3-round-xxlarge formButton followPlaylistButton" >
                    <span id="followingPlaylistStatus" >
                      <c:choose>
                        <c:when test="${fn:contains(userFollowedPlaylists,selectedPlaylist)}">
                          UNFOLLOW
                        </c:when>
                        <c:otherwise>
                          FOLLOW
                        </c:otherwise>
                      </c:choose>
                    </span>
                  </button>
                </c:if>
              </div>
              <div id="">
                <div class="w3-dropdown-click playlist_header_more_action_list">
                  <button class="w3-button playlist_header_more_button" title="More" id="play-dropdown" ><i class="fa fa-ellipsis-h" aria-hidden="true"></i></button>
                  <div class="w3-dropdown-content w3-bar-block w3-border playlist_action_list">
                     <!--
                    <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Go to Playlist Radio</button>
                    <hr>
                    <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Collaborative Playlist</button>
                     -->
                    <!-- <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Make Secret</button>-->
                    <!-- <hr>-->
                     <c:if test="${selectedPlaylist.powner == user}">
                      <button id = "edit_playlist_button" class="w3-bar-item w3-button playlist_action_dropdown">Edit Details</button>
                     </c:if>
                    <div id="edit_playlist_modal" class="w3-modal w3-animate-opacity">
                      <div class="w3-modal-content w3-card-4" style="height:500px; overflow:scroll;">
                        <header class="w3-container w3-theme-d3">
                          <span onclick="document.getElementById('edit_playlist_modal').style.display='none'" 
                            class="w3-button w3-large w3-display-topright">&times;</span>
                          <h5>Edit Playlist Details</h5>
                        </header>
                        <div class="w3-container w3-theme-d4">
                          <form:form action="editPlaylistDetails" method="POST" enctype="multipart/form-data">
                            <label>Name</label>
                            <br>
                            <input type="text" class="w3-input" name="playlist_name" value="${selectedPlaylist.pname}">
                            <br>
                            <div class="row" style="margin-top: 3%;">
                              <div class="col-md-4" style="margin-left:2%">
                                <label>Image</label>
                                <div class="content">
                                  <!--  <img src = "https://www.fuse.tv/image/56fe73a1e05e186b2000009b/768/512/the-boxer-rebellion-ocean-by-ocean-album-cover-full-size.jpg" width="100%" height=width>-->
                                  <input class="w3-input w3-border" type="file" name="file" accept="image/*">
                                </div>
                              </div>
                              <div class="col-md-2"></div>
                              <div class="col-md-5">
                                  <p>Description <span class="w3-badge w3-blue"></span></p>
<!--                                          <c:set var="des" value="{selectedPlaylist.des}"/>
                                          <span id="description-length">${fn:length(des)}</span>/300</span></p>-->
                                <textarea type="playlist_description" rows="14" cols="30">${selectedPlaylist.des}</textarea>
                              </div>
                            </div>
                            <div style="text-align:center;margin-top: 4%;">
                              <button id ="cancel_edit_button"  class="w3-button w3-round-xxlarge w3-black"> Cancel</button>
                              <button type="submit" class="w3-button w3-round-xxlarge w3-green" style="margin-left: 40px;">Save</button>
                            </div>
                          </form:form>
                        </div>
                      </div>
                    </div>
                    <c:if test="${selectedPlaylist.powner == user}">
                    <button id="remove_playlist_button" type="button" class="w3-bar-item w3-button playlist_action_dropdown">Delete</button>
                    </c:if>
                    <div id="remove_playlist_modal" class="w3-modal">
                      <div class="w3-modal-content">
                        <div class="w3-container w3-grey" >
                          <span onclick="document.getElementById('remove_playlist_modal').style.display='none'" class="w3-button w3-display-topright">&times;</span>
                          <div style="text-align:center;">
                            <p>Do you really want to remove this playlist?</p>
                            <div>
                              <button id ="cancel_delete_button" class="w3-button w3-round-xxlarge w3-black"> Cancel</button>
                              <button id="delete_playlist_confirm_button" class="w3-button w3-round-xxlarge w3-red" style="margin-left: 40px;"> Delete</button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <hr>
                     <!--
                    <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Create Similar Playlist</button>
                   
                    <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Download</button> 
                    <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Share</button>
                    -->
                  </div>
                </div>
              </div>
              <div class="col-md-5"></div>
              <div class="col-md-3">
                <p id="playlist-follower"><c:out value="${selectedPlaylist.followers}"></c:out> </p> <p>FOLLOWERS</p>
                
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- filter input div block -->
      <!--  <div id="filter_and_download">
        <div class="filter_left" style="width:50%;">
          <div class="input-group" id="filter_container">
            <span class="input-group-addon" id="search_span">
            <i class="fa fa-search" aria-hidden="true"></i>
            </span>-->
            <!-- <input type="search"  id="filter_keyword" name="q" onkeyup="filterAlbum()" placeholder="Filter"> -->
            <!-- <input placeholder= "filter" class="w3-input w3-border w3-animate-input" type="search" style="width:30%">
          </div>
        </div>
        <div class="download_right"style="width: 20%;margin-top:1%;">
          <p3 class="suggestion-topic" style="font-size: 1.5em;">Download</p3>
        </div>
      </div>-->
    </div>
    </div>
    <script>
      //filter by texts entered
      function filterAlbum()
      {
      
      }
      //sort by keyword handler
      $('#sorted_by_keyword').on('change',function(){
        //alert(this.value);
      });
    </script>
    <script>
      function dropdownDisplay(song_div) {
          var x = document.getElementById(song_div);
          if (x.className.indexOf("w3-show") == -1) {
              x.className += " w3-show";
          } else {
              x.className = x.className.replace(" w3-show", "");
          }
      }
    </script>
    <!--  Container for track list -->
    <div class="suggestion-container" id="playlist-song-collection">
    <div id="top_content">
		<p style="display: inline; font-size: 18px; margin-right:2%;">Sorted by</p>
		<select id="sort_song_by_keyword" class="styled-select black rounded"
			style="display: inline; font-size: 18px;">
			<option id="sort_by" value=""></option>
			<option id="sort_by" value="Title">Title</option>
		</select>
	</div>
	<br>
      <div class="table-responsive">
        <table class="table">
          <thead>
            <tr>
              <th></th>  <!-- play/pause button -->
              <th>TITLE</th>
            
              <th>
                <p> ARTIST</p>
              </th>
              <th>
                <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
              </th>
              <th> </th>
              <!-- more button -->
              <!--   <th> <p> <i class="fa fa-clock-o" aria-hidden="true"></i> </p></th> -->
            </tr>
          </thead>
          <tbody>
          <%
          	JSONArray data = (JSONArray)session.getAttribute("jsonList");
    		System.out.println("shhshada");
            System.out.println("data length " + data.length());
           
            for (int i = 0; i < data.length(); i++)
            {
            	JSONObject obj = data.getJSONObject(i);
            	
           %>
           	<tr class= "song_info" id = "songID<%=obj.get("songID")%>">  
              <td><button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">
                <i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i>
                  <span class="song-id-in-playlist" style="display:none"><%= obj.get("songID") %></span>
                  </button>
              </td>
            
<!--               not implemented 
              <td>  <button class="unstyle-buttons tracklist-save-button" data-toggle="tooltip-save" title="Remove from Your Library"  onclick="removeSong()">
                <i class="material-icons"><span class="tracklist-add-delete-button">done</span></i>
                </button>
              </td>-->
              <td>
                <p> <%= obj.get("songTitle") %> </p>
              </td>
              <td>
                <p> 
                	<% JSONArray artist = data.getJSONObject(i).getJSONArray("songArtist");
                		for (int j = 0; j < artist.length(); j++)
                		{
                			JSONObject obj1 = artist.getJSONObject(j);
                			%>
                                        <div class="artist-item medium-boxes-description"><a href="#"><%= obj1.getString("name")%></a>
                                        <span style="display:none;" class="artist-email"><%= obj1.getString("aemail")%></span></div>
                			
                			<% 
                		}
                	%>
          
               </p>
              </td>
        
              <td>
                <p> 2017-09-29</p>
              </td>
           
              <td>
                <div class="w3-dropdown-click more_action_list">
                  <button class="w3-button more_button"><i class="fa fa-ellipsis-h" aria-hidden="true"></i></button>
                  <div class="w3-dropdown-content w3-bar-block w3-border song_action_list">
                    <button class="w3-bar-item w3-button add-to-queue-btn" id = "add-to-queue-btn">Add to Queue<span style="display:none;" class="song-id"><%= obj.get("songID") %></button> 
                    
<!--                    <button onclick="" class="w3-bar-item w3-button">Go to Song Radio</button>-->
                    <hr>
                    <button onclick="" class="w3-bar-item w3-button">Go to Artist</button> 
                    <button onclick="" class="w3-bar-item w3-button">Go to Album</button>
                    <hr>
                    <!-- not implemented -->
<!--                    <button onclick="" class="w3-bar-item w3-button">Remove from Your Library</button>-->
             
                    <div class="w3-dropdown-hover">
                     <button onclick="" class="w3-bar-item w3-button add-to-playlist-button dropdown1">Add to Playlist</button>
                      <div class="w3-dropdown-content w3-bar-block w3-border add-to-playlists-section">
                        <!--
                          <button onclick="" class="w3-bar-item w3-button">Go to Song Radio</button>
                                   <hr>
                                   <button onclick="" class="w3-bar-item w3-button">Go to Artist</button>
                                   <button onclick="" class="w3-bar-item w3-button">Go to Album</button>
                                 -->
                        <button onclick="" class="w3-bar-item w3-button">Create New Playlist</button>
                        <hr>
                        <c:if test="${not empty user_playlist}">
                          <ul class="nav nav-stacked flex-column" >
                            <c:forEach var="playlist" items="${user_playlist}">
                              <c:if test="${playlist.powner == user}">
                               <c:if test="${playlist != selectedPlaylist}">
                                <div class="add-to-playlist-item">
                                  <button onclick="" class="w3-bar-item w3-button">${playlist.pname}</button>
                                  <span class="song-id" style="display:none;"><%= obj.getInt("songID") %></span>
                                  <span class="add-song-to-playlist-id" style="display:none;">${playlist.pid}</span>
                                </div>
                                </c:if>
                              </c:if>
                            </c:forEach>
                          </ul>
                        </c:if>
                      </div>
                    </div>
                    
                    <div class="remove-song-item">
                         <button onclick="" class="w3-bar-item w3-button removeSongInPlaylistButton">Remove from this playlist</button>
                         <span class="song-id" style="display:none;"><%= obj.getInt("songID") %></span>
                         <span class="playlist-id" style="display:none;">${selectedPlaylist.pid}</span>
                     </div>
      
                    <button onclick="" class="w3-bar-item w3-button">Share</button>
                  </div>
                </div>
              </td>
            </tr>
           <%
            }
            %>
          
       
          </tbody>
        </table>
      </div>
    </div>
  </body>
</html>