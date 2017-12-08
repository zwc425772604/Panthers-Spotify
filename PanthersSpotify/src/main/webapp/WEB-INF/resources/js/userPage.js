var hoverEnabled = true;
$(document).ready(function(){
	var email = $(".friends-email",this).text();
	console.log(email);
	$.ajax({
        url: "${cp}/../getUserHistory",
        type: "POST",
        asyn: false,
        data: {"email" : email},
        cache: false,
        success : function(response)
        {
        	if(response.localeCompare('private') == 0){
        		$("#song-table").empty();
        		$("#song-table").append("This user's history is set to Private");
        	}
        	else{
        		var actual_JSON = JSON.parse(response);
        		insertSongsTable(actual_JSON);
        	}
        	
        },
        error: function(e)
        {
          console.log(e);
        }
      });
      
      $.ajax({
        url: "${cp}/../getFriendPlaylist",
        type: "POST",
        asyn: false,
        data: {"email" : email},
        cache: false,
        success : function(response)
        {
        	console.log(response);
                var actual_JSON = JSON.parse(response);
        	insertFriendPlaylist(actual_JSON);
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
		var songArtist = data[i]['songArtist'][0]['name'];
		var albumName = data[i]['albumName'];
		var albumID = data[i]['albumId'];
		var date = data[i]['songReleaseDate'];
		$("#song-table").find('tbody').append([
			'<tr>',	
				'<td style="padding-bottom:0; padding-top:0.8em; padding-left:1em;"> <button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">',
	            '<i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button>',
	           '</td>',
			  '<td>' + songTitle + '</td>',
			  '<td class="album-item"> ' +albumName+ '<span class="album-ID" style="display: none;">' +albumID + '</span></td>',
			  '<td class="artist-item">' + songArtist + '<span style="display:none;" class="artist-email">'+  data[i]['songArtist'][0]['aemail'] +'</span>',
			  '</td>',
			  '<td>' + date +' </td>',	
			 '</tr>'
		].join(''));
	}
}
function insertFriendPlaylist(data)
{   
        console.log("insert playlist table called");
	var num = data.length;
	for(i = 0; i < num; i++){
		var playlistName = data[i]['playlistName'];
		var numSongs = data[i]['playlistNumSongs'];
		var numFollowers = data[i]['playlistNumFollowers'];
		var playlistID = data[i]['playlistID'];
		var date = data[i]['playlistCreateDate'];
		$("#playlist-table").find('tbody').append([
			'<tr>',	
				'<td style="padding-bottom:0; padding-top:0.8em; padding-left:1em;"> <button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">',
	            '<i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button>',
	           '</td>',
			  '<td><div class="playlist-item"><a href="#">' + playlistName + '</a><span style="display:none;" class="playlist_id">' + playlistID + '</span></div></td>',
			  '<td class="album-item"> ' +numSongs+ '</td>',
			  '<td class="artist-item">' + numFollowers +'</span>',
			  '</td>',
			  '<td>' + date +' </td>',	
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



$(document).on("click", ".album-item", function(){
	var albumId = $(".album-ID", this).text(); //get the pid of the playlist
	console.log(albumId);
	
	  $.ajax({
        url: "${cp}/../getSpecificAlbum",
        type: "POST",
        data : {"albumID" : albumId},
        asyn: false,
        cache: false,
        success : function(response)
        {
          $("#main-changing-content").load("jsp/albumInfo.jsp");
        },
        error: function(e)
        {
          console.log(e);
        }
	  });
});

