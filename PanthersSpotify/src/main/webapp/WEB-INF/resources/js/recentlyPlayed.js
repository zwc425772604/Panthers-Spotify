$(document).ready(function(){
	var email = "himself";
	$.ajax({
        url: "${cp}/../getUserHistory",
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
		$("#song-table").find('tbody').append([
			'<tr>',	
			 '<td style="padding-bottom:0; padding-top:0.8em; padding-left:1em;"> <button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">',
               '<i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button>',
              '</td>',
			  '<td>' + songTitle + '</td>',
			  '<td> </td>',
			  '<td> </td>',
			 '</tr>'
		].join(''));
	}
}
