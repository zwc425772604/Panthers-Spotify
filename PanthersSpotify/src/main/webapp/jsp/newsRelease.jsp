<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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

