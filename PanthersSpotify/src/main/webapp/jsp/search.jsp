<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<script src="${cp}/resources/js/search.js" /></script>
<link rel="stylesheet" href="${cp}/resources/css/songsFormat.css">  

<div class="suggestion-container" id = "charts-container">
  <div class="suggestion-container-top" style="margin-bottom: 5%;">
     <h6 class="suggestion-topic" style="font-size: 2em;" id="search-keyword">Searched Results for: </h6>
  </div>
  <div id="results">
	    <div id="search-songs" class="col-sm-11">
	    <h6 class="suggestion-topic" style="font-size: 1.5em;" id="search-keyword">Song: </h6>
	      <div class="table-responsive" style="margin-bottom: 5%;">
	        <table class="table" id="song-table">
	          <thead>
	            <tr>
	              <th></th>
	              <!-- play/pause button -->
	              <th></th>
	              <!-- add/remove button -->
	              <th>TITLE</th>
	              <th>ALBUM</th>
	              <th>ARTIST</th>
	              <th>
	                <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
	              </th>
	              <th> </th>
	              <!-- more button -->
	              <th>
	                <p> <i class="fa fa-clock-o" aria-hidden="true"></i> </p>
	              </th>
	            </tr>
	          </thead>
	          <tbody>
	          </tbody>
	        </table>
	      </div>
	    </div>
	  </div>
	  
	  <div id="search-albums" class="col-sm-12">
	    <h6 class="suggestion-topic" style="font-size: 1.5em;" >Album: </h6>
	    <div class="line"></div>
	    <section class="row placeholders" id="infoContainer">
		  <c:if test="${not empty album_list}">
			<c:forEach var="album" items="${album_list}">
				<div
					class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">
					<div class="hover-control album-item">
						<img src="${cp}/resources/data${album.photoUrl}"
							width=100% height=width class="img-rounded info-image"
							> <span class="album-ID"
							style="display: none;">${album.aid}</span>
					</div>
					<div class="album-item medium-boxes-description">
						<h6 style="text-align: center;">
							<a href="#"><span id="album-name">${album.aname}</span></a> <span
								class="album-ID" style="display: none;">${album.aid}</span>
						</h6>
					</div>
				</div>
			</c:forEach>
		</c:if>
	  </section>  
	  </div>
	  <div id="search-playlists">
	    <h6 class="suggestion-topic" style="font-size: 1.5em;" >Playlist: </h6>
	    <div class="line"></div>
	    <section class="row placeholders">
		    <c:if test="${not empty SearchingPlaylist}">
		      <c:forEach var="overviewPlaylist" items="${SearchingPlaylist}">
		        <div class="col-xs-6 col-sm-4 col-md-2 col-sm-2 placeholder medium-boxes">
		          <a class="playlist-item">
		          <c:choose>
		            <c:when test="${not empty overviewPlaylist.photoUrl}">
		            <img width=100% height=width src="${cp}/resources/data${overviewPlaylist.photoUrl}"  class="img-rounded" alt="Generic placeholder thumbnail">
		            </c:when>
		            <c:otherwise>
		            <img width=100% height=width src="http://res.cloudinary.com/dn1agy1ea/image/upload/v1495644755/empty-album-cover_wvtnrn.png"  class="img-rounded" alt="Generic placeholder thumbnail">
		            </c:otherwise>
		          </c:choose>     
		          <span style="display:none;" class="playlist_id">${overviewPlaylist.pid}</span>
		          </a>
		          <div class="suggestion-boxes-description playlist-item">
		            <h6 id="playlistName">               	
		              ${overviewPlaylist.pname}       
		            </h6>
		            <div class="text-muted" id="playlistDes">${overviewPlaylist.des}</div>
		            <span style="display:none;" class="playlist_id">${overviewPlaylist.pid}</span>
		          </div>
		        </div>
		      </c:forEach>
		    </c:if>
  </section>
	  </div>
	  <div >
      <h6 class="suggestion-topic" style="font-size: 1.5em;" >
      Artists
      </h6>
      <section class="row placeholders">
       <c:choose>
		  <c:when test="${not empty artist_list}">
			<c:forEach var="artist" items="${artist_list}">
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-4 col-xl-3 placeholder medium-boxes">
					<div class="hover-control artist-item">
						<img src="${cp}/resources/data${artist.photoUrl}"
							width=100% height=width class="img-rounded info-image"
							alt="Generic placeholder thumbnail" style="border-radius: 100px;"> <span class="artist-email"
							style="display: none;">${artist.email}</span>
					</div>
					<div class="artist-item medium-boxes-description">
						<h6 style="text-align: center;">
							<a href="#"><span id="album-name">${artist.userName}</span></a> <span
								class="artist-email" style="display: none;">${artist.email}</span>
						</h6>
					</div>
				</div>
			</c:forEach>
		  </c:when>
		</c:choose>
	  </section>
    </div>
  </div>
  	
</div>