<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="${cp}/resources/css/account.css">
    <script src="${cp}/resources/js/account.js" /></script>
  </head>
  <body>
    <div class="suggestion-container" id="release-container" style="margin-top: 5%;">
      <div class="suggestion-container-top">
        <h3 class="suggestion-topic" style="font-size: 4em;">Change Password:</h3>
      </div>
      <div class="line"></div>
      <div class="panel-body" style="margin-top: 2%;">
        <span id='message'></span>
        <div id="getConfirmationEmailForm">
	        <form:form action="javascript:validateFormInputs()">
	          <div class="form-group row">
	            <label for="staticEmail" class="col-md-4 col-form-label">Password</label>
	            <div class="col-md-5">
	              <input type="text" class="form-control" id="password"
	                name="password" />
	            </div>
	          </div>
	          <div class="form-group row">
	            <label for="confirmedPassowrd" class="col-md-4 col-form-label">Confirm
	            Password</label>
	            <div class="col-md-5">
	              <input type="text" class="form-control" id="confirm_password"
	                name="confirm_password" />
	            </div>
	          </div>
	          <p class="input_error_message" id="password_error"></p>
	          <div class="form-group row">
	            <div class="col-md-4" style="margin-top: 2%;">
	              <input type="submit" class="form-control" value="Get Confirmation Email">
	            </div>
	          </div>
	        </form:form>
        </div>
        <div id="submitPwdForm" style="display:none;">
          <form:form action="javascript:submitConfirm()">
          <div class="form-group row">
            <label for="staticEmail" class="col-md-4 col-form-label">Verification</label>
            <div class="col-md-5">
              <input type="text" class="form-control" id="verf"
                name="verf" />
            </div>
          </div>
          <span id="verf-error"></span>
          <p class="input_error_message" id="password_error"></p>
          <div class="form-group row">
            <div class="col-md-4" style="margin-top: 2%;">
              <input type="submit" class="form-control" value="Submit">
            </div>
          </div>
        </form:form>
        </div>
        <!-- end of form -->
        <form:form action="deleteUserAccount" method="POST">
          <div class="form-group row">
            <div class="col-md-4" style="margin-top: 2%;">
              <input type="submit" class="form-control" value="Delete Account"
                name="deleteUser">
            </div>
          </div>
        </form:form>
      </div>
      <!-- end of panel body -->
    </div>
    </div>
  </body>
</html>