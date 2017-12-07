<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="${cp}/resources/css/account.css">
    <script src="${cp}/resources/js/userAccount.js" /></script>  
  </head>
  <body>
    <div class="suggestion-container" id = "release-container" style="margin-top: 5%;">
      <div class="suggestion-container-top">
        <h3 class="suggestion-topic" style="font-size: 4em;">Accounts:</h3>
      </div>
      <div class="line"></div>
      <div class="panel-body" style="margin-top: 2%;">
        <span id='message'></span>
        <form:form action="editUserAccount"  method="POST" enctype="multipart/form-data">
          <div class="form-group row">
            <label for="staticEmail" class="col-md-4 col-form-label">Gender</label>
            <div class="col-md-5">
              <input type="text"  class="form-control" id="gender" name="gender" value="${user.gender}"/>
            </div>
          </div>
          <div class="form-group row">
            <label for="confirmedPassowrd" class="col-md-4 col-form-label">First Name</label>
            <div class="col-md-5">
              <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}"/>                
            </div>
          </div>
          <div class="form-group row">
            <label for="confirmedPassowrd" class="col-md-4 col-form-label">Last Name</label>
            <div class="col-md-5">
              <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}"/>                
            </div>
          </div>
          <div class="form-group row" id="dialog-image-box">      
		        <label for="confirmedPassowrd" class="col-md-4 col-form-label">Image</label>
		        <div class="col-md-5" style="margin-left: 1%;">
		          <!--<c:choose>
		           <c:when test="${not empty user.photoUrl}">
		             <img id="user-img" width=100% height=width src="${cp}/resources/data${user.photoUrl}" style="margin-left: 1em;" class="img-rounded" alt="Generic placeholder thumbnail">
		           </c:when>
		           <c:otherwise>
		             <img id="user-img" width=100% height=width  src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg"  class="img-rounded" alt="Generic placeholder thumbnail">
		           </c:otherwise>
		          </c:choose>     		-->          
		          <input class="w3-input" type="file" name="file" id="browse" style="border-bottom: 0 !important;" accept="image/*">
		          <div id="preview" style="margin-left: 0.5em">
		            <c:choose>
			           <c:when test="${not empty user.photoUrl}">
			             <img id="user-img" width=100% height=width src="${cp}/resources/data${user.photoUrl}" style="margin-left: 1em;" class="img-rounded" alt="Generic placeholder thumbnail">
			           </c:when>
			           <c:otherwise>
			             
			           </c:otherwise>
			        </c:choose> 
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
    </div>
  </body>
</html>