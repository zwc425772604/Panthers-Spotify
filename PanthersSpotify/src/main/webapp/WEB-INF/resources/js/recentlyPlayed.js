$(document).ready(function(){
	var email = "himself";
	$.ajax({
        url: "${cp}/../getCurrentUserHistory",
        type: "POST",
        asyn: false,
        data: {"email" : email},
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



function insertSongsTable(data)
{
	var num = data.length;
	for(i = 0; i < num; i++){
		var songTitle = data[i]['songTitle'];
		var songArtist = data[i]['songArtist'][0]['name'];
		var albumName = data[i]['albumName'];
		var albumID = data[i]['albumId'];
		var date = data[i]['songReleaseDate'];
		$("#recently-play-song-table").find('tbody').append([
			'<tr id="recently-play-song' + data[i]['songID'] + '">',	
			 '<td style="padding-bottom:0; padding-top:0.8em; padding-left:1em;"> <button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">',
               '<i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button>',
              '</td>',
			  '<td>' + songTitle + '</td>',
			  '<td class="album-item"> ' +albumName+ '<span class="album-ID" style="display: none;">' +albumID + '</span></td>',
			  '<td class="artist-item">' + songArtist + '<span style="display:none;" class="artist-email">'+  data[i]['songArtist'][0]['aemail'] +'</span>',
			  '</td>',
			  '<td>' +date+' </td>',			 
			 '</tr>'
		].join(''));
	}
}


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

