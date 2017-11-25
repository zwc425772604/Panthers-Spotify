<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Album Page - Panthers Spotify</title>
   <script src="${cp}/resources/js/album.js"></script>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
   <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
   <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<div class="suggestion-container" id = "release-container" style="margin-top: 3%;">
   <div class="suggestion-container-top">
      <div class="album-image-box" style="width: 20%">
         <object type="image/png" data="https://s.discogs.com/images/default-release-cd.png"  width=100% height=width>
         <img src="" class="img-rounded" alt="Generic placeholder thumbnail">
         </object>
      </div>
      <div class="albumInfoBoxes" style="width:70%">
         <div id ="albumInfo" style="margin-top: 4%; margin-left:5%;">
            <h5> Album Page </h5>
            <p style="font-size: 1.8em;">
               <c:out value="${selectedAlbum.aname}"></c:out>
            </p>
            <div class="col-md-5" style="display:inline;">		            		            
            </div>
         </div>
      </div>
   </div>
</div>