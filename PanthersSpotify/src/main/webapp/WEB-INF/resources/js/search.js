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
				$("#song-table").find('tbody').append([
					'<tr>',
					  '<td>' + '</td>',
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