<!--  Container for DISCOVER -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cp"
	value="${pageContext.request.servletContext.contextPath}"
	scope="request" />
<script src="${cp}/resources/js/album.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="${cp}/resources/css/album.css">
<div class="album-top" id="release-container">
	<div class="suggestion-container-top">
		<h3 class="suggestion-topic" style="font-size: 4em;">All Albums</h3>
	</div>
	<div class="line"></div>
	<!-- filter input div block -->
	<!--<div class="input-group" id="filter_container">
		<span class="input-group-addon" id="search_span"> <i
			class="fa fa-search" aria-hidden="true"></i>
		</span> <input type="search" id="filter_keyword" name="q"
			onkeyup="filterAlbum()" placeholder="Filter">
	</div>-->
</div>
<script>
	//filter by texts entered
	function filterAlbum() {

	}
	//sort by keyword handler
	$('#sorted_by_keyword').on('change', function() {
		//alert(this.value);
	});
</script>
<!--  Container for Album -->
<div class="suggestion-container" id="charts-container">
	<div id="top_content">
		<p style="display: inline; font-size: 18px; margin-right:2%;">Sorted by</p>
		<select id="sorted_by_keyword" class="styled-select black rounded"
			style="display: inline; font-size: 18px;">
			<option id="sort_by" value=""></option>
			<option id="sort_by" value="Title">Title           </option>
		</select>
	</div>
	<!-- Saved albums section -->
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
						<h6 style="padding-top: 100%; text-align: center;">
							<a href="#"><span id="album-name">${album.aname}</span></a> <span
								class="album-ID" style="display: none;">${album.aid}</span>
						</h6>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</section>
</div>
