<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="${cp}/resources/css/account.css">
  </head>
  <script>
    function validateFormInputsforAccount()
    {
      var gender = $("#gender").val();
      var firstName = $("#firstName").val();
      var middleName = $("#middleName").val();
      var lastName = $("#lastName").val();
    
      $.ajax({
          url: "${cp}/editUserAccount",
          type: "POST",
          data : {"gender" : gender, "firstName" : firstName, "middleName" : middleName, "lastName" : lastName },
          asyn: true,
          cache: false,
          success : function(response)
          {
            console.log(response);         
          },
          error: function(e)
          {
            console.log(e);
          }
    
        });
        $("#main-changing-content").load("jsp/browse.jsp");
      
    
    }
  </script> 
  <body>
    <div class="suggestion-container" id = "release-container" style="margin-top: 5%;">
      <div class="suggestion-container-top">
        <h3 class="suggestion-topic" style="font-size: 4em;">Accounts:</h3>
      </div>
      <div class="line"></div>
      <div class="panel-body" style="margin-top: 2%;">
        <span id='message'></span>
        <form:form action="javascript:validateFormInputsforAccount()">
          <div class="form-group row">
            <label for="staticEmail" class="col-md-4 col-form-label">Gender</label>
            <div class="col-md-5">
              <input type="text"  class="form-control" id="gender" name="gedner" value="${user.gender}"/>
            </div>
          </div>
          <div class="form-group row">
            <label for="confirmedPassowrd" class="col-md-4 col-form-label">First Name</label>
            <div class="col-md-5">
              <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}"/>                
            </div>
          </div>
          <div class="form-group row">
            <label for="confirmedPassowrd" class="col-md-4 col-form-label">Middle Name</label>
            <div class="col-md-5">
              <input type="text" class="form-control" id="middleName" name="middleName" value="${user.middleName}"/>                
            </div>
          </div>
          <div class="form-group row">
            <label for="confirmedPassowrd" class="col-md-4 col-form-label">Last Name</label>
            <div class="col-md-5">
              <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}"/>                
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