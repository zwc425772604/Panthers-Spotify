<!--  Container for DISCOVER -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style type="text/css">
  .hover-control
  {
  position: relative;
  /*text-align: center;*/
  float: left;
  }
  .hover-buttons
  {
  left:0;
  position: absolute;
  top: 50%;
  width: 100%;
  }
  .info-image
  {
  opacity: 1;
  display:block;
  width: 100%;
  height: auto;
  transition: .5s ease;
  backface-visibility: hidden;
  }
  ul.left_sizebar li
  {
  display:inline;
  margin-left: 10%;
  font-size: 16px;
  }
  /* for the close button in input type search */
  input[type="search"]::-webkit-search-cancel-button
  {
  -webkit-appearance: searchfield-cancel-button;
  }
  #search_span
  {
  background-color: #343a40;
  color: white;
  border: none;
  color: #cccccc;
  }
  #filter_keyword
  {
  background-color: #343a40;
  border-color: inherit;
  -webkit-box-shadow: none;
  box-shadow: none;border:none;
  color: #cccccc;
  }
  #filter_container
  {
  margin-top: 3%;
  }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
  $(document).ready(function(){
  
  });
  //style for the filter container
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
  
</script>
<div class="suggestion-container" id = "release-container" style="margin-top: 10%;">
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic" style="font-size: 4em;">Albums</h3>
  </div>
  <div class="line"></div>
  <!-- filter input div block -->
  <div class="input-group" id="filter_container">
    <span class="input-group-addon" id="search_span">
    <i class="fa fa-search" aria-hidden="true"></i>
    </span>
    <input type="search" id="filter_keyword" name="q" onkeyup="filterAlbum()" placeholder="Filter">
  </div>
</div>
<script>
  //filter by texts entered
  function filterAlbum()
  {
  
  }
  //sort by keyword handler
   $('#sorted_by_keyword').on('change',function(){
     //alert(this.value);
  });
</script>
<!--  Container for Album -->
<div class="suggestion-container" id = "charts-container">
  <div id ="top_content">
    <p style="display:inline;font-size:18px;">Sorted by</p>
    <select id = "sorted_by_keyword" style="display:inline; font-size:20px;">
      <option value="Artist">Artist</option>
      <option value="Title">Title</option>
      <option value="Recently Added">Recently Added</option>
    </select>
    <div style="display:inline; float:right;margin-right:5%;">
      <p style="display:inline;font-size:18px;">Saved Albums Only</p>
      <label class="switch" style="display:inline">
      <input type="checkbox" checked>
      <span class="slider round"></span>
      </label>
    </div>
  </div>
  <div class="line"></div>
  <!-- Saved albums section -->
  <section class="row placeholders" id = "infoContainer">
	  <c:if test="${not empty album_list}">
	     <c:forEach var="album" items="${album_list}">
		    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">
		      <div class = "hover-control">
		        <img src="https://www.fuse.tv/image/56fe73a1e05e186b2000009b/768/512/the-boxer-rebellion-ocean-by-ocean-album-cover-full-size.jpg" width=100% height=width class="img-rounded info-image" alt="Generic placeholder thumbnail">
		        <!-- this div display when hover on the image of album/artist -->
		        <div class = "hover-buttons" style="display:none;">
		          <ul class="left_sizebar">
		            <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Add User"  id="playbar-prev-button"><i class="material-icons">add</i></button></li>
		            <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Edit User Information" id="playbar-shuffle-button"> <i class="material-icons">mode_edit</i></button></li>
		            <li><button class="unstyle-buttons" data-toggle="tooltip-play" title="Delete User" id="playbar-play-button" onclick="playSong()"> <i class="material-icons">delete_forever</i></button></li>
		          </ul>
		        </div>
		      </div>
		      <div class="medium-boxes-description">
		        <h6><a href="#"> <span id = "album_name">${album.aname} </span></a></h6>
		        <h6><a href="#"> <span id = "artist_name">${album.dis}</span></a> </h6>
		      </div>
		    </div>
	   </c:forEach>
    </c:if>
    

  </section>
</div>