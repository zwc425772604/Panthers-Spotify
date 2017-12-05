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
        	  $("#paymentForm").hide();
        	  $("#upgradeAccountMessage").html("Successfully upgrade to premium");
        	  $("#paymentGoHome").show();
        	  }
          else{$("#upgradeAccountMessage").html("Failed to upgrade to premium"); }
         
        },
        error: function(e)
        {
        	$("#upgradeAccountMessage").html("Failed to upgrade to premium");
          console.log(e);
        }
      });
}

function paymentFinished(){
	location.reload();
	$("main-changing-content").load("jsp/browse.jsp");
}