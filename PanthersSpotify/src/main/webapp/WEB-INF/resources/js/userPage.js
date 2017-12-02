var hoverEnabled = true;
$(document).ready(function(){
	var email = $(".friends-email",this).text();
	$.ajax({
        url: "${cp}/../getUserHistory",
        type: "POST",
        asyn: false,
        data: {"email" : email},
        cache: true,
        success : function(response)
        {
        	var actual_JSON = JSON.parse(response);
        	insertSongsTable(actual_JSON);
        },
        error: function(e)
        {
          console.log(e);
        }
      });
});


function insertSongsTable(data)
{
	var num = data.length;
	for(i = 0; i < num; i++){
		var songTitle = data[i]['songTitle'];
		$("#song-table").find('tbody').append([
			'<tr>',	
			  '<td> </td>',
			  '<td>' + songTitle + '</td>',
			  '<td> </td>',
			  '<td> </td>',
			 '</tr>'
		].join(''));
	}
}

$(".AddFriendButton").click(function(){
	var status = $("#friendStatus").text().trim();
	var email = $("#friendEmail",this).text().trim();
	
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
				var friends = JSON.parse(response);
				insertFriends(friends);
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

