<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="${cp}/resources/css/userSetting.css">
    <script src="${cp}/resources/js/userSetting.js" /></script>  
  </head>
  <body>
    <div class="suggestion-container" id = "release-container" style="margin-top: 5%;">
      <div class="suggestion-container-top">
        <h3 class="suggestion-topic" style="font-size: 4em;">Setting:</h3>
      </div>
      <div class="line"></div>
      <div class="panel-body" style="margin-top: 2%;">
        <span id='message'></span>
        <form:form action="">
          <div class="form-group row">
            <label for="staticEmail" class="col-md-4 col-form-label">Language</label>
            <div class="col-md-5">
              <select>
				  <option value="English">English</option>
				  <option value="Spanish">Spanish</option>
				  <option value="Chinese">Chinese</option>
				</select>
            </div>
          </div>
        </form:form>
        <!-- end of form -->
      </div>
      <!-- end of panel body -->
    </div>
  </body>
</html>