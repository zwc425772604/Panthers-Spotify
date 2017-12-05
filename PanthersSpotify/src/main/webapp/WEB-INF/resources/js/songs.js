function dropdownDisplay(song_div) {
      var x = document.getElementById(song_div);
      if (x.className.indexOf("w3-show") == -1) {
          x.className += " w3-show";
      } else {
          x.className = x.className.replace(" w3-show", "");
      }
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
            url: "${cp}/../addSongToPlaylist",
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