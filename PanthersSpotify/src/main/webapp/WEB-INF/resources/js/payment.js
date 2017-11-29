function submitUpgradeForm()
{
	var cardNum = $("#cardNumber").val();
	var cvv = $("#cvv").val();
	var holderName = $("#holderName").val();
	var expirationDate = $("#expirationDate").val();
	
	$.ajax({
        url: "${cp}/../upgrade",
        type: "POST",
        data: {"cardNum" : cardNum, "holderName" : holderName, "cvv" : cvv, "expirationDate" : expirationDate},
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