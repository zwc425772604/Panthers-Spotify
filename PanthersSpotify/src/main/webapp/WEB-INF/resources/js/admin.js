
$(document).ready(function(){
	
		
	$.ajax({			
        url: "{cp}/../loadUserTables",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
          console.log(response);
          var actual_JSON = JSON.parse(response);
          insertUserTables(actual_JSON);
         // $("#main-changing-content").load("jsp/songs.jsp");
        },
        error: function(e)
        {
          
          console.log(e);               
        }
      });
	 
	 $.ajax({
	        url: "{cp}/../loadAlbum",
	        type: "POST",
	        asyn: false,
	        cache: true,
	        success : function(response)
	        {
	          console.log(response);
	          console.log("load album done");
	        //  $("#main-changing-content").load("jsp/album.jsp");
	        },
	        error: function(e)
	        {
	         
	          console.log(e);               
	        }
	      });
	 $.ajax({
	        url: "{cp}/../loadAllPlaylists",
	        type: "POST",
	        asyn: false,
	        cache: true,
	        success : function(response)
	        {
	          console.log(response);
	          var actual_JSON = JSON.parse(response);
	          insertPlaylistTables(actual_JSON);
	        //  $("#main-changing-content").load("jsp/album.jsp");
	        },
	        error: function(e)
	        {
	         
	          console.log(e);               
	        }
	      });
});

function insertUserTables(data)
{
	
	for (var i = 0; i < data.length; i++)
		{
			$("#users-table").find('tbody').append([
				'<tr class="user-items w3-blue-grey">',
				  '<td>' + data[i]['userID'] + '</td>',
				  '<td>' + data[i]['userFirstName'] + '</td>',
				  '<td>' + data[i]['userLastName'] + '</td>',
				  '<td>' + data[i]['userType'] + '</td>',
				  '<td>' + data[i]['username'] + '</td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons">delete_forever</i></button></td>',
				 '</tr>' 
			].join(''));
		}
	

}

function insertPlaylistTables(data)
{
	for (var i = 0; i < data.length; i++)
		{
			$("#playlists-table").find('tbody').append([
				'<tr>',
				  '<td>' + data[i]['playlistID'] + '</td>',
				  '<td>' + data[i]['playlistName'] + '</td>',
				  '<td>' + data[i]['playlistOwner'] + '</td>',
				  '<td>' + data[i]['playlistNumSongs'] + '</td>',
				  '<td>' + data[i]['playlistNumFollowers'] + '</td>',
				  '<td>' + data[i]['playlistCreateDate'] + '</td>',
				  '<td><button class="unstyle-buttons delete-playlist-button"  data-toggle="tooltip-play" title="Remove this playlist"> <i class="material-icons">delete_forever</i></button></td>',
				 '</tr>' 
			].join(''));
		}
}


$(document).ready ( function () {
    $(document).on ("click", ".delete-user-button", function () {
    	var userID = $(this).closest('tr').children('td:eq(0)').text();
    	console.log("userId is : " + userID);
    	 $.ajax({
 	        url: "{cp}/../deleteSelectedUserAccount",
 	        data: {"userID" : userID},
 	        type: "POST",
 	        asyn: false,
 	        cache: false,
 	        success : function(response)
 	        {
 	          console.log(response);
 	         
 	        },
 	        error: function(e)
 	        {
 	         
 	          console.log(e);               
 	        }
 	      });
    });
});

$(document).on ("click", ".delete-playlist-button", function () {
	var pid = $(this).closest('tr').children('td:eq(0)').text();
	console.log("pid is : " + pid);
	 $.ajax({
	        url: "{cp}/../deleteSelectedPlaylist",
	        data: {"playlistID" : pid},
	        type: "POST",
	        asyn: false,
	        cache: false,
	        success : function(response)
	        {
	          console.log(response);
	         
	        },
	        error: function(e)
	        {
	         
	          console.log(e);               
	        }
	      });
});








