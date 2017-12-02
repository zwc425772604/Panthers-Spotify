$(document).ready(function(){
	console.log("HI")
	$.ajax({
        url: "${cp}/../getGenre",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
        	var actual_JSON = JSON.parse(response);
        	insertGenre(actual_JSON);
        },
        error: function(e)
        {
          console.log(e);
        }
      });
});

var genrelist = [];
function insertGenre(data){
	var len = (data['genres']).length;

	for (i = 0; i < len; i++){
		$("#genre-blocks").append([
			'<div class="col-xs-6 col-sm-4 col-md-2 col-sm-2 placeholder  genre-box">',
		      '<img class="genre-img" src="https://wallpaperscraft.com/image/circles_light_shape_size_47439_1920x1080.jpg" width=135% height=width class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">',
		      '<div class="centered genre-item"><div id="genre-name">'+ data['genres'][i]['genre' + i]+ '</div></div>',
		      '</div>'			
		].join(''));
		genrelist.push(data['genres'][i]['genre' + i]);
	}
}

$(document).on("click", ".genre-item", function(){
	var genre = $("#genre-name",this).text();
	$.ajax({
        url: "${cp}/../getPageGenre",
        data: {"genre" : genre, "pageNum":1},
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
        	
        },
        error: function(e)
        {
          console.log(e);
        }
      });
	$("#main-changing-content").load("jsp/genreInfo.jsp");
});