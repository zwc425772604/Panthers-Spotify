<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <script src="${cp}/resources/js/userSetting.js" /></script>  
    <link rel="stylesheet" href="${cp}/resources/css/userSetting.css">
  </head>
  <body>
    <div class="suggestion-container" id = "release-container" style="margin-top: 5%;">
      <div class="suggestion-container-top">
        <h3 class="suggestion-topic" style="font-size: 4em;">Setting:</h3>
      </div>
      <div class="line"></div>
      <div class="panel-body" style="margin-top: 2%;">
        <span id='message'></span>
        <form:form action="javascript:sendUserSetting()">
          <div class="form-group row">
            <label for="staticEmail" class="col-md-4 col-form-label">Language</label>
            <div class="col-md-5">
              <select id="lang" class="styled-select black rounded">
                <c:choose>
              	  <c:when test="${user.language == 'English'}">
				  <option value="English" selected>English</option>
				  <option value="Chinese">Chinese</option>
				  </c:when>
				  <c:otherwise>
				  <option value="English" >English</option>
				  <option value="Chinese" selected>Chinese</option>
				  </c:otherwise>
				</c:choose>
				</select>
            </div>
           </div>
            <div class="form-group row">
            <label for="staticEmail" class="col-md-4 col-form-label">Set my profile to:</label>
            <div class="col-md-5">
              <div class="col-md-5">
              <select id="privacy" class="styled-select black rounded">
              	<c:choose>
              	  <c:when test="${user.isPublic}">
				  <option value="public"  selected>Public</option>
				  <option value="private">Private</option>
				  </c:when>
				  <c:otherwise>
				  <option value="public">Public</option>
				  <option value="private" selected>Private</option>
				  </c:otherwise>
				</c:choose>
			  </select>
            </div>
            </div>
           </div>
           <div class="form-group row">
            <div class="col-md-4" style="margin-top: 2%;">
              <input type="submit" class="form-control" value="Submit">
            </div>
          </div>
        </form:form>
        <!-- end of form -->
      </div>
      <!-- end of panel body -->
    </div>
  </body>
</html>