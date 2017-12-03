
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<script src="${cp}/resources/js/artist.js"></script>
<link rel="stylesheet" href="${cp}/resources/css/artist.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<div class="artist-top" id = "release-container" >
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic" style="font-size: 4em;">All Artists</h3>
  </div>
  <div class="line"></div>
  <!-- <div id = "filter_text" style="margin-top: 3%;">
    <input type="search" id="filter_keyword" name="q" onkeyup="filterAlbum()" placeholder="Filter">
    </div> -->
</div>
<script>
  function filterArtist()
  {
  
  }
  //sort by keyword handler
  $('#sorted_by_keyword').on('change',function(){
    //alert(this.value);
  })
</script>
<!--Container for release 1-->
<!--  Container for DISCOVER -->
<div class="suggestion-container" id = "charts-container">

  <section class="row placeholders" style="margin-top: 3%;" id="artist-page">
  </section>
</div>
