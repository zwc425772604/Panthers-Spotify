function displayPage()
{
	$("#main-changing-content").load("jsp/dashboard.jsp");
}
$(document).ready(function(){
        $.ajax({
        url: "{cp}/../displayCheckRoyalty",
        type: "POST",
        asyn: false,
        cache: false,
        success : function(response)
        {
         
          var actual_JSON = JSON.parse(response);
           console.log(actual_JSON);
           insertRevenueDetailTable(actual_JSON);
//          insertBasicUserTables(actual_JSON);
         // $("#main-changing-content").load("jsp/songs.jsp");
        },
        error: function(e)
        {
          console.log(e);
        }
      });

	$.ajax({
        url: "{cp}/../loadUserTables/0",
        type: "POST",
        asyn: false,
        cache: false,
        success : function(response)
        {
          //console.log(response);
          var actual_JSON = JSON.parse(response);
          insertBasicUserTables(actual_JSON);
         // $("#main-changing-content").load("jsp/songs.jsp");
        },
        error: function(e)
        {
          console.log(e);
        }
      });

	$.ajax({
		url: "{cp}/../loadUserTables/1",
		  type: "POST",
		  asyn: false,
		  cache: false,
		  success : function(response)
		  {
	         //console.log(response);
		    var actual_JSON = JSON.parse(response);
		    insertPremiumUserTables(actual_JSON);
		  },
		  error: function(e)
		  {
			  console.log(e);
		  }
	});
	$.ajax({
		url: "{cp}/../loadUserTables/2",
		type: "POST",
		asyn: false,
		cache: false,
		success : function(response)
			{
				var actual_JSON = JSON.parse(response);
				insertArtistTables(actual_JSON);
			},
		error: function(e)
		{
			console.log(e);
		}
	});

	$.ajax({
		url: "{cp}/../loadPendingSongs",
		type: "POST",
		asyn: false,
		cache: false,
		success : function(response)
			{
//				console.log(response);
				var actual_JSON = JSON.parse(response);
				insertPendingSongsTables(actual_JSON);
			},
		error: function(e)
		{
			console.log(e);
		}
	});

	$.ajax({
		url: "{cp}/../loadApprovedSongs",
		type: "POST",
		asyn: false,
		cache: false,
		success : function(response)
			{
//				console.log(response);
				var actual_JSON = JSON.parse(response);
				insertApprovedSongsTables(actual_JSON);
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
	        cache: false,
	        success : function(response)
	        {
                    var actual_JSON = JSON.parse(response);
                    insertAlbumTable(actual_JSON);
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
	        cache:false,
	        success : function(response)
	        {
	         // console.log(response);
	          var actual_JSON = JSON.parse(response);
	          insertPlaylistTables(actual_JSON);
	        //  $("#main-changing-content").load("jsp/album.jsp");
	        },
	        error: function(e)
	        {

	          console.log(e);
	        }
	      });
	 $.ajax({
	        url: "{cp}/../getInfo",
	        type: "POST",
	        asyn: false,
	        cache: false,
	        success : function(response)
	        {
	         
	          var actual_JSON = JSON.parse(response);
	           console.log(actual_JSON);
	           insertSiteStatisticTables(actual_JSON);
//	          insertBasicUserTables(actual_JSON);
	         // $("#main-changing-content").load("jsp/songs.jsp");
	        },
	        error: function(e)
	        {
	          console.log(e);
	        }
	      });
});

function insertSiteStatisticTables(data){
	$("#siteStatDiv").empty();
	var numSong = data['numSongs'];
	numSong = numSong + 1000;
	$("#siteStatDiv").html(
			"	Statistic: <br>" +
			"		Number of Albums: " + data['numAlbums'] + "<br>" +
			"		Number of Songs: " + numSong + "<br>" +
			"		Number of Playlists: " + data['numPlaylists'] + "<br>" +
			"		Number of Artists: " + data['numArtist'] + "<br>" +
			"		Number of Basic Users: " + data['numBasics'] + "<br>" +
			"		Number of Premium Users: " + data['numPremiums'] + "<br>" +
			"		Number of All Users: " + data['numberUsers'] + "<br>" 
			);
}


function insertBasicUserTables(data)
{
	var num = data.length;
	$("#num-of-basic-user").html(num);
	for (var i = 0; i < num; i++)
		{
		    var ban = data[i]['userBan'];
		    var color;
		   if(ban == 0){
		    	color= '';
		    }
		    else{
		    	color='class="w3-red"';
		    }
			$("#basic-users-table").find('tbody').append([
				'<tr ' + color + '>',
				  '<td>' + data[i]['userID'] + '</td>',
				  '<td>' + data[i]['userFirstName'] + '</td>',
				  '<td>' + data[i]['userLastName'] + '</td>',
				  '<td>' + data[i]['userType'] + '</td>',
				  '<td>' + data[i]['username'] + '</td>',
				  '<td><button class="unstyle-buttons edit_user_button"  data-toggle="tooltip-play" title="Edit This User Profiles"> <i class="material-icons" style="color:black;">mode_edit</i></button></td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons" style="color:black;">delete_forever</i></button></td>',
				  '<td><button class="unstyle-buttons ban-user-button"  data-toggle="tooltip-play" title="Ban This User"> <i class="material-icons" style="color:black;">not_interested</i></button></td>',
				 '</tr>'
			].join(''));
		}


}

function insertPremiumUserTables(data)
{
	var num = data.length;
	$("#num-of-premium-user").html(num);
	for (var i = 0; i < num; i++)
		{
			var ban = data[i]['userBan'];
		    var color;
		    if(ban == 0){
		    	color= '';
		    }
		    else{
		    	color='class="w3-red"';
		    }
			$("#premium-users-table").find('tbody').append([
				'<tr ' + color + '>',
				  '<td>' + data[i]['userID'] + '</td>',
				  '<td>' + data[i]['userFirstName'] + '</td>',
				  '<td>' + data[i]['userLastName'] + '</td>',
				  '<td>' + data[i]['userType'] + '</td>',
				  '<td>' + data[i]['username'] + '</td>',
				  '<td><button class="unstyle-buttons edit_user_button"  data-toggle="tooltip-play" title="Edit This User Profiles"> <i class="material-icons" style="color:black;">mode_edit</i></button></td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons" style="color:black;">delete_forever</i></button></td>',
				  '<td><button class="unstyle-buttons ban-user-button"  data-toggle="tooltip-play" title="Ban This User"> <i class="material-icons" style="color:black;">not_interested</i></button></td>',
				 '</tr>'
			].join(''));
		}
}

function insertArtistTables(data)
{
	var num = data.length;
	$("#num-of-artist").html(num);
	for (var i = 0; i < num; i++)
		{
			$("#artist-table").find('tbody').append([
				'<tr>',
				  '<td>' + data[i]['artistEmail'] + '</td>',
				  '<td>' + data[i]['artistName'] + '</td>',
				  '<td>' + data[i]['artistFirstName'] + '</td>',
				  '<td>' + data[i]['artistLastName'] + '</td>',
				  '<td><button class="unstyle-buttons edit_user_button"  data-toggle="tooltip-play" title="Edit This User Profiles"> <i class="material-icons" style="color:black;">mode_edit</i></button></td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons" style="color:black;">delete_forever</i></button></td>',
					'<td><button class="unstyle-buttons send-single-royalty-check"  data-toggle="tooltip-play" title="Send Royalty Check to this Artist" style="color:black;"> <i class="material-icons">payment</i></button></td>',
				 '</tr>'
			].join(''));
		}
}

function insertPlaylistTables(data)
{
	var num = data.length;
	$("#num-of-playlist").html(num);
	for (var i = 0; i < num; i++)
	{
            $("#playlists-table").find('tbody').append([
            '<tr>',
		'<td>' + data[i]['playlistID'] + '</td>',
		'<td>' + data[i]['playlistName'] + '</td>',
		'<td>' + data[i]['playlistOwner'] + '</td>',
		'<td>' + data[i]['playlistNumSongs'] + '</td>',
		'<td>' + data[i]['playlistNumFollowers'] + '</td>',
		'<td>' + data[i]['playlistCreateDate'] + '</td>',
		'<td><button class="unstyle-buttons delete-playlist-button"  data-toggle="tooltip-play" title="Remove this playlist"> <i class="material-icons" style="color:black;">delete_forever</i></button></td>',
            '</tr>'
            ].join(''));
	}
}

function insertPendingSongsTables(data)
{
	var num = data.length;
	$("#num-of-pending-songs").html(num + 500);
	var songs = [];
	for (var i = 0; i < num; i++)
		{
			if (songs.indexOf(data[i]['songID']) == -1) //check to see if there is duplicate songID
			{
				$("#pending-songs-table").find('tbody').append([
					'<tr>',
					  '<td>' + data[i]['songID'] + '</td>',
					  '<td>' + data[i]['songTitle'] + '</td>',
					  '<td>' + data[i]['songArtist'].join() + '</td>',
					  '<td>' + data[i]['songGenre'] + '</td>',
						'<td>Pending</td>',
 					  '<td><button class="unstyle-buttons approveSongButton"  data-toggle="tooltip-play" title="Approve this song"> <i class="material-icons" style="color:black;">check</i></button></td>',
 					  '<td><button class="unstyle-buttons removeSongButton"  data-toggle="tooltip-play" title="Remove this song"> <i class="material-icons" style="color:black;">close</i></button></td>',
					 '</tr>'
				].join(''));
				songs.push(data[i]['songID']);
			}

		}
}
function insertApprovedSongsTables(data)
{
	var num = data.length;
	$("#num-of-approved-songs").html(num + 500);
	var songs = [];
	for (var i = 0; i < num; i++)
		{
			if (songs.indexOf(data[i]['songID']) == -1) //check to see if there is duplicate songID
			{
				$("#approved-songs-table").find('tbody').append([
					'<tr>',
					  '<td>' + data[i]['songID'] + '</td>',
					  '<td>' + data[i]['songTitle'] + '</td>',
					  '<td>' + data[i]['songArtist'].join() + '</td>',
					  '<td>' + data[i]['songGenre'] + '</td>',
						'<td>Approved</td>',					  
 					  '<td><button class="unstyle-buttons removeSongButton"  data-toggle="tooltip-play" title="Remove this song"> <i class="material-icons" style="color:black;">close</i></button></td>',
					 '</tr>'
				].join(''));
				songs.push(data[i]['songID']);
			}

		}
}

function insertAlbumTable(data)
{
	var num = data.length;
	$("#num-of-album").html(num);
	for (var i = 0; i < num; i++)
	{
            $("#albums-table").find('tbody').append([
		'<tr>',
                    '<td>' + data[i]['albumID'] + '</td>',
                    '<td>' + data[i]['albumName'] + '</td>',
		    '<td><div class="tooltip1">' + data[i]['albumDescription'].substring(0,30) + '<span class="tooltiptext1">' + data[i]['albumDescription'] + '</span></div></td>',
                    '<td>' + data[i]['albumNumberSongs'] + '</td>',
                    '<td>' + data[i]['albumNumFollowers'] + '</td>',
                    '<td>' + data[i]['albumReleaseDate'] + '</td>',
                    '<td><button class="unstyle-buttons delete-album-button"  data-toggle="tooltip-play" title="Remove this album"> <i class="material-icons" style="color:black;">delete_forever</i></button></td>',
		'</tr>'
            ].join(''));
	}
}

function insertRevenueDetailTable(data){
    var artist_length = data['allArtistRoyalty'].length;
    console.log("first artist royalty is " + data['allArtistRoyalty'][0]['artistRoyalty']);
    console.log("number of artist is " + artist_length);
    for (var i = 0; i < artist_length; i++)
    {
        $("#royalty-table").find('tbody').append([
        '<tr>',
            '<td>' + data['allArtistRoyalty'][i]['artistName'] + '</td>',
            '<td>' + data['allArtistRoyalty'][i]['artistRoyalty'] + '</td>',
        '</tr>'
        ].join(''));
    }
    $('#royalty-table tr:gt(9)').hide();// :gt selector has 0 based index
    $("#premium-income-table").find('tbody').append([
        '<tr>',
            '<td>' + data['numPremium'] + '</td>',
            '<td>' + data['premiumBenefit'] + '</td>',
        '</tr>'
        ].join(''));
    $("#net-income").text(data['revenue']);
}

$(document).on("click", "#show-all-rows", function(){
    var text = $("#show-hide-all-text").text();
    if(text.localeCompare("Show All") == 0)
    {
        $("#show-hide-all-text").text("Hide")
        $('#royalty-table tr').show();
    }
    else{
        $("#show-hide-all-text").text("Show All")
        $('#royalty-table tr:gt(9)').hide();
    }
    
});

$(document).on ("click", ".delete-playlist-button", function () {
	var pid = $(this).closest('tr').children('td:eq(0)').text();
	console.log("pid is : " + pid);
	$(this).closest('tr').remove();
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

$(document).ready ( function () {
    $(document).on ("click", ".delete-album-button", function () {
        $("#confirmDeleteModal").show();
    	var albumID = $(this).closest('tr').children('td:eq(0)').text().trim();
	    	 $(this).closest('tr').remove();
    	console.log("albumId is : " + albumID);
        $(document).on('click', "#confirmDeleteButton", function(){
            $("#confirmDeleteModal").hide();
           
            	$.ajax({
 	        url: "{cp}/../removeAlbum",
 	        data: {"albumID" : albumID},
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
});

$(document).ready ( function () {
	    $(document).on ("click", ".ban-user-button", function () {
	    	var userID = $(this).closest('tr').children('td:eq(0)').text();
	    	console.log("userId  is : " + userID);
	    	$(this).closest('tr').css("background","red");
	   	 	$.ajax({
	 	        url: "{cp}/../banUser",
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

$(document).ready ( function () {
    $(document).on ("click", ".delete-user-button", function () {
    	var userID = $(this).closest('tr').children('td:eq(0)').text();
    	console.log("userId is : " + userID);
    	$(this).closest('tr').remove();
    	 $.ajax({
 	        url: "{cp}/../deleteSelectedUserAccount",
 	        data: {"userID" : userID},
 	        type: "POST",
 	        asyn: false,
 	        cache: false,
 	        success : function(response)
 	        {
 	          console.log(response);
 	         $(this).closest('tr').remove();
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
	$(this).closest('tr').remove();
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


$(document).on ("click", ".send-single-royalty-check", function () {
	var artistEmail = $(this).closest('tr').children('td:eq(0)').text();
	console.log("artistEmail is : " + artistEmail);
	$("#sendRoyalModal").show();
	$(document).on("submit", "#sendSingleRoyaltyForm", function (event) {
		event.preventDefault();
		var royalty = $("#royalty").val();
		console.log("royalty is " + royalty);
		$.ajax({
	        url: "${cp}/../payOneCheckRoyalty",
	        type: "POST",
	        data : {"artistEmail" : artistEmail, "royalty" : royalty },
	        asyn: false,
	        cache: false,
	        success : function(response)
	        {
	         	console.log(response);
	         	$("#sendRoyalModal").hide();
	        },
	        error: function(e)
	        {
	          console.log(e);
	        }
		  });
	});
});

$(document).on ("click", ".approveSongButton", function () {
	var songID = $(this).closest('tr').children('td:eq(0)').text();
	var songTitle = $(this).closest('tr').children('td:eq(1)').text();
	var songArtist = $(this).closest('tr').children('td:eq(2)').text();
	var songGenre = $(this).closest('tr').children('td:eq(3)').text();
	var stat = $(this).closest('tr').children('td:eq(4)').text();
	console.log("status is " + stat);
	console.log("song id is " + songID);
	$(this).closest('tr').remove();
	 $.ajax({
	        url: "{cp}/../approveSongByAdmin",
	        data: {"songID" : songID},
	        type: "POST",
	        asyn: false,
	        cache: false,
	        success : function(response)
	        {
	          console.log(response);
	          $(this).closest('tr').children('td:eq(4)').text(response);
	          $("#approved-songs-table").find('tbody').append([
					'<tr>',
					  '<td>' + songID + '</td>',
					  '<td>' + songTitle + '</td>',
					  '<td>' + songArtist + '</td>',
					  '<td>' + songGenre + '</td>',
						'<td>Approved</td>',					  
					  '<td><button class="unstyle-buttons removeSongButton"  data-toggle="tooltip-play" title="Remove this song"> <i class="material-icons" style="color:black;">close</i></button></td>',
					 '</tr>'
				].join(''));
	        },
	        error: function(e)
	        {

	          console.log(e);
	        }
	      });
	
});

$(document).on ("click", ".removeSongButton", function () {
	var songID = $(this).closest('tr').children('td:eq(0)').text();
	console.log("remove sid: "+ songID);
	$(this).closest('tr').remove();
	 $.ajax({
	        url: "{cp}/../removeSongByAdmin",
	        data: {"songID" : songID},
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

$(document).on ("click", ".edit_user_button", function () {
	var userID = $(this).closest('tr').children('td:eq(0)').text();
	$("#edit-user-profile-modal").show();

//	 alert("haha");
});
$("#cancel_edit_button").click(function(){

	  event.preventDefault(); document.getElementById('edit-user-profile-modal').style.display='none';
});


$(document).on ("click", "#add-new-artist-button", function () {
	var userID = $(this).closest('tr').children('td:eq(0)').text();

	$('#new-artist-dialog').dialog({
        height: 600,
        width: 550,
        modal: true,
        resizable: false,
        dialogClass: 'no-close'
  	});


});

$(document).on ("click", "#add-new-user-button", function () {
	$('#new-user-dialog').dialog({
        height: 600,
        width: 550,
        modal: true,
        resizable: false,
        dialogClass: 'no-close'
  	});
});



$(document).on ("click", "#add-new-playlist-button", function () {

	 	 $('#new-playlist-dialog').dialog({
	          //autoOpen: true,
	          height: 430,
	          width: 700,
	          modal: true,
	          resizable: false,
	          background: "#2f2f2f",
	          //color: Black,
	          //closeOnEscape: false,
	          //open: function(event, ui) { $(".ui-dialog-titlebar-close", ui).hide(); }
	          //closeOnEscape: false,
	          //beforeclose: function (event, ui) { return false; },
	          dialogClass: 'no-close'
	     });
	  //     window.open("browse.jsp","_blank");
	});

