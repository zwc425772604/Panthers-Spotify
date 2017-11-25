<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<script src="${cp}/resources/js/search.js" /></script>

<div class="suggestion-container scrolled-main" id = "charts-container">
  <div class="suggestion-container-top">
     <h6 class="suggestion-topic" style="font-size: 2em;" id="search-keyword">Searched Results for: </h6>
  </div>
  <div id="results">
	  <div class="suggestion-container">
	    <div id="search-songs" class="col-sm-10">
	    <h6 class="suggestion-topic" style="font-size: 1.5em;" id="search-keyword">Song: </h6>
	      <div class="table-responsive">
	        <table class="table" id="song-search-table">
	          <thead>
	            <tr>
	              <th></th>
	              <!-- play/pause button -->
	              <th></th>
	              <!-- add/remove button -->
	              <th>TITLE</th>
	              <th>ALBUM</th>
	              <th>ARTIST</th>
	              <th>
	                <p> <i class="fa fa-calendar" aria-hidden="true"></i> </p>
	              </th>
	              <th> </th>
	              <!-- more button -->
	              <th>
	                <p> <i class="fa fa-clock-o" aria-hidden="true"></i> </p>
	              </th>
	            </tr>
	          </thead>
	          <tbody>
	          </tbody>
	        </table>
	      </div>
	    </div>
	  </div>
	  <div class="suggestion-container">
	    <div id="search-songs" class="col-sm-10">
	      <section class="row placeholders" id = "infoContainer">
	      </section>
	    </div>
	  </div>
  </div>
</div>