
$(document).ready(function(){
	document.getElementById('approve_song').focus();
	
	$.ajax({
		url: "{cp}/../displayArtistCheckRoyalty",
		type: "POST",
		asyn: false,
		cache: false,
		success : function(response)
			{
				console.log(response);
				var actual_JSON = JSON.parse(response);
				displayArtistRoyalty(actual_JSON);
			},
		error: function(e)
		{
			console.log(e);
		}
	});
	
	$.ajax({
		url: "{cp}/../getAllSongsForArtist/approved",
		type: "POST",
		asyn: false,
		cache: false,
		success : function(response)
			{
				console.log(response);
				var actual_JSON = JSON.parse(response);
				insertArtistApprovedSongsTables(actual_JSON);
			},
		error: function(e)
		{
			console.log(e);
		}
	});
	
	$.ajax({
		url: "{cp}/../getAllSongsForArtist/pending",
		type: "POST",
		asyn: false,
		cache: false,
		success : function(response)
			{
				console.log(response);
				var actual_JSON = JSON.parse(response);
				insertArtistPendingSongsTables(actual_JSON);
			},
		error: function(e)
		{
			console.log(e);
		}
	});
	 
});

function myFunction() {
    var x = document.getElementById("Demo");
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else {
        x.className = x.className.replace(" w3-show", "");
    }
   }


function insertArtistPendingSongsTables(data)
{
	var num = data.length;
	$("#num-of-pending-songs").html(num);
	var songs = [];
	for (var i = 0; i < num; i++)
		{
			if (songs.indexOf(data[i]['songID']) == -1) //check to see if there is duplicate songID
			{
				$("#artist-pending-songs-table").find('tbody').append([
					'<tr>',
					'<td>' + data[i]['songID'] + '</td>',
					'<td>' + data[i]['songTitle'] + '</td>',
					'<td>' + data[i]['songArtist'][0]['name'] + '</td>',
					'<td>' + data[i]['songGenre'] + '</td>',
				    '<td>Pending</td>',
 					'<td><button class="unstyle-buttons requestSongToRemoveButton" title="Request to remove this song"> <i class="material-icons">close</i></button></td>',
					'</tr>'
				].join(''));
				songs.push(data[i]['songID']);
			}
		}
}

function insertArtistApprovedSongsTables(data)
{
	var num = data.length;
	$("#num-of-approved-songs").html(num);
	for (var i = 0; i < num; i++)
		{
			$("#artist-approved-songs-table").find('tbody').append([
				'<tr>',
					'<td>' + data[i]['songID'] + '</td>',
					'<td>' + data[i]['songTitle'] + '</td>',
					'<td>' + data[i]['songArtist'][0]['name'] + '</td>',
					'<td>' + data[i]['songGenre'] + '</td>',
				    '<td>Approved</td>',
 					'<td><button class="unstyle-buttons requestSongToRemoveButton" title="Request to remove this song"> <i class="material-icons">close</i></button></td>',
					'</tr>'
				].join(''));

		}
}

function displayArtistRoyalty(data)
{
	$("#artistRoyaltyText").html(data['artistRoyalty']);
	console.log(data['artistRoyalty']);
}



    $(document).on ("click", ".requestSongToRemoveButton", function () {
    	var songID = $(this).closest('tr').children('td:eq(0)').text();
        $(this).closest('tr').remove();
        var num = $("#num-of-pending-songs").text();
	$("#num-of-pending-songs").html(num - '0' -1);
	   	 $.ajax({
	   	        url: "{cp}/../removeRequestSongForUploading",
	   	        data: {"songId" : songID},
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



$(document).on ("click", "#add_new_song_button", function () {
    $('#new_song_dialog').dialog({
     height: 600,
     width: 550,
     modal: true,
     resizable: false,
     dialogClass: 'no-close'
  	});
    });


$(document).on ("click", "#add_new_concert_button", function () {
    $('#new_concert_dialog').dialog({
     height: 300,
     width: 550,
     modal: true,
     resizable: true,
     dialogClass: 'no-close'
  	});
  });


$(document).on ("click", "#edit_info_button", function () {
    $('#edit_info_dialog').dialog({
     height: 300,
     width: 550,
     modal: true,
     resizable: true,
     dialogClass: 'no-close'
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





