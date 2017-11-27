<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<head>
   <title>Sign Up - Panther Spotify</title>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <link rel="stylesheet" href="${cp}/resources/css/custom.css">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script src="${cp}/resources/js/main.js"></script>
   <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
   <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
   <link rel="stylesheet" href="${cp}/resources/css/adminAddUser.css">
   <script>
      function validateEmailFormat(email)
      {
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
      }
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
      function checkEmailAddress(email, email_confirm)
      {
      	if (email.localeCompare(email_confirm) == 0)
          {
            return 0;
          }
          else {
            return -1;
          }
      }
      
      function validateFormInputs()
      {
        var email = $("#email_login").val();
        var email1 = $("#re_email_login").val();
        var password = $("#user_password").val();
        var password1 = $("#confirm_password").val();
        var username = $("#username_login").val();
        var gender = $("input[name='gender']:checked").val();
        var dob = new Date($("#date_of_birth").val());
        var first_name = $("#user_first_name").val();
        var last_name= $("#user_last_name").val();
        var correct_email = validateEmailFormat(email);
        var password_matched = checkPassword(password,password1);
        var email_matched = checkPassword(email,email1);
        if (password_matched == 0 && correct_email && email_matched == 0)
        {
          //ajax call to sign up
          $("#password_error").text("");
          $("#email_error").text("");
          $.ajax({
            url: "${cp}/userSignUp",
            type: "POST",
            data : {"username" : username, "email" : email, "password" : password, "dob" : dob,
                    "first_name" : first_name, "last_name" : last_name, "gender" : gender},
            asyn: true,
            cache: false,
            success : function(response)
            {
              console.log(response);
              $("#signup_message").empty();
              $("#signup_message").css("color","green");
              $("#signup_message").text(response);
            },
            error: function(e)
            {
              console.log(e);
              $("#signup_message").empty();
              $("#signup_message").css("color","red");
              $("#signup_message").text("duplicate username");
            }
      
          });
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
          if (correct_email)
          {
            $("#email_error").text("");
          }
          else
          {
            $("#email_error").text("Incorrect email");
          }
          if (email_matched == 0)
          {
          	$("#email_error").text("");
         	}
          else
          {
          	$("#email_error").text("Emails do not match");
          }
      
        }
        $("#main-changing-content").load("jsp/browse.jsp");
      }
   </script>
</head>
<body>
   <div class="suggestion-container" id = "release-container" style="margin-top: 5%;">
      <div class="suggestion-container-top">
         <h3 class="suggestion-topic" style="font-size: 3em;">Add New User</h3>
      </div>
      <div class="row">
         <div class="col-sm-8 col-md-8">
            <div id ="message" style="text-align:center">
               <p style="color:buttonface; font-size: 2em;" id = "signup_message">
                  <c:out value="${signUpMessage}"></c:out>
               </p>
            </div>
            <div id = "loginForm">
               <form:form class="w3-container" action="javascript:validateFormInputs()">
                  <div class="w3-section">
                     <label> Username: </label>
                     <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="maximum of 20 characters)" name="username" id = "username_login" required>
                     <p class = "input_error_message"  id = "username_error"></p>
                     <label>New Email address: </label>
                     <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Email" name="email" id = "email_login" required>
                     <label> Re-enter Email address: </label>
                     <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Email" name="re-email" id = "re_email_login" required>
                     <p class = "input_error_message" id = "email_error"></p>
                     <label> Password: </label>
                     <input class="w3-input w3-border" type="password" placeholder="maximum of 20 characters" name="password" id = "user_password" required>
                     <p class = "input_error_message"></p>
                     <label> Re-enter your password: </label>
                     <input class="w3-input w3-border" type="password" placeholder="Confirm Password" name="confirm_password" id = "confirm_password" required>
                     <p class = "input_error_message" id="password_error"></p>
                     <label> First name: </label>
                     <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="First name" name="first_name" id = "user_first_name" required>
                     <p class = "input_error_message"></p>
                     <label> Last name: </label>
                     <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Last name" name="last_name" id = "user_last_name" required>
                     <p class = "input_error_message"></p>
                     <label> Date of Birth: </label>
                     <input class="w3-input w3-border" type="date" placeholder="Date of Birth (yyyy-mm-dd)" name="bob" id="date_of_birth" required>
                     <p class = "input_error_message"></p>
                     <div class="gender_radiobuttons">
                        <input class="w3-input w3-border" type="radio" name="gender" value="M"> <label class="gender_role">Male</label>
                        <input class="w3-input w3-border" type="radio" name="gender" value="F"> <label class="gender_role">Female</label>
                     </div>
                     <br>
                     <button class="btn formButton" id ="loginButton" type="submit">Sign Up</button>
                  </div>
               </form:form>
            </div>
         </div>
      </div>
   </div>
</body>