$(document).ready(function(){
	console.log("Loaded album page");
});


$(document).on("click", ".album-item", function(){
	var albumId = $("#album-ID", this).text(); //get the pid of the playlist
	  $.ajax({
        url: "${cp}/../getSpecificAlbum",
        type: "POST",
        data : {"albumID" : albumId},
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

$( "#filter_container" )
.mouseover(function() {
  $('#filter_keyword').css('color', 'white');
  $('#search_span').css('color','white');
})
.mouseout(function(){
  $('#filter_keyword').css('color','#cccccc');
  $('#search_span').css('color','#cccccc');
});
//need to add the close icon at the right in input
$( "#filter_keyword" )
.focus(function(){
  $('#filter_keyword').css('background-color','gray');
  $('#search_span').css('background-color','gray');
});
$("#filter_keyword").blur(function(){
  $('#filter_keyword').css('background-color','#343a40');
  $('#search_span').css('background-color','#343a40');
});

//show the buttons when mouse is on the specific album image
$( ".hover-control" )
.mouseover(function() {

  $(".hover-buttons", this).css('display', 'inline'); //display the options
  $(".info-image",this).css('opacity','0.3'); //change the effect of image
  // these 2 lines worked too
  // $(this).children("div:first").css('display','inline');
  // $(this).children("img:first").css('opacity','0.3');

})
.mouseout(function() {
  $(".hover-buttons", this).css('display', 'none');
  $(".info-image",this).css('opacity','1');
});
