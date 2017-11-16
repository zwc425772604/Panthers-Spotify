
$(document).ready(function(){
//hide all other containers in <div id= 'middle-content'> beside except overview_container
//$("#overview_container").siblings().hide();
	$.ajax({
        url: "${cp}/../getUserFriendList",
        type: "POST",
        asyn: true,
        cache: true,
        success : function(response)
        {
          //Template
          //var photoUrl = response.url
          //var playlistName = playlistName
          //var playlistDesc = playlistDesc       
           
        },
        error: function(e)
        {                	
          console.log(e);                 	
        }  	
	});	
	
	$.ajax({
        url: "${cp}/../getOverviewPlaylist",
        type: "POST",
        asyn: true,
        cache: true,
        success : function(response)
        {
          //Template
          //var photoUrl = response.url
          //var playlistName = playlistName
          //var playlistDesc = playlistDesc       
          $("#main-changing-content").load("jsp/browse.jsp");    
        },
        error: function(e)
        {                	
          console.log(e);                 	
        }  	
	});	
	
	$(".playlist-item").click(function(){
		var pid = $(".playlist_id", this).text(); //get the pid of the playlist
		  $.ajax({
	          url: "${cp}/../getSpecificPlaylist",
	          type: "POST",
	          data : {"playlist_id" : pid },
	          asyn: true,
	          cache: true,
	          success : function(response)
	          {
	            console.log(response);
	            $("#main-changing-content").load("jsp/playlist.jsp");
	          },
	          error: function(e)
	          {                	
	            console.log(e);                 	
	          }       
		  });
	});

	$("#new_playlist_button").click(function(){	
	 	 $('#dialog').dialog({
	          //autoOpen: true,
	          height: 550,
	          width: 450,
	          modal: true,
	          resizable: false,
	          //closeOnEscape: false,
	          //open: function(event, ui) { $(".ui-dialog-titlebar-close", ui).hide(); }
	          //closeOnEscape: false,
	          //beforeclose: function (event, ui) { return false; },
	          dialogClass: 'no-close'
	     });           
	  //     window.open("browse.jsp","_blank");
	});
});

$(document).on("click", ".playlist-item", function(){
	var pid = $(".playlist_id", this).text(); //get the pid of the playlist
	  $.ajax({
        url: "${cp}/../getSpecificPlaylist",
        type: "POST",
        data : {"playlist_id" : pid },
        asyn: true,
        cache: true,
        success : function(response)
        {
          console.log(response);
          $("#main-changing-content").load("jsp/playlist.jsp");
        },
        error: function(e)
        {                	
          console.log(e);                 	
        }       
	  });
});


function displayLeftNavbarContent(nav_name)
{
    //compare the string
	if (nav_name.localeCompare('browse') == 0)
	{
	  $("#main-changing-content").load("jsp/browse.jsp");
	}
	else if (nav_name.localeCompare('albums') == 0)
	{
		$.ajax({
	        url: "${cp}/../loadAlbum",
	        type: "POST",
	        asyn: false,
	        cache: true,
	        success : function(response)
	        {
	          console.log(response);	         
	        },
	        error: function(e)
	        {
	         
	          console.log(e);               
	        }
	      });
	   $("#main-changing-content").load("jsp/album.jsp");
	}
	else if (nav_name.localeCompare('artists') == 0)
	{
	   $("#main-changing-content").load("jsp/artist.jsp");
	}
	else if (nav_name.localeCompare('songs') == 0)
	{       	
	
		$.ajax({
	        url: "${cp}/../loadSong",
	        type: "POST",
	        asyn: false,
	        cache: true,
	        success : function(response)
	        {
	          console.log(response);	         
	        },
	        error: function(e)
	        {
	          
	          console.log(e);               
	        }
	      });
		 $("#main-changing-content").load("jsp/songs.jsp");
	}
	else
	{
	   $("#main-changing-content").load("jsp/overview.jsp");
	    }
}
	  
function displayAccount(){
	$("#main-changing-content").load("jsp/account.jsp");
}
  
function openDialogBox()
{
  //   $("#dialog").dialog();
	$('#dialog').dialog({
	  //autoOpen: true,
	  height: 550,
	  width: 450,
	  modal: true,
	  resizable: false,
	  //closeOnEscape: false,
	  //open: function(event, ui) { $(".ui-dialog-titlebar-close", ui).hide(); }
	  //closeOnEscape: false,
	  //beforeclose: function (event, ui) { return false; },
	  dialogClass: 'no-close'
	  });
}

var audio;
$(document).ready(function(){
  //  $('[data-toggle="tooltip"]').tooltip();
// $('#middle-content').load('overview.html');

audio = $("audio")[0];
var current = $('span.currentPos');
var time = $('div.timeBar');
var currentPos;
var maxduration;
var percentage;

audio.src = "data/audios/Faded.mp3";

$("audio").on("loadedmetadata", function() {
var t = secondsToTime(audio.duration);


$('#duration').text(t);

});
//get HTML5 video time duration
});

$(function() {
    $("#slider").slider({
      range: "min",
      min: 0,
      max: 100,
      classes: {
  "ui-slider": "highlight"
}
    });

    var startPos = $("#slider").slider("value");
    var   endPos = '';

    $("#slider").on("slidestop", function(event, ui) {
        playSong();
        endPos = ui.value;

        if (startPos != endPos) {
            // do stuff
            var length = endPos / 100;
            var currPos = audio.duration * length;
            audio.currentTime = currPos;
            $("#slider-range .ui-slider-range").css({"background":"green"});
        }

        startPos = endPos;
      playSong();
        //alert("startPos is " + startPos);

    });
});
var isPlaying = false;

function playSong()
{

  if (isPlaying)
  {
    isPlaying = false;
    $(".play-pause-button").text("play_circle_filled");
    $(".playingStatus").text("PLAY"); //change the button status in songs page
    audio.pause();
  }
  else
  {
    isPlaying = true;
    $(".play-pause-button").text("pause_circle_filled");
    $(".playingStatus").text("PAUSE"); //change the button status in songs page
    audio.play();
  }



}
function testPro()
{
  alert("haha");
}

//express seconds as minutes:seconds notation(02:36)
function secondsToTime(t)
{
  var currentSeconds = (Math.floor(t % 60) < 10 ? '0' : '') + Math.floor(t % 60)
  var currentMinutes = Math.floor(t / 60);
  return currentMinutes + ":" + currentSeconds;
}

function updateTime()
{
  // var currentSeconds = (Math.floor(audio.currentTime % 60) < 10 ? '0' : '') + Math.floor(audio.currentTime % 60);
  // var currentMinutes = Math.floor(audio.currentTime / 60);
  var t = secondsToTime(audio.currentTime);
  //Sets the current song location compared to the song duration.
//  document.getElementById('currentPos').innerHTML = currentMinutes + ":" + currentSeconds;
   document.getElementById('currentPos').innerHTML = t;
   if (audio.currentTime == audio.duration)
   {
     //alert("time is up");
     playSong();
   }
  //Fills out the slider with the appropriate position.
   var percentageOfSong = (audio.currentTime/audio.duration);
   var sliderValue = percentageOfSong * 100;
   $( "#slider" ).slider( "value",sliderValue );
  // var percentageOfSlider = document.getElementById('songSlider').offsetWidth * percentageOfSong;
  //
  // //Updates the track progress div.
  // document.getElementById('trackProgress').style.width = Math.round(percentageOfSlider) + "px";
}

function displayCharts()
{
  $('#middle-content').load('charts.html');
}
// $(document).ready(function(){
//   $("#overview_content").siblings().hide();
// });



