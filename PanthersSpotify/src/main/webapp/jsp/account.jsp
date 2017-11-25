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
      function checkPassword(password, password_confirm)
      {
        if (password.localeCompare(password_confirm) == 0)
        {
          return 0;
        }
        else {
          return -1;
        }
      }
      
      function validateFormInputs()
      {
        var password = $("#password").val();
        var password1 = $("#confirm_password").val();
        var password_matched = checkPassword(password,password1);
        if (password_matched == 0 )
        {
          //ajax call to sign up
          $("#password_error").text("");
          $("#email_error").text("");
          $.ajax({
            url: "${cp}/editUserPassword",
            type: "POST",
            data : {"password" : password },
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
        else
        {
      
          if (password_matched == 0)
          {
            $("#password_error").text("");
          }
          else
          {
            $("#password_error").text("Passwords do not match");
          }
          
      
        }
      
      }
      
   </script>
   <body>
      <div class="suggestion-container" id = "release-container" style="margin-top: 5%;">
         <div class="suggestion-container-top">
            <h3 class="suggestion-topic" style="font-size: 4em;">Change Password:</h3>
         </div>
         <div class="line"></div>
         <div class="panel-body" style="margin-top: 2%;">
            <span id='message'></span>
            <form:form action="javascript:validateFormInputs()">
               <div class="form-group row">
                  <label for="staticEmail" class="col-md-4 col-form-label">Password</label>
                  <div class="col-md-5">
                     <input type="text"  class="form-control" id="password" name="password"/>
                  </div>
               </div>
               <div class="form-group row">
                  <label for="confirmedPassowrd" class="col-md-4 col-form-label">Confirm Password</label>
                  <div class="col-md-5">
                     <input type="text" class="form-control" id="confirm_password" name="confirm_password"/>                
                  </div>
               </div>
               <p class = "input_error_message" id="password_error"></p>
               <div class="form-group row">
                  <div class="col-md-4" style="margin-top: 2%;">
                     <input type="submit" class="form-control" value="Submit">
                  </div>
               </div>
            </form:form>
            <!-- end of form -->
            <form:form action="deleteUserAccount" method="POST">
               <div class="form-group row">
                  <div class="col-md-4" style="margin-top: 2%;">
                     <input type="submit" class="form-control" value="Delete Account" name="deleteUser">
                  </div>
               </div>
            </form:form>
         </div>
         <!-- end of panel body -->
      </div>
      </div>
   </body>
</html>