$(document).ready(function(){
	console.log("HI")
	$.ajax({
        url: "${cp}/../getNewsRelease",
        type: "POST",
        asyn: false,
        cache: true,
        success : function(response)
        {
        	
        	console.log(response);
        },
        error: function(e)
        {
          console.log(e);
        }
      });
});