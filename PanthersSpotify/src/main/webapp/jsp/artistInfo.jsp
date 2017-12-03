<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Artist Page - Panthers Spotify</title>
  <link rel="stylesheet" href="${cp}/resources/css/artistInfo.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<div class="suggestion-container" id = "release-container" style="margin-top: 3%;">
  <div class="suggestion-container-top">
    <div class="user_image_box" style="width: 20%">
      <img src="${cp}/resources/data${selectedArtist.photoUrl}" width=100% class="img-rounded" alt="Generic placeholder thumbnail">
    </div>
    <div class="userInfoBoxes" style="width:70%">
      <div id ="userInfo" style="margin-top: 4%; margin-left:5%;">
        <h5> Artist Page </h5>
        <p style="font-size: 1.8em;" id="artist-name">
          <c:out value="${selectedArtist.userName}"></c:out>
        </p>
        <div>    
            <button class="w3-button w3-round-xxlarge formButton followArtistButton" style="width:auto" >
              <span id="followArtistStatus" >
                <c:choose>
                  <c:when test="${fn:contains(userFollowingArtist,selectedArtist.email)}">
                    Unfollow
                  </c:when>
                  <c:otherwise>
                    Follow
                  </c:otherwise>
                </c:choose>
              </span>
              <span id="artistEmail" style="display:none;">
                <c:out value="${selectedArtist.email}"></c:out>
              </span>
            </button>
      </div>
      </div>
    </div>
  </div>
</div>
<div class="suggestion-container row" style="margin-top:3%;">
  <div style="width: 60%; margin: 2%;">
    <div id="album-header" style="border-bottom: 1px solid white; margin-bottom: 2%;
                                font-size: 1.5em;">
      Albums
    </div>
    <section class="row placeholders" id="infoContainer" style="padding-left: 5%;">
		<c:if test="${not empty album_list}">
			<c:forEach var="album" items="${album_list}">
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-4 col-xl-3 placeholder medium-boxes">
					<div class="hover-control album-item">
						<img src="${cp}/resources/data${album.photoUrl}"
							width=100% height=width class="img-rounded info-image"
							alt="Generic placeholder thumbnail"> <span class="album-ID"
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
  <div id="bio-section" style="width: 30%; margin: 2%;">
    <div id="bio-header" style="border-bottom: 1px solid white; margin-bottom: 2%;
                                font-size: 1.5em;">
      Bio
    </div>
    <div id="artist-info" style="padding: 3%;">
    </div>
  
  </div>

</div>
