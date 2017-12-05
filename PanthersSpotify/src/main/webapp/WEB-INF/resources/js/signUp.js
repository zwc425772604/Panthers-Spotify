function user_login()
{
    //need to validate the username and password in later stage
 window.open("main.html", "_self");
}
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
    var email = $("#email").val();
var email1 = $("#reenteredEmail").val();
var password = $("#password").val();
var password1 = $("#confirmedPassword").val();
var username = $("#username").val();
var gender = $("input[name='gender']:checked").val();
var dob = new Date($("#dateOfBirth").val());
var firstName = $("#firstName").val();
var middleName = $("#middleName").val();
var lastName= $("#lastName").val();
var correct_email = validateEmailFormat(email);
var password_matched = checkPassword(password,password1);
var email_matched = checkEmailAddress(email,email1);
if (password_matched == 0 && correct_email && email_matched == 0)
{
  //ajax call to sign up
  $("#password_error").text("");
  $("#email_error").text("");
  $.ajax({
    url: "${cp}/../userSignUp",
    type: "POST",
    data : {"username" : username, "email" : email, "password" : password, "dob" : dob,
            "firstName" : firstName, "middleName" : middleName, "lastName" : lastName, "gender" : gender},
    asyn: false,
    cache: false,
    success : function(response)
    {
      console.log(response);
      if (response.localeCompare("failed")==0)
    	  {
    	      $("#signup_message").empty();
          $("#signup_message").css("color","red");
          $("#signup_message").text("This email is already registered");
    	  }
      else
      {
    	    $("#signup_message").empty();
        $("#signup_message").css("color","green");
        $("#signup_message").text(response);
        window.location.href = "http://localhost:8080/PanthersSpotify/"; 
        
      }
     
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
  
  }