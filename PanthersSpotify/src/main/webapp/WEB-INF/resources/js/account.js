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
  
function submitConfirm(){
	var verf = $("#verf").val();
	var pwd = $("#password").val();
	if(!verf)
	{
		$("#verf-error").empty();
		$("#verf-error").append("Verf cannot be black");
	}
	else{
		$.ajax({
		    url: "${cp}/../editUserPassword",
		    type: "POST",
		        asyn: true,
		        cache: false,
		        data: {"token": verf, "password":pwd},
		        success : function(response)
		        {		          
		          $("#main-changing-content").load("jsp/browse.jsp");
		        },
		        error: function(e)
		        {
		          console.log(e);
		        }
		  
		      });
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
	    url: "${cp}/../sendToken",
	    type: "POST",
	        asyn: true,
	        cache: false,
	        success : function(response)
	        {
	          $("#submitPwdForm").show();
	        },
	        error: function(e)
	        {
	          console.log(e);
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

