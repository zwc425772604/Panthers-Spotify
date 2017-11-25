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
