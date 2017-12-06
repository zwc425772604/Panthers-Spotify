
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
	
	$(document).on('click',".followArtistButton",function(){
	$(".followArtistButton").unbind('click').bind('click', function(){
		var status = $("#followArtistStatus").text().trim();
		var email = $("#artistEmail",this).text().trim();
		console.log("THis");
		if (status.localeCompare('Follow') == 0 )
		{
			$.ajax({
				url: "followSpecificArtist",
				type: "POST",
				data: {"artistEmail" : email},
				asyn: true,
				cache: false,
				success: function(response)
				{
					console.log(response);
					$("#followArtistStatus").html("Unfollow");
					var cn = parseInt($("#artist-follower-number").text());
					var cn = cn + 1;
					console.log(cn);
					$("#artist-follower-number").empty();
					$("#artist-follower-number").html(cn);
				}
			});
		}
		else
		{
			$.ajax({
				url: "unfollowSpecificArtist",
				type: "POST",
				data: {"artistEmail" : email},
				asyn: true,
				cache: false,
				success: function(response)
				{
					console.log(response);
					$("#followArtistStatus").html("Follow");
					var cn = parseInt($("#artist-follower-number").text());
					var cn = cn -1;
					console.log(cn);
					$("#artist-follower-number").empty();
					$("#artist-follower-number").html(cn);
				}
			});
		}

	});
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
        	 $.ajax({
        	        url: "${cp}/../getRelatedArtist",
        	        type: "POST",
        	        data : {"artistEmail" : email},
        	        asyn: false,
        	        cache: false,
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
        	                },
        	                complete: function(){
        	                	$.ajax({
        	                        url: "${cp}/../getArtistInfo",
        	                        type: "POST",
        	                        data : {"artistEmail" : email},
        	                        asyn: true,
        	                        cache: true,
        	                        success : function(response)
        	                        {
        	                          $("#artist-info").empty();
        	                          console.log(response);
        	                          var actual_JSON = JSON.parse(response);
        	                          if(!actual_JSON['artistBio']){
        	                        	  actual_JSON['artistBio'] = "This artist doesn't include a bio.";
        	                          }
        	                          $("#artist-info").append(actual_JSON['artistBio']);
        	                          $.ajax({
		              	                        url: "${cp}/../getConcertInfo",
		              	                        type: "POST",
		              	                        data : {"artistEmail" : email},
		              	                        asyn: true,
		              	                        cache: true,
		              	                        success : function(response)
		              	                        {
		              	                             console.log(response);
		              	                             var actual_JSON = JSON.parse(response);
		              	                             inputConcertTable(actual_JSON);
		              	                        },
		              	                        error: function(e)
		              	                        {
		              	                          console.log(e);
		              	                        }
		              	                	  });
        	                          
        	                        },
        	                        error: function(e)
        	                        {
        	                          console.log(e);
        	                        }
        	                	  });
        	                	
        	                }
        	        	  });
        	        	
        	        }
        	 });
        },
        error: function(e)
        {
          console.log(e);
        }
      
	  });
});

function inputConcertTable(data){
	$("#artist-concert-info").empty();
	var num = data['concertList'].length;
	if(num == 0){
		$("#artist-concert-info").append("This artist has no upcoming concerts.");
	}
	else{
		for(var i = 0; i < num; i++){
			var cname = data['concertList'][i]['name'];
			var date = data['concertList'][i]['date'];
			var address =data['concertList'][i]['address'];
			$("#artist-concert-info").append([
				'<div id="artist-concert-name" style="font-size:1.2em; font-weight: bolder;color:lightgreen; margin-top: 1em;">'+ cname +'</div>',
			      '<div id="artist-concert-date" style="font-size:1em; font-weight: bolder;">' + date +'</div>',
			      '<div id="artist-cocnert-address" style="font-size: 1em;">' +address +'</div>'
			].join(''));
		}
	}
}


function insertArtistsPage(data)
{
	var num = data.length;
	for (var i = 0; i < num; i++)
		{
		    if(!data[i]['artistUrl']){
		    	data[i]['artistUrl'] = "http://res.cloudinary.com/dn1agy1ea/image/upload/v1495644755/empty-album-cover_wvtnrn.png";
		    }
		    else{
		    	data[i]['artistUrl'] = "${cp}/../resources/data" + data[i]['artistUrl'] 
		    }
			$("#artist-page").append([
				'<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">',
		        '<div class="artist-item">',
		        '<img src="' + data[i]['artistUrl'] +'" width=100% class="img-circle" alt="Generic placeholder thumbnail" style="border-radius: 100px;">',
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


