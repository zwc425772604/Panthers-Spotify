<!DOCTYPE html>
<html lang="en">
<head>
  <title>Sign Up - Panther Spotify</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link rel="stylesheet" href="custom.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="js/main.js"></script>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <link rel="stylesheet" href="home.css">
  <script>
    function user_login()
    {
      //need to validate the username and password in later stage
       window.open("main.html", "_self");
    }
  </script>
</head>
<body>

  <div class="container-fluid" style="margin-top:5%;">
    <div class="row">
      <div class="col-sm-4 col-md-4">
      </div>
      <div class="col-sm-4 col-md-4">
          <div id="bannerimage" style="background-image: url(https://pro.keepvid.com/images/en/spotify/spotify-logo2.jpg); no repeat"></div>

          <div id = "loginForm">
            <br>

            <form class="w3-container" action="javascript:user_login()">
              <div class="w3-section">
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Username or email" name="usrname" id = "username_login" required>
                <p style="color:red"></p>
                <input class="w3-input w3-border" type="password" placeholder="Password" name="psw" id = "user_password" required>
                <p style="color:red"></p>
                <input class="w3-input w3-border" type="password" placeholder="Confirm Password" name="psw" id = "user_password" required>
                <p style="color:red"></p>

                <button class="btn formButton" id ="loginButton" type="submit">Sign Up</button>
              </div>
            </form>
          </div>

          <center>
            <p style="color: white;"> Already have an account? <a href= "home.html" style="color: green;"> Log in </a> </p>
        </center>
      </div>
    </div>
  </div>




</body>

</html>
