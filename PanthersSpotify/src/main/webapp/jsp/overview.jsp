<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<!--  Container for DISCOVER -->
<div class="suggestion-container" id = "discover-container">
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic">Playlist Collections</h3>
    <button class="suggestion-top-page-button"><i class="fa fa-angle-left fa-2x" aria-hidden="true"></i></button>
    <button class="suggestion-top-page-button-right"><i class="fa fa-angle-right fa-2x" aria-hidden="true"></i></button>
  </div>
  <div class="line"></div>
  <section class="row placeholders">
  	
  	<c:if test="${not empty overviewPlaylist}">
  		<c:forEach var="overviewPlaylist" items="${overviewPlaylist}">
  		<div class="col-xs-6 col-sm-4 col-md-2 col-sm-2 placeholder medium-boxes">
  			  <a class="playlist-item">
  			  	<object width=100% height=width data="http://www.designformusic.com/wp-content/uploads/2015/10/insurgency-digital-album-cover-design.jpg" type="image/png">
              		<img src="${overviewPlaylist.photoUrl}"  class="img-rounded" alt="Generic placeholder thumbnail">
              	</object>
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

<!--Container for CHARTS-->
<!--  <div class="suggestion-container" id = "charts-container">
  <div class="line"></div>
  <section class="row placeholders">
    <div class="col-md-3 placeholder medium-boxes">
      <img src="http://profilerehab.com/facebook_covers/grunge/maroon_grunge_cover_15.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Charts</h6>
        <div class="text-muted text-over-image-desp" >Global and regional top charts</div>
      </div>
    </div>
    <div class="col-md-3 placeholder medium-boxes">
      <img src="http://www.mosta2bal.com/vb/badeencache/2/10215style.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">New Releases</h6>
        <div class="text-muted text-over-image-desp" >Macklemore, The Killers, lllenium</div>
      </div>
    </div>
    <div class="col-md-3 placeholder medium-boxes">
      <img src="http://www.trendycovers.com/covers/music_is_my_boyfriend_facebook_cover_1351699049.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover</h6>
        <div class="text-muted text-over-image-desp" >Recommended for you</div>
      </div>
    </div>
  </section>
</div> -->
<!-- Podcast and Videos  -->
<!-- <div class="suggestion-container">
  <div class="line"></div>
  <section class="row placeholders">
    <div class=" col-sm-5 placeholder suggestion-boxes">
      <img src="https://www.fuse.tv/image/56fe73a1e05e186b2000009b/768/512/the-boxer-rebellion-ocean-by-ocean-album-cover-full-size.jpg" width=80% height=width class="img-rounded" alt="Generic placeholder thumbnail">
      <div>
        <h6 class="text-over-image">Podcasts</h6>
        <div class="text-muted text-over-image-desp">A wide range of voices</div>
      </div>
    </div>
    <div  class="col-sm-1 placeholder suggestion-boxes"></div>
    <div class=" col-sm-5 placeholder suggestion-boxes">
      <img src="https://spark.adobe.com/images/landing/examples/blizzard-album-cover.jpg" width=80% height=width class="img-rounded" alt="Generic placeholder thumbnail">
      <div>
        <h6 class="text-over-image">Videos</h6>
        <div class="text-muted text-over-image-desp">Series, specials, and more</div>
      </div>
    </div>
  </section>
</div> -->
<!--Container for Genres & Moods-->
<!-- <div class="suggestion-container">
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic">Genres & Moods</h3>
    <button class="suggestion-top-page-button"><i class="fa fa-angle-left fa-2x" aria-hidden="true"></i></button>
    <button class="suggestion-top-page-button-right"><i class="fa fa-angle-right fa-2x" aria-hidden="true"></i></button>
  </div>
  <div class="line"></div>
  <section class="row placeholders">
    <div class="col-sm-2 placeholder medium-boxes">
      <img src="http://profilerehab.com/facebook_covers/grunge/maroon_grunge_cover_15.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover Weekly</h6>
        <div class="text-muted text-over-image-desp" >Your weekly mixtape of fresh music. Enjoy new discoveries and deep ..</div>
      </div>
    </div>
    <div class="col-sm-2 placeholder medium-boxes">
      <img src="http://www.mosta2bal.com/vb/badeencache/2/10215style.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover Weekly</h6>
        <div class="text-muted text-over-image-desp" >Your weekly mixtape of fresh music. Enjoy new discoveries and deep ..</div>
      </div>
    </div>
    <div class="col-sm-2 placeholder medium-boxes">
      <img src="http://www.trendycovers.com/covers/music_is_my_boyfriend_facebook_cover_1351699049.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover Weekly</h6>
        <div class="text-muted text-over-image-desp" >Your weekly mixtape of fresh music. Enjoy new discoveries and deep ..</div>
      </div>
    </div>
    <div class="col-sm-2 placeholder medium-boxes">
      <img src="http://www.trendycovers.com/covers/music_is_my_boyfriend_facebook_cover_1351699049.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover Weekly</h6>
        <div class="text-muted text-over-image-desp" >Your weekly mixtape of fresh music. Enjoy new discoveries and deep ..</div>
      </div>
    </div>
    <div class="col-sm-2 placeholder medium-boxes">
      <img src="http://profilerehab.com/facebook_covers/grunge/maroon_grunge_cover_15.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover Weekly</h6>
        <div class="text-muted text-over-image-desp" >Your weekly mixtape of fresh music. Enjoy new discoveries and deep ..</div>
      </div>
    </div>
    <div class="col-sm-2 placeholder medium-boxes">
      <img src="http://www.mosta2bal.com/vb/badeencache/2/10215style.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover Weekly</h6>
        <div class="text-muted text-over-image-desp" >Your weekly mixtape of fresh music. Enjoy new discoveries and deep ..</div>
      </div>
    </div>
    <div class="col-sm-2 placeholder medium-boxes">
      <img src="http://www.trendycovers.com/covers/music_is_my_boyfriend_facebook_cover_1351699049.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover Weekly</h6>
        <div class="text-muted text-over-image-desp" >Your weekly mixtape of fresh music. Enjoy new discoveries and deep ..</div>
      </div>
    </div>
    <div class="col-sm-2 placeholder medium-boxes">
      <img src="http://www.trendycovers.com/covers/music_is_my_boyfriend_facebook_cover_1351699049.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Discover Weekly</h6>
        <div class="text-muted text-over-image-desp" >Your weekly mixtape of fresh music. Enjoy new discoveries and deep ..</div>
      </div>
    </div>
  </section>
</div> -->
