$(document).ready(function(){
	
	var text = $("#search", this).val();
	$("#search-keyword").append(text);
	$("#search").val("");
	$.ajax({
	    url: "${cp}/../search",
	    type: "POST",
	    data : {"input" : text},
	    asyn: false,
	    cache: true,
	    success : function(response)
	    {
	    	if(response.length == 0){
	    		$("#search-keyword").empty();
	    		$("#search-keyword").append("Please enter a keyword");
	    		$("#results").remove();
	    	}
	    	else if(response == "empty"){
	    		$("#search-keyword").empty();
	    		$("#search-keyword").append("No Results Found");
	    		$("#results").remove();
	    	}
	    	else{
	    		console.log(response);
		    	var actualJson = JSON.parse(response);
		    	songTable(actualJson[0]);
		    	albumTable(actualJson[1]);
	    	}
	    },
	    error: function(e)
	    {
	
	      console.log("error");
	    }
	});
});


function songTable(jsonString)
{
	console.log(jsonString);
	var num = jsonString.length;
	var songs = [];
	for (var i = 0; i < num; i++)
		{
			if (songs.indexOf(jsonString[i]['songID']) == -1) //check to see if there is duplicate songID
			{
				$("#song-search-table").find('tbody').append([
					'<tr>',
					'<td><button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">',
	                '<i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button>',
	              '</td>',
	              '<td> <button class="unstyle-buttons tracklist-save-button" data-toggle="tooltip-save" title="Remove from Your Library"  onclick="removeSong()">',
	                '<i class="material-icons"><span class="tracklist-add-delete-button">done</span></i>',
	                '</button>',
	              '</td>',
					  '<td>' + jsonString[i]['songTitle'] + '</td>',
					  '<td>' +  '</td>',
					  '<td>' + '</td>',
					  '<td>' + '</td>',
					  '<td>' + '</td>',
					  '<td>' + '</td>',
					  
					 '</tr>'
				].join(''));
				songs.push(jsonString[i]['songID']);
			}

		}
}

function albumTable(json){
	
}