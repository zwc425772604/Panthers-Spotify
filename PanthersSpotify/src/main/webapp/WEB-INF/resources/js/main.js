$(document).ready(function(){
//hide all other containers in <div id= 'middle-content'> beside except overview_container
//$("#overview_container").siblings().hide();
	//Loading Friend List for the user
	$.ajax({
        url: "${cp}/../getUserFriendList",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
        	console.log(response);
        	var actual_JSON = JSON.parse(response);
        	insertFriends(actual_JSON);
        },
        error: function(e)
        {
          console.log(e);
        }
	});

	//Loading top followed playlist
	$.ajax({
        url: "${cp}/../getOverviewPlaylist",
        type: "POST",
        asyn: false,
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

//	$(".playlist-item").click(function(){
//		var pid = $(".playlist_id", this).text(); //get the pid of the playlist
//		  $.ajax({
//	          url: "${cp}/../getSpecificPlaylist",
//	          type: "POST",
//	          data : {"playlist_id" : pid },
//	          asyn: true,
//	          cache: true,
//	          success : function(response)
//	          {
//	            console.log(response);
//	            $("#main-changing-content").load("jsp/playlist.jsp");
//	          },
//	          error: function(e)
//	          {
//	            console.log(e);
//	          }
//		  });
//	});

	$("#new_playlist_button").click(function(){
	 	 $('#dialog').dialog({
	          //autoOpen: true,
	          height: 430,
	          width: 700,
	          modal: true,
	          resizable: false,
	          background: "#2f2f2f",
	          //color: Black,
	          //closeOnEscape: false,
	          //open: function(event, ui) { $(".ui-dialog-titlebar-close", ui).hide(); }
	          //closeOnEscape: false,
	          //beforeclose: function (event, ui) { return false; },
	          dialogClass: 'no-close'
	     });
	  //     window.open("browse.jsp","_blank");
	});

	$("#addFriendButton").click(function(){
	 	 $('#addFriendDialog').dialog({
	          //autoOpen: true,
	          height: 550,
	          width: 450,
	          modal: true,
	          resizable: false,
	          dialogClass: 'no-close'
	     });
	});
	
	$("#ad-close").click(function(){
		$("#advertisement").hide();
	})
	
});


function genre_page(){
	$("#middle-container").load("jsp/genre.jsp");
}

function newrelease_page(){
	$("#middle-container").load("jsp/newsRelease.jsp");
}

function overview_page(){
	$("#middle-container").load("jsp/overview.jsp");
}

function chart_page(){
	$("#middle-container").load("jsp/charts.jsp");
}

function discover_page(){
	$("#middle-container").load("jsp/discover.jsp");
}


$(document).on("click", ".right-col-friends-name", function(){
		var username = $(".right-col-friends-name", this).text(); //get the pid of the playlist
		var email = $(".friends-email",this).text();
		  $.ajax({
	        url: "${cp}/../getUserPage",
	        type: "POST",
	        data : {"username" : username, "userEmail" : email },
	        asyn: true,
	        cache: true,
	        success : function(response)
	        {
	          console.log(response);
	          $("#main-changing-content").load("jsp/userPage.jsp");
	        },
	        error: function(e)
	        {
	          console.log(e);
	        }
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
          console.log("playlist reponse "+response);
          //$("#main-changing-content").load("jsp/playlist.jsp");
          $.get("jsp/playlist.jsp", function(data) {
              $("#main-changing-content").html(data)
          });
        },
        error: function(e)
        {
          console.log(e);
        }
	  });
});


$(document).on("click", "#playbar-queue-button", function () {
	$.ajax({
        url: "${cp}/../getSongQueue",
        type: "POST",
        asyn: false,
        cache: false,
        success : function(response)
        {
        	console.log("getSong queuee:  "+ response);
        	 $.get("jsp/queue.jsp", function(data) {
                 $("#main-changing-content").html(data)
             });
        },
        error: function(e)
        {
          console.log(e);
        }
	});
	
	//$("#main-changing-content").load("jsp/queue.jsp");
  	
});

$(document).on("submit", "#findFriendForm", function (event) {
	event.preventDefault();
	var userEmail = $("#addFriendUserEmail").val();
	console.log("userEmail in find friend is " + userEmail);
	$.ajax({
        url: "${cp}/../findFriend",
        type: "POST",
        data : {"userEmail" : userEmail },
        asyn: false,
        cache: false,
        success : function(response)
        {
         	console.log(response);
        	if (response.localeCompare("ok") == 0){
        		$('#addFriendDialog').dialog('close');
        		$("#main-changing-content").load("jsp/userPage.jsp");
        	}else{
        		$("#findFriendMessage").text("no user found");
        		console.log("aha");
        	}

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
	else if (nav_name.localeCompare('recently_played') == 0)
	{
		   $("#main-changing-content").load("jsp/recentlyPlayed.jsp");
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
	          $("#main-changing-content").load("jsp/songs.jsp");
	        },
	        error: function(e)
	        {

	          console.log(e);
	        }
	      });
		
	}
	else
	{
	   $("#main-changing-content").load("jsp/overview.jsp");
	    }
}

function displayAccount(){
	$("#main-changing-content").load("jsp/account.jsp");
}

function displayUserAccount(){
	$("#main-changing-content").load("jsp/userAccount.jsp");
}
function displayUpgradeForm()
{
	$("#main-changing-content").load("jsp/pay.jsp");
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

audio.src = "${cp}/../resources/data/audios/Artists/Jay-Z@gmail.com/Jay-Z,Alicia Keys - Empire State Of Mind.mp3";

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


var volumeDrag = false;
$("#rangeinput").on('mousedown',function(e){
	volumeDrag = true;
	audio.muted = false;
	updateVolume(e.main);
	
});

function updateVolume(volume){
	console.log(volume);
	audio.volume = volume / 100;
}

var muted = false;
function volumeMute(data){
	var up = "<i id=\"volume-up\" class=\"material-icons\">volume_up</i>";
	var mute = "<i id=\"volume-off\" class=\"material-icons\">volume_off</i>";
	data.innerHTML = "";
	if(muted){
		data.innerHTML = up;
		audio.volume = 0.5;
		muted = false;
		$("#rangeinput").val(50);
	}else{
		data.innerHTML = mute;
		audio.volume = 0;
		muted = true;
		$("#rangeinput").val(0);
	}
	
}

function search()
{
	$("#main-changing-content").load("jsp/search.jsp");	
}

$(document).on("click", "#playbar-shuffle-button", function () {
	$.ajax({
        url: "${cp}/../shuffle",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
          console.log(response);
          var queueJSP = document.getElementById("queueDiv");
          if (queueJSP != null){
        	  $.get("jsp/queue.jsp", function(data) {
                  $("#main-changing-content").html(data)
              });
          }
        },
        error: function(e)
        {

          console.log(e);
        }
      });
})

$(document).on("click", "#playbar-prev-button", function () {
	$.ajax({
        url: "${cp}/../preSong",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
          //console.log("pre:  ..."+ response);
          updatePlayerButton(response);
          var queueJSP = document.getElementById("queueDiv");
          if (queueJSP != null){
        	  $.get("jsp/queue.jsp", function(data) {
                  $("#main-changing-content").html(data)
              });
          }
        },
        error: function(e)
        {

          console.log(e);
        }
      });
})

$(document).on("click", "#playbar-next-button", function () {
	$.ajax({
        url: "${cp}/../nextSong",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
          //console.log("next:  "+response);
          updatePlayerButton(response);
          var queueJSP = document.getElementById("queueDiv");
          if (queueJSP != null){
        	  $.get("jsp/queue.jsp", function(data) {
                  $("#main-changing-content").html(data)
              });
          }
        },
        error: function(e)
        {

          console.log(e);
        }
      });
})

function updatePlayerButton(data){
	var json = JSON.parse(data);
	if (json.nowPlay==null){
		$("#playbar-play-button").prop("disabled",true);
	}else{
		$("#playbar-play-button").prop("disabled",false);
	}
	if (json.previous.song==null){
		$("#playbar-prev-button").prop("disabled",true);
	}else{
		$("#playbar-prev-button").prop("disabled",false);
	}
	if (json.nextUp.length==0){
		$("#playbar-next-button").prop("disabled",true);
	}else{
		$("#playbar-next-button").prop("disabled",false);
	}
}