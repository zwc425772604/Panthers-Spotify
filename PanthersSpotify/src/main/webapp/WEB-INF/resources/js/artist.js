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
          $("#main-changing-content").load("jsp/artistInfo.jsp");
        },
        error: function(e)
        {
          console.log(e);
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
		      	  '</object>',
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