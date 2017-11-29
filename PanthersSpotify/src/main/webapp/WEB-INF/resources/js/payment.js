function submitUpgradeForm()
{
	var cardNum = $("#cardNumber").val();
	$.ajax({
        url: "${cp}/../upgrade",
        type: "POST",
        data: {"cardNum" : cardNum},
        asyn: false,
        cache: true,
        success : function(response)
        {
          console.log(response);
          if (response.localeCompare("ok") == 0)
        	  {
        	  $("#upgradeAccountMessage").html("Successfully upgrade to premium");
        	  }
          else{$("#upgradeAccountMessage").html("Failed to upgrade to premium"); }
         
        },
        error: function(e)
        {

          console.log(e);
        }
      });
}