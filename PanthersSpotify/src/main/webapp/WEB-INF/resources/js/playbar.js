
$(document).ready(function(){
	
	player = $("#jquery_jplayer_1").jPlayer({
		ready: function (event) {
			$(this).jPlayer("setMedia", {
//				title: "Bubble",
				// m4a: "http://jplayer.org/audio/m4a/Miaow-07-Bubble.m4a",
				// oga: "http://jplayer.org/audio/ogg/Miaow-07-Bubble.ogg",
          //mp3: "${cp}/../resources/data/audios/Artists/Jay-Z@gmail.com/Jay-Z,Alicia Keys - Empire State Of Mind.mp3"

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
//	          pause: "#playbar-pause-button",
//                  repeat: "#playbar-repeat-button",
//	          stop: "#stop",
//	          mute: "#mute",
//	          unmute: "#unmute",
//	          currentTime: "#currentTime",
//	         duration: "#duration"
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
    				playNextSong();
    	});
        
        
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
            	 var actual_json = JSON.parse(response);
            	 addToPlaybarPlaylist(actual_json);
            	 player.jPlayer("pause");
            	 updatePlayerButton(actual_json);
            },
            error: function(e)
            {
              console.log(e);
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
//	for (var i = 0; i < numSong; i++)
//		{
//			console.log(song_json[i]['songTitle']);
//			myPlaylist.push({mp3:"${cp}/../" + song_json[i]['songPath']});	
//		}
//	currentIndex = 0;
	myPlaylist.push({mp3 : "${cp}/../" + songObject.song['songPath']});
	player.jPlayer("setMedia", myPlaylist[0]);
	//player.jPlayer("play");
}



//function playNextSong()
//{
//	if (currentIndex < numSong)
//		{
//			currentIndex += 1;
//			player.jPlayer("setMedia", myPlaylist[currentIndex]);
//			player.jPlayer("play");
//		}
//}
//
//function playPreviousSong()
//{
//	if (currentIndex > 0)
//	{
//		currentIndex -= 1;
//		player.jPlayer("setMedia", myPlaylist[currentIndex]);
//		player.jPlayer("play");
//	}
//	else
//	{
//		player.jPlayer("pause");
//	}
//}

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

/$(document).on("click", "#playbar-prev-button", function () {
	console.log("play prev");
//	playPreviousSong();
	$.ajax({
        url: "${cp}/../preSong",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
          console.log("pre song in js response : "+ response);
          var actual_json = JSON.parse(response);
          addToPlaybarPlaylist(actual_json);
          updatePlayerButton(actual_json);
          player.jPlayer("play");
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

$(document).on("click","#playbar-play-button",function (){
	
	
	
})



$(document).on("click", "#playbar-next-button", function () {
	console.log("play next");
//	playNextSong();
	$.ajax({
        url: "${cp}/../nextSong",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
          //console.log("next:  "+response);
          console.log("next button json response " + response );
          var actual_json = JSON.parse(response);
          console.log("next song js response " + actual_json);
          addToPlaybarPlaylist(actual_json);
          player.jPlayer("play");
          updatePlayerButton(actual_json);
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



