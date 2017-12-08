$(document).ready(function(){
	var text = $("#search", this).val();
	$("#search-keyword").append(text);
	$("#search").val("");
	$.ajax({
	    url: "${cp}/../search",
	    type: "POST",
	    data : {"input" : text},
	    asyn: false,
	    cache: false,
	    success : function(response)
	    {
	    	if(response.length == 0){
	    		$("#search-keyword").empty();
	    		$("#search-albums").empty();
	    		$("#search-playlists").empty();
	    		$("#search-keyword").append("Please enter a keyword");
	    		$("#results").remove();
	    	}
	    	else if(response == "empty"){
	    		$("#search-keyword").empty();
	    		$("#search-albums").empty();
	    		$("#search-playlists").empty();
	    		$("#search-keyword").append("No Results Found");
	    		$("#results").remove();
	    	}
	    	else{
	    		console.log(response);
		    	var actualJson = JSON.parse(response);
		    	if(actualJson[0].length == 0){
		    		$("#search-songs").empty();
		    	}
		    	else{	    		
		    		songTable(actualJson[0]);
		    	}
		    	if(actualJson[1].length == 0){
		    		$("#search-albums").empty();
		    	}
		    	else{	    		
		    		
		    	}
		    	if(actualJson[2].length == 0){
		    		$("#search-playlists").empty();
		    	}
		    	else{	    		
		    		
		    	}
	    	}
	    },
	    error: function(e)
	    {	
	      console.log("error");
	    }
	});
	
});

$(document).on("click", ".album-item", function(){
	var albumId = $(".album-ID", this).text(); //get the pid of the playlist
	console.log(albumId);
	
	  $.ajax({
        url: "${cp}/../getSpecificAlbum",
        type: "POST",
        data : {"albumID" : albumId},
        asyn: false,
        cache: true,
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

function songTable(jsonString)
{
	console.log(jsonString);
	var num = jsonString.length;
	var songs = [];
	for (var i = 0; i < num; i++)
		{
			if (songs.indexOf(jsonString[i]['songID']) == -1) //check to see if there is duplicate songID
			{
				$("#song-table").find('tbody').append([
					'<tr>',
					'<td style="padding-bottom:0; padding-top:0.8em; padding-left:1em;"> <button class="unstyle-buttons playbar-play-button" data-toggle="tooltip-play" title="Play">',
	                    '<i class="material-icons"><span class="song-page-play-pause-button">play_circle_filled</span></i></button>',
	                 '</td>',
					  '<td>' + jsonString[i]['songTitle'] + '</td>',		  
					 '</tr>'
				].join(''));
				songs.push(jsonString[i]['songID']);
			}

		}
}

function albumTable(json){

}