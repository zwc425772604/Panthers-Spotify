<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Sign Up - Panther Spotify</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${cp}/resources/css/custom.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${cp}/resources/js/signUp.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="${cp}/resources/css/home.css">
    <script>
      
    </script>
  </head>
  <body>
    <div class="container-fluid" style="margin-top:5%;">
      <div class="row">
        <div class="col-sm-4 col-md-4">
        </div>
        <div class="col-sm-4 col-md-4">
          <div id="bannerimage" style="background-image: url(https://pro.keepvid.com/images/en/spotify/spotify-logo2.jpg); no repeat"></div>
          <br>
          <div id ="message" style="text-align:center">
            <p style="color:buttonface; font-size: 2em;" id = "signup_message">
              <c:out value="${signUpMessage}"></c:out>
            </p>
          </div>
          <div id = "loginForm">
            <form:form class="w3-container" action="javascript:validateFormInputs()">
              <div class="w3-section">
                <label> Username: </label>
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="maximum of 20 characters" name="username" id = "username" required>
                <p class = "inputErrorMessage"  id = "usernameError"></p>
                <label>New Email address: </label>
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Email" name="email" id = "email" required>
                <label> Re-enter Email address: </label>
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Email" name="reenteredEmail" id = "reenteredEmail" required>
                <p class = "inputErrorMessage" id = "emailError"></p>
                <label> Password: </label>
                <input class="w3-input w3-border" type="password" placeholder="maximum of 20 characters" name="password" id = "password" required>
                <p class = "inputErrorMessage"></p>
                <label> Re-enter your password: </label>
                <input class="w3-input w3-border" type="password" placeholder="Confirm Password" name="confirmedPassword" id = "confirmedPassword" required>
                <p class = "inputErrorMessage" id="passwordError"></p>
                <label> First name: </label>
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="First name" name="firstName" id = "firstName" required>
                <p class = "inputErrorMessage"></p>
                <label> Middle name: </label>
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Middle name(optional)" name="middleName" id = "middleName">
                <label> Last name: </label>
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Last name" name="lastName" id = "lastName" required>
                <p class = "inputErrorMessage"></p>
                <label> Date of Birth: </label>
                <input class="w3-input w3-border" type="date" placeholder="Date of Birth (mm/dd/yyyy)" name="DOB" id="dateOfBirth" required>
                <p class = "inputErrorMessage"></p>
                <div class="gender_radiobuttons">
                  <input class="w3-input w3-border" type="radio" name="gender" value="M"> <label class="gender_role">Male</label>
                  <input class="w3-input w3-border" type="radio" name="gender" value="F"> <label class="gender_role">Female</label>
                </div>
                <br>
                <button class="btn formButton" id ="loginButton" type="submit">Sign Up</button>
              </div>
            </form:form>
          </div>
          <div style="text-align: center;">
            <p style="color: white;"> Already have an account? <a href= "home.html" style="color: green;"> Log in </a> </p>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>