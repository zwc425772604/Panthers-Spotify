var hoverEnabled = true;
$(document).ready(function(){
  // $.ajax({
  //       url: "{cp}/../getUserFriendList",
  //       type: "POST",
  //       asyn: false,
  //       cache: false,
  //       success : function(response)
  //       {
  //         console.log(response);
  //         var actual_JSON = JSON.parse(response);
  //         insertFriends(actual_JSON);
  //        // $("#main-changing-content").load("jsp/songs.jsp");
  //       },
  //       error: function(e)
  //       {
  //         console.log(e);
  //       }
  //     });


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

$(".AddFriendButton").click(function(){
	var status = $("#friendStatus").text().trim();
	var email = $("#friendEmail",this).text().trim();
	console.log("add friend button clicked");
	if (status.localeCompare('Add Friend') == 0 )
	{
		$.ajax({
			url: "addFriend",
			type: "POST",
			data: {"femail" : email},
			asyn: true,
			cache: false,
			success: function(response)
			{
				console.log(response);
				$("#friendStatus").html("Delete Friend");
				var friends = JSON.parse(response);
        insertFriends(friends);
			}
		});
	}
	else
	{
		$.ajax({
			url: "deleteFriend",
			type: "POST",
			data: {"femail" : email},
			asyn: true,
			cache: false,
			success: function(response)
			{
				console.log(response);
				$("#friendStatus").html("Add Friend");
			}
		});
	}

});

function insertFriends(data)
{
  $("#userFriendsList").empty();
  for (var i = 0; i < data.length; i++)
  {
    $("#userFriendsList").append([
      '<div class="right-col-section row">',
        '<div><img src = "http://weclipart.com/gimg/0A3C841B9FA4F2C6/13099629981030824019profile.svg.hi.png" class="rounded-circle right-col-image"',
        'alt = "Generic placeholder thumbnail"></div>',
        '<div class="right-col-desp"><div class = "right-col-friends-name">' + data[i]['username'] + '<span style="display:none;" class="friends-email">' + data[i]['userID'] + '</span>',
        '</div></div></div>'
    ].join(''));
  }
}
