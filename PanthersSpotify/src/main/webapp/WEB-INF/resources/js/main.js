$(document).ready(function(){
//hide all other containers in <div id= 'middle-content'> beside except overview_container
//$("#overview_container").siblings().hide();
	//Loading Friend List for the user
	$.ajax({
        url: "${cp}/../getUserFriendList",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
        	console.log(response);
        	var actual_JSON = JSON.parse(response);
        	insertFriends(actual_JSON);
        },
        error: function(e)
        {
          console.log(e);
        }
	});

	//Loading top followed playlist
	$.ajax({
        url: "${cp}/../getOverviewPlaylist",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
          //Template
          //var photoUrl = response.url
          //var playlistName = playlistName
          //var playlistDesc = playlistDesc
          $("#main-changing-content").load("jsp/browse.jsp");
        },
        error: function(e)
        {
          console.log(e);
        }
	});

//	$(".playlist-item").click(function(){
//		var pid = $(".playlist_id", this).text(); //get the pid of the playlist
//		  $.ajax({
//	          url: "${cp}/../getSpecificPlaylist",
//	          type: "POST",
//	          data : {"playlist_id" : pid },
//	          asyn: true,
//	          cache: true,
//	          success : function(response)
//	          {
//	            console.log(response);
//	            $("#main-changing-content").load("jsp/playlist.jsp");
//	          },
//	          error: function(e)
//	          {
//	            console.log(e);
//	          }
//		  });
//	});

	$("#new_playlist_button").click(function(){
	 	 $('#dialog').dialog({
	          //autoOpen: true,
	          height: 430,
	          width: 700,
	          modal: true,
	          resizable: false,
	          background: "#2f2f2f",
	  
	          dialogClass: 'no-close'
	     });
	
	});

	$("#addFriendButton").click(function(){
	 	 $('#addFriendDialog').dialog({
	          //autoOpen: true,
	          height: 550,
	          width: 450,
	          modal: true,
	          resizable: false,
	          dialogClass: 'no-close'
	     });
	});
	
	$("#ad-close").click(function(){
		$("#advertisement").hide();
	})
	
});


function genre_page(){
	$("#middle-container").load("jsp/genre.jsp");
}

function newrelease_page(){
	$.when(newReleaseAjax()).done(function(){
		$("#middle-container").load("jsp/newsRelease.jsp");
	});
}

function newReleaseAjax(){
	return $.ajax({
        url: "${cp}/../getNewsRelease",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
        	
        	console.log(response);
        },
        error: function(e)
        {
          console.log(e);
        }
      });
}

function overview_page(){
	$("#middle-container").load("jsp/overview.jsp");
}

function chart_page(){
	$("#middle-container").load("jsp/charts.jsp");
}

function discover_page(){
	$("#middle-container").load("jsp/discover.jsp");
}

$(document).on("click", ".right-col-friends-name", function(){
		var username = $(".right-col-friends-name", this).text(); //get the pid of the playlist
		var email = $(".friends-email",this).text();
		  $.ajax({
	        url: "${cp}/../getUserPage",
	        type: "POST",
	        data : {"username" : username, "userEmail" : email },
	        asyn: true,
	        cache: true,
	        success : function(response)
	        {
	          console.log(response);
	          $("#main-changing-content").load("jsp/userPage.jsp");
	        },
	        error: function(e)
	        {
	          console.log(e);
	        }
		  });
});

$(document).on("click", ".playlist-item", function(){
	var pid = $(".playlist_id", this).text(); //get the pid of the playlist
	  $.ajax({
        url: "${cp}/../getSpecificPlaylist",
        type: "POST",
        data : {"playlist_id" : pid },
        asyn: true,
        cache: true,
        success : function(response)
        {
        	docCookies.setItem("playlistSongJSON", response);
          console.log("playlist reponse "+response);
          //$("#main-changing-content").load("jsp/playlist.jsp");
          $.get("jsp/playlist.jsp", function(data) {
              $("#main-changing-content").html(data)
          });
        },
        error: function(e)
        {
          console.log(e);
        }
	  });
});


$(document).on("click", "#playbar-queue-button", function () {
	$.ajax({
        url: "${cp}/../getSongQueue",
        type: "POST",
        asyn: false,
        cache: false,
        success : function(response)
        {
        	console.log("getSong queuee:  "+ response);
        	 $.get("jsp/queue.jsp", function(data) {
                 $("#main-changing-content").html(data)
             });
        },
        error: function(e)
        {
          console.log(e);
        }
	});
	
	//$("#main-changing-content").load("jsp/queue.jsp");
  	
});

$(document).on("submit", "#findFriendForm", function (event) {
	event.preventDefault();
	var userEmail = $("#addFriendUserEmail").val();
	console.log("userEmail in find friend is " + userEmail);
	$.ajax({
        url: "${cp}/../findFriend",
        type: "POST",
        data : {"userEmail" : userEmail },
        asyn: false,
        cache: false,
        success : function(response)
        {
         	console.log(response);
        	if (response.localeCompare("ok") == 0){
        		$('#addFriendDialog').dialog('close');
        		$("#main-changing-content").load("jsp/userPage.jsp");
        	}else{
        		$("#findFriendMessage").text("no user found");
        		console.log("aha");
        	}

        },
        error: function(e)
        {
          console.log(e);
        }
	  });
});

function displayLeftNavbarContent(nav_name)
{
    //compare the string
	if (nav_name.localeCompare('browse') == 0)
	{
	  $("#main-changing-content").load("jsp/browse.jsp");
	}
	else if (nav_name.localeCompare('albums') == 0)
	{
		$.when(loadAlbumAjax()).done(function(){
			$("#main-changing-content").load("jsp/album.jsp");
		});
	   
	}
	else if (nav_name.localeCompare('artists') == 0)
	{		
		$("#main-changing-content").load("jsp/artist.jsp");
	   
	}
	else if (nav_name.localeCompare('recently_played') == 0)
	{
		   $("#main-changing-content").load("jsp/recentlyPlayed.jsp");
		}
	else if (nav_name.localeCompare('songs') == 0)
	{

		$.ajax({
	        url: "${cp}/../loadSong",
	        type: "POST",
	        asyn: false,
	        cache: true,
	        success : function(response)
	        {
	          console.log(response);
	          $("#main-changing-content").load("jsp/songs.jsp");
	        },
	        error: function(e)
	        {

	          console.log(e);
	        }
	      });
		
	}
	else
	{
	   $("#main-changing-content").load("jsp/overview.jsp");
	    }
}

function loadAlbumAjax(){
	return $.ajax({
        url: "${cp}/../loadAlbum",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
          console.log(response);
        },
        error: function(e)
        {

          console.log(e);
        }
      });
}

function displayAccount(){
	$("#main-changing-content").load("jsp/account.jsp");
}

function displayUserAccount(){
	$("#main-changing-content").load("jsp/userAccount.jsp");
}
function displayUpgradeForm()
{
	$("#main-changing-content").load("jsp/pay.jsp");
}

function openDialogBox()
{
  //   $("#dialog").dialog();
	$('#dialog').dialog({
	  //autoOpen: true,
	  height: 550,
	  width: 450,
	  modal: true,
	  resizable: false,
	  dialogClass: 'no-close'
	  });
}


$(document).ready(function(){
function displayCharts()
{
  $('#middle-content').load('charts.html');
}


var volumeDrag = false;
$("#rangeinput").on('mousedown',function(e){
	volumeDrag = true;
	audio.muted = false;
	updateVolume(e.main);
	
});

function updateVolume(volume){
	console.log(volume);
	audio.volume = volume / 100;
}

var muted = false;
function volumeMute(data){
	var up = "<i id=\"volume-up\" class=\"material-icons\">volume_up</i>";
	var mute = "<i id=\"volume-off\" class=\"material-icons\">volume_off</i>";
	data.innerHTML = "";
	if(muted){
		data.innerHTML = up;
		audio.volume = 0.5;
		muted = false;
		$("#rangeinput").val(50);
	}else{
		data.innerHTML = mute;
		audio.volume = 0;
		muted = true;
		$("#rangeinput").val(0);
	}
	
}

function search()
{
	$("#main-changing-content").load("jsp/search.jsp");	
}
});
