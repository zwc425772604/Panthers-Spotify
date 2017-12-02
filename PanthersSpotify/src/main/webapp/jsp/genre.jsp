<%-- 
  Document   : genre
  Created on : Oct 19, 2017, 8:54:31 PM
  Author     : Weichao ZHao
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
    <script src="${cp}/resources/js/genre.js" /></script>

<!DOCTYPE html>
<!--  Container for DISCOVER -->
<style>
.genre-box{
	margin-top: 3%;
	margin-bottom: 3%;
	margin-right: 7%;
}
.centered {
    position: absolute;
    top: 50%;
    left: 70%;
    transform: translate(-50%, -50%);
    font-size: 2em;
}
</style>

<div class="suggestion-container" id = "charts-container">
  <div class="suggestion-container-top">
    <h3 class="suggestion-topic">Genres & Moods</h3>
  </div>
  <div class="line"></div>
  <section class="row placeholders" id="genre-blocks">
    
  </section>
</div>