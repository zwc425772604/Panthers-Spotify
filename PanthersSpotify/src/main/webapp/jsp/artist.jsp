<!--  Container for DISCOVER -->
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<script src="${cp}/resources/js/artist.js"></script>
<link rel="stylesheet" href="${cp}/resources/css/artist.css">
<style type="text/css">
  #filter_keyword
  {
  width: 90%;
  font-size: 16px;
  }
  /* for the close button in input type search */
  input[type="search"]::-webkit-search-cancel-button
  {
  -webkit-appearance: searchfield-cancel-button;
  }
  #search_span
  {
  background-color: #343a40;
  color: white;
  border: none;
  color: #cccccc;
  }
  #filter_keyword
  {
  background-color: #343a40;
  border-color: inherit;
  -webkit-box-shadow: none;
  box-shadow: none;border:none;
  color: #cccccc;
  }
  #filter_container
  {
  margin-top: 3%;
  }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
  $(document).ready(function(){
  
  });
  //style for the filter container
  $( "#filter_container" )
  .mouseover(function() {
    $('#filter_keyword').css('color', 'white');
    $('#search_span').css('color','white');
  })
  .mouseout(function(){
    $('#filter_keyword').css('color','#cccccc');
    $('#search_span').css('color','#cccccc');
  });
  //need to add the close icon at the right in input
  $( "#filter_keyword" )
  .focus(function(){
    $('#filter_keyword').css('background-color','gray');
    $('#search_span').css('background-color','gray');
  });
  $("#filter_keyword").blur(function(){
    $('#filter_keyword').css('background-color','#343a40');
    $('#search_span').css('background-color','#343a40');
      });
  
</script>
<div class="artist-top" id = "release-container" >
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic" style="font-size: 4em;">Artists</h3>
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
