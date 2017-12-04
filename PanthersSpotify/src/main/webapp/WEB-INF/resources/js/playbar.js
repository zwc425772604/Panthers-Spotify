var player;
var result;
var isShuffle = false;
$(document).ready(function(){
    
	 $.ajax({
            url: "${cp}/../getSongQueue",
            type: "POST",
            asyn: false,
            cache: false,
            success : function(response)
            {
            	console.log("getSong queuee:  "+ response);
            	 var actual_json = JSON.parse(response);
                 result = actual_json;
                 updateSongInfo(actual_json.nowPlay);
//            	 addToPlaybarPlaylist(actual_json);
//            	 player.jPlayer("pause");
            	 updatePlayerButton(actual_json);
            },
            error: function(e)
            {
              console.log(e);
            }
    	}); 
	player = $("#jquery_jplayer_1").jPlayer({
		ready: function (event) {
			$(this).jPlayer("setMedia", {
//				title: "Bubble",
                            // mp3: "${cp}/../resources/data/audios/Artists/Jay-Z@gmail.com/Jay-Z,Alicia Keys - Empire State Of Mind.mp3"
                            mp3: "${cp}/../" + result.nowPlay.song['songPath']
			});
		},
		swfPath: "${cp}/resources/js",
		supplied: "mp3",
		wmode: "window",
		useStateClassSkin: true,
		autoBlur: false,
		smoothPlayBar: true,
		keyEnabled: true,
		remainingDuration: false,
		toggleDuration: true,
		 cssSelectorAncestor: "",
	        cssSelector: {
	          play: "#playbar-play-button",
	          next: "#playbar-next-button",

	        },
	});
//	var cssSelector = { jPlayer: "#jquery_jplayer_1", cssSelectorAncestor: "#jp_container_1" };
//	var playlist = []; // Empty playlist
//	var options = { swfPath: "/js", supplied: "ogv, m4v, oga, mp3" };
//	var myPlaylist = new jPlayerPlaylist(cssSelector, playlist, options);
	
	player.on($.jPlayer.event.play, function(e){
		 $(".play-pause-button").text("pause_circle_filled");
		 $(".playingStatus").text("PAUSE");
	});
        player.on($.jPlayer.event.pause, function(e){
		 $(".play-pause-button").text("play_circle_filled");
		 $(".playingStatus").text("PLAY");
	});
        player.on($.jPlayer.event.ended, function(e){
           console.log("isShuffle value " + isShuffle);
          if (isShuffle)
          {
            console.log("need to shuffle song");
            shuffleSongs();
          }
          else
          {
              playNextSong();
          }
        });
        
          
        
});

var currentIndex;
var numSong;
var myPlaylist;
function addToPlaybarPlaylist(song_json)
{
	myPlaylist = [];
	numSong = song_json.length;
	var songObject = song_json.nowPlay;
	console.log("songObject is " + songObject.song['songPath']);
	myPlaylist.push({mp3 : "${cp}/../" + songObject.song['songPath']});
	player.jPlayer("setMedia", myPlaylist[0]);
	player.jPlayer("play");
}
  
$(document).on("click", "#playbar-forward-button", function () {
    var currentTime = $('#jquery_jplayer_1').data('jPlayer').status.currentTime;
    var songDuration = $('#jquery_jplayer_1').data('jPlayer').status.duration;
        if (currentTime < songDuration - 15) {
            $("#jquery_jplayer_1").jPlayer("play", currentTime + 15);     
        }
//        else{
//            playNextSong();
//        }
  });
$(document).on("click", "#playbar-backward-button", function () {
    var currentTime = $('#jquery_jplayer_1').data('jPlayer').status.currentTime;
        if (currentTime > 15) {
            $("#jquery_jplayer_1").jPlayer("play", currentTime - 15);     
        }  
  });
  
  $(document).on("click", "#playbar-shuffle-button", function () {
      isShuffle = !isShuffle;
  if(isShuffle){
    $("#shuffle-icon").css("color","green");
  }
  else{
    $("#shuffle-icon").css("color","");
  }
  shuffleSongs(); //call this method after the song ended
  });
  
  

 function shuffleSongs(){
$.ajax({
    url: "${cp}/../shuffle",
    type: "POST",
    data:{"isShuffle":isShuffle},
    asyn: false,
    cache: false,
    success : function(response)
    {
      console.log("shuffle song json response" + response);
      result = JSON.parse(response);
      console.log("shuffle song response is " + result );
      //playNextSong();
      var queueJSP = document.getElementById("queueDiv");
      if (queueJSP !== null){
        $.get("jsp/queue.jsp", function(data) {
              $("#main-changing-content").html(data)
          });
      }
    },
    error: function(e)
    {

      console.log(e);
    }
  } );
}


function playPreviousSong(){
  console.log("play prev");
//  playPreviousSong();
  $.ajax({
        url: "${cp}/../preSong",
        type: "POST",
        
        asyn: false,
        cache: true,
        success : function(response)
        {
          console.log("pre song in js response : "+ response);
          result = JSON.parse(response);
          addToPlaybarPlaylist(result);
          updateSongInfo(result.nowPlay);
          updatePlayerButton(result);
          //player.jPlayer("play");
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
}

function playNextSong(){
  console.log("play next");
//  playNextSong();

    if((result.nextUp).length !=0)
    {
   
  $.ajax({
        url: "${cp}/../nextSong",
        type: "POST",
        data:{"isShuffle":isShuffle},
        asyn: false,
        cache: true,
        success : function(response)
        {
          //console.log("next:  "+response);
          console.log("next button json response " + response );
          result = JSON.parse(response);
          console.log("next song js response " + result);
          addToPlaybarPlaylist(result);
          updateSongInfo(result.nowPlay);
          //player.jPlayer("play");
          updatePlayerButton(result);
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
  }
  else
  {
      player.jPlayer("stop");
  }
}
    
	




$(document).on("click", "#playbar-prev-button", function () {
    playPreviousSong();
    });
	

$(document).on("click", "#playbar-next-button", function () {
	if (isShuffle)
   {
        console.log("need to shuffle song");
        shuffleSongs();
   }
   else
   {
        playNextSong();
   }
});

function updateSongInfo(nowPlay)
{
	var songName = nowPlay.song['title'];
	console.log(songName);
	$("#playbar-song-name").html(songName);
	$("#playbar-artist-name").empty();
	var artistArray = nowPlay.artists;
	for (var i = 0; i < artistArray.length; i++)
	{
            console.log(artistArray[i]['name']);
            $("#playbar-artist-name").append(
                    '<div class="artist-item medium-boxes-description"><a href="#">' + artistArray[i]['name'] +  '</a>' +
                    '<span style="display:none;" class="artist-email">' + artistArray[i]['email'] + '</span></div>'
                    );
	}
	//$("#playbar-artist-name");
	
}

function updatePlayerButton(actual_json){
	console.log("update player button " + actual_json);
	console.log("update player button now play" + actual_json.nowPlay);
	console.log("update player button previous song" + actual_json.previous);
	console.log("update player button next song" + actual_json.nextUp);
	if (actual_json.nowPlay.song==null){
		$("#playbar-play-button").prop("disabled",true);
	}else{
		$("#playbar-play-button").prop("disabled",false);
	}
	if (actual_json.previous.song==null){
		$("#playbar-prev-button").prop("disabled",true);
	}else{
		$("#playbar-prev-button").prop("disabled",false);
	}
	if ((actual_json.nextUp).length==0){
		$("#playbar-next-button").prop("disabled",true);
	}else{
		$("#playbar-next-button").prop("disabled",false);
	}
}
