<!--  Container for DISCOVER -->
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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

<div class="suggestion-container" id = "release-container" style="margin-top: 10%;">
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic" style="font-size: 4em;">Artists</h3>
  </div>
  <div class="line"></div>

  <!-- <div id = "filter_text" style="margin-top: 3%;">
    <input type="search" id="filter_keyword" name="q" onkeyup="filterAlbum()" placeholder="Filter">
  </div> -->
  <div class="input-group" id="filter_container">
    <span class="input-group-addon" id="search_span">
        <i class="fa fa-search" aria-hidden="true"></i>
    </span>
  <input type="search" id="filter_keyword" name="q" onkeyup="filterArtist()" placeholder="Filter">

  </div>

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

      <div id ="top_content">
        <p style="display:inline;font-size:18px;">Sorted by</p>
        <select id = "sorted_by_keyword" style="display:inline; font-size:20px;">
          <option value="Name">Name</option>
          <option value="Recently Added">Recently Added</option>
        </select>
      </div>

    <div class="line"></div>
      <section class="row placeholders" style="margin-top: 3%;">
        <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">
          <img class="w3-circle" src="https://spark.adobe.com/images/landing/examples/blizzard-album-cover.jpg" width=100% height=width  alt="Generic placeholder thumbnail">
          <div class="medium-boxes-description" style="text-align:center;">
            <h6> <a href = "#"> <span id="artist_name"> Name </span> </a> </h6>
            <div class="text-muted"> 10 songs </div>
          </div>
        </div>
        <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">
          <img class="w3-circle" src="https://www.fuse.tv/image/56fe73a1e05e186b2000009b/768/512/the-boxer-rebellion-ocean-by-ocean-album-cover-full-size.jpg" width=100% height=width  alt="Generic placeholder thumbnail">
          <div class="medium-boxes-description" style="text-align:center;">
            <h6> <a href = "#"> <span id="artist_name"> Name </span> </a> </h6>
            <div class="text-muted"> 6 songs </div>
          </div>
        </div>
        <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">
          <img src="https://i.pinimg.com/736x/8d/e4/20/8de42050e671b93b1d6bad2f2764ba89--calm-graphic-design-sun-graphic.jpg" width=100% height=width class="w3-circle" alt="Generic placeholder thumbnail">
          <div class="medium-boxes-description" style="text-align:center;">
            <h6> <a href = "#"> <span id="artist_name"> Name </span> </a> </h6>
            <div class="text-muted"> 14 songs </div>
          </div>
        </div>
        <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2  placeholder medium-boxes">
          <img src="https://peopledotcom.files.wordpress.com/2017/08/taylor-swift7.jpg?w=2000&h=2013" width=100% height=width class="w3-circle" alt="Generic placeholder thumbnail">
          <div class="medium-boxes-description" style="text-align:center;">
            <h6> <a href = "#"> <span id="artist_name"> Name </span> </a> </h6>
            <div class="text-muted"> 12 songs </div>
          </div>
        </div>
        <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">
          <img src="https://www.fuse.tv/image/56fe73a1e05e186b2000009b/768/512/the-boxer-rebellion-ocean-by-ocean-album-cover-full-size.jpg" width=100% height=width class="w3-circle" alt="Generic placeholder thumbnail">
          <div class="medium-boxes-description" style="text-align:center;">
            <h6> <a href = "#"> <span id="artist_name"> Name </span> </a> </h6>
            <div class="text-muted"> 6 songs </div>
          </div>
        </div>
        <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 placeholder medium-boxes">
          <img src="https://spark.adobe.com/images/landing/examples/blizzard-album-cover.jpg" width=100% height=width class="w3-circle" alt="Generic placeholder thumbnail">
          <div class="medium-boxes-description" style="text-align:center;">
            <h6> <a href = "#"> <span id="artist_name"> Name </span> </a> </h6>
            <div class="text-muted"> 4 songs </div>
          </div>
        </div>



      </section>
  </div>
