<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!--  Container for DISCOVER -->
<div class="suggestion-container" id = "discover-container">
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic">Top Followed Playlists</h3>
  </div>
  <div class="line"></div>
  <section class="row placeholders">
    <c:if test="${not empty overviewPlaylist}">
      <c:forEach var="overviewPlaylist" items="${overviewPlaylist}">
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
