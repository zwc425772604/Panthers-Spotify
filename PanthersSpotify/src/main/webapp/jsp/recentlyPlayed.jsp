<!--  Container for DISCOVER -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<head>
  <script src="${cp}/resources/js/recentlyPlayed.js"></script>
   <script src="${cp}/resources/js/playbar.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <link rel="stylesheet" href="${cp}/resources/css/recentlyPlayed.css">
  <style>
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
    margin-left: -100%;
    margin-top: -10%;
    }
    .add-to-playlists-section
    {
    margin-left: -100%;
    background-color: gray;
    margin-top: -60%;
    }
  </style>
</head>
<script>
  function dropdownDisplay(song_div) {
      var x = document.getElementById(song_div);
      if (x.className.indexOf("w3-show") == -1) {
          x.className += " w3-show";
      } else {
          x.className = x.className.replace(" w3-show", "");
      }
  }
  
  function filterAlbum()
  {
  
  }
  //sort by keyword handler
  $('#sorted_by_keyword').on('change',function(){
   //alert(this.value);
  });
  
  var hoverEnabled = true;
  $(document).ready(function(){
   $(".more_action_list").css('visibility','hidden'); //hide the ... button when page is loaded
   $(".playbar-play-button").css('visibility','hidden'); //hide the play button when page is loaded
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
       $(".more_action_list",this).css('visibility','visible');
       $(".playbar-play-button",this).css('visibility','visible');
     }
  
   },
   mouseout: function() {
     if (hoverEnabled)
     {
       $(".more_action_list",this).css('visibility','hidden');
       $(".playbar-play-button",this).css('visibility','hidden');
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
  $(".add-to-playlist-item").click(function(){
  	var songID = $(".song-id", this).text();
  	var playlistID = $(".add-song-to-playlist-id",this).text();
  	  $.ajax({
            url: "${cp}/addSongToPlaylist",
            type: "POST",
            data : {"playlistID" : playlistID, "songID" : songID },
            asyn: false,
            cache: false,
            success : function(response)
            {
              console.log(response);
  
            },
            error: function(e)
            {
              console.log(e);
            }
  	  });
  });
</script>
<div class="recentlyPlayed-top" id = "recentlyPlayed-container" >
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic" style="font-size: 4em;">Recently Played</h3>
  </div>
  <div class = "">
    <button class="btn formButton" onclick="playSong();">  <span class="playingStatus"> PLAY </span></button>
  </div>
  <!-- filter input div block -->

</div>
<!--  Container for track list -->
<div id="song-collection">
      <div class="table-responsive">
        <table class="table" id="recently-play-song-table">
          <thead>
            <tr>
              <th id="th-play-button"></th>
              <!-- play/pause button -->
              <th id="th-song-title">
                <p> TITLE </p>
              </th>
              <th id="th-album">ALBUM</th>
              <th id="th-artist">ARTIST</th>
              <th id="th-date">
                <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
              </th>
            </tr>
          </thead>
          <tbody>
          
          </tbody>
        </table>
      </div>

    </div>