$(document).ready(function(){
	$.ajax({
        url: "${cp}/../loadFollowingArtist",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
        	var actual_JSON = JSON.parse(response);
        	insertFollowingArtistsPage(actual_JSON);
        },
        error: function(e)
        {
          console.log(e);
        }
      });
});

function insertFollowingArtistsPage(data)
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
			$("#following-artist-page").append([
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