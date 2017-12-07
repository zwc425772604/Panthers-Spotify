var hoverEnabled = true;
$(document).ready(function(){
	$(".more_action_list").css('visibility','hidden'); //hide the ... button when page is loaded
	$(".playbar-play-button").css('visibility','hidden'); //hide the play button when page is loaded
	
});

$(document).on("change","#sort_song_by_keyword", function(){
	console.log("do u want to sort?");
	$.ajax({
		url: "${cp}/../sortSongInPlaylist",
        type: "POST",
        asyn: false,
        cache: false,
        success : function(response)
        {
        	console.log("what the result: "+response);
        	$("#main-changing-content").load("jsp/playlist.jsp");
        },
        error: function(e)
        {
          console.log(e);
        }     
     });
})


$(document).on("click","#remove_playlist_button",function(){
	console.log("daddy love u!!!!!!");
 	$("#remove_playlist_modal").show();
});
$(document).on("click","#edit_playlist_button", function(){
	
 	
 	$("#edit_playlist_modal").show();
});
$(document).on("click","#delete_playlist_confirm_button",function(){
	
  $("#remove_playlist_modal").hide();
  var pid = $("#playlistID").text();
  $.ajax({
      url: "${cp}/../removeSpecificPlaylist",
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
$(document).on("click","#cancel_delete_button", function(){
  $("#remove_playlist_modal").hide();
});
$(document).on("click","#cancel_edit_button", function(){
  event.preventDefault(); document.getElementById('edit_playlist_modal').style.display='none';
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



$(document).on("click",".remove-song-item", function(){
	var songID = $(".song-id", this).text();
	var playlistID = $(".playlist-id", this).text();
	console.log("song id to remove is " + songID);
	console.log("playlistID is " + playlistID);
	 $.ajax({
         url: "${cp}/../removeSongFromPlaylist",
         type: "POST",
         data : {"playlistID" : playlistID, "songID" : songID },
         asyn: false,
         cache: false,
         success : function(response)
         {
        	 var songDiv = "#songID"+songID;
             console.log("song id: "+ songDiv);
             $(songDiv).remove();
             console.log(response);
         },
         error: function(e)
         {
           console.log(e);
         }
	  });
});

$(document).on("click","#play-playlist-button", function(){
    var pid = $("#playlistID").text().trim();
    var status = $(".playingStatus",this).text().trim();
    console.log("play button status is: "+ status);
	console.log("pid to play is " + pid);
	if (status=="PLAY"){
		 $.ajax({
	          url: "${cp}/../playPlaylist ",
	          type: "POST",
	          data : {"pid" : pid},
	          asyn: false,
	          cache: false,
	          success : function(response)
	          {
	            console.log(response);
	            var actual_json = JSON.parse(response);
	            result = actual_json;
	            console.log(actual_json);
	            addToPlaybarPlaylist(actual_json);
	            updateSongInfo(result.nowPlay);
	            $(".playingStatus",this).text("PAUSE");
	            //updateButtons()
	          },
	          error: function(e)
	          {
	            console.log(e);
	          }
		  });
	}else{
		player.jPlayer("pause");
	}
});

$(document).on("click",".playbar-play-button", function(){
    console.log('song in playlist is clicked');
    var sid = $(".song-id-in-playlist",this).text().trim();
    console.log("sond Id to play in playlist is " + sid);
    var pid = $("#playlistID").text().trim();
    console.log("playlist ID to play is " + pid );
     if ($(".song-page-play-pause-button",this).text().localeCompare('play_circle_filled') == 0)
     {
       $(".song-page-play-pause-button").text("play_circle_filled");
       $(".song-page-play-pause-button",this).text("pause_circle_filled");
       //playSongInPlaylist();
       $.ajax({
           url: "${cp}/../playPlaylist ",
           type: "POST",
           data : {"pid" : pid, "sid":sid},
           asyn: false,
           cache: false,
           success : function(response)
           {
             console.log("song in queue: "+ response);
             var actual_json = JSON.parse(response);
             updateSongInfo(actual_json.nowPlay);
             console.log(actual_json);
             addToPlaybarPlaylist(actual_json);
             //updateButtons()
           },
           error: function(e)
           {
             console.log(e);
           }
  	  });
        player.jPlayer("play");
     }
     else
     {
        $(".song-page-play-pause-button",this).text("play_circle_filled");
        player.jPlayer("pause");
     }
   });
//   else
//   {
//     player.jPlayer("play");
//     $(".song-page-play-pause-button").text("play_circle_filled");
//     $(".song-page-play-pause-button",this).text("pause_circle_filled");
//   }

function appendPlaylistToPlaylistSection(data)
{
    console.log("append playlist hehrherhrhisorheishr");
    $("#user-playlist-section").prepend('<li class="nav-item playlist-item" id="playlistID'+data[0]['playlistId']+'"><a class="nav-link color-nav">' + data[0]['playlistName'] + 
            '</a><span style="display:none;" class="playlist_id">' + data[0]['playlistId'] + '</span></li>');
}

$(document).on("click",".playlist_header_more_button",function()
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

$(document).on("click",".followPlaylistButton", function(){
	var status = $("#followingPlaylistStatus").text().trim();
	var pid = $("#playlistID").text().trim();
	console.log(status);
	console.log("follow playlist button clicked");
	if (status.localeCompare('FOLLOW') == 0 )
	{
		$.ajax({
			url: "followSpecificPlaylist",
			type: "POST",
			data: {"playlistID" : pid},
			asyn: false,
			cache: false,
			success: function(response)
			{
				console.log(response);
				$("#followingPlaylistStatus").html("UNFOLLOW");
                var playlist_json = JSON.parse(response);
                appendPlaylistToPlaylistSection(playlist_json);
                var cn = parseInt($("#playlist-follower").text());
				var cn = cn + 1;
				console.log(cn);
				$("#playlist-follower").empty();
				$("#playlist-follower").html(cn);
			}
		});
	}
	else
	{
		$.ajax({
			url: "unfollowSpecificPlaylist",
			type: "POST",
			data: {"playlistID" : pid},
			asyn: false,
			cache: false,
			success: function(response)
			{
				console.log("what the fk... "+ response);
				$("#followingPlaylistStatus").html("FOLLOW");
                var playlistToRemove = "#playlistID" + pid;
                $(playlistToRemove).remove();
                var cn = parseInt($("#playlist-follower").text());
				var cn = cn - 1;
				console.log(cn);
				$("#playlist-follower").empty();
				$("#playlist-follower").html(cn);
			}
		});
	}
});

