function validateFormInputsforAccount()
    {
      var gender = $("#gender").val();
      var firstName = $("#firstName").val();
      var lastName = $("#lastName").val();
    
      $.ajax({
          url: "${cp}/../editUserAccount",
          type: "POST",
          data : {"gender" : gender, "firstName" : firstName, "lastName" : lastName },
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