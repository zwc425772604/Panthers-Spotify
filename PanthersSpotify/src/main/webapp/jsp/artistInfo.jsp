<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Artist Page - Panthers Spotify</title>
<script src="${cp}/resources/js/artist.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<div class="suggestion-container" id = "release-container" style="margin-top: 3%;">
	<div class="suggestion-container-top">
		<div class="user_image_box" style="width: 20%">
			<object type="image/png" data="http://weclipart.com/gimg/0A3C841B9FA4F2C6/13099629981030824019profile.svg.hi.png"  width=100% height=width>
        		<img src="" class="img-rounded" alt="Generic placeholder thumbnail">
      		</object>
      	</div>
      	<div class="userInfoBoxes" style="width:70%">
      		<div id ="userInfo" style="margin-top: 4%; margin-left:5%;">
      			<h5> Artist Page </h5>
          		<p style="font-size: 1.8em;"><c:out value="${selectedArtist.uname}"></c:out>  </p>          		
          		<div class="col-md-5" style="display:inline;">		            		            
		        </div>
      		</div>
      	</div>
	</div>
</div>