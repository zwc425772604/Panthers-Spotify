<!--  Container for DISCOVER -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<script src="${cp}/resources/js/album.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
<link rel="stylesheet" href="${cp}/resources/css/album.css">

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
		  <div class="hover-control album-item">
		    <img src="https://s.discogs.com/images/default-release-cd.png" width=100% height=width class="img-rounded info-image" alt="Generic placeholder thumbnail">
		    <span class="album-ID" style="display: none;">${album.aid}</span>
		  </div>
		  <div class="album-item medium-boxes-description">
		      <h6>
		        <a href="#"><span id = "album-name">${album.aname}</span></a>
		        <span class="album-ID" style="display: none;">${album.aid}</span>
		      </h6>
		    <h6><a href="#"> <span id = "artist_name">${album.des}</span></a> </h6>
		  </div>
	    </div>
	  </c:forEach>
    </c:if>
  </section>
</div>
<!-- this div display when hover on the image of album/artist -->
		        <!--  <div class = "hover-buttons" style="display:none;">
		          <ul class="left_sizebar">
		            <li><button class="unstyle-buttons" data-toggle="tooltip-mute" title="Add User"  id="playbar-prev-button"><i class="material-icons">add</i></button></li>
		            <li><button class="unstyle-buttons" data-toggle="tooltip-queue" title="Edit User Information" id="playbar-shuffle-button"> <i class="material-icons">mode_edit</i></button></li>
		            <li><button class="unstyle-buttons" data-toggle="tooltip-play" title="Delete User" id="playbar-play-button" onclick="playSong()"> <i class="material-icons">delete_forever</i></button></li>
		          </ul>
		        </div>-->