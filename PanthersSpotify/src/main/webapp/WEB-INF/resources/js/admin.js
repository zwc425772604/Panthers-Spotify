function displayPage()
{
	$("#main-changing-content").load("jsp/dashboard.jsp");
}
$(document).ready(function(){


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
				console.log(response);
				var actual_JSON = JSON.parse(response);
				insertPendingSongsTables(actual_JSON);
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
});

function insertBasicUserTables(data)
{
	var num = data.length;
	$("#num-of-basic-user").html(num);
	for (var i = 0; i < num; i++)
		{
		    var ban = data[i]['userBan'];
		    var color;
		    if(ban == 0){
		    	color= '#f1f1f1';
		    }
		    else{
		    	color='red';
		    }
			$("#basic-users-table").find('tbody').append([
				'<tr style="background:' + color + '">',
				  '<td>' + data[i]['userID'] + '</td>',
				  '<td>' + data[i]['userFirstName'] + '</td>',
				  '<td>' + data[i]['userLastName'] + '</td>',
				  '<td>' + data[i]['userType'] + '</td>',
				  '<td>' + data[i]['username'] + '</td>',
				  '<td><button class="unstyle-buttons edit_user_button"  data-toggle="tooltip-play" title="Edit This User Profiles"> <i class="material-icons">mode_edit</i></button></td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons">delete_forever</i></button></td>',
				  '<td><button class="unstyle-buttons ban-user-button"  data-toggle="tooltip-play" title="Ban This User"> <i class="material-icons">not_interested</i></button></td>',
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
		    	color= '#f1f1f1';
		    }
		    else{
		    	color='red';
		    }
			$("#premium-users-table").find('tbody').append([
				'<tr style="background:' + color + '">',
				  '<td>' + data[i]['userID'] + '</td>',
				  '<td>' + data[i]['userFirstName'] + '</td>',
				  '<td>' + data[i]['userLastName'] + '</td>',
				  '<td>' + data[i]['userType'] + '</td>',
				  '<td>' + data[i]['username'] + '</td>',
				  '<td><button class="unstyle-buttons edit_user_button"  data-toggle="tooltip-play" title="Edit This User Profiles"> <i class="material-icons">mode_edit</i></button></td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons">delete_forever</i></button></td>',
				  '<td><button class="unstyle-buttons ban-user-button"  data-toggle="tooltip-play" title="Ban This User"> <i class="material-icons">not_interested</i></button></td>',
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
				  '<td><button class="unstyle-buttons edit_user_button"  data-toggle="tooltip-play" title="Edit This User Profiles"> <i class="material-icons">mode_edit</i></button></td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons">delete_forever</i></button></td>',
					'<td><button class="unstyle-buttons send-single-royalty-check"  data-toggle="tooltip-play" title="Send Royalty Check to this Artist"> <i class="material-icons">payment</i></button></td>',
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
				  '<td><button class="unstyle-buttons delete-playlist-button"  data-toggle="tooltip-play" title="Remove this playlist"> <i class="material-icons">delete_forever</i></button></td>',
				 '</tr>'
			].join(''));
		}
}

function insertPendingSongsTables(data)
{
	var num = data.length;
	$("#num-of-playlist").html(num);
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
 					  '<td><button class="unstyle-buttons approveSongButton"  data-toggle="tooltip-play" title="Approve this song"> <i class="material-icons">check</i></button></td>',
 					  '<td><button class="unstyle-buttons removeSongButton"  data-toggle="tooltip-play" title="Remove this song"> <i class="material-icons">close</i></button></td>',
					 '</tr>'
				].join(''));
				songs.push(data[i]['songID']);
			}

		}
}


$(document).ready ( function () {
    $(document).on ("click", ".ban-user-button", function () {
    	var userID = $(this).closest('tr').children('td:eq(0)').text();
    	console.log("userId is : " + userID);
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
	var stat = $(this).closest('tr').children('td:eq(4)').text();
	console.log("status is " + stat);
	console.log("song id is " + songID);
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
	        },
	        error: function(e)
	        {

	          console.log(e);
	        }
	      });
	
});

$(document).on ("click", ".removeSongButton", function () {
	var songID = $(this).closest('tr').children('td:eq(0)').text();
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

