
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



