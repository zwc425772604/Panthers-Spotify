$(document).ready(function(){
	

});

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
  

var savedPwd;

function submitConfirm(){
	var verf = $("#verf").val();
	var pwd = savedPwd;
	console.log(pwd);
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
		        	if(response.localeCompare('invalid error') == 0){
		        		console.log(response); 
		        		$("#submitPwdForm").hide();
			        	$("#getConfirmationEmailForm").show();
			        	$("#password_error").text("Your verfication is wrong. Please try again.");
		        	}
		        	else{
		        		console.log(response);
		        		$("#main-changing-content").load("jsp/browse.jsp");
		        	}
		          
		        },
		        error: function(e)
		        {
		        	$("#submitPwdForm").hide();
		        	$("#getConfirmationEmailForm").show();
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
	          $("#getConfirmationEmailForm").hide();
	          savedPwd = password;
	        },
	        error: function(e)
	        {
	          $("#password_error").text("Error when changing password. Please try again.");
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

function deleteAccountForm()
{
	$('#deleteAccountDialog').dialog({
        //autoOpen: true,
        height: 250,
        width: 450,
        modal: true,
        resizable: true,
        dialogClass: 'no-close'
   });
}

$(document).on("submit","#deleteAccountForm",function(){
	event.preventDefault();
	$.ajax({
	    url: "${cp}/../deleteUserAccount",
	    type: "POST",
	    asyn: false,
	    cache: false,
	    success : function(response)
	    {
	    	window.location.href='';
	         	
	    },
	    error: function(e)
	    {
	      console.log(e);
	    }
      });
});

