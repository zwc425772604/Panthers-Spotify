
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Panthers Spotify Homepage</title>
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
    <link rel="stylesheet" href="${cp}/resources/css/home.css">
  </head>
  <body>
    <div class="container-fluid" style="margin-top:5%;">
      <div class="row">
        <div class="col-sm-4 col-md-4"></div>
        <div class="col-sm-4 col-md-4">
          <div id="bannerimage" style="background-image: url(https://pro.keepvid.com/images/en/spotify/spotify-logo2.jpg); no repeat"></div>
          <div id = "loginForm">
            <!--                <div class="wrapper" style="margin-top:3%;">
              <button type="button" class="btn formButton" id="facebookButton">LOG IN WITH FACEBOOK</button>
              </div>
              <br>
              <p style="text-align:center; color: gray;font-size:1.5em;"> OR </p>-->
            <br>
            <div>
              <p id="error_message" style="color:#ff4d4d; font-size:2em; "> ${error_message}</p>
            </div>
            <form:form class="w3-container" method="POST" action="main">
              <div class="w3-section">
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="email" name="email" id = "username_login" required>
                <input class="w3-input w3-border" type="password" placeholder="Password" name="password" id = "user_password" required>
                <input class="w3-check w3-margin-top" type="checkbox" checked="checked"> <span style="color: white">Remember me </span>
                <button class="btn formButton" id ="loginButton" type="submit">LOG IN</button>
              </div>
            </form:form>
          </div>
          <center>
            <div id="bottomLinks" style="margin-top:10%;">
              <form:form action="signup" method="GET">
                <button class="btn formButton" id="signButton" type="submit">Sign Up</button>
              </form:form>
              <!--                <p> <a href="signup"> SIGN UP </a></p>-->
              <p> <a href="" target="_self"> RESET PASSWORD </a></p>
              <p> <a href="" target="_self"> SETTINGS </a></p>
            </div>
          </center>
        </div>
      </div>
    </div>
  </body>
</html>
