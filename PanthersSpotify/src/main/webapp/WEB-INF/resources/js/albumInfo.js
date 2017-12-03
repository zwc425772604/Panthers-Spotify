$(document).ready(function(){
	console.log("HI");
	$.ajax({
        url: "${cp}/../getSpecificAlbumInfo",
        type: "POST",
        asyn: false,
        cache: false,
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


$(document).on("click", ".artist-item", function(){
	var email = $(".artist-email", this).text(); //get the pid of the playlist
	  $.ajax({
        url: "${cp}/../getSpecificArtist",
        type: "POST",
        data : {"email" : email},
        asyn: false,
        cache: false,
        success : function(response)
        {
          $("#main-changing-content").load("jsp/artistInfo.jsp");
        },
        error: function(e)
        {
          console.log(e);
        }
	  });
});


function insertSongsTable(data)
{
	
	var num = data['songsInAlbum'].length;
	var albumName = data['albumName'];
	var albumID = data['albumID'];
	var albumPhotoUrl = data['albumPhotoUrl'];
	$(".album-image-box").empty();
	$(".album-image-box").append([		
	           '<img src="${cp}/../resources/data' +albumPhotoUrl +'" width=100% class="img-rounded" alt="Generic placeholder thumbnail" style="border-radius: 100px;">'
	].join(''));
	console.log("loaded photo");
	$("#album-name-place").empty();
	$("#album-hidden-id").empty();
	$("#album-name-place").append(albumName);
	$("#album-hidden-id").append(albumID);
	var artistLength = data['songArtist'].length;
	$("#album-artists").empty();
	for (var i = 0; i < artistLength - 1; i++)
		{			
			$("#album-artists").append([
				'<div class="artist-item">',
					'<div id="artist-name">' + data['songArtist'][i]['name'] + ', ' + '</div>',
					'<span class="artist-email" style="display:none;">' + data['songArtist'][i]['aemail'] + '</span>',
				'</div>'
				
			].join(''));
		}
	$("#album-artists").append([
		'<div class="artist-item">',
		'<div id="artist-name">' + data['songArtist'][artistLength - 1]['name'] +  '</div>',
		'<span class="artist-email" style="display:none;">' + data['songArtist'][artistLength - 1]['aemail'] + '</span>' + '</div>'
	].join(''));
	var songs = [];
	for (var i = 0; i < num; i++)
		{
			if (songs.indexOf(data['songsInAlbum'][i]['songID']) == -1) //check to see if there is duplicate songID
			{
				$("#song-table").find('tbody').append([
					'<tr">',	
					  '<td style="padding-bottom:0; padding-top:0.8em; padding-left:1em;"> <button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">',
	                     '<i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button>',
	                  '</td>',
					  '<td>' + data['songsInAlbum'][i]['songTitle'] + '</td>',
					  '<td> </td>',
					  '<td> </td>',
					 '</tr>'
				].join(''));
			}

		}
}