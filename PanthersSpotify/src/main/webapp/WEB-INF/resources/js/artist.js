$(document).ready(function(){
	$.ajax({
        url: "${cp}/../loadArtists",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
        	var actual_JSON = JSON.parse(response);
        	insertArtistsPage(actual_JSON);
        },
        error: function(e)
        {
          console.log(e);
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

$(document).on("click", ".artist-item", function(){
	var email = $(".artist-email", this).text(); //get the pid of the playlist
	  $.ajax({
        url: "${cp}/../getSpecificArtist",
        type: "POST",
        data : {"email" : email},
        asyn: false,
        cache: true,
        success : function(response)
        {
       
        },
        error: function(e)
        {
          console.log(e);
        },
        complete : function(){
        	$.ajax({
                url: "${cp}/../getArtistAlbum",
                type: "POST",
                data : {"artistEmail" : email},
                asyn: false,
                cache: true,
                success : function(response)
                {
                	$("#main-changing-content").load("jsp/artistInfo.jsp");
                },
                error: function(e)
                {
                  console.log(e);
                }
        	  });
        	$.ajax({
                url: "${cp}/../getArtistInfo",
                type: "POST",
                data : {"artistEmail" : email},
                asyn: false,
                cache: true,
                success : function(response)
                {
                  console.log(response);
                  var actual_JSON = JSON.parse(response);
                  if(!actual_JSON['artistBio']){
                	  actual_JSON['artistBio'] = "This artist doesn't include a bio.";
                  }
                  $("#artist-info").append(actual_JSON['artistBio']);
                  
                },
                error: function(e)
                {
                  console.log(e);
                }
        	  });
        }
	  });
});

function insertArtistsPage(data)
{
	var num = data.length;
	for (var i = 0; i < num; i++)
		{
			$("#artist-page").append([
				'<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">',
		        '<div class="artist-item">',
		        '<img src="${cp}/../resources/data' + data[i]['artistUrl'] +'" width=100% class="img-rounded" alt="Generic placeholder thumbnail">',
		      	  '<span style="display:none;" class="artist-email">'+  data[i]['artistEmail'] +'</span>',
		        '</div>',
		        '<div class="artist-item medium-boxes-description" style="text-align:center;">',
		          '<h6> <a href="#"> <span id="artist_name">'+  data[i]['artistName'] +'</span> </a> </h6>',
		          '<span style="display:none;" class="artist-email">'+  data[i]['artistEmail'] +'</span>',      
		        '</div>',
		      '</div>'
			].join(''));
		}


}