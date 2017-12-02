<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<script src="${cp}/resources/js/newRelease.js" /></script>
<!--Container for release 1-->
<!--  Container for DISCOVER -->
<div class="suggestion-container" id = "charts-container">
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic">New Albums</h3>
  </div>
  <div class="line"></div>
  <section class="row placeholders" id="infoContainer">
		<c:if test="${not empty album_list}">
			<c:forEach var="album" items="${album_list}">
				<div
					class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">
					<div class="hover-control album-item">
						<img src="${cp}/resources/data${album.photoUrl}"
							width=100% height=width class="img-rounded info-image"
							alt="Generic placeholder thumbnail"> <span class="album-ID"
							style="display: none;">${album.aid}</span>
					</div>
					<div class="album-item medium-boxes-description">
						<h6>
							<a href="#"><span id="album-name">${album.aname}</span></a> <span
								class="album-ID" style="display: none;">${album.aid}</span>
						</h6>
						<h6>
							<a href="#"> <span id="artist_name">${album.des.substring(0,50)}</span></a>
						</h6>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</section>
</div>
<!--Container for release 2-->
<div class="suggestion-container" id = "release-container">
  <div class="line"></div>
  <section class="row placeholders">
    <div class="col-md-5 placeholder medium-boxes">
      <img src="http://profilerehab.com/facebook_covers/grunge/maroon_grunge_cover_15.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Top 50 by Country</h6>
      </div>
    </div>
    <div class="col-md-5 placeholder medium-boxes">
      <img src="http://www.mosta2bal.com/vb/badeencache/2/10215style.jpg" width=110% height=70% class="img-rounded image-over-by-text" alt="Generic placeholder thumbnail">
      <div >
        <h6 class="text-over-image">Viral 50 by Country</h6>
      </div>
    </div>
  </section>
</div>
