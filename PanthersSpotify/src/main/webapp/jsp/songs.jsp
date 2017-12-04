<!--  Container for DISCOVER -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<head>
  <!-- 	<link rel="stylesheet" href="${cp}/resources/css/songs.css"> -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://www.w3schools.com/lib/w3.js"></script> <!-- for include html in div tag -->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="${cp}/resources/js/songs.js" /></script>
  <link rel="stylesheet" href="${cp}/resources/css/songs.css">
</head>
<div class="suggestion-container" id = "release-container" style="margin-top: 3%;">
  <div class="suggestion-container-top ">
    <h3 class="suggestion-topic" style="font-size: 4em;">Song</h3>
  </div>
  <div class = "">
    <button class="btn formButton" onclick="playSong();">  <span class="playingStatus"> PLAY </span></button>
  </div>
  <!-- filter input div block -->
  <div class="input-group" id="filter_container">
    <span class="input-group-addon" id="search_span">
    <i class="fa fa-search" aria-hidden="true"></i>
    </span>
    <input type="search" id="filter_keyword" name="q" oninput="w3.filterHTML('#songs-table-info', '.song_info', this.value)" placeholder="Filter">
  </div>
</div>
<!--  Container for track list -->
<div class="suggestion-container" id = "charts-container">
  <div class="table-responsive">
    <table class="table" id="songs-table-info">
      <thead>
        <tr>
          <th></th>
          <!-- play/pause button -->
          <th></th>
          <!-- add/remove button -->
          <th>TITLE</th>
          <th>ALBUM</th>
          <th>ARTIST</th>
          <th>
            <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
          </th>
          <th> </th>
          <!-- more button -->
          <th>
            <p> <i class="fa fa-clock-o" aria-hidden="true"></i> </p>
          </th>
        </tr>
      </thead>
      <tbody>
        <c:if test="${not empty songs}">
          <c:forEach var="song" items="${songs}">
            <tr class="song_info">
              <td><button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">
                <i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button>
              </td>
              <td> <button class="unstyle-buttons tracklist-save-button" data-toggle="tooltip-save" title="Remove from Your Library"  onclick="removeSong()">
                <i class="material-icons"><span class="tracklist-add-delete-button">done</span></i>
                </button>
              </td>
              <td>
                <p>${song.stitle}</p>
              </td>
              <td>
                <p> <a href="#"></a> </p>
              </td>
              <td>
                <p> <a href="#"></a></p>
              </td>
              <td>
                <p></p>
              </td>
              <!-- ... button dropdown -->
              <td>
                <div class="w3-dropdown-click more_action_list">
                  <button class="w3-button more_button"><i class="fa fa-ellipsis-h" aria-hidden="true"></i></button>
                  <div class="w3-dropdown-content w3-bar-block w3-border song_action_list">
                      <button class="w3-bar-item w3-button add-to-queue-btn" id = "add-to-queue-btn">Add to Queue<span style="display:none;" class="song-id">${song.sid}</span></button> 
                    <button onclick="" class="w3-bar-item w3-button">Go to Song Radio</button>
                    <hr>
                    <button onclick="" class="w3-bar-item w3-button">Go to Artist</button> <!-- a song might have multiple artists -->
                    <button onclick="" class="w3-bar-item w3-button">Go to Album</button>
                    <hr>
                    <button onclick="" class="w3-bar-item w3-button">Remove from Your Library</button>
                    <!-- <button onclick="" class="w3-bar-item w3-button add-to-playlist-button dropdown1">Add to Playlist</button> --> <!-- w3css hover dropdown -->
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
                                <div class="add-to-playlist-item">
                                  <button onclick="" class="w3-bar-item w3-button">${playlist.pname}</button>
                                  <span class="song-id" style="display:none;">${song.sid}</span>
                                  <span class="add-song-to-playlist-id" style="display:none;">${playlist.pid}</span>
                                </div>
                              </c:if>
                            </c:forEach>
                          </ul>
                        </c:if>
                      </div>
                    </div>
                    <button onclick="" class="w3-bar-item w3-button">Share</button>
                  </div>
                </div>
              </td>
              <td>
                <p> </p>
              </td>
            </tr>
          </c:forEach>
        </c:if>
      </tbody>
    </table>
  </div>
</div>