$(document).ready(function(){
	$.ajax({
        url: "${cp}/../getSpecificAlbumInfo",
        type: "POST",
        asyn: false,
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
	var songs = [];
	for (var i = 0; i < num; i++)
		{
			if (songs.indexOf(data[i]['songID']) == -1) //check to see if there is duplicate songID
			{
				$("#pending-songs-table").find('tbody').append([
					'<tr>',					  
					  '<td>' + data[i]['songTitle'] + '</td>',
					  '<td>' + data[i]['songArtist'].join() + '</td>',
					  '<td>' + data[i]['songGenre'] + '</td>',
					 '</tr>'
				].join(''));
				songs.push(data[i]['songID']);
			}

		}
}