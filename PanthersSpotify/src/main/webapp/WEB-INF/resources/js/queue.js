
$(document).ready(function(){

});

function insertBasicUserTables(data)
{
	var num = data.length;
	$("#num-of-basic-user").html(num);
	for (var i = 0; i < num; i++)
		{
			$("#basic-users-table").find('tbody').append([
				'<tr>',
				  '<td>' + data[i]['userID'] + '</td>',
				  '<td>' + data[i]['userFirstName'] + '</td>',
				  '<td>' + data[i]['userLastName'] + '</td>',
				  '<td>' + data[i]['userType'] + '</td>',
				  '<td>' + data[i]['username'] + '</td>',
				  '<td><button class="unstyle-buttons edit_user_button"  data-toggle="tooltip-play" title="Edit This User Profiles"> <i class="material-icons">mode_edit</i></button></td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons">delete_forever</i></button></td>',
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
			$("#premium-users-table").find('tbody').append([
				'<tr>',
				  '<td>' + data[i]['userID'] + '</td>',
				  '<td>' + data[i]['userFirstName'] + '</td>',
				  '<td>' + data[i]['userLastName'] + '</td>',
				  '<td>' + data[i]['userType'] + '</td>',
				  '<td>' + data[i]['username'] + '</td>',
				  '<td><button class="unstyle-buttons edit_user_button"  data-toggle="tooltip-play" title="Edit This User Profiles"> <i class="material-icons">mode_edit</i></button></td>',
				  '<td><button class="unstyle-buttons delete-user-button"  data-toggle="tooltip-play" title="Delete This User"> <i class="material-icons">delete_forever</i></button></td>',
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


