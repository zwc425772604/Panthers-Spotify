<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--  Container for DISCOVER -->
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
 
<style type="text/css">


  /* for the close button in input type search */
  input[type="search"]::-webkit-search-cancel-button
  {
    -webkit-appearance: searchfield-cancel-button;
  }

  #search_span
  {
    background-color: #343a40;
    color: white;
    border: none;
    color: #cccccc;
  }
  #filter_keyword
  {
    width: 90%;
    font-size: 16px;
      background-color: #343a40;
      border-color: inherit;
  -webkit-box-shadow: none;
  box-shadow: none;border:none;
  color: #cccccc;
  }
  #filter_container
  {
    margin-top: 3%;

  }

  .formButton
  {
      display:block;
       border-radius: 10px;
        color:white;
        width: 90px;
        background-color:#00cc00;
  }
  .song_action_list
  {
    background-color: gray;
    margin-left: 5%;
  }
  .playlist_action_dropdown
  {
    font-size: 10px;
  }

  .playlist_action_list
  {
    max-height:350px;
    overflow:scroll;
  }
  
  #filter_and_download .download_right
  {
  	float: right;
  } 
  #filter_and_download .filter_left
  {
  	float: left;
  } 
}


</style>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
   <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var hoverEnabled = true;
$(document).ready(function(){
  $(".more_action_list").hide(); //hide the ... button when page is loaded
  $(".playbar-play-button").hide(); //hide the play button when page is loaded
  $("#remove_playlist_button").click(function(){

	 	
	 	$("#remove_playlist_modal").show();
	});
  $("#delete_playlist_confirm_button").click(function(){
	 
	  $("#remove_playlist_modal").hide();
	  var pid = $("#playlist_id").text();
	  $.ajax({
          url: "${cp}/removeSpecificPlaylist",
          type: "POST",
          data : {"playlist_id" : pid },
          asyn: true,
          cache: false,
          success : function(response)
          {
            console.log(response);
           // $("#main-changing-content").load("jsp/playlist.jsp");
            window.location.replace("http://localhost:10455/PanthersSpotify/main");
          },
          error: function(e)
          {
            console.log(e);
         
          }
    
        });
  });
  $("#cancel_delete_button").click(function(){
	  $("#remove_playlist_modal").hide();
  });
});

//style for the filter container
$( "#filter_container" )
.mouseover(function() {
  $('#filter_keyword').css('color', 'white');
  $('#search_span').css('color','white');
})
.mouseout(function(){
  $('#filter_keyword').css('color','#cccccc');
  $('#search_span').css('color','#cccccc');
});

//style for the filter keyword input tag
$( "#filter_keyword" )
.focus(function(){
  $('#filter_keyword').css('background-color','gray');
  $('#search_span').css('background-color','gray');
});
$("#filter_keyword").blur(function(){
  $('#filter_keyword').css('background-color','#343a40');
  $('#search_span').css('background-color','#343a40');
  });

//toggle between add and remove in the song lists
$(".tracklist-save-button")
.mouseover(function()
{
  $(".tracklist-add-delete-button",this).text("clear");
})
.mouseout(function()
{
  $(".tracklist-add-delete-button",this).text("done");
});


$( ".song_info" ).bind({
  mouseover: function() {
    if (hoverEnabled)
    {
      $(".more_action_list",this).show();
      $(".playbar-play-button",this).show();
    }

  },
  mouseout: function() {
    if (hoverEnabled)
    {
      $(".more_action_list",this).hide();
      $(".playbar-play-button",this).hide();
    }

  }
});

$(".more_button").click(function()
{
  var x =$(this).siblings( ".song_action_list");
  if($(this).siblings( ".song_action_list").hasClass('w3-show') == false)
  {
    $(this).siblings( ".song_action_list").addClass("w3-show");
  }
  else {
    $(this).siblings( ".song_action_list").removeClass("w3-show");
  }
  hoverEnabled = !hoverEnabled;

});
$(".playlist_header_more_button").click(function()
{
  var x =$(this).siblings( ".playlist_action_list");
  if($(this).siblings( ".playlist_action_list").hasClass('w3-show') == false)
  {
    $(this).siblings( ".playlist_action_list").addClass("w3-show");
  }
  else {
    $(this).siblings( ".playlist_action_list").removeClass("w3-show");
  }
  hoverEnabled = !hoverEnabled;
  //alert("clicked");
});


// handler for play/pause button in the song list
$(".playbar-play-button").click(function(){

    if (isPlaying)
    {
      // $(".song-page-play-pause-button",this).text("play_circle_filled");
      if ($(".song-page-play-pause-button",this).text().localeCompare('play_circle_filled') == 0)
      {
        isPlaying = false;
        $(".song-page-play-pause-button").text("play_circle_filled");
        $(".song-page-play-pause-button",this).text("pause_circle_filled");
        playSong();
      }
      else
      {
          $(".song-page-play-pause-button",this).text("play_circle_filled");
          playSong();
      }
    }
    else
    {
      playSong();

      $(".song-page-play-pause-button").text("play_circle_filled");
      $(".song-page-play-pause-button",this).text("pause_circle_filled");
    }

});



</script>

<div class="suggestion-container" id = "release-container" style="margin-top: 3%;">

  <!-- <div class="suggestion-container-top">
    <h3 class="suggestion-topic" style="font-size: 4em;">Songs</h3>
  </div>
  <div class = "">
      <button class="btn formButton" onclick="playSong();">  <span class="playingStatus"> PLAY </span></button>
  </div> -->
  <div class="suggestion-container-top">
    
      <div class="playlist_image_box" style="width: 20%">
        <img src="https://www.fuse.tv/image/56fe73a1e05e186b2000009b/768/512/the-boxer-rebellion-ocean-by-ocean-album-cover-full-size.jpg" width=100% height=width class="img-rounded" alt="Generic placeholder thumbnail">
      </div>
      <div class="playlist_details_box" style="width:70%">
        <div id ="playlist-info" style="margin-top: 4%; margin-left:5%;">
          <h5> Playlist </h5>
          <p style="font-size: 1.8em;"><c:out value="${selected_playlist.pname}"></c:out>  </p>
          <p id="playlist_id" style="display:none;"><c:out value="${selected_playlist.pid}"></c:out>  </p>
          <p style="font-size: 1.2em;"><c:out value="${selected_playlist.des}"></c:out>  </p>
          <p style="font-size: 1.1em;"> Created by: <a href="#"> <c:out value="${selected_playlist.powner.uname}"></c:out> </a> ⋅ <span id="num_song"> 1 song </span> , <span id="total_length"> 4 min 26 sec </span></p>
          <div class="row">

          <div class="col-md-3" style="display:inline;">
            <button class="btn formButton" onclick="playSong();">  <span class="playingStatus"> PLAY </span></button>
          </div>
     
          <div class="col-md-3">
            <div class="w3-dropdown-click playlist_header_more_action_list">
              <button class="w3-button playlist_header_more_button" title="More" ><i class="fa fa-ellipsis-h" aria-hidden="true"></i></button>
              <div class="w3-dropdown-content w3-bar-block w3-border playlist_action_list">
                <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Go to Playlist Radio</button>
                <hr>
                <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Collaborative Playlist</button> <!-- a song might have multiple artists -->
                <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Make Secret</button>
                <hr>
                <button id = "edit_playlist_button" class="w3-bar-item w3-button playlist_action_dropdown">Edit Details</button>
                <button class="w3-bar-item w3-button playlist_action_dropdown" disabled>Report</button> <!-- w3css hover dropdown -->
                <button id="remove_playlist_button" type="button" class="w3-bar-item w3-button playlist_action_dropdown">Delete</button>
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
                <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Create Similar Playlist</button>
                <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Download</button> <!-- w3css hover dropdown -->
                <button onclick="" class="w3-bar-item w3-button playlist_action_dropdown">Share</button>
              </div>
            </div>
          </div>
          <div class="col-md-5"></div>
          <div class="col-md-1">
          	<p>FOLLOWERS</p>
          	<p><c:out value="${selected_playlist.followers}"></c:out></p>
          	</div>
          </div>
        </div>
      </div>
    
    
  </div>


  <!-- filter input div block -->
  <div id="filter_and_download">
	    <div class="filter_left" style="width:50%;">
	      <div class="input-group" id="filter_container">
	        <span class="input-group-addon" id="search_span">
	            <i class="fa fa-search" aria-hidden="true"></i>
	        </span>
	      <!-- <input type="search"  id="filter_keyword" name="q" onkeyup="filterAlbum()" placeholder="Filter"> -->
	        <input placeholder= "filter" class="w3-input w3-border w3-animate-input" type="search" style="width:30%">
	      </div>
	
	     </div>
     
	     <div class="download_right"style="width: 20%;margin-top:1%;">
	         <p3 class="suggestion-topic" style="font-size: 1.5em;">Download</p3>
	     </div>
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
  <div class="suggestion-container" style="margin-top: 5%;" id = "charts-container">
    <div class="table-responsive">
      <table class="table">
        <thead>
          <tr>
            <th></th> <!-- play/pause button -->
            <th></th> <!-- add/remove button -->
            <th> <p> TITLE </p> </th>
            <th> <p> ARTIST</p> </th>
            <th> <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p></th>
            <th> </th> <!-- more button -->
          <!--   <th> <p> <i class="fa fa-clock-o" aria-hidden="true"></i> </p></th> -->
          </tr>
        </thead>
        <tbody>
          <tr class= "song_info">
            <td><button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">
               <i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button></td>
            <td>  <button class="unstyle-buttons tracklist-save-button" data-toggle="tooltip-save" title="Remove from Your Library"  onclick="removeSong()">
                <i class="material-icons"><span class="tracklist-add-delete-button">done</span></i>
              </button></td>
            <td><p> HUMBLE. </p></td>
            <td><p> <a href="#"> Kendrick Lamar </a> </p></td>
            <!-- <td><p> <a href="#"> DAMN. </a></p></td> -->
            <td>  <p> 2017-09-29</p></td>
            <!-- ... button dropdown -->
            <td>
              <div class="w3-dropdown-click more_action_list">
                <button class="w3-button more_button"><i class="fa fa-ellipsis-h" aria-hidden="true"></i></button>
                <div class="w3-dropdown-content w3-bar-block w3-border song_action_list">
                  <button onclick="" class="w3-bar-item w3-button">Add to Queue</button>
                  <button onclick="" class="w3-bar-item w3-button">Go to Song Radio</button>
                  <hr>
                  <button onclick="" class="w3-bar-item w3-button">Go to Artist</button> <!-- a song might have multiple artists -->
                  <button onclick="" class="w3-bar-item w3-button">Go to Album</button>
                  <hr>
                  <button onclick="" class="w3-bar-item w3-button">Remove from Your Library</button>
                  <button onclick="" class="w3-bar-item w3-button">Add to Playlist</button> <!-- w3css hover dropdown -->
                  <button onclick="" class="w3-bar-item w3-button">Share</button>
                </div>
              </div>

            </td>
            <!-- <td><p> 2:57 </p> </td> -->
          </tr>

          <tr class= "song_info">
            <td><button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">
               <i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button></td>
            <td>  <button class="unstyle-buttons tracklist-save-button" data-toggle="tooltip-save" title="Remove from Your Library" onclick="removeSong()">
                <i class="material-icons"><span class="tracklist-add-delete-button">done</span></i>
              </button></td>
            <td><p> DNA </p></td>
            <td><p> <a href="#"> Kendrick Lamar </a> </p></td>
           <!--  <td><p> <a href="#"> DAMN. </a> </p></td> -->
            <td><p> 2017-09-29</p></td>
            <td>
              <div class="w3-dropdown-click more_action_list">
                <button class="w3-button more_button"><i class="fa fa-ellipsis-h" aria-hidden="true"></i></button>
                <div class="w3-dropdown-content w3-bar-block w3-border song_action_list">
                  <button onclick="" class="w3-bar-item w3-button">Add to Queue</button>
                  <button onclick="" class="w3-bar-item w3-button">Go to Song Radio</button>
                  <hr>
                  <button onclick="" class="w3-bar-item w3-button">Go to Artist</button> <!-- a song might have multiple artists -->
                  <button onclick="" class="w3-bar-item w3-button">Go to Album</button>
                  <hr>
                  <button onclick="" class="w3-bar-item w3-button">Remove from Your Library</button>
                  <button onclick="" class="w3-bar-item w3-button">Add to Playlist</button> <!-- w3css hover dropdown -->
                  <button onclick="" class="w3-bar-item w3-button">Share</button>
                </div>
              </div>

            </td>
          <!--   <td><p> 3:06 </p> </td> -->
          </tr>

          <tr class= "song_info">
            <td><button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">
               <i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button></td>
            <td>  <button class="unstyle-buttons tracklist-save-button" data-toggle="tooltip-save" title="Remove from Your Library" onclick="removeSong()">
                <i class="material-icons"><span class="tracklist-add-delete-button">done</span></i>
              </button></td>
            <td><p> Hiphop Lover </p></td>
            <td><p> <a href="#"> Dok2 </a></p></td>
            <!-- <td><p> <a href="#"> Reborn </a> </p></td> -->
            <td>  <p> 2017-09-28</p></td>
            <td>
              <div class="w3-dropdown-click more_action_list">
                <button  class="w3-button more_button"><i class="fa fa-ellipsis-h" aria-hidden="true"></i></button>
                <div class="w3-dropdown-content w3-bar-block w3-border song_action_list">
                  <button onclick="" class="w3-bar-item w3-button">Add to Queue</button>
                  <button onclick="" class="w3-bar-item w3-button">Go to Song Radio</button>
                  <hr>
                  <button onclick="" class="w3-bar-item w3-button">Go to Artist</button> <!-- a song might have multiple artists -->
                  <button onclick="" class="w3-bar-item w3-button">Go to Album</button>
                  <hr>
                  <button onclick="" class="w3-bar-item w3-button">Remove from Your Library</button>
                  <button onclick="" class="w3-bar-item w3-button">Add to Playlist</button> <!-- w3css hover dropdown -->
                  <button onclick="" class="w3-bar-item w3-button">Share</button>
                </div>
              </div>

            </td>
           <!--  <td><p> 3:19 </p> </td> -->
          </tr>
        </tbody>
      </table>
    </div>

  </div>
	
</body>
</html>