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

