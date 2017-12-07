<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONException"%>
<c:set var="cp"
  value="${pageContext.request.servletContext.contextPath}"
  scope="request" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	JSONObject queue = (JSONObject)session.getAttribute("queueJSON");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-dark-grey.css">
<script src="${cp}/resources/js/queue.js"></script>
<div>
  <h1 style="color:white;">Play Queue</h1> 
</div>

<div id="queueDiv" class="w3-container info-table">
<% if (queue.length()==0)
	{
	%>
	<h3 align="center" style="color:white;">Your Queue is empty</h3>>
	<%
	}else
	{
		JSONObject nowPlay = queue.getJSONObject("nowPlay");
		if (nowPlay.length()==0){
			%>
			<h3 align="center" style="color:white;">Your Queue is empty</h3>>
			<%
		}else{
			JSONObject nowSong = nowPlay.getJSONObject("song");
	%>
 <div id = "nowPlayingSongDiv">
  <h3 style="color:white;">Now Playing</h3>
  <table class="w3-table-all w3-hoverable" id="now-playing-table">
    <thead>
      <tr class="w3-light-grey">
        <th></th>
        <!-- play/pause button -->
        <th></th>
        <!-- save to library -->
        <th>TITLE</th>
        <th>ARTIST</th>
        <th>ALBUM</th>
        <th></th>
        <!-- more -->
        <th></th>
        <!-- song duration -->
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><button class="unstyle-buttons playbar-play-button"
          data-toggle="tooltip-play" title="Play">
          <i class="material-icons"><span
            class="song-page-play-pause-button">play_circle_filled</span></i>
          </button>
        </td>
        <td><button class="unstyle-buttons tracklist-save-button"
          data-toggle="tooltip-save" title="Remove from Your Library"
          onclick="removeSong()">
          <i class="material-icons"><span
            class="tracklist-add-delete-button">done</span></i>
          </button>
        </td>
        <td> <%= nowSong.get("title") %> </td>
        <td>
			<% JSONArray nartists = nowPlay.getJSONArray("artists");
				for (int j = 0; j < nartists.length(); j++)
	    		{
	    			JSONObject obj1 = nartists.getJSONObject(j);
	    			%>
	    			<a  style="color:black;" href="{cp}/../getSpecificArtist/<%= obj1.getString("email")%>"> <%= obj1.getString("name") %> </a><br>
	    			<% 
	    		}
			%>
			
		</td>
        <td>
			<% JSONObject nalbum = nowPlay.getJSONObject("album");
				boolean hasNalbum = (nalbum.length()==0);
			%>
				<a  style="color:black;" href="{cp}/../getSpecificArtist/<%= hasNalbum ? "" : nalbum.get("id")%>"> <%= hasNalbum ? "unknown" : nalbum.get("name") %> </a><br>
		</td>
        <td>
          <button class="w3-button more_button">
          <i class="fa fa-ellipsis-h" aria-hidden="true"></i>
          </button>
        </td>
        <td></td>
      </tr>
    </tbody>
  </table>
</div>
  <br>
<div id ="nextPlayingSongDiv">
	<% JSONArray nextUp = (JSONArray)queue.getJSONArray("nextUp");
		if (nextUp.length()>0){
	%>
  <h3 style ="color:white;">Next Up</h3>
  <table class="w3-table-all w3-hoverable" id="next-playing-table">
    <thead>
      <tr class="w3-light-grey">
        <th></th>
        <!-- play/pause button -->
        <th></th>
        <!-- save to library -->
        <th>TITLE</th>
        <th>ARTIST</th>
        <th>ALBUM</th>
        <th></th>
        <!-- more -->
        <th></th>
        <!-- song duration -->
      </tr>
    </thead>
    <tbody>
    	<%
    		for ( int i=0; i<nextUp.length();i++){
    			JSONObject ns = nextUp.getJSONObject(i);
    			JSONObject nextSong = ns.getJSONObject("song");
    	%>
      <tr>
        <td><button class="unstyle-buttons playbar-play-button"
          data-toggle="tooltip-play" title="Play">
          <i class="material-icons"><span
            class="song-page-play-pause-button">play_circle_filled</span></i>
          </button>
        </td>
        <td><button class="unstyle-buttons tracklist-save-button"
          data-toggle="tooltip-save" title="Remove from Your Library"
          onclick="removeSong()">
          <i class="material-icons"><span
            class="tracklist-add-delete-button">done</span></i>
          </button>
        </td>
        <td>
        	<%= nextSong.get("title") %>
        </td>
        <td>
			<% JSONArray artists = ns.getJSONArray("artists");
				for (int j = 0; j < artists.length(); j++)
	    		{
	    			JSONObject obj1 = artists.getJSONObject(j);
	    			%>
	    			<a  style="color:black;" href="{cp}/../getSpecificArtist/<%= obj1.getString("email")%>"> <%= obj1.getString("name") %> </a><br>
	    			<% 
	    		}
			%>
		</td>
        <td><% JSONObject album = ns.getJSONObject("album"); 
        		boolean hasAlbum = (album.length()==0);
        	%>
				<a  style="color:black;" href=<%= hasAlbum ? "" : "{cp}/../getSpecificArtist/"+album.get("id") %>> <%= (album.length()==0) ? "unknown" : album.get("name") %> </a>
				<br>
		</td>
        <td>
          <button class="w3-button more_button">
          <i class="fa fa-ellipsis-h" aria-hidden="true"></i>
          </button>
        </td>
        <td></td>
      </tr>
      <% 
   			}
		}
      %>
    </tbody>
  </table>
</div>
</div>
<% 
		}
	}
%>
<div id="historyDiv" class="w3-container info-table"
  style="display: none">
  <table class="w3-table-all w3-hoverable" id="next-playing-table">
    <thead>
      <tr class="w3-light-grey">
        <th></th>
        <!-- play/pause button -->
        <th></th>
        <!-- save to library -->
        <th>TITLE</th>
        <th>ARTIST</th>
        <th>ALBUM</th>
        <th></th>
        <!-- more -->
        <th></th>
        <!-- song duration -->
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><button class="unstyle-buttons playbar-play-button"
          data-toggle="tooltip-play" title="Play">
          <i class="material-icons"><span
            class="song-page-play-pause-button">play_circle_filled</span></i>
          </button>
        </td>
        <td><button class="unstyle-buttons tracklist-save-button"
          data-toggle="tooltip-save" title="Remove from Your Library"
          onclick="removeSong()">
          <i class="material-icons"><span
            class="tracklist-add-delete-button">done</span></i>
          </button>
        </td>
        <td>Song Title</td>
        <td>Artist Name</td>
        <td>Album</td>
        <td>
          <button class="w3-button more_button">
          <i class="fa fa-ellipsis-h" aria-hidden="true"></i>
          </button>
        </td>
        <td>2:15</td>
      </tr>
    </tbody>
  </table>
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
