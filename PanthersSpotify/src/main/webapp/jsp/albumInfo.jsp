<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Album Page - Panthers Spotify</title>
   <link rel="stylesheet" href="${cp}/resources/css/songsFormat.css">  
   <link rel="stylesheet" href="${cp}/resources/css/albumInfo.css">  
   <script src="${cp}/resources/js/albumInfo.js"></script>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
   <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
   <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<div id="album-page-main">
   <div id="top-background">
     <div id="top-container">
	   <div class="suggestion-container-top">
	      <div class="album-image-box" style="width: 20%">
	      </div>
	      <div class="albumInfoBoxes" style="width:70%">
	         <div id ="albumInfo" style="margin-top: 4%; margin-left:5%;">
	            <h5> Album Page </h5>
	            <p>
	               <div id="album-name-place"></div>
	               <span id="album-hidden-id" style="display: none;"></span>
	            </p>
	            <p id="album-createdby">
	            	<div>Created By</div>
	            	<div id="album-artists">	            		
	            	</div>
	            </p>
	            <div class="col-md-5" style="display:inline;">		            		            
	            </div>
	         </div>
	      </div>
	   </div>
	 </div>
   </div>
   <div class="row">
   <div id="playlist-song-collection" style="width: 65%; ">
      <div class="table-responsive">
        <table class="table" id="song-table">
          <thead>
            <tr>
              <th id="th-play-button"></th>
              <!-- play/pause button -->
              <th id="th-song-title">
                <p> TITLE </p>
              </th>
              <th id="th-date">
                <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
              </th>
              <th></th>
            </tr>
          </thead>
          <tbody>
          
          </tbody>
        </table>
      </div>
    </div>
    <div  style="width: 20%; margin-left: 4%; margin-top: 0.9%;">
      <div id="related-album-header" style="border-bottom: 1px solid white; margin-bottom: 2%; margin-top: 2%;
                                font-size: 1.2em; padding-left: 0.2em; font-weight:bolder; ">
        Description
      </div>
      <div id="album-info" style="padding: 3%; margin-bottom: 2em;"></div>
      <div id="related-album-header" style="border-bottom: 1px solid white; margin-bottom: 2%;
                                font-size: 1.2em; padding-left: 0.2em; font-weight:bolder;">
        Related Album
      </div>
      <div id="related-albums" style="padding: 3%;"></div>
    </div>
   </div>
</div>