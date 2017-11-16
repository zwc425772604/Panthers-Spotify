var hoverEnabled = true;
$(document).ready(function(){
  $(".more_action_list").hide(); //hide the ... button when page is loaded
  $(".playbar-play-button").hide(); //hide the play button when page is loaded
  $("#remove_playlist_button").click(function(){

	 	
	 	$("#remove_playlist_modal").show();
	});
  $("#edit_playlist_button").click(function(){

	 	
	 	$("#edit_playlist_modal").show();
	});
  $("#delete_playlist_confirm_button").click(function(){
	 
	  $("#remove_playlist_modal").hide();
	  var pid = $("#playlistID").text();
	  $.ajax({
          url: "${cp}/removeSpecificPlaylist",
          type: "POST",
          data : {"playlistID" : pid },
          asyn: true,
          cache: false,
          success : function(response)
          {
            console.log(response);
           // $("#main-changing-content").load("jsp/playlist.jsp");
            window.location.replace("http://localhost:8080/PanthersSpotify/main");
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
  $("#cancel_edit_button").click(function(){
	
	  event.preventDefault(); document.getElementById('edit_playlist_modal').style.display='none';
  });
  
  
  $(".followPlaylistButton").click(function(){
		var status = $("#followingPlaylistStatus").text();
		var pid = $("#playlistID").text();
		
		if (status.localeCompare('FOLLOW') == 0 )
		{
			$.ajax({
				url: "followSpecificPlaylist",
				type: "POST",
				data: {"playlistID" : pid},
				asyn: true,
				cache: false,
				success: function(response)
				{
					console.log(response);
					$("#followingPlaylistStatus").html("UNFOLLOW");
				}
			});
		}
		else
		{
			$.ajax({
				url: "unfollowSpecificPlaylist",
				type: "POST",
				data: {"playlistID" : pid},
				asyn: true,
				cache: false,
				success: function(response)
				{
					console.log(response);
					$("#followingPlaylistStatus").html("FOLLOW");
				}
			});
		}
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




