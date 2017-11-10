<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Royalty Payment - Panther Spotify</title>
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
        <div class="col-md-4 col-md-4"></div>
        <div class="col-md-4 col-md-4">
          <div id="bannerimage" style="background-image: url(https://pro.keepvid.com/images/en/spotify/spotify-logo2.jpg); no repeat"></div>
          <div class="panel-group" style= "margin-top: 5%;">
            <div class="panel panel-default">
              <div class="panel-heading">
                <div class="form-group row">
                  <label for="staticEmail" class="col-md-4 col-form-label">Payment Form:</label>
                  <div class="col-md-offset-2 col-md-6">
                    <i class="fa fa-cc-mastercard" style="font-size:3em"></i>
                    <i class="fa fa-cc-visa" style="font-size:3em"></i>
                    <i class="fa fa-cc-discover" style="font-size:3em;"></i>
                  </div>
                </div>
              </div>
              <div class="panel-body">
                <form action = "">
                  <div class="form-group row">
                    <label for="staticEmail" class="col-md-4 col-form-label">Recipient Name:</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control" id="staticEmail" placeholder="enter the name of the artist"required>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputPassword" class="col-md-4 col-form-label">Recipient Email Address:</label>
                    <div class="col-md-8">
                      <input type="email" class="form-control" id="inputPassword" placeholder="enter the recipient's email address" required>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputPassword" class="col-md-4 col-form-label">Amount($):</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control" id="inputPassword" placeholder="enter the amount paid to the artist" required>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputPassword" class="col-md-4 col-form-label">Card Number:</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control" id="inputPassword" placeholder="enter valid card number" required>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputPassword" class="col-md-8 col-form-label">Expiration Date:</label>
                    <label for="inputPassword" class="col-md-4 col-form-label">CV Code:</label>
                    <div class="col-md-8">
                      <input type="text" class="form-control" id="inputPassword" placeholder="MM/YY" required>
                    </div>
                    <div class="col-md-4">
                      <input type="text" class="form-control" id="inputPassword" placeholder="CVC" required>
                    </div>
                  </div>
                  <div class="form-group row">
                    <div class="col-md-offset-8 col-md-4">
                      <input type="submit" class="form-control" id="inputPassword" value="Confirm Payment">
                    </div>
                  </div>
                </form>
                <!-- end of form -->
              </div>
              <!-- end of panel body -->
            </div>
          </div>
          <!-- end of panel-group -->
        </div>
        <!-- end of col-md-4 div-->
      </div>
    </div>
    </div>
  </body>
</html>
