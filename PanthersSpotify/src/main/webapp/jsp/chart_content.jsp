<%-- 
  Document   : browse
  Created on : Oct 19, 2017, 8:55:35 PM
  Author     : Weichao ZHao
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<script>
  $(document).ready(function(){
    //hide all other containers in <div id= 'middle-content'> beside except overview_container
    $("#overview_container").siblings().hide();
  });
</script>
<div id="bannerimage" style="background-image: url(http://ep.id-t.com/dam/asset/pictures/website-content/newsitems/banner-sensation-spotify-playlist-.jpeg?uuid=6j3c6dvv5ncwviv4yuwizw7tfa);"></div>
<!--NavBar-->
<nav class="navbar navbar-expand-md bg-dark navbar-dark"
  id="bootstrap-overrides-navbar">
  <ul class="navbar-nav mr-auto tab" id="navbar-ul">
    <li class="nav-item">
      <a class="nav-link tablinks" href="javascript:displayContainer('overview_container')">OVERVIEW </a>
    </li>
    <li class="nav-item">
      <a class="nav-link tablinks" href="javascript:displayContainer('charts_container')">CHARTS</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="javascript:displayContainer('genre_container')">GENRES &amp MOODS</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="javascript:displayContainer('new_releases_container')">NEW RELEASES</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="javascript:displayContainer('discover_container')">DISCOVER</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="javascript:displayContainer('concerts_container')">CONCERTS</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="javascript:void(0)">MORE</a>
    </li>
  </ul>
</nav>
<div id= "middle-container">
  <div id = "overview_container" w3-include-html="jsp/overview.jsp"></div>
  <div id = "charts_container" w3-include-html="jsp/charts.jsp"></div>
  <div id = "genre_container" w3-include-html="jsp/genre.jsp"></div>
  <div id = "new_releases_container" w3-include-html="jsp/NewsRelease.jsp"></div>
  <div id = "discover_container" w3-include-html="jsp/discover.jsp"></div>
  <div id = "concerts_container" w3-include-html="jsp/charts.jsp"></div>
  <!-- <div id = "middle-content"></div> -->
</div>
<script>
  w3.includeHTML();
  function displayContainer(name)
  {
    var n = "#" + name;
    $(n).siblings().hide();
    $(n).show();
  }
</script>