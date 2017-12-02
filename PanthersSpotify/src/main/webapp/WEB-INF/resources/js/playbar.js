$(document).ready(function(){

	player = $("#jquery_jplayer_1").jPlayer({
		ready: function (event) {
			$(this).jPlayer("setMedia", {
				title: "Bubble",
				// m4a: "http://jplayer.org/audio/m4a/Miaow-07-Bubble.m4a",
				// oga: "http://jplayer.org/audio/ogg/Miaow-07-Bubble.ogg",
          mp3: "${cp}/../resources/data/audios/Artists/Jay-Z@gmail.com/Jay-Z,Alicia Keys - Empire State Of Mind.mp3"

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
//	          pause: "#playbar-pause-button",
//                  repeat: "#playbar-repeat-button",
//	          stop: "#stop",
//	          mute: "#mute",
//	          unmute: "#unmute",
//	          currentTime: "#currentTime",
//	         duration: "#duration"
	        },
	});
	
	player.on($.jPlayer.event.play, function(e){
		 $(".play-pause-button").text("pause_circle_filled");	
	});
        player.on($.jPlayer.event.pause, function(e){
		 $(".play-pause-button").text("play_circle_filled");	
	});
 
});


